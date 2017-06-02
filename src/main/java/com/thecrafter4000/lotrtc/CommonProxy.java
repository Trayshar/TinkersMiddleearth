package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.items.MaterialRegistry;
import com.thecrafter4000.lotrtc.items.TinkersMEBlocks;
import com.thecrafter4000.lotrtc.items.TinkersMEItems;
import com.thecrafter4000.lotrtc.smeltery.FractionSmelteryLogic;
import com.thecrafter4000.lotrtc.smeltery.SmelteryRecipes;
import com.thecrafter4000.lotrtc.tools.ToolRecipes;

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
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy {

	public static final CreativeTabs LotRTiCTab = new CreativeTabs("lotrtc") {
		
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(TinkersMEBlocks.smelteryHighElves);
		}
	};
	
    public void preInit(FMLPreInitializationEvent e) {
    	TinkersMEConfig.config = new Configuration(e.getSuggestedConfigurationFile());
    	TinkersMEConfig.load();
    	TinkersMEItems.preInit(e);
    	TinkersMEBlocks.preInit(e);
    	MaterialRegistry.setup();
    	ToolRecipes.registerMaterials();
    	TinkersMEEvents eh = new TinkersMEEvents();
    	FMLCommonHandler.instance().bus().register(eh);
    	MinecraftForge.EVENT_BUS.register(eh);
    }

    public void init(FMLInitializationEvent e) {
    	GameRegistry.registerTileEntity(FractionSmelteryLogic.class, "lotrtc:fractionsmelterylogic");
    	patchLotrOres();
    	TinkersMEBlocks.init(e);
    	TinkersMEItems.init(e);
    	SmelteryRecipes.registerCasting();
    	ToolRecipes.registerToolCasting();
    	SmelteryRecipes.registerSmelteryMeltings();
    	SmelteryRecipes.registerAlloys();
    }

    public void postInit(FMLPostInitializationEvent e) {
    	ToolRecipes.registerMetals();
    }
    
    public static void patchLotrOres(){
    	OreDictionary.registerOre("blockCopper", new ItemStack(LOTRMod.blockOreStorage, 1, 0));
    	OreDictionary.registerOre("blockTin", new ItemStack(LOTRMod.blockOreStorage, 1, 1));
    	OreDictionary.registerOre("blockBronze", new ItemStack(LOTRMod.blockOreStorage, 1, 2));
    	OreDictionary.registerOre("blockSilver", new ItemStack(LOTRMod.blockOreStorage, 1, 3));
    	OreDictionary.registerOre("nuggetObsidian", LOTRMod.obsidianShard);
    }
}
