package com.thecrafter4000.lotrtc.client;

import org.lwjgl.opengl.GL11;

import com.thecrafter4000.lotrtc.TinkersMiddleearth;
import com.thecrafter4000.lotrtc.tools.ToolRegistry;
import com.thecrafter4000.lotrtc.tools.ToolRegistry.ToolPartRenderEntry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.client.ToolGuiElement;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.crafting.ToolRecipe;
import tconstruct.library.tools.ToolCore;
import tconstruct.tools.gui.GuiButtonTool;
import tconstruct.tools.gui.ToolStationGui;

@SideOnly(Side.CLIENT)
public class ToolStationGui2 extends ToolStationGui {

	public ResourceLocation[] textures = new ResourceLocation[4];
	public int[] xIcon = new int[]{0,0,0,0};
	public int[] yIcon = new int[]{0,0,0,0};
	
	public ToolStationGui2(ToolStationGui gui) {
		super(Minecraft.getMinecraft().thePlayer.inventory, gui.logic, gui.logic.getWorldObj(), gui.logic.xCoord, gui.logic.yCoord, gui.logic.zCoord);
	}
	
	@Override public void initGui() {
		super.initGui();
		
		// Overriding buttons
        this.buttonList.clear();
        ToolGuiElement repair = TConstructClientRegistry.toolButtons.get(0);
        GuiButtonTool repairButton = new GuiButtonTool2(0, guiLeft, guiTop, repair); // Repair
        repairButton.enabled = false;
        this.buttonList.add(repairButton);

        for (int iter = 1; iter < TConstructClientRegistry.toolButtons.size(); iter++)
        {
            ToolGuiElement element = TConstructClientRegistry.toolButtons.get(iter);
            GuiButtonTool button = new GuiButtonTool2(iter, guiLeft + 22 * (iter % 5), guiTop + 22 * (iter / 5), element);
            this.buttonList.add(button);
        }
	}
	
	@Override public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		
		// Reseting previous values
		textures = new ResourceLocation[]{null, null, null, null};
		xIcon = new int[]{0,0,0,0};
		yIcon = new int[]{0,0,0,0};
		
		ToolGuiElement element = TConstructClientRegistry.toolButtons.get(guiType);
		
		if(element instanceof ExtendedToolGuiElement) {
			// Getting recipe
			ToolCore tool = ((ExtendedToolGuiElement) element).tool;
			ToolRecipe recipe = ToolBuilder.instance.recipeList.get(tool.getToolName());
			if(recipe == null){
				for(ToolRecipe r : ToolBuilder.instance.combos){
					if(r.getType() == tool) recipe = r;
				}
			}
			if(recipe == null) {
				TinkersMiddleearth.logger.error("Error getting tool recipe for tool " + tool.getToolName() + "!");
			}

			// Setting values
			for(ToolPartRenderEntry e : ToolRegistry.partRender){
				if(recipe.validAccessory(e.part)){
					textures[2] = e.tex;
					xIcon[2] = e.x;
					yIcon[2] = e.y;
				}
				if(recipe.validExtra(e.part)){
					textures[3] = e.tex;
					xIcon[3] = e.x;
					yIcon[3] = e.y;
				}
				if(recipe.validHandle(e.part)){
					textures[1] = e.tex;
					xIcon[1] = e.x;
					yIcon[1] = e.y;

				}
				if(recipe.validHead(e.part)){
					textures[0] = e.tex;
					xIcon[0] = e.x;
					yIcon[0] = e.y;
				}
			}
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		
        for (int i = 0; i < slotX.length; i++) {
//    		this.drawString(fontRendererObj, String.valueOf(i), this.guiLeft + 110 + slotX[i], this.guiTop + slotY[i], 1);

        	if (!logic.isStackInSlot(i + 1) && textures[i] != null) {
        		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        		this.mc.getTextureManager().bindTexture(textures[i]);
        		this.drawTexturedModalRect(this.guiLeft + 110 + slotX[i], this.guiTop + slotY[i], 18 * xIcon[i], 18 * yIcon[i], 18, 18);
        	}
        }
	}
}
