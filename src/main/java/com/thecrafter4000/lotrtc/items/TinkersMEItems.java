package com.thecrafter4000.lotrtc.items;

import com.thecrafter4000.lotrtc.manual.ManualItem;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class TinkersMEItems {
	
	public static Item buckets;
	public static Item manual;
	
	public static void preInit(FMLPreInitializationEvent e) {
		GameRegistry.registerItem(buckets = new LotrFilledBucket(Block.getBlockFromItem(buckets)), "buckets");
		GameRegistry.registerItem(manual = new ManualItem(), "manual");
	}
	
	public static void init(FMLInitializationEvent e) {}
}
