package com.thecrafter4000.lotrtc.client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.thecrafter4000.lotrtc.TinkersMiddleearth;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Post;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.library.client.StencilGuiElement;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.client.ToolGuiElement;
import tconstruct.library.crafting.StencilBuilder;
import tconstruct.tools.gui.GuiButtonStencil;
import tconstruct.tools.gui.GuiButtonTool;
import tconstruct.tools.gui.StencilTableGui;
import tconstruct.tools.gui.ToolForgeGui;
import tconstruct.tools.gui.ToolStationGui;
import tconstruct.tools.logic.StencilTableLogic;

@SideOnly(Side.CLIENT)
public class GuiEventHandler {

	public static void register(){
		MinecraftForge.EVENT_BUS.register(new GuiEventHandler());
	}
	
	@SubscribeEvent
	public void onInitGuiPost(InitGuiEvent.Post e){
		if(e.gui.getClass() == StencilTableGui.class) {
			onStencilGui(e);
		}
		else if(e.gui.getClass() == ToolForgeGui.class) {
//			onForgeGui(e);
		}
		else if(e.gui.getClass() == ToolStationGui.class) {
//			onStationGui(e);
		}
	}
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e){
		if(e.gui == null) return;
		if(e.gui.getClass() == ToolStationGui.class){
			e.gui = new ToolStationGui2((ToolStationGui) e.gui);
		}else if(e.gui.getClass() == ToolForgeGui.class){
			e.gui = new ToolForgeGui2((ToolForgeGui) e.gui);
		}
	}
	
	private void onStationGui(Post e) {
        int xSize = 176 + 110;
        int ySize = 166;
        int guiLeft = (e.gui.width - 176) / 2 - 110;
        int guiTop = (e.gui.height - ySize) / 2;

        e.buttonList.clear();
        ToolGuiElement repair = TConstructClientRegistry.toolButtons.get(0);
        GuiButtonTool repairButton = new GuiButtonTool2(0, guiLeft, guiTop, repair); // Repair
        repairButton.enabled = false;
        e.buttonList.add(repairButton);

        for (int iter = 1; iter < TConstructClientRegistry.toolButtons.size(); iter++)
        {
            ToolGuiElement element = TConstructClientRegistry.toolButtons.get(iter);
            GuiButtonTool button = new GuiButtonTool2(iter, guiLeft + 22 * (iter % 5), guiTop + 22 * (iter / 5), element);
            e.buttonList.add(button);
        }
    }

	private void onForgeGui(Post e) {
        int xSize = 176 + 110;
        int ySize = 166;
        int guiLeft = (e.gui.width - 176) / 2 - 110;
        int guiTop = (e.gui.height - ySize) / 2;
		
        e.buttonList.clear();
        ToolGuiElement repair = TConstructClientRegistry.toolButtons.get(0);
        GuiButtonTool repairButton = new GuiButtonTool2(0, guiLeft, guiTop, repair); // Repair
        repairButton.enabled = false;
        e.buttonList.add(repairButton);
        int offset = TConstructClientRegistry.tierTwoButtons.size();

        for (int iter = 0; iter < TConstructClientRegistry.tierTwoButtons.size(); iter++)
        {
            ToolGuiElement element = TConstructClientRegistry.tierTwoButtons.get(iter);
            GuiButtonTool button = new GuiButtonTool2(iter + 1, guiLeft + 22 * ((iter + 1) % 5), guiTop + 22 * ((iter + 1) / 5), element);
            e.buttonList.add(button);
        }

        for (int iter = 1; iter < TConstructClientRegistry.toolButtons.size(); iter++)
        {
            ToolGuiElement element = TConstructClientRegistry.toolButtons.get(iter);
            GuiButtonTool button = new GuiButtonTool2(iter + offset, guiLeft + 22 * ((iter + offset) % 5), guiTop + 22 * ((iter + offset) / 5), element);
            e.buttonList.add(button);
        }
	}

	private void onStencilGui(InitGuiEvent.Post e){
		try{ // Copy of StencilTableGui.initGui()
			// Getting non-visible fields without reflection. I don't like getting mc fields with reflection.
			int xSize = 176;
			int ySize = 166;
			int guiLeft = (e.gui.width - xSize) / 2;
	        int guiTop = (e.gui.height - ySize) / 2;
	        // Getting tinkers fields
	        StencilTableLogic logic = (StencilTableLogic) getProtectedField(StencilTableGui.class, "logic", e.gui);
			int activeButton = 0;
	        
			int bpr = 4; // buttons per row
	        int cornerX = guiLeft - 22 * bpr;
	        int cornerY = guiTop + 2;
	
	        e.buttonList.clear();
	
	        for (int iter = 0; iter < TConstructClientRegistry.stencilButtons.size(); iter++)
	        {
	            StencilGuiElement element = TConstructClientRegistry.stencilButtons.get(iter);
	            if (element.stencilIndex == -1)
	                continue;
	            GuiButtonStencil button = new GuiButtonStencil2(cornerX + 22 * (iter % bpr), cornerY + 22 * (iter / bpr), element);
	            e.buttonList.add(button);
	        }
	        // TiCon:
	        
	        // secondary buttons, yay!
	        // these are to use for other mods :I
	        
	        // TheCrafter4000:
	        
	        // Good joke dude :D
	        // I have to override this!
	        cornerX = guiLeft + xSize + 4;
	        for (int iter = 0; iter < TConstructClientRegistry.stencilButtons2.size(); iter++)
	        {
	            StencilGuiElement element = TConstructClientRegistry.stencilButtons2.get(iter);
	            if (element.stencilIndex == -1)
	                continue;
	            GuiButtonStencil button = new GuiButtonStencil2(cornerX + 22 * (iter % bpr), cornerY + 22 * (iter / bpr), element);
	            e.buttonList.add(button);
	        }
	
	        // get the correct setting :C
	        ItemStack stack;
	        if (logic.getStackInSlot(1) != null)
	        {
	            int newActiveButton = StencilBuilder.getId(logic.getStackInSlot(1));
	            
	            // StencilTableGui.setActiveButton
	            
	            // activate old button
	            ((GuiButton) e.buttonList.get(newActiveButton)).enabled = true;
	            // update active button
	            activeButton = newActiveButton;
	            // deactivate the button
	            ((GuiButton) e.buttonList.get(activeButton)).enabled = false;
	            
	            stack = StencilBuilder.getStencil(((GuiButtonStencil) e.buttonList.get(activeButton)).element.stencilIndex);
	        }
	        else
	            stack = null;
	
	        logic.setSelectedPattern(stack);
	        
	        Method m = StencilTableGui.class.getDeclaredMethod("updateServer", ItemStack.class);
	        m.setAccessible(true);
	        m.invoke(e.gui, stack);
	        
	        // Set active button
			setProtectedField(StencilTableGui.class, "activeButton", e.gui, activeButton);
		}catch(NoClassDefFoundError ncdfe){
			TinkersMiddleearth.logger.catching(ncdfe);
		}catch(Exception ex){
			ex.printStackTrace();;
		}
	}
	
	private Object getProtectedField(Class clazz, String name, Object holder) throws Exception {
		Field f = clazz.getDeclaredField(name);
		f.setAccessible(true);
		return f.get(holder);
	}
	
	private void setProtectedField(Class clazz, String name, Object holder, Object obj) throws Exception {
		Field f = clazz.getDeclaredField(name);
		f.setAccessible(true);
		f.set(holder, obj);
	}
}
