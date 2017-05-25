package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.smeltery.FractionSmelteryLogic;
import com.thecrafter4000.lotrtc.smeltery.LotrSmelteryFraction;
import com.thecrafter4000.lotrtc.smeltery.SmelteryRecipeHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.Smeltery;
import tconstruct.smeltery.items.FilledBucket;

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
    	registerSmelteryStuff();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
    

	public static void registerSmelteryStuff(){
		Smeltery.addMelting(LOTRMod.blockOreStorage, 3, FluidType.getTemperatureByFluid(ModBlocks.moltenSilverFluid), new FluidStack(ModBlocks.moltenSilverFluid, TConstruct.blockLiquidValue));
		Smeltery.addMelting(LOTRMod.rock, 3, FluidType.getTemperatureByFluid(ModBlocks.moltenSarlluinFluid), new FluidStack(ModBlocks.moltenSarlluinFluid, TConstruct.stoneLiquidValue));
		SmelteryRecipeHandler.addMelting(LotrSmelteryFraction.Dwarf, LOTRMod.blockOreStorage, 4, 0, ModBlocks.moltenMithrilFluid, TConstruct.oreLiquidValue);
		SmelteryRecipeHandler.addMelting(LotrSmelteryFraction.Dwarf, LOTRMod.oreMithril, 0, 0, ModBlocks.moltenMithrilFluid, TConstruct.oreLiquidValue);

	}
}
