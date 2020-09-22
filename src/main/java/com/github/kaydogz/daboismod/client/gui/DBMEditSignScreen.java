package com.github.kaydogz.daboismod.client.gui;

import com.github.kaydogz.daboismod.client.renderer.DBMSignTileEntityRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.CUpdateSignPacket;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class DBMEditSignScreen extends Screen {

    private final DBMSignTileEntityRenderer.DBMSignModel model = new DBMSignTileEntityRenderer.DBMSignModel();
    /** Reference to the sign object. */
    private final SignTileEntity tileSign;
    /** Counts the number of screen updates. */
    private int updateCounter;
    /** The index of the line that is being edited. */
    private int editLine;
    private TextInputUtil textInputUtil;

    public DBMEditSignScreen(SignTileEntity teSign) {
        super(new TranslationTextComponent("sign.edit"));
        this.tileSign = teSign;
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, I18n.format("gui.done"), (p_214266_1_) -> {
            this.close();
        }));
        this.tileSign.setEditable(false);
        this.textInputUtil = new TextInputUtil(this.minecraft, () -> {
            return this.tileSign.getText(this.editLine).getString();
        }, (p_214265_1_) -> {
            this.tileSign.setText(this.editLine, new StringTextComponent(p_214265_1_));
        }, 90);
    }

    @Override
    public void removed() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        ClientPlayNetHandler clientplaynethandler = this.minecraft.getConnection();
        if (clientplaynethandler != null) {
            clientplaynethandler.sendPacket(new CUpdateSignPacket(this.tileSign.getPos(), this.tileSign.getText(0), this.tileSign.getText(1), this.tileSign.getText(2), this.tileSign.getText(3)));
        }

        this.tileSign.setEditable(true);
    }

    @Override
    public void tick() {
        ++this.updateCounter;
        if (!this.tileSign.getType().isValidBlock(this.tileSign.getBlockState().getBlock())) {
            this.close();
        }
    }

    private void close() {
        this.tileSign.markDirty();
        this.minecraft.player.closeScreen();
    }

    @Override
    public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
        this.textInputUtil.putChar(p_charTyped_1_);
        return true;
    }

    @Override
    public void onClose() {
        this.close();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_UP) {
            this.editLine = this.editLine - 1 & 3;
            this.textInputUtil.putCursorAtEnd();
            return true;
        } else if (keyCode != GLFW.GLFW_KEY_DOWN && keyCode != GLFW.GLFW_KEY_ENTER && keyCode != GLFW.GLFW_KEY_KP_ENTER) {
            return this.textInputUtil.specialKeyPressed(keyCode) || super.keyPressed(keyCode, scanCode, modifiers);
        } else {
            this.editLine = this.editLine + 1 & 3;
            this.textInputUtil.putCursorAtEnd();
            return true;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float p_render_3_) {
        RenderHelper.setupGuiFlatDiffuseLighting();
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 40, 16777215);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.push();
        matrixstack.translate(this.width / 2.0D, 0.0D, 50.0D);
        float f = 93.75F;
        matrixstack.scale(93.75F, -93.75F, 93.75F);
        matrixstack.translate(0.0D, -1.3125D, 0.0D);
        BlockState blockstate = this.tileSign.getBlockState();
        boolean flag = blockstate.getBlock() instanceof StandingSignBlock;
        if (!flag) {
            matrixstack.translate(0.0D, -0.3125D, 0.0D);
        }

        boolean flag1 = this.updateCounter / 6 % 2 == 0;
        float f1 = 0.6666667F;
        matrixstack.push();
        matrixstack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = this.minecraft.getRenderTypeBuffers().getBufferSource();
        Material material = DBMSignTileEntityRenderer.getDBMMaterial(blockstate.getBlock());
        IVertexBuilder ivertexbuilder = material.getBuffer(irendertypebuffer$impl, this.model::getRenderType);
        this.model.signBoard.render(matrixstack, ivertexbuilder, 15728880, OverlayTexture.NO_OVERLAY);
        if (flag) {
            this.model.signStick.render(matrixstack, ivertexbuilder, 15728880, OverlayTexture.NO_OVERLAY);
        }

        matrixstack.pop();
        float f2 = 0.010416667F;
        matrixstack.translate(0.0D, (double)0.33333334F, (double)0.046666667F);
        matrixstack.scale(0.010416667F, -0.010416667F, 0.010416667F);
        int i = this.tileSign.getTextColor().getTextColor();
        String[] astring = new String[4];

        for(int j = 0; j < astring.length; ++j) {
            astring[j] = this.tileSign.getRenderText(j, (p_228192_1_) -> {
                List<ITextComponent> list = RenderComponentsUtil.splitText(p_228192_1_, 90, this.minecraft.fontRenderer, false, true);
                return list.isEmpty() ? "" : list.get(0).getFormattedText();
            });
        }

        Matrix4f matrix4f = matrixstack.getLast().getMatrix();
        int k = this.textInputUtil.getEndIndex();
        int l = this.textInputUtil.getStartIndex();
        int i1 = this.minecraft.fontRenderer.getBidiFlag() ? -1 : 1;
        int j1 = this.editLine * 10 - this.tileSign.signText.length * 5;

        for(int k1 = 0; k1 < astring.length; ++k1) {
            String s = astring[k1];
            if (s != null) {
                float f3 = (float)(-this.minecraft.fontRenderer.getStringWidth(s) / 2);
                this.minecraft.fontRenderer.renderString(s, f3, (float)(k1 * 10 - this.tileSign.signText.length * 5), i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880);
                if (k1 == this.editLine && k >= 0 && flag1) {
                    int l1 = this.minecraft.fontRenderer.getStringWidth(s.substring(0, Math.max(Math.min(k, s.length()), 0)));
                    int i2 = (l1 - this.minecraft.fontRenderer.getStringWidth(s) / 2) * i1;
                    if (k >= s.length()) {
                        this.minecraft.fontRenderer.renderString("_", (float)i2, (float)j1, i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880);
                    }
                }
            }
        }

        irendertypebuffer$impl.finish();

        for(int k3 = 0; k3 < astring.length; ++k3) {
            String s1 = astring[k3];
            if (s1 != null && k3 == this.editLine && k >= 0) {
                int l3 = this.minecraft.fontRenderer.getStringWidth(s1.substring(0, Math.max(Math.min(k, s1.length()), 0)));
                int i4 = (l3 - this.minecraft.fontRenderer.getStringWidth(s1) / 2) * i1;
                if (flag1 && k < s1.length()) {
                    fill(matrix4f, i4, j1 - 1, i4 + 1, j1 + 9, -16777216 | i);
                }

                if (l != k) {
                    int j4 = Math.min(k, l);
                    int j2 = Math.max(k, l);
                    int k2 = (this.minecraft.fontRenderer.getStringWidth(s1.substring(0, j4)) - this.minecraft.fontRenderer.getStringWidth(s1) / 2) * i1;
                    int l2 = (this.minecraft.fontRenderer.getStringWidth(s1.substring(0, j2)) - this.minecraft.fontRenderer.getStringWidth(s1) / 2) * i1;
                    int i3 = Math.min(k2, l2);
                    int j3 = Math.max(k2, l2);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferbuilder = tessellator.getBuffer();
                    RenderSystem.disableTexture();
                    RenderSystem.enableColorLogicOp();
                    RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
                    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    bufferbuilder.pos(matrix4f, (float)i3, (float)(j1 + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.pos(matrix4f, (float)j3, (float)(j1 + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.pos(matrix4f, (float)j3, (float)j1, 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.pos(matrix4f, (float)i3, (float)j1, 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.finishDrawing();
                    WorldVertexBufferUploader.draw(bufferbuilder);
                    RenderSystem.disableColorLogicOp();
                    RenderSystem.enableTexture();
                }
            }
        }

        matrixstack.pop();
        RenderHelper.setupGui3DDiffuseLighting();
        super.render(mouseX, mouseY, p_render_3_);
    }
}
