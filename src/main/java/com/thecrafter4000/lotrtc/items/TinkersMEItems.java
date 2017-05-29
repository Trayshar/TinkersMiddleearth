package com.thecrafter4000.lotrtc.items;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class TinkersMEItems {
	
	public static Item buckets;
	public static Item morgulBone;
	
	public static void preInit(FMLPreInitializationEvent e) {
		GameRegistry.registerItem(buckets = new LotrFilledBucket(Block.getBlockFromItem(buckets)), "buckets");
	}
	
	public static void init(FMLInitializationEvent e) {}
}
