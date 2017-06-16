package com.thecrafter4000.lotrtc.items;

import java.lang.reflect.Field;

import com.thecrafter4000.lotrtc.TinkersMiddleearth;
import com.thecrafter4000.lotrtc.manual.ManualItem;
import com.thecrafter4000.lotrtc.tools.LotRBattleAxe;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.crafting.ToolRecipe;
import tconstruct.library.tools.ToolCore;
import tconstruct.tools.TinkerTools;

public class TinkersMEItems {
	
	public static Item buckets;
	public static Item manual;
	
	public static LotRBattleAxe battleaxe;
	
	public static void preInit(FMLPreInitializationEvent e) {
		GameRegistry.registerItem(buckets = new LotrFilledBucket(Block.getBlockFromItem(buckets)), "buckets");
		GameRegistry.registerItem(manual = new ManualItem(), "manual");
	
		battleaxe = new LotRBattleAxe();
		GameRegistry.registerItem(battleaxe, "battleaxe");
		TConstructRegistry.addToolMapping(battleaxe);
	}
	
	public static void init(FMLInitializationEvent e) {
		GameRegistry.addShapelessRecipe(new ItemStack(TinkersMEItems.manual, 1, 1), new ItemStack(TinkersMEItems.manual));
		
		System.out.println(TinkerTools.battleaxe.getClass().getSimpleName());
		
		for(ToolRecipe t : ToolBuilder.instance.combos){
			if(t.getType() == TinkerTools.battleaxe) {
				ToolBuilder.instance.combos.remove(t);
	        	ToolBuilder.instance.recipeList.remove(TinkerTools.battleaxe.getToolName());
	    		ToolBuilder.instance.addNormalToolRecipe(TinkersMEItems.battleaxe, TinkerTools.broadAxeHead, TinkerTools.toughRod, TinkerTools.broadAxeHead, TinkerTools.toughBinding);
		        TinkersMiddleearth.logger.info("Replaced recipe for battleaxe");
	        	break;
	        }
		}
		
		System.out.println(TinkerTools.battleaxe.getClass().getSimpleName());
	}
	
	public static void postInit(FMLPostInitializationEvent e){}
}
