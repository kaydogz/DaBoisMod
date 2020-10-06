package com.github.kaydogz.daboismod.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class DBMKeyBindings {
	
	public static KeyBinding quest_menu;
	public static KeyBinding activate_magnetism;
	public static KeyBinding activate_crown;
	
	/**
	 * Registers key bindings for DaBoisMod.
	 */
	public static void registerKeyBindings() {
		final String daBoisCategory = new TranslationTextComponent("key.daboismod.categories.daBois").getFormattedText();
		DBMKeyBindings.quest_menu = registerKeyBinding("key.daboismod.questMenu", GLFW.GLFW_KEY_Z, daBoisCategory);
		DBMKeyBindings.activate_magnetism = registerKeyBinding("key.daboismod.magnetism", GLFW.GLFW_KEY_R, daBoisCategory);
		DBMKeyBindings.activate_crown = registerKeyBinding("key.daboismod.crown", GLFW.GLFW_KEY_V, daBoisCategory);
	}

	private static KeyBinding registerKeyBinding(final String translationKeyIn, final int keyCodeIn, final String categoryIn) {
		final KeyBinding keyBinding = new KeyBinding(new TranslationTextComponent(translationKeyIn).getFormattedText(), keyCodeIn, categoryIn);
		ClientRegistry.registerKeyBinding(keyBinding);
		return keyBinding;
	}
}
