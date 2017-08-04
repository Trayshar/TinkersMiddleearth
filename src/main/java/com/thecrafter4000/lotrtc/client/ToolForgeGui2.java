package com.thecrafter4000.lotrtc.client;

import java.lang.reflect.Field;

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
import tconstruct.tools.gui.ToolForgeGui;
import tconstruct.tools.logic.ToolForgeLogic;

@SideOnly(Side.CLIENT)
public class ToolForgeGui2 extends ToolForgeGui {

	public ResourceLocation[] textures = new ResourceLocation[4];
	public int[] xIcon = new int[]{0,0,0,0};
	public int[] yIcon = new int[]{0,0,0,0};
	
	public ToolForgeGui2(ToolForgeGui gui) {
		super(Minecraft.getMinecraft().thePlayer.inventory, (ToolForgeLogic) gui.logic, gui.logic.getWorldObj(), gui.logic.xCoord, gui.logic.yCoord, gui.logic.zCoord);
	}
	
	@Override public void initGui() {
        super.initGui();
        
        // Just replacing buttons
        this.buttonList.clear();
        ToolGuiElement repair = TConstructClientRegistry.toolButtons.get(0);
        GuiButtonTool repairButton = new GuiButtonTool2(0, guiLeft, guiTop, repair); // Repair
        repairButton.enabled = false;
        this.buttonList.add(repairButton);
        int offset = TConstructClientRegistry.tierTwoButtons.size();

        for (int iter = 0; iter < TConstructClientRegistry.tierTwoButtons.size(); iter++)
        {
            ToolGuiElement element = TConstructClientRegistry.tierTwoButtons.get(iter);
            GuiButtonTool button = new GuiButtonTool2(iter + 1, guiLeft + 22 * ((iter + 1) % 5), guiTop + 22 * ((iter + 1) / 5), element);
            this.buttonList.add(button);
        }

        for (int iter = 1; iter < TConstructClientRegistry.toolButtons.size(); iter++)
        {
            ToolGuiElement element = TConstructClientRegistry.toolButtons.get(iter);
            GuiButtonTool button = new GuiButtonTool2(iter + offset, guiLeft + 22 * ((iter + offset) % 5), guiTop + 22 * ((iter + offset) / 5), element);
            this.buttonList.add(button);
        }
	}

	/**
	 *  Helper func
	 */
	public int getSelectedButton(){
		Field f = null;
		try {
			f = ToolForgeGui.class.getDeclaredField("selectedButton");
		} catch (Exception e) {}
		
		try{
			if(f == null) f = ToolForgeGui.class.getDeclaredField("field_146290_a"); // Deobfuscated name
			f.setAccessible(true);
			return f.getInt(this);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		
		// Reseting previous values
		textures = new ResourceLocation[]{null, null, null, null};
		xIcon = new int[]{0,0,0,0};
		yIcon = new int[]{0,0,0,0};
		
		// Getting tool element
		int index = getSelectedButton();
		if(index < 1) {
			return;
		}
		int off = TConstructClientRegistry.tierTwoButtons.size();
		ToolGuiElement element = null;
		if(index > off) {
			element = TConstructClientRegistry.toolButtons.get(index - off);
		}
		else {
			element = TConstructClientRegistry.tierTwoButtons.get(index-1);
		}
		//ToDo: Using button element field
		
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
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
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
