package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.items.MaterialRegistry;
import com.thecrafter4000.lotrtc.items.TinkersMEBlocks;
import com.thecrafter4000.lotrtc.items.TinkersMEItems;
import com.thecrafter4000.lotrtc.smeltery.FactionSmelteryLogic;
import com.thecrafter4000.lotrtc.smeltery.SmelteryRecipes;
import com.thecrafter4000.lotrtc.tools.HitDelayPatcher;
import com.thecrafter4000.lotrtc.tools.ToolRecipes;
import com.thecrafter4000.lotrtc.tools.ToolRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.LOTRMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy {

	public static final CreativeTabs LotRTiCTab = new CreativeTabs("lotrtc") {
		public Item getTabIconItem() {
			return Item.getItemFromBlock(TinkersMEBlocks.smelteryHighElves);
		}
	};

	public void preInit(FMLPreInitializationEvent e) {
    	HitDelayPatcher.patch();
//    	TinkersMEConfig.config = new Configuration(e.getSuggestedConfigurationFile());
//    	TinkersMEConfig.load();
    	TinkersMEItems.preInit(e);
    	TinkersMEBlocks.preInit(e);
    	ToolRegistry.preInit();
    	MaterialRegistry.setup();
    	ToolRecipes.registerMaterials();
    	
    	TinkersMEEvents eventHandler = new TinkersMEEvents();
    	FMLCommonHandler.instance().bus().register(eventHandler);
    	MinecraftForge.EVENT_BUS.register(eventHandler);
    }

    public void init(FMLInitializationEvent e) {
		//TODO [SmelteryV2] Yes. there is a typo. Sadly to late, change this will invalidate all smelteries. Might want to do exactly that when Smeltery Update rolls out.
		GameRegistry.registerTileEntity(FactionSmelteryLogic.class, "lotrtc:fractionsmelterylogic");
    	patchLotrOres();
    	TinkersMEBlocks.init(e);
    	TinkersMEItems.init(e);
    	ToolRegistry.init();
    	SmelteryRecipes.registerCasting();
    	ToolRecipes.registerToolCasting();
    	SmelteryRecipes.registerSmelteryMeltings();
    	SmelteryRecipes.registerAlloys();

/* Thanks god, this stuff got never released; Very unbalanced
		// Modifiers. Will get their own class some day.
        ModifyBuilder.registerModifier(new ModDurability(new ItemStack[] { new ItemStack(LOTRMod.diamond) }, 0, 500, 0f, 4, "Diamond", "\u00a7b" + StatCollector.translateToLocal("modifier.tool.diamond"), "\u00a7b"));
        ModifyBuilder.registerModifier(new ModDurability(new ItemStack[] { new ItemStack(LOTRMod.emerald) }, 1, 0, 0.5f, 3, "Emerald", "\u00a72" + StatCollector.translateToLocal("modifier.tool.emerald"), "\u00a72"));

        ModifyBuilder.registerModifier(new ModExtraModifier(new ItemStack[] { new ItemStack(LOTRMod.emerald), new ItemStack(Blocks.gold_block) }, "Tier1Free"));
        ModifyBuilder.registerModifier(new ModExtraModifier(new ItemStack[] { new ItemStack(LOTRMod.blockGem, 1, 9), new ItemStack(LOTRMod.blockGem, 1, 5) }, "Tier1.5Free"));
*/
    }

    public void postInit(FMLPostInitializationEvent e) {
    	ToolRecipes.registerMetals();
    	TinkersMEItems.postInit(e);
    	ToolRegistry.postInit();
    }
    
    public static void patchLotrOres() {
    	OreDictionary.registerOre("blockCopper", new ItemStack(LOTRMod.blockOreStorage, 1, 0));
    	OreDictionary.registerOre("blockTin", new ItemStack(LOTRMod.blockOreStorage, 1, 1));
    	OreDictionary.registerOre("blockBronze", new ItemStack(LOTRMod.blockOreStorage, 1, 2));
    	OreDictionary.registerOre("blockSilver", new ItemStack(LOTRMod.blockOreStorage, 1, 3));
    	OreDictionary.registerOre("nuggetObsidian", LOTRMod.obsidianShard);
    	OreDictionary.registerOre("Mallornrod", LOTRMod.mallornStick);
    }
}
