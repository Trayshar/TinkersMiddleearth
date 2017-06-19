package com.thecrafter4000.lotrtc.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import tconstruct.library.client.StencilGuiElement;
import tconstruct.tools.gui.GuiButtonStencil;

public class GuiButtonStencil2 extends GuiButtonStencil {

	public final ResourceLocation texture;
	public int texX, texY;
	
	public GuiButtonStencil2(int posX, int posY, StencilGuiElement e) {
		super(e.stencilIndex, posX, posY, e.buttonIconX, e.buttonIconY, e.domain, e.texture, e);
		this.texture = new ResourceLocation(e.domain, e.texture);
		this.texX = e.buttonIconX;
		this.texY = e.buttonIconY;
	}

    @Override
    public void drawButton (Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            boolean var4 = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(texture);

            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int var5 = this.getHoverState(this.field_146123_n);
            int index = 18 * getHoverState(field_146123_n);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 144 + index * 2, 234, 18, 18);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, texX * 18, texY * 18, 18, 18);
        }
    }
}
