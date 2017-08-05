package com.thecrafter4000.lotrtc.manual;

import com.thecrafter4000.lotrtc.items.TinkersMEBlocks;
import com.thecrafter4000.lotrtc.items.TinkersMEItems;

import lotr.common.LOTRMod;
import mantle.lib.client.MantleClientRegistry;
import net.minecraft.item.ItemStack;
import tconstruct.tools.TinkerTools;

public class ManualContentRegistry {

	public static void init(){
		/* First book */
		MantleClientRegistry.registerManualIcon("highelvensmelterycontroller", new ItemStack(TinkersMEBlocks.smelteryHighElves, 1, 0));
		MantleClientRegistry.registerManualIcon("highelvensmelterybrick", new ItemStack(TinkersMEBlocks.smelteryHighElves, 1, 2));
		MantleClientRegistry.registerManualIcon("bluedwarvensteel", new ItemStack(LOTRMod.blueDwarfSteel));
		
		ItemStack searedbrick = new ItemStack(TinkerTools.materials, 1, 2);
		ItemStack[] controller = new ItemStack[]{searedbrick, searedbrick, searedbrick, searedbrick, null, searedbrick, searedbrick, searedbrick, searedbrick};
		MantleClientRegistry.registerManualLargeRecipe("highelvensmelterycontroller", new ItemStack(TinkersMEBlocks.smelteryHighElves, 1, 0), controller);
		MantleClientRegistry.registerManualLargeRecipe("dwarvensmelterycontroller", new ItemStack(TinkersMEBlocks.smelteryDwarven, 1, 0), controller);
		MantleClientRegistry.registerManualLargeRecipe("angmarsmelterycontroller", new ItemStack(TinkersMEBlocks.smelteryAngmar, 1, 0), controller);
		/* Second book */
		MantleClientRegistry.registerManualIcon("mithrilsword", new ItemStack(LOTRMod.swordMithril));
		MantleClientRegistry.registerManualIcon("mithrilingot", new ItemStack(LOTRMod.mithril));
		MantleClientRegistry.registerManualIcon("dwarvenwarhammer", new ItemStack(LOTRMod.hammerDwarven));
		MantleClientRegistry.registerManualIcon("dwarveningot", new ItemStack(LOTRMod.dwarfSteel));
		MantleClientRegistry.registerManualIcon("bluedwarvenwarhammer", new ItemStack(LOTRMod.hammerBlueDwarven));
		MantleClientRegistry.registerManualIcon("bluedwarveningot", new ItemStack(LOTRMod.blueDwarfSteel));
		MantleClientRegistry.registerManualIcon("elvensword", new ItemStack(LOTRMod.swordHighElven));
		MantleClientRegistry.registerManualIcon("elveningot", new ItemStack(LOTRMod.elfSteel));
		MantleClientRegistry.registerManualIcon("mordorpickaxe", new ItemStack(LOTRMod.pickaxeOrc));
		MantleClientRegistry.registerManualIcon("orcingot", new ItemStack(LOTRMod.orcSteel));
		MantleClientRegistry.registerManualIcon("urukhelmet", new ItemStack(LOTRMod.helmetUruk));
		MantleClientRegistry.registerManualIcon("urukingot", new ItemStack(LOTRMod.urukSteel));
		MantleClientRegistry.registerManualIcon("blackurukhelmet", new ItemStack(LOTRMod.helmetBlackUruk));
		MantleClientRegistry.registerManualIcon("blackurukingot", new ItemStack(LOTRMod.blackUrukSteel));
		
		
		MantleClientRegistry.registerManualLargeRecipe("autosmelt", new ItemStack(TinkersMEItems.autosmelt), new ItemStack[]{null, new ItemStack(LOTRMod.blackUrukSteel), null, new ItemStack(LOTRMod.blackUrukSteel), new ItemStack(LOTRMod.balrogFire), new ItemStack(LOTRMod.blackUrukSteel), null, new ItemStack(LOTRMod.blackUrukSteel), null});

	}
}
