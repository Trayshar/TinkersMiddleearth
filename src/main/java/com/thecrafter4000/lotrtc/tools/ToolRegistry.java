package com.thecrafter4000.lotrtc.tools;

import com.thecrafter4000.lotrtc.TinkersMiddleearth;
import com.thecrafter4000.lotrtc.client.ExtendedToolGuiElement;
import com.thecrafter4000.lotrtc.items.MaterialRegistry;
import com.thecrafter4000.lotrtc.items.TinkersMEItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.*;
import tconstruct.library.tools.DynamicToolPart;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.util.IPattern;
import tconstruct.smeltery.TinkerSmeltery;

import java.util.*;

public class ToolRegistry {

	/* Tools */
	
	public static final List<ToolCore> removeRecipe = new ArrayList<ToolCore>();
	public static final List<ToolEntry> tools = new ArrayList<ToolEntry>();
	
	/**
	 * Add's an normal tool
	 */
	public static void addTool(String name, ToolCore tool, int buttonX, int buttonY, int guiType, int[] iconX, int[] iconY){
		tools.add(new ToolEntry(name, tool, "textures/gui/icons.png", buttonX, buttonY, guiType, iconX, iconY, "lotrtc", true, false, tool.getHeadItem(), tool.getHandleItem(), tool.getAccessoryItem(), tool.getExtraItem()));
	}
	
	/**
	 * Add's an tool only crafted on tool forge
	 */
	public static void addTierTwoTool(String name, ToolCore tool, int buttonX, int buttonY, int guiType, int[] iconX, int[] iconY){
		tools.add(new ToolEntry(name, tool, "textures/gui/icons.png", buttonX, buttonY, guiType, iconX, iconY, "lotrtc", true, true, tool.getHeadItem(), tool.getHandleItem(), tool.getAccessoryItem(), tool.getExtraItem()));
	}
	
	/**
	 *  Add's an tool, but no button
	 */
	public static void addToolWithoutGui(String name, ToolCore tool){
		tools.add(new ToolEntry(name, tool, "", 0, 0, 0, new int[]{}, new int[]{}, "lotrtc", false, false, tool.getHeadItem(), tool.getHandleItem(), tool.getAccessoryItem(), tool.getExtraItem()));
	}
	
	/**
	 *  Removes all recipes crafting a tool
	 */
	public static void removeRecipe(ToolCore tool){
		removeRecipe.add(tool);
	}
	
	public static class ToolEntry{
		public final String name;
		public final ToolCore tool;
		public final Item[] recipe;
		
		public final int guiType;
		public final String domain;
		public final String textureButton;
		public final int xButton, yButton;
		public final int[] xIcon, yIcon;
		public final boolean addGui;
		public final boolean tierTwo;
		
		public ToolEntry(String name, ToolCore tool, String texture, int buttonX, int buttonY, int guiType, int[] iconX, int[] iconY, String domain, boolean addGui, boolean tierTwo, Item... items) {
			this.name = name;
			this.tool = tool;
			this.recipe = items;
			this.domain = domain;
			this.textureButton = texture;
			this.xButton = buttonX;
			this.yButton = buttonY;
			this.addGui = addGui;
			this.xIcon = iconX;
			this.yIcon = iconY;
			this.guiType = guiType;
			this.tierTwo = tierTwo;
		}
	}
	
	/* Tool parts */
	
	/* Integer -> Pattern ID. I use an map instead of an list so I can remove an pattern if necessary. */
	public final static Map<Integer, ToolPartEntry> parts = new HashMap<Integer, ToolPartEntry>();
	public static final List<ToolPartRenderEntry> partRender = new ArrayList<ToolPartRenderEntry>();
	public static int nextIndex = 0;

	/**
	 *  Add's a tool part
	 */
	public static void addToolPart(DynamicToolPart item, int materialCosts, int iconX, int iconY){
		addToolPart(item, materialCosts, "textures/gui/icons.png", iconX, iconY, true);
	}
	
	/**
	 *  Add's a tool part
	 */
	public static void addToolPart(DynamicToolPart item, int materialCosts, String texture, int iconX, int iconY, boolean addSmelteryCasting ){
		parts.put(nextIndex, new ToolPartEntry(item, materialCosts, addSmelteryCasting, texture, iconX, iconY));
		nextIndex++;
	}
		
	/**
	 *  Registers an icon for an tool part on tool station/forge. It is used to draw the slot backgrounds for non-tinker items.
	 */
	public static void addToolPartRender(Item tool, ResourceLocation tex, int x, int y){
		partRender.add(new ToolPartRenderEntry(tool, tex, x, y));
	}
	
	public static class ToolPartEntry{
		public final Item item;
		public final String texture;
		public final String name;
		public final String domain;
		public final boolean addSmelteryCasting;
		public final int materialCosts;
		
		public final String textureButton;
		public final int xButton;
		public final int yButton;
		public int guiID;
		
		/* Gets all necessary information from the item */
		public ToolPartEntry(DynamicToolPart item, int materialCosts, boolean addSmelteryCasting, String texture, int iconX, int iconY) {
			this.item = item;
			this.texture = item.texture;
			this.name = item.partName;
			this.domain = item.modTexPrefix;
			this.materialCosts = materialCosts;
			this.addSmelteryCasting = addSmelteryCasting;
			this.textureButton = texture;
			this.xButton = iconX;
			this.yButton = iconY;
		}
	}
	
	public static class ToolPartRenderEntry{
		public final Item part;
		public final ResourceLocation tex;
		public final int x;
		public final int y;
		
		public ToolPartRenderEntry(Item part, ResourceLocation tex, int x, int y) {
			this.part = part;
			this.tex = tex;
			this.x = x;
			this.y = y;
		}
	}
	
	/* Common code */
	
	public static void preInit(){
		/* Tools */
		for(ToolEntry t : tools){
			GameRegistry.registerItem(t.tool, t.name);
			TConstructRegistry.addToolMapping(t.tool);
		}
		/* Parts */
		for(Integer i : parts.keySet()){
			ToolPartEntry e = parts.get(i);
			String low = Character.toLowerCase(e.name.charAt(0)) + e.name.substring(1); // Makes first letter lower cast
			GameRegistry.registerItem(e.item, low);
			TConstructRegistry.addItemToDirectory(low, e.item);
		}
	}
	
	public static void init(){
		/* Tools */
		
		/* Have to copy the list cause I can't iterate something I'm changing */
		for(ToolRecipe t : new ArrayList<ToolRecipe>(ToolBuilder.instance.combos)){
			if(removeRecipe.contains(t.getType())) {
	        	ToolBuilder.instance.recipeList.remove(t.getType().getToolName());
				ToolBuilder.instance.combos.remove(t);
	        }
		}
		/* Boring code */
		for(ToolEntry t : tools){
			ToolBuilder.addToolRecipe(t.tool, t.recipe);
		}
		
		/* Parts & Patterns*/
		
		/* Creates a list containing all non-metal ids */
		List<Integer> nonMetals = new ArrayList<Integer>(Arrays.asList(0, 1, 3, 4, 5, 6, 7, 8, 9, 17));
		
		/* Creates an mapping betwen id and fluid. Used for table casting. */
		HashMap<Integer, Fluid> materials = new HashMap<Integer, Fluid>(MaterialRegistry.mapfluids);
		int[] liquidDamage = new int[] { 2, 13, 10, 11, 12, 14, 15, 6, 16, 18 }; // ItemStack
		for(int i = 0; i < TinkerSmeltery.liquids.length; i ++){
			materials.put(liquidDamage[i], TinkerSmeltery.liquids[i].getFluid());
		}

		/* Own materials */
		for(Integer i : MaterialRegistry.mapfluids.keySet()){
			if(MaterialRegistry.mapfluids.get(i) == null) {
				nonMetals.add(i);
				materials.remove(i);
			}
		}
		
		/* Add part crafting */
		for(Integer material : nonMetals){
			for(Integer pattern : parts.keySet()) {
				TConstructRegistry.addPartMapping(TinkersMEItems.woodPattern, pattern, material, new ItemStack(parts.get(pattern).item, 1, material));
			}
		}
				
		/* Add table casting */
		LiquidCasting tableCasting = TConstructRegistry.instance.getTableCasting();
        for (Integer pattern : parts.keySet()) {
        	ItemStack cast = new ItemStack(TinkersMEItems.metalPattern, 1, pattern);
        
        	tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.ingotLiquidValue), new ItemStack(parts.get(pattern).item, 1, Short.MAX_VALUE), false, 50);
        	tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenGoldFluid, TConstruct.ingotLiquidValue * 2), new ItemStack(parts.get(pattern).item, 1, Short.MAX_VALUE), false, 50);
        	
        	for (Integer id : materials.keySet()) {
        		Fluid fs = materials.get(id);
        		if(fs != null){
        			int fluidAmount = ((IPattern) TinkersMEItems.metalPattern).getPatternCost(cast) * TConstruct.ingotLiquidValue / 2;
        			ItemStack toolpart = new ItemStack(parts.get(pattern).item, 1, id);
        			tableCasting.addCastingRecipe(toolpart, new FluidStack(fs, fluidAmount), cast, 50);
        			Smeltery.addMelting(FluidType.getFluidType(fs), toolpart, 0, fluidAmount);
        		}else{
        			TinkersMiddleearth.logger.warn("[ToolRegistry] Error registering table casting: Wrong ID! ID=" + id + ", Pattern=" + pattern);
        			TinkersMiddleearth.logger.warn("[ToolRegistry] This is an bug. Report it.");
        		}
        	}	
        }
	}

	public static void postInit(){
		Integer[] partIndecies = parts.keySet().toArray(new Integer[]{});
		int nextPartIndex = 0;
		
		/* set gui id's */
		for(int i = 0; nextPartIndex < partIndecies.length; i++){
			if(!StencilBuilder.instance.stencils.containsKey(i)) {
				parts.get(partIndecies[nextPartIndex]).guiID = i;
				StencilBuilder.registerStencil(i, TinkersMEItems.woodPattern, partIndecies[nextPartIndex]);
				nextPartIndex++;
			}
		}
		
		/* Register */
		for(ToolPartEntry entry : parts.values()){
			TConstructClientRegistry.addStencilButton2(entry.xButton, entry.yButton, entry.guiID, entry.domain, entry.textureButton);
		}
		
		/* Register */
		for(ToolEntry entry : tools){
			if(entry.addGui){
				String desc = String.format("gui.toolstation.%s.desc", entry.tool.getToolName().toLowerCase());
				if(entry.tierTwo) {
					TConstructClientRegistry.addTierTwoButton(new ExtendedToolGuiElement(entry.guiType, entry.xButton, entry.yButton, entry.xIcon, entry.yIcon, entry.tool.getLocalizedToolName(), desc, entry.domain, entry.textureButton, entry.tool));
				}else{
					TConstructClientRegistry.addToolButton(new ExtendedToolGuiElement(entry.guiType, entry.xButton, entry.yButton, entry.xIcon, entry.yIcon, entry.tool.getLocalizedToolName(), desc, entry.domain, entry.textureButton, entry.tool));					
				}
			}
		}
	}
}