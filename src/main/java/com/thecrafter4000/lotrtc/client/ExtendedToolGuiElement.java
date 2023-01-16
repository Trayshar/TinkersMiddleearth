package com.thecrafter4000.lotrtc.client;

import com.thecrafter4000.lotrtc.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;
import tconstruct.library.client.ToolGuiElement;
import tconstruct.tools.gui.GuiButtonTool;
import tconstruct.tools.gui.ToolStationGui;

@SideOnly(Side.CLIENT)
public class ExtendedToolGuiElement extends ToolGuiElement {

	/**
	 * Whether to use TC texture or our own, per icon slot
	 */
	public final boolean[] customTextureData;
	
	public ExtendedToolGuiElement(int st, int bx, int by, int[] xi, int[] yi, String t, String b, String d, String tex, boolean[] customTextureData) {
		super(st, bx, by, xi, yi, t, b, d, tex);
		this.customTextureData = customTextureData;
	}

	/**
	 * Partially overwrites drawGuiContainerBackgroundLayer in ToolStationGui, see TinkersMEClassTransformer
	 */
	public static void drawCustomSlots(ToolStationGui gui, int cornerX, int guiTop, boolean[] customTextureData) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		gui.mc.getTextureManager().bindTexture(ClientProxy.tc_icons);
		for (int i = 0; i < gui.slotX.length; i++) {
			gui.drawTexturedModalRect(cornerX + gui.slotX[i], guiTop + gui.slotY[i], 144, 216, 18, 18);
		}

		for (int i = 0; i < gui.slotX.length; i++) {
			if (!gui.logic.isStackInSlot(i + 1)) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				gui.mc.getTextureManager().bindTexture(customTextureData[i] ? ClientProxy.icons : ClientProxy.tc_icons);
				gui.drawTexturedModalRect(cornerX + gui.slotX[i], guiTop + gui.slotY[i], 18 * gui.iconX[i], 18 * gui.iconY[i], 18, 18);
			}
		}

	}

	/**
	 * Injected into actionPerformed in ToolStationGui and ToolStationForge, see TinkersMEClassTransformer
	 */
	public static boolean[] getCustomTextureData(GuiButton btn) {
		if(btn instanceof GuiButtonTool && ((GuiButtonTool) btn).element instanceof ExtendedToolGuiElement) {
			return ((ExtendedToolGuiElement) ((GuiButtonTool) btn).element).customTextureData;
		}
		return emptyTextureData;
	}
	public static final boolean[] emptyTextureData = new boolean[]{false, false, false, false};

}
