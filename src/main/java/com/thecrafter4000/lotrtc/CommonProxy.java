package com.thecrafter4000.lotrtc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thecrafter4000.lotrtc.smeltery.FractionSmelteryLogic;
import com.thecrafter4000.lotrtc.smeltery.LotrFilledBucket;
import com.thecrafter4000.lotrtc.smeltery.LotrSmelteryFraction;
import com.thecrafter4000.lotrtc.smeltery.SmelteryRecipeHandler;
import com.thecrafter4000.lotrtc.smeltery.SmelteryRecipes;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.LOTRMod;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.library.crafting.Smeltery;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.smeltery.items.FilledBucket;
import tconstruct.tools.TinkerTools;
import tconstruct.util.config.PHConstruct;

public class CommonProxy {

	public static Item buckets;
	
    public void preInit(FMLPreInitializationEvent e) {
    	GameRegistry.registerItem(buckets = new LotrFilledBucket(mantle.blocks.BlockUtils.getBlockFromItem(buckets)), "buckets");
    	ModBlocks.preInit(e);
    }

    public void init(FMLInitializationEvent e) {
    	ModBlocks.init(e);
    	NetworkRegistry.INSTANCE.registerGuiHandler(LotRTCIntegrator.instance, new GuiHandler());
    	GameRegistry.registerTileEntity(FractionSmelteryLogic.class, "lotrtc:fractionsmelterylogic");
    	patchLotrOres();
    	SmelteryRecipes.registerCasting();
    	SmelteryRecipes.registerAlloys();
    }

    public void postInit(FMLPostInitializationEvent e) {
    	SmelteryRecipes.registerSmelteryStuff();
    }
    
    public static void patchLotrOres(){
    	OreDictionary.registerOre("blockCopper", new ItemStack(LOTRMod.blockOreStorage, 1, 0));
    	OreDictionary.registerOre("blockTin", new ItemStack(LOTRMod.blockOreStorage, 1, 1));
    	OreDictionary.registerOre("blockBronze", new ItemStack(LOTRMod.blockOreStorage, 1, 2));
    	OreDictionary.registerOre("blockSilver", new ItemStack(LOTRMod.blockOreStorage, 1, 3));
    	OreDictionary.registerOre("nuggetObsidian", LOTRMod.obsidianShard);
    }
     
}
