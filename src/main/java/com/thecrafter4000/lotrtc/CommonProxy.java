package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.client.StencilGuiEventHandler;
import com.thecrafter4000.lotrtc.items.MaterialRegistry;
import com.thecrafter4000.lotrtc.items.TinkersMEBlocks;
import com.thecrafter4000.lotrtc.items.TinkersMEItems;
import com.thecrafter4000.lotrtc.smeltery.FractionSmelteryLogic;
import com.thecrafter4000.lotrtc.smeltery.SmelteryRecipes;
import com.thecrafter4000.lotrtc.tools.HitDelayPatcher;
import com.thecrafter4000.lotrtc.tools.ToolRecipes;
import com.thecrafter4000.lotrtc.tools.ToolRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.items.tools.Battleaxe;
import tconstruct.items.tools.Longsword;
import tconstruct.library.ActiveToolMod;
import tconstruct.library.TConstructRegistry;
import tconstruct.tools.TinkerTools;

public class CommonProxy {

	public static final CreativeTabs LotRTiCTab = new CreativeTabs("lotrtc") {
		
		@Override
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
    	
    	StencilGuiEventHandler.register();
    }

    public void init(FMLInitializationEvent e) {
    	GameRegistry.registerTileEntity(FractionSmelteryLogic.class, "lotrtc:fractionsmelterylogic");
    	patchLotrOres();
    	TinkersMEBlocks.init(e);
    	TinkersMEItems.init(e);
    	ToolRegistry.init();
    	SmelteryRecipes.registerCasting();
    	ToolRecipes.registerToolCasting();
    	SmelteryRecipes.registerSmelteryMeltings();
    	SmelteryRecipes.registerAlloys();
    }

    public void postInit(FMLPostInitializationEvent e) {
    	ToolRecipes.registerMetals();
    	TinkersMEItems.postInit(e);
    	ToolRegistry.postInit();
    }
    
    public static void patchLotrOres(){
    	OreDictionary.registerOre("blockCopper", new ItemStack(LOTRMod.blockOreStorage, 1, 0));
    	OreDictionary.registerOre("blockTin", new ItemStack(LOTRMod.blockOreStorage, 1, 1));
    	OreDictionary.registerOre("blockBronze", new ItemStack(LOTRMod.blockOreStorage, 1, 2));
    	OreDictionary.registerOre("blockSilver", new ItemStack(LOTRMod.blockOreStorage, 1, 3));
    	OreDictionary.registerOre("nuggetObsidian", LOTRMod.obsidianShard);
    	OreDictionary.registerOre("Mallornrod", LOTRMod.mallornStick);
    }
}
