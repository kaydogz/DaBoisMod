package com.github.kaydogz.daboismod.client.gui;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IPlayerCapability;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.network.DBMNetworkHandler;
import com.github.kaydogz.daboismod.network.client.CCancelQuestPacket;
import com.github.kaydogz.daboismod.network.client.CClaimQuestPacket;
import com.github.kaydogz.daboismod.quest.Quest;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.AirItem;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class QuestScreen extends Screen {
	
	protected static final ResourceLocation QUEST_BACKGROUND = DaBoisMod.modLocation("textures/gui/quest_background.png");

	private final int xSize = 248;
	private final int ySize = 166;
	private ArrayList<Quest> quests;
	private float percentage;

	public static int currentPage = 0;
	
	private Button claimButton;
	private Button cancelButton;

	private Button leftButton;
	private Button rightButton;

	public QuestScreen() {
		super(new TranslationTextComponent("gui.daboismod.quest.title"));
	}

	/**
	 * Updates the current page and quests from the client player.
	 */
	public void updateQuests() {
		LazyOptional<IPlayerCapability> playerCap = PlayerProvider.getCapabilityOf(this.minecraft.player);
		if (!playerCap.isPresent()) return;

		// Quests and Current Page
		this.quests = DaBoisMod.get(playerCap).getQuests();
		currentPage = MathHelper.clamp(currentPage, 0, this.quests.size() - 1);

		this.updatePageState();
	}

	/**
	 * Refreshes variables that vary by page. Eg. Buttons, percentages, etc.
	 */
	protected void updatePageState() {
		boolean isQuestsNotEmpty = !this.quests.isEmpty();

		// Bar Percentage
		if (isQuestsNotEmpty) {
			int quota = this.quests.get(currentPage).getQuota();
			this.percentage = quota == 0 ? 1.0F : Math.min((float) this.quests.get(currentPage).getCount() / quota, 1.0F);
		} else {
			this.percentage = 1.0F;
		}

		// Left Button
		boolean leftButtonFlag = currentPage > 0;
		this.leftButton.visible = leftButtonFlag;
		this.leftButton.active = leftButtonFlag;

		// Right Button
		boolean rightButtonFlag = currentPage < this.quests.size() - 1;
		this.rightButton.visible = rightButtonFlag;
		this.rightButton.visible = rightButtonFlag;

		// Claim and Cancel Buttons
		boolean isComplete = isQuestsNotEmpty && this.quests.get(currentPage).isComplete();
		this.claimButton.visible = isQuestsNotEmpty;
		this.claimButton.active = isComplete;
		this.cancelButton.visible = isQuestsNotEmpty;
		this.cancelButton.active = !isComplete && isQuestsNotEmpty;
	}

	protected void onLeftButtonPress(Button b) {
		currentPage = Math.max(currentPage - 1, 0);
		this.updatePageState();
	}

	protected void onRightButtonPress(Button b) {
		currentPage = Math.min(currentPage + 1, this.quests.size() - 1);
		this.updatePageState();
	}

	protected void onClaimButtonPress(Button b) {
		DBMNetworkHandler.sendToServer(new CClaimQuestPacket(this.quests.get(currentPage)));
	}

	protected void onCancelButtonPress(Button b) {
		DBMNetworkHandler.sendToServer(new CCancelQuestPacket(this.quests.get(currentPage)));
	}

	@Override
	protected void init() {
		super.init();

		// Left Arrow Button
		addButton(this.leftButton = new Button((this.width - this.xSize) / 2 - 30, this.height / 2 - 10, 20, 20, new StringTextComponent("<"), this::onLeftButtonPress));

		// Right Arrow Button
		addButton(this.rightButton = new Button((this.width + this.xSize) / 2 + 10, this.height / 2 - 10, 20, 20, new StringTextComponent("<"), this::onRightButtonPress));

		// Claim Button
		addButton(this.claimButton = new Button(this.width / 2 - 90, (this.height - this.ySize) / 2 + 135, 80, 20, new TranslationTextComponent("gui.daboismod.quest.claim"), this::onClaimButtonPress));

		// Cancel Button
		addButton(this.cancelButton = new Button(this.width / 2 + 10, (this.height - this.ySize) / 2 + 135, 80, 20, new TranslationTextComponent("gui.daboismod.quest.cancel"), this::onCancelButtonPress));

		this.updateQuests();
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (super.keyPressed(keyCode, scanCode, modifiers)) {
			return true;
		} else {
			InputMappings.Input keyInput = InputMappings.getInputByCode(keyCode, scanCode);
			if (keyCode == GLFW.GLFW_KEY_ESCAPE || this.minecraft.gameSettings.keyBindInventory.isActiveAndMatches(keyInput)) {
				this.minecraft.player.closeScreen();
				return true;
			}
			return false;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.renderGuiBackground(matrixStack, mouseX, mouseY, partialTicks);

		super.render(matrixStack, mouseX, mouseY, partialTicks);

		// Render Progress Bar (Always Rendered So The Bar Can Be Covered When Quests are Empty)
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(QUEST_BACKGROUND);
		this.blit(matrixStack,
				(this.width - this.xSize) / 2 + 52,								// X Position to Render At
				(this.height - this.ySize) / 2 + 94,								// Y Position to Render At
				0,															// Texture Offset X
				this.ySize + 1 + (this.quests.isEmpty() ? 20 : 0),			// Texture Offset Y
				(int) (149 * this.percentage),										// Width
				20															// Height
		);

		if (this.quests.isEmpty()) {

			// 'You have no quests available.'
			drawCenteredString(matrixStack, this.font, new TranslationTextComponent("gui.daboismod.quest.none"),
					this.width / 2,
					this.height / 2 - 4,
					16777215
			);

		} else {
			final Quest quest = this.quests.get(currentPage);

			// 'Quest X / Y'
			drawCenteredString(matrixStack, this.font, new TranslationTextComponent("gui.daboismod.quest.questNumber", currentPage + 1, this.quests.size()),
					this.width / 2,
					(this.height - this.ySize) / 2 - 9,
					16777215
			);

			// 'Objective: X'
			drawCenteredString(matrixStack, this.font, new TranslationTextComponent("gui.daboismod.quest.objective", new TranslationTextComponent(quest.getQuestTask().getTranslationKey(), quest.getQuota())),
					this.width / 2,
					this.height / 2 - 63,
					16777215
			);

			// 'Difficulty: X'
			drawCenteredString(matrixStack, this.font, new TranslationTextComponent("gui.daboismod.quest.difficulty", quest.getDifficulty().getTextComponent()),
					this.width / 2,
					this.height / 2 - 45,
					16777215
			);

			// 'Completed: X / Y'
			drawCenteredString(matrixStack, this.font, new TranslationTextComponent("gui.daboismod.quest.completed", quest.getCount(), quest.getQuota()),
					this.width / 2,
					this.height / 2,
					16777215
			);

			// 'Reward: X'
			drawCenteredString(matrixStack, this.font, new TranslationTextComponent("gui.daboismod.quest.reward", (quest.getReward().getItem() == Items.AIR ? new TranslationTextComponent("gui.daboismod.quest.reward.nothing") : "  ")),
					this.width / 2 - 3,
					this.height / 2 - 22,
					16777215
			);

			// Render Item & Tooltip
			if (!(quest.getReward().getItem() instanceof AirItem)) {
				int itemPosX = this.width / 2 + 12;
				int itemPosY = this.height / 2 - 28;
				this.itemRenderer.renderItemAndEffectIntoGUI(quest.getReward(), itemPosX, itemPosY);
				this.itemRenderer.renderItemOverlays(this.font, quest.getReward(), itemPosX, itemPosY);
				if (mouseX > itemPosX - 4 && mouseX < itemPosX + 16 && mouseY > itemPosY - 2 && mouseY < itemPosY + 18) this.renderTooltip(matrixStack, quest.getReward(), mouseX, mouseY);
			}
		}
	}
	
	public void renderGuiBackground(MatrixStack matrixStack, int mouseX, int mouseY, float val) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.minecraft.getTextureManager().bindTexture(QUEST_BACKGROUND);
	    this.blit(matrixStack, (this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}

	public static int getCurrentPage() {
		return currentPage;
	}
}
