package com.thecrafter4000.lotrtc.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import tconstruct.library.client.ToolGuiElement;
import tconstruct.tools.gui.GuiButtonTool;

public class GuiButtonTool2 extends GuiButtonTool {

	public ResourceLocation background;
	
	public GuiButtonTool2(int id, int posX, int posY, ToolGuiElement e) {
		super(id, posX, posY, e.buttonIconX, e.buttonIconY, e.domain, e.texture, e);
		this.background = new ResourceLocation(e.domain, e.texture);
	}

    @Override
    public void drawButton (Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            boolean var4 = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(background);

            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int var5 = this.getHoverState(this.field_146123_n);
            int index = 18 * getHoverState(field_146123_n);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 144 + index * 2, 216, 18, 18);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, element.buttonIconX * 18, element.buttonIconY * 18, 18, 18);
        }
    }
}
