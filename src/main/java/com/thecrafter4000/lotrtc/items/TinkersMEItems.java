package com.thecrafter4000.lotrtc.items;

import com.thecrafter4000.lotrtc.manual.ManualItem;
import com.thecrafter4000.lotrtc.tools.LotRBattleAxe;
import com.thecrafter4000.lotrtc.tools.LotRPattern;
import com.thecrafter4000.lotrtc.tools.ToolRegistry;
import com.thecrafter4000.lotrtc.tools.Warhammer;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.tools.DynamicToolPart;
import tconstruct.tools.TinkerTools;

public class TinkersMEItems {
	
	public static final ResourceLocation icons = new ResourceLocation("lotrtc", "textures/gui/icons.png");
	public static final ResourceLocation ticons = new ResourceLocation("tinker", "textures/gui/icons.png");
	
	public static Item buckets;
	public static Item manual;
	// Patterns
	public static LotRPattern woodPattern;
	public static LotRPattern metalPattern;
	// Tools
	public static LotRBattleAxe battleaxe;
	public static Warhammer warhammer;
	// Tool parts
	public static Item warHammerHead;
	
	public static void preInit(FMLPreInitializationEvent e) {
		GameRegistry.registerItem(buckets = new LotrFilledBucket(Block.getBlockFromItem(buckets)), "buckets");
		GameRegistry.registerItem(manual = new ManualItem(), "manual");
		
//		GameRegistry.addRecipe(new ItemStack(TinkerTools.materials, 1, 26), new Object[]{" # ", "#b#", " # ", '#', new ItemStack(TinkerTools.materials, 1, 25), 'b', LOTRMod.emerald}); // Silky jewel
		
		// Tool parts
		ToolRegistry.addToolPart((DynamicToolPart) (warHammerHead = new DynamicToolPart("_warhammer_head", "WarHammerHead", "lotrtc").setUnlocalizedName("lotrtc.WarHammerHead")), 8, 0, 1);
		ToolRegistry.addToolPartRender(warHammerHead, icons, 0, 1);
		// Tools
		ToolRegistry.addTierTwoTool(warhammer = new Warhammer(), "warhammer", 0, 0, 1, new int[]{0, 8, 9, 0}, new int[]{0, 3, 3, 0});
		ToolRegistry.addToolWithoutGui(battleaxe = new LotRBattleAxe(), "battleaxe");
		// Patterns
		GameRegistry.registerItem(woodPattern = new LotRPattern("pattern_", "lotrtc.WoodPattern"), "woodPattern");
		GameRegistry.registerItem(metalPattern = new LotRPattern("cast_", "lotrtc.MetalPattern"), "metalPattern");
	}
	
	public static void init(FMLInitializationEvent e) {
		GameRegistry.addShapelessRecipe(new ItemStack(TinkersMEItems.manual, 1, 1), new ItemStack(TinkersMEItems.manual));
		PatternBuilder.instance.addToolPattern(woodPattern);
		ToolRegistry.removeRecipe(TinkerTools.battleaxe);
		ToolRegistry.adjustDamage(battleaxe, 1);
	}
	
	public static void postInit(FMLPostInitializationEvent e){}
}
