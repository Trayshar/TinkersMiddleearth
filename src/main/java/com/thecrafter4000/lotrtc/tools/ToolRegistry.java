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
import tconstruct.smeltery.TinkerSmeltery;

import java.util.*;

//TODO: [ToolsV2] Remove gui stuff, have another class for that
public class ToolRegistry {

	/* Tools */
	public static Map<ToolCore, ToolData> tools = new HashMap<>();
	/* Integer -> Pattern ID. I use an map instead of a list so I can remove patterns if necessary. */
	public static Map<Integer, ToolPartEntry> parts = new HashMap<Integer, ToolPartEntry>();
	public static List<ToolPartRenderEntry> partRender = new ArrayList<ToolPartRenderEntry>();
	private static Set<ToolCore> recipeless = new HashSet<>();
	private static int nextIndex = 0;

	/**
	 * Add's an normal tool
	 */
	public static void addTool(ToolCore tool, String name, int buttonX, int buttonY, int guiType, int[] iconX, int[] iconY) {
		addTool(tool, name, "textures/gui/icons.png", buttonX, buttonY, guiType, iconX, iconY, "lotrtc", true, false);
	}

	/**
	 * Add's an tool only crafted on tool forge
	 */
	public static void addTierTwoTool(ToolCore tool, String name, int buttonX, int buttonY, int guiType, int[] iconX, int[] iconY) {
		addTool(tool, name, "textures/gui/icons.png", buttonX, buttonY, guiType, iconX, iconY, "lotrtc", true, true);
	}

	/**
	 *  Add's an tool, but no button
	 */
	public static void addToolWithoutGui(ToolCore tool, String name) {
		addTool(tool, name, "", 0, 0, 0, new int[]{}, new int[]{}, "lotrtc", false, false);
	}

	/**
	 * Internal method
	 */
	private static void addTool(ToolCore tool, String name, String texture, int buttonX, int buttonY, int guiType, int[] iconX, int[] iconY, String domain, boolean addGui, boolean tierTwo) {
		tools.put(tool, new ToolData(name, texture, buttonX, buttonY, guiType, iconX, iconY, domain, addGui, tierTwo, tool.getHeadItem(), tool.getHandleItem(), tool.getAccessoryItem(), tool.getExtraItem()));
	}

	/* Tool parts */

	/**
	 *  Removes all recipes crafting a tool
	 */
	public static void removeRecipe(ToolCore tool) {
		recipeless.add(tool);
	}

	public static void adjustDamage(ToolCore tool, int damage) {
		tools.get(tool).adjustDamage = damage;
	}

	public static void preInit() {
		/* Tools */
		ToolRegistry.tools.forEach((tool, data) -> {
			GameRegistry.registerItem(tool, data.name);
			TConstructRegistry.addToolMapping(tool);
		});

		/* Parts */
		for (Integer i : parts.keySet()) {
			ToolPartEntry e = parts.get(i);
			String low = Character.toLowerCase(e.name.charAt(0)) + e.name.substring(1); // Makes first letter lower cast
			GameRegistry.registerItem(e.item, low);
			TConstructRegistry.addItemToDirectory(low, e.item);
		}
	}

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

	public static void init(){
		/* Tools */

		/* Have to copy the list cause I can't iterate something I'm changing */
		for (ToolRecipe t : new ArrayList<>(ToolBuilder.instance.combos)) {
			if (recipeless.contains(t.getType())) {
				ToolBuilder.instance.recipeList.remove(t.getType().getToolName());
				ToolBuilder.instance.combos.remove(t);
			}
		}
		/* Register recipes */
		ToolRegistry.tools.forEach((tool, data) -> ToolBuilder.addToolRecipe(tool, data.recipe));

		/* Parts & Patterns*/

		/* Creates a list containing all non-metal ids */
		List<Integer> nonMetals = new ArrayList<>(Arrays.asList(0, 1, 3, 4, 5, 6, 7, 8, 9, 17));

		/* Creates an mapping between id and fluid. Used for table casting. */
		HashMap<Integer, Fluid> materials = new HashMap<>(MaterialRegistry.fluidMap);
		int[] liquidDamage = new int[] { 2, 13, 10, 11, 12, 14, 15, 6, 16, 18 }; // ItemStack
		for(int i = 0; i < TinkerSmeltery.liquids.length; i ++){
			materials.put(liquidDamage[i], TinkerSmeltery.liquids[i].getFluid());
		}

		/* Own materials */
		for (Integer i : MaterialRegistry.fluidMap.keySet()) {
			if (MaterialRegistry.fluidMap.get(i) == null) {
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
		LiquidCasting tableCasting = TConstructRegistry.getTableCasting();
		for (Integer pattern : parts.keySet()) {
			ItemStack cast = new ItemStack(TinkersMEItems.metalPattern, 1, pattern);

			tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.ingotLiquidValue), new ItemStack(parts.get(pattern).item, 1, Short.MAX_VALUE), false, 50);
			tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenGoldFluid, TConstruct.ingotLiquidValue * 2), new ItemStack(parts.get(pattern).item, 1, Short.MAX_VALUE), false, 50);

			for (Integer id : materials.keySet()) {
				Fluid fs = materials.get(id);
				if(fs != null) {
					int fluidAmount = TinkersMEItems.metalPattern.getPatternCost(cast) * TConstruct.ingotLiquidValue / 2;
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

	public static void postInit() {
		Integer[] partIndices = parts.keySet().toArray(new Integer[]{});
		int nextPartIndex = 0;

		/* set gui id's */
		for (int i = 0; nextPartIndex < partIndices.length; i++){
			if(!StencilBuilder.instance.stencils.containsKey(i)) {
				parts.get(partIndices[nextPartIndex]).guiID = i;
				StencilBuilder.registerStencil(i, TinkersMEItems.woodPattern, partIndices[nextPartIndex]);
				nextPartIndex++;
			}
		}

		/* Register */
		for(ToolPartEntry entry : parts.values()){
			TConstructClientRegistry.addStencilButton2(entry.xButton, entry.yButton, entry.guiID, entry.domain, entry.textureButton);
		}

		/* Register */
		ToolRegistry.tools.forEach((tool, data) -> {
			if (data.addGui) {
				String desc = String.format("gui.toolstation.%s.desc", tool.getToolName().toLowerCase());
				if (data.tierTwo) {
					TConstructClientRegistry.addTierTwoButton(new ExtendedToolGuiElement(data.guiType, data.xButton, data.yButton, data.xIcon, data.yIcon, tool.getLocalizedToolName(), desc, data.domain, data.textureButton, tool));
				} else {
					TConstructClientRegistry.addToolButton(new ExtendedToolGuiElement(data.guiType, data.xButton, data.yButton, data.xIcon, data.yIcon, tool.getLocalizedToolName(), desc, data.domain, data.textureButton, tool));
				}
			}
		});
	}

	/* Common code */

	public static class ToolData {
		public String name;
		public Integer adjustDamage = null;
		private Item[] recipe;
		private int guiType;
		private String domain;
		private String textureButton;
		private int xButton, yButton;
		private int[] xIcon, yIcon;
		private boolean addGui;
		private boolean tierTwo;
		private boolean removeRecipe = false;

		private ToolData(String name, String texture, int buttonX, int buttonY, int guiType, int[] iconX, int[] iconY, String domain, boolean addGui, boolean tierTwo, Item... items) {
			this.name = name;
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

	public static class ToolPartEntry {
		public String name;
		public boolean addSmelteryCasting;
		public int materialCosts;
		private Item item;
		private String texture;
		private String domain;
		private String textureButton;
		private int xButton;
		private int yButton;
		private int guiID;

		/* Gets all necessary information from the item */
		private ToolPartEntry(DynamicToolPart item, int materialCosts, boolean addSmelteryCasting, String texture, int iconX, int iconY) {
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

	public static class ToolPartRenderEntry {
		public final Item part;
		public final ResourceLocation tex;
		public final int x;
		public final int y;

		private ToolPartRenderEntry(Item part, ResourceLocation tex, int x, int y) {
			this.part = part;
			this.tex = tex;
			this.x = x;
			this.y = y;
		}
	}
}