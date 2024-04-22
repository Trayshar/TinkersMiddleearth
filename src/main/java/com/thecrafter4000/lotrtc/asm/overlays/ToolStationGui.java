package com.thecrafter4000.lotrtc.asm.overlays;

import com.thecrafter4000.lotrtc.ClientProxy;
import com.thecrafter4000.lotrtc.client.ExtendedToolGuiElement;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import tconstruct.tools.gui.GuiButtonTool;
import tconstruct.tools.logic.ToolStationLogic;

// TODO: Not used yet, work in progress
public class ToolStationGui extends tconstruct.tools.gui.ToolStationGui {
    /**
     * Shadow vars in actual class
     */
    private static final ResourceLocation background = new ResourceLocation("tinker", "textures/gui/toolstation.png");
    private static final ResourceLocation icons = new ResourceLocation("tinker", "textures/gui/icons.png");
    private static final ResourceLocation description = new ResourceLocation("tinker", "textures/gui/description.png");
    private static final boolean[] emptyTextureData = new boolean[]{false, false, false, false};
    /**
     * Injected via ASM in actual class
     */
    public boolean[] customTextureData;

    /**
     * Necessary for syntax reason, not actually used
     */
    public ToolStationGui(InventoryPlayer inventoryplayer, ToolStationLogic stationlogic, World world, int x, int y, int z) {
        super(inventoryplayer, stationlogic, world, x, y, z);
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int cornerX = this.guiLeft + 110;
        this.drawTexturedModalRect(cornerX, this.guiTop, 0, 0, 176, this.ySize);
        if (this.active) {
            this.drawTexturedModalRect(cornerX + 62, this.guiTop, 0, this.ySize, 112, 22);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(icons);
        for (int i = 0; i < slotX.length; i++) {
            drawTexturedModalRect(cornerX + slotX[i], this.guiTop + slotY[i], 144, 216, 18, 18);
        }

        for (int i = 0; i < slotX.length; i++) {
            if (!logic.isStackInSlot(i + 1)) {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(customTextureData[i] ? icons : ClientProxy.tc_icons);
                this.drawTexturedModalRect(cornerX + slotX[i], this.guiTop + slotY[i], 18 * iconX[i], 18 * iconY[i], 18, 18);
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(description);
        this.drawTexturedModalRect(cornerX + 176, this.guiTop, 0, 0, 126, this.ySize + 30);
    }

    public void updateTextureData(GuiButton btn) {
        if (btn instanceof GuiButtonTool && ((GuiButtonTool) btn).element instanceof ExtendedToolGuiElement) {
            this.customTextureData = ((ExtendedToolGuiElement) ((GuiButtonTool) btn).element).customTextureData;
        }
        this.customTextureData = emptyTextureData;
    }
}
