package com.github.kaydogz.daboismod.client.gui;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IPlayerCap;
import com.github.kaydogz.daboismod.capability.provider.PlayerCapability;
import com.github.kaydogz.daboismod.network.DBMPacketHandler;
import com.github.kaydogz.daboismod.network.client.CCancelQuestPacket;
import com.github.kaydogz.daboismod.network.client.CClaimQuestPacket;
import com.github.kaydogz.daboismod.quest.Quest;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
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
		LazyOptional<IPlayerCap> playerCap = PlayerCapability.getCapabilityOf(this.minecraft.player);
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
		DBMPacketHandler.sendToServer(new CClaimQuestPacket(this.quests.get(currentPage)));
	}

	protected void onCancelButtonPress(Button b) {
		DBMPacketHandler.sendToServer(new CCancelQuestPacket(this.quests.get(currentPage)));
	}

	@Override
	protected void init() {
		super.init();

		// Left Arrow Button
		addButton(this.leftButton = new Button((this.width - this.xSize) / 2 - 30, this.height / 2 - 10, 20, 20, "<", this::onLeftButtonPress));

		// Right Arrow Button
		addButton(this.rightButton = new Button((this.width + this.xSize) / 2 + 10, this.height / 2 - 10, 20, 20, ">", this::onRightButtonPress));

		// Claim Button
		addButton(this.claimButton = new Button(this.width / 2 - 90, (this.height - this.ySize) / 2 + 135, 80, 20, new TranslationTextComponent("gui.daboismod.quest.claim").getFormattedText(), this::onClaimButtonPress));

		// Cancel Button
		addButton(this.cancelButton = new Button(this.width / 2 + 10, (this.height - this.ySize) / 2 + 135, 80, 20, new TranslationTextComponent("gui.daboismod.quest.cancel").getFormattedText(), this::onCancelButtonPress));

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
	public void render(int mouseX, int mouseY, float p_render_3_) {
		this.renderBackground();
		this.renderGuiBackground(mouseX, mouseY, p_render_3_);

		super.render(mouseX, mouseY, p_render_3_);

		// Render Progress Bar (Always Rendered So The Bar Can Be Covered When Quests are Empty)
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(QUEST_BACKGROUND);
		this.blit((this.width - this.xSize) / 2 + 52,						// X Position to Render At
				(this.height - this.ySize) / 2 + 94,						// Y Position to Render At
				0,															// Texture Offset X
				this.ySize + 1 + (this.quests.isEmpty() ? 20 : 0),			// Texture Offset Y
				(int) (149 * this.percentage),										// Width
				20															// Height
		);

		if (this.quests.isEmpty()) {

			// 'You have no quests available.'
			this.drawCenteredString(this.font, new TranslationTextComponent("gui.daboismod.quest.none").getFormattedText(),
					this.width / 2,
					this.height / 2 - 4,
					16777215
			);

		} else {

			// 'Quest X / Y'
			this.drawCenteredString(this.font, new TranslationTextComponent("gui.daboismod.quest.questNumber",
							currentPage + 1,
							this.quests.size()
					).getFormattedText(),
					this.width / 2,
					(this.height - this.ySize) / 2 - 9,
					16777215
			);

			// 'Objective: X'
			this.drawCenteredString(this.font, new TranslationTextComponent("gui.daboismod.quest.objective",
							new TranslationTextComponent(this.quests.get(currentPage).getQuestTask().getTranslationKey(), this.quests.get(currentPage).getQuota()).getFormattedText()
					).getFormattedText(),
					this.width / 2,
					this.height / 2 - 63,
					16777215
			);

			// 'Difficulty: X'
			this.drawCenteredString(this.font, new TranslationTextComponent("gui.daboismod.quest.difficulty",
							this.quests.get(currentPage).getDifficulty().getTextComponent().getFormattedText()
					).getFormattedText(),
					this.width / 2,
					this.height / 2 - 45,
					16777215
			);

			// 'Completed: X / Y'
			this.drawCenteredString(this.font, new TranslationTextComponent("gui.daboismod.quest.completed",
							this.quests.get(currentPage).getCount(),
							this.quests.get(currentPage).getQuota()
					).getFormattedText(),
					this.width / 2,
					this.height / 2,
					16777215
			);

			// 'Reward: X'
			this.drawCenteredString(this.font, new TranslationTextComponent("gui.daboismod.quest.reward",
							(this.quests.get(currentPage).getReward().getItem() == Items.AIR ? new TranslationTextComponent("gui.daboismod.quest.reward.nothing").getFormattedText() : "  ")
					).getFormattedText(),
					this.width / 2 - 3,
					this.height / 2 - 22,
					16777215
			);

			// Render Item & Tooltip
			ItemStack reward = this.quests.get(currentPage).getReward();
			if (!(reward.getItem() instanceof AirItem)) {
				int itemPosX = this.width / 2 + 12;
				int itemPosY = this.height / 2 - 28;
				this.itemRenderer.renderItemAndEffectIntoGUI(reward, itemPosX, itemPosY);
				this.itemRenderer.renderItemOverlays(this.font, reward, itemPosX, itemPosY);
				if (mouseX > itemPosX - 4 && mouseX < itemPosX + 16 && mouseY > itemPosY - 2 && mouseY < itemPosY + 18) this.renderTooltip(reward, mouseX, mouseY);
			}
		}
	}
	
	public void renderGuiBackground(int mouseX, int mouseY, float p_render_3_) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.minecraft.getTextureManager().bindTexture(QUEST_BACKGROUND);
	    this.blit((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}

	public static int getCurrentPage() {
		return currentPage;
	}
}
