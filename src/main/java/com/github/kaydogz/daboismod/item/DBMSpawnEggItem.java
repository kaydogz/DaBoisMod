package com.github.kaydogz.daboismod.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.ArrayList;
import java.util.Map;

/**
 * Since SpawnEggItems require a EntityType but are registered before EntityTypes, this class is needed to workaround that.
 * Thanks to Cadiboo for this class.
 * #TODO Once Forge fixes this, remove this.
 *
 * @author Cadiboo
 */
public class DBMSpawnEggItem extends SpawnEggItem {

	protected static final ArrayList<DBMSpawnEggItem> UNADDED_EGGS = new ArrayList<>();
	
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;
	
	public DBMSpawnEggItem(final NonNullSupplier<EntityType<?>> entityTypeSupplier, final int primaryColorIn, final int secondaryColorIn, final Item.Properties builder) {
		super(null, primaryColorIn, secondaryColorIn, builder);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		DBMSpawnEggItem.UNADDED_EGGS.add(this);
	}
	
	public DBMSpawnEggItem(final RegistryObject<? extends EntityType<?>> entityTypeSupplier, final int primaryColorIn, final int secondaryColorIn, final Item.Properties builder) {
		super(null, primaryColorIn, secondaryColorIn, builder);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier);
		DBMSpawnEggItem.UNADDED_EGGS.add(this);
	}
	
	/**
	 * Adds all the supplier based spawn eggs to vanilla's map and registers an
	 * IDispenseItemBehavior for each of them as normal spawn eggs have one
	 * registered for each of them during {@link net.minecraft.dispenser.IDispenseItemBehavior#init()}
	 * but supplier based ones won't have had their EntityTypes created yet.
	 */
	public static void initUnaddedEggs() {
		final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "field_195987_b");
		DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior() {
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				Direction direction = source.getBlockState().get(DispenserBlock.FACING);
				EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
				entitytype.spawn(source.getWorld(), stack, null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
				stack.shrink(1);
				return stack;
			}
		};
		for (final SpawnEggItem egg : UNADDED_EGGS) {
			EGGS.put(egg.getType(null), egg);
			DispenserBlock.registerDispenseBehavior(egg, defaultDispenseItemBehavior);
			// ItemColors for each spawn egg don't need to be registered because this method is called before ItemColors is created
		}
		UNADDED_EGGS.clear();
	}
	
	@Override
	public EntityType<?> getType(CompoundNBT tag) {
		return this.entityTypeSupplier.get();
	}
}
