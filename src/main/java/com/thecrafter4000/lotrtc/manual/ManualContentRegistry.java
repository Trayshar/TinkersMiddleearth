package com.thecrafter4000.lotrtc.manual;

import com.thecrafter4000.lotrtc.items.TinkersMEBlocks;

import lotr.common.LOTRMod;
import mantle.lib.client.MantleClientRegistry;
import net.minecraft.item.ItemStack;
import tconstruct.tools.TinkerTools;

public class ManualContentRegistry {

	public static void init(){
		MantleClientRegistry.registerManualIcon("highelvensmelterycontroller", new ItemStack(TinkersMEBlocks.smelteryHighElves, 1, 0));
		MantleClientRegistry.registerManualIcon("highelvensmelterybrick", new ItemStack(TinkersMEBlocks.smelteryHighElves, 1, 2));
		MantleClientRegistry.registerManualIcon("bluedwarvensteel", new ItemStack(LOTRMod.blueDwarfSteel));
		
		ItemStack searedbrick = new ItemStack(TinkerTools.materials, 1, 2);
		ItemStack[] controller = new ItemStack[]{searedbrick, searedbrick, searedbrick, searedbrick, null, searedbrick, searedbrick, searedbrick, searedbrick};
		MantleClientRegistry.registerManualLargeRecipe("highelvensmelterycontroller", new ItemStack(TinkersMEBlocks.smelteryHighElves, 1, 0), controller);
		MantleClientRegistry.registerManualLargeRecipe("dwarvensmelterycontroller", new ItemStack(TinkersMEBlocks.smelteryDwarven, 1, 0), controller);
		MantleClientRegistry.registerManualLargeRecipe("angmarsmelterycontroller", new ItemStack(TinkersMEBlocks.smelteryAngmar, 1, 0), controller);
	}
	
}
