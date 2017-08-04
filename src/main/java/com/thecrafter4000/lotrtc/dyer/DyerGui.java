package com.thecrafter4000.lotrtc.dyer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.gui.LOTRGuiArmorStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tconstruct.TConstruct;

@SideOnly(Side.CLIENT)
public class DyerGui extends GuiContainer {

	public DyerLogic logic;
	
	public DyerGui(DyerLogic logic) {
		super(new DyerContainer(Minecraft.getMinecraft().thePlayer.inventory, logic));
		this.logic = logic;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButton(0, guiLeft, guiTop + 28, 36, 18, "Dye!"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		
		if(button.id == 0){
			logic.dyeItem();
			TConstruct.packetPipeline.sendToServer(new DyePacket(logic.xCoord, logic.yCoord, logic.zCoord));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("lotrtc", "textures/gui/dyer.png"));
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}
