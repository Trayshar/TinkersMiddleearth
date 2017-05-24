package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.smeltery.FractionSmeltery;
import com.thecrafter4000.lotrtc.smeltery.FractionSmelteryItemBlock;
import com.thecrafter4000.lotrtc.smeltery.SmelteryFraction;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import tconstruct.smeltery.blocks.SmelteryBlock;
import tconstruct.smeltery.itemblocks.SmelteryItemBlock;

public class ModBlocks {

	public static SmelteryBlock smelteryElves;
	
    public static void preInit(FMLPreInitializationEvent e) {
    	GameRegistry.registerBlock(smelteryElves = new FractionSmeltery(SmelteryFraction.Elf), FractionSmelteryItemBlock.class, "SmelteryElves");
    }

    public static void init(FMLInitializationEvent e) {

    }
}
