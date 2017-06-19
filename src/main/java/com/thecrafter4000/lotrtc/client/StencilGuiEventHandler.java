package com.thecrafter4000.lotrtc.client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.thecrafter4000.lotrtc.TinkersMiddleearth;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.library.client.StencilGuiElement;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.StencilBuilder;
import tconstruct.tools.gui.GuiButtonStencil;
import tconstruct.tools.gui.StencilTableGui;
import tconstruct.tools.logic.StencilTableLogic;

public class StencilGuiEventHandler {

	public static void register(){
		MinecraftForge.EVENT_BUS.register(new StencilGuiEventHandler());
	}
	
	@SubscribeEvent
	public void onInitGuiPost(InitGuiEvent.Post e){
		if(e.gui.getClass() != StencilTableGui.class) return;
		
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
	
	        // secondary buttons, yay!
	        // these are to use for other mods :I
	        // Good joke dude :DDDDDD
	        // I have to override this shit!!!!!!!
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
			Field f = StencilTableGui.class.getDeclaredField("activeButton");
			f.setAccessible(true);
			f.set(e.gui, activeButton);
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
}
