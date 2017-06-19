package com.thecrafter4000.lotrtc.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thecrafter4000.lotrtc.TinkersMiddleearth;
import com.thecrafter4000.lotrtc.items.MaterialRegistry;
import com.thecrafter4000.lotrtc.items.TinkersMEItems;
import com.thecrafter4000.lotrtc.tools.ToolRegistry.ToolEntry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.crafting.StencilBuilder;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.crafting.ToolRecipe;
import tconstruct.library.tools.DynamicToolPart;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.util.IPattern;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tconstruct.tools.TinkerTools.MaterialID;

public class ToolRegistry {

	/* Tools */
	
	public static final List<ToolCore> removeRecipe = new ArrayList<ToolCore>();
	public static final List<ToolEntry> tools = new ArrayList<ToolEntry>();
	
	public static void addTool(String name, ToolCore tool, Item... items){
		tools.add(new ToolEntry(name, tool, items));
	}
	
	public static void removeRecipe(ToolCore tool){
		removeRecipe.add(tool);
	}
	
	public static class ToolEntry{
		public final String name;
		public final ToolCore tool;
		public final Item[] recipe;
		
		public ToolEntry(String name, ToolCore tool, Item... items) {
			this.name = name;
			this.tool = tool;
			this.recipe = items;
		}
	}
	
	/* Tool parts */
	
	/* Integer -> Pattern ID. I use an map instead of an list so I can remove an pattern if necessary. */
	public final static Map<Integer, ToolPartEntry> parts = new HashMap<Integer, ToolPartEntry>();
	public static int nextIndex = 0;

	public final static void addToolPart(DynamicToolPart item, int materialCosts){
		addToolPart(item, materialCosts, "textures/gui/icons.png", true);
	}
	
	public final static void addToolPart(DynamicToolPart item, int materialCosts, String texture, boolean addSmelteryCasting){
		parts.put(nextIndex, new ToolPartEntry(item, materialCosts, addSmelteryCasting, texture));
		nextIndex++;
	}
		
	public static int getPatternCost(int meta) {
		ToolPartEntry e = parts.get(meta);
		if(e == null){
			TinkersMiddleearth.logger.warn("[ToolRegistry] Called getPatternOutput with invalid meta: " + meta + "!");
			TinkersMiddleearth.logger.warn("If you haven't created this pattern with invalid meta using commands, that's an bug.");
			return 0;
		}
		return e.materialCosts;
	}
	
	public static class ToolPartEntry{
		public final Item item;
		public final String texture;
		public final String name;
		public final String domain;
		public final boolean addSmelteryCasting;
		public final int materialCosts;
		
		public final String textureButtonStencil;
		public final int xButtonStencil = 0;
		public final int yButtonStencil = 3;
		public int stencilGuiID;
		
		/* Gets all necessary information from the item */
		public ToolPartEntry(DynamicToolPart item, int materialCosts, boolean addSmelteryCasting, String texture) {
			this.item = item;
			this.texture = item.texture;
			this.name = item.partName;
			this.domain = item.modTexPrefix;
			this.materialCosts = materialCosts;
			this.addSmelteryCasting = addSmelteryCasting;
			this.textureButtonStencil = texture;
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
		
		/* register stencil building */
		for(int i = 0; nextPartIndex < partIndecies.length; i++){
			if(!StencilBuilder.instance.stencils.containsKey(i)) {
				parts.get(partIndecies[nextPartIndex]).stencilGuiID = i;
				StencilBuilder.registerStencil(i, TinkersMEItems.woodPattern, partIndecies[nextPartIndex]);
				nextPartIndex++;
			}
		}
		
		for(ToolPartEntry entry : parts.values()){
			TConstructClientRegistry.addStencilButton2(entry.xButtonStencil, entry.yButtonStencil, entry.stencilGuiID, entry.domain, entry.textureButtonStencil);
		}
	}
}