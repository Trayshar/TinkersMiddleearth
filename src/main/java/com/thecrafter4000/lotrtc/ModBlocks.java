package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.smeltery.FractionSmeltery;
import com.thecrafter4000.lotrtc.smeltery.FractionSmelteryItemBlock;
import com.thecrafter4000.lotrtc.smeltery.LotrSmelteryFraction;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockWineGlass;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.crafting.FluidType;
import tconstruct.smeltery.blocks.SmelteryBlock;

public class ModBlocks {

	public static SmelteryBlock smelteryElves;
	public static SmelteryBlock smelteryDwarven;
	public static SmelteryBlock smelteryOrcish;
	
	/* Normal Smeltery  */
	public static Fluid moltenSilverFluid;
	public static Fluid moltenSarlluinFluid;
	public static Block moltenSarlluin;
	public static Block moltenSilver;

	/* Dwarven Smeltery */
	public static Fluid moltenMithrilFluid;
	public static Fluid moltenBlueDwarvenSteelFluid;
	public static Fluid moltenDwarvenSteelFluid;
	public static Fluid moltenGalvornFluid;
	public static Block moltenMithril;
	public static Block moltenBlueDwarvenSteel;
	public static Block moltenDwarvenSteel;
	public static Block moltenGalvorn;
	
	/* Elven Smeltery */
	public static Fluid moltenElvenSteelFluid;
	public static Block moltenElvenSteel;
	
	/* Orcish Smeltery*/
	public static Fluid moltenMorgulSteelFluid;
	public static Fluid moltenOrcSteelFluid;
	public static Fluid moltenUrukSteelFluid;
	public static Fluid moltenBlackUrukSteelFluid;
	public static Block moltenMorgulSteel;
	public static Block moltenOrcSteel;
	public static Block moltenUrukSteel;
	public static Block moltenBlackUrukSteel;


    public static void preInit(FMLPreInitializationEvent e) {
    	GameRegistry.registerBlock(smelteryElves = new FractionSmeltery(LotrSmelteryFraction.Elf), FractionSmelteryItemBlock.class, "SmelteryElves");
    	GameRegistry.registerBlock(smelteryDwarven = new FractionSmeltery(LotrSmelteryFraction.Dwarf), FractionSmelteryItemBlock.class, "SmelteryDwarven");
    	GameRegistry.registerBlock(smelteryOrcish = new FractionSmeltery(LotrSmelteryFraction.Orc), FractionSmelteryItemBlock.class, "SmelteryOrcish");
    	
    	moltenSilverFluid =  registerFluid("silver_lotr");
    	moltenSilver = moltenSilverFluid.getBlock();
    	
    	moltenSarlluinFluid = registerFluid("sarlluin");
    	moltenSarlluin = moltenSarlluinFluid.getBlock();

    	moltenMithrilFluid = registerFluid("mithril_lotr");
    	moltenMithril = moltenMithrilFluid.getBlock();

    	moltenBlueDwarvenSteelFluid = registerFluid("blue_dwarven_steel");
    	moltenBlueDwarvenSteel = moltenBlueDwarvenSteelFluid.getBlock();

    	moltenDwarvenSteelFluid = registerFluid("dwarven_steel");
    	moltenDwarvenSteel = moltenDwarvenSteelFluid.getBlock();

    	moltenGalvornFluid = registerFluid("galvorn");
    	moltenGalvorn = moltenGalvornFluid.getBlock();

    	moltenElvenSteelFluid = registerFluid("elven_steel");
    	moltenElvenSteel = moltenElvenSteelFluid.getBlock();

    	moltenMorgulSteelFluid = registerFluid("morgul_steel");
    	moltenMorgulSteel = moltenMorgulSteelFluid.getBlock();

    	moltenOrcSteelFluid = registerFluid("orc_steel");
    	moltenOrcSteel = moltenOrcSteelFluid.getBlock();

    	moltenUrukSteelFluid = registerFluid("uruk_steel");
    	moltenUrukSteel = moltenUrukSteelFluid.getBlock();

    	moltenBlackUrukSteelFluid = registerFluid("black_uruk_steel");
    	moltenBlackUrukSteel = moltenBlackUrukSteelFluid.getBlock();
    	
    	FluidType.registerFluidType("Silver", LOTRMod.blockOreStorage, 3, 500, moltenSilverFluid, false);
    	FluidType.registerFluidType("Sarlluin", LOTRMod.rock, 3, 800, moltenSarlluinFluid, true);
    	FluidType.registerFluidType("Mithril", LOTRMod.blockOreStorage, 4, 500, moltenMithrilFluid, true);
    	FluidType.registerFluidType("BlueDwarvenSteel", LOTRMod.blockOreStorage, 15, 700, moltenBlueDwarvenSteelFluid, true);
    	FluidType.registerFluidType("DwarvenSteel", LOTRMod.blockOreStorage, 7, 750, moltenDwarvenSteelFluid, true);
    	FluidType.registerFluidType("Galvorn", LOTRMod.blockOreStorage, 8, 800, moltenGalvornFluid, false);
    	FluidType.registerFluidType("ElvenSteel", LOTRMod.blockOreStorage2, 1, 750, moltenElvenSteelFluid, true);
    	FluidType.registerFluidType("MorgulSteel", LOTRMod.blockOreStorage2, 1, 800, moltenMorgulSteelFluid, false);
    	FluidType.registerFluidType("OrcSteel", LOTRMod.blockOreStorage2, 1, 600, moltenOrcSteelFluid, true);
    	FluidType.registerFluidType("UrukSteel", LOTRMod.blockOreStorage2, 1, 700, moltenUrukSteelFluid, true);
    	FluidType.registerFluidType("BlackUrukSteel", LOTRMod.blockOreStorage2, 0, 800, moltenBlackUrukSteelFluid, true);
    }

    public static void init(FMLInitializationEvent e) {

    }

    /* Copied from TiC cause it's necassary.  */
    
    public static Fluid registerFluid(String name) {
        return registerFluid(name, "liquid_" + name);
    }

    public static Fluid registerFluid(String name, String texture) {
        return registerFluid(name, name + ".molten", "fluid.molten." + name, texture, 3000, 6000, 1300, Material.lava);
    }

    public static Fluid registerFluid(String name, String fluidName, String blockName, String texture, int density, int viscosity, int temperature, Material material) {
        // create the new fluid
        Fluid fluid = new Fluid(fluidName).setDensity(density).setViscosity(viscosity).setTemperature(temperature);

        if(material == Material.lava)
            fluid.setLuminosity(12);

        // register it if it's not already existing
        boolean isFluidPreRegistered = !FluidRegistry.registerFluid(fluid);

        // register our fluid block for the fluid
        // this constructor implicitly does fluid.setBlock to it, that's why it's not called separately
        LotrTCFluid block = new LotrTCFluid(fluid, material, texture);
        block.setBlockName(blockName);
        GameRegistry.registerBlock(block, blockName);

        fluid.setBlock(block);
        block.setFluid(fluid);

        // if the fluid was already registered we use that one instead
        if (isFluidPreRegistered)
        {
            fluid = FluidRegistry.getFluid(fluidName);

            // don't change the fluid icons of already existing fluids
            if(fluid.getBlock() != null)
                block.suppressOverwritingFluidIcons();
            // if no block is registered with an existing liquid, we set our own
            else
                fluid.setBlock(block);
        }


        if (FluidContainerRegistry.fillFluidContainer(new FluidStack(fluid, 1000), new ItemStack(Items.bucket)) == null) {
            // custom hacks for teh lookup. hoooray for inconsintency.
            boolean reg = false;
            for(int i = 0; i < LotrFilledBucket.textureNames2.length; i++)
                if(LotrFilledBucket.textureNames2[i].equals(name)) {
                    FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(fluid, 1000), new ItemStack(CommonProxy.buckets, 1, i), new ItemStack(Items.bucket)));
                    reg = true;
                }

            if(!reg)
                LotRTCIntegrator.logger.error("Couldn't register fluid container for " + name);
        }

        return fluid;
}
}
