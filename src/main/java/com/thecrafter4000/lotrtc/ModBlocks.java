package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.smeltery.FractionSmeltery;
import com.thecrafter4000.lotrtc.smeltery.FractionSmelteryItemBlock;
import com.thecrafter4000.lotrtc.smeltery.LotrFilledBucket;
import com.thecrafter4000.lotrtc.smeltery.LotrSmelteryFraction;
import com.thecrafter4000.lotrtc.smeltery.LotrTCFluid;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockWineGlass;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.crafting.FluidType;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.smeltery.blocks.SmelteryBlock;

public class ModBlocks {

	public static SmelteryBlock smelteryHighElves;
	public static SmelteryBlock smelteryDwarven;
	public static SmelteryBlock smelteryAngmar;
	
	/* Normal Smeltery  */
	public static Fluid moltenSarlluinFluid;
	public static Fluid moltenCoalFluid;
	public static Block moltenCoal;
	public static Block moltenSarlluin;

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
	public static Fluid moltenEdhelmirFluid;
	public static Block moltenElvenSteel;
	public static Block moltenEdhelmir;
	
	/* Orcish Smeltery*/
	public static Fluid moltenMorgulSteelFluid;
	public static Fluid moltenOrcSteelFluid;
	public static Fluid moltenUrukSteelFluid;
	public static Fluid moltenBlackUrukSteelFluid;
	public static Fluid moltenDurnaurFluid;
	public static Fluid moltenGuldurilFluid;
	public static Block moltenMorgulSteel;
	public static Block moltenOrcSteel;
	public static Block moltenUrukSteel;
	public static Block moltenBlackUrukSteel;
	public static Block moltenDurnaur;
	public static Block moltenGulduril;


    public static void preInit(FMLPreInitializationEvent e) {
    	GameRegistry.registerBlock(smelteryHighElves = new FractionSmeltery(LotrSmelteryFraction.Elf, "highelves"), FractionSmelteryItemBlock.class, "SmelteryHighElves");
    	GameRegistry.registerBlock(smelteryDwarven = new FractionSmeltery(LotrSmelteryFraction.Dwarf, "dwarven"), FractionSmelteryItemBlock.class, "SmelteryDwarven");
    	GameRegistry.registerBlock(smelteryAngmar = new FractionSmeltery(LotrSmelteryFraction.Orc, "angmar"), FractionSmelteryItemBlock.class, "SmelteryAngmar");
    	
    	moltenSarlluinFluid = registerFluid("sarlluin");
    	moltenSarlluin = moltenSarlluinFluid.getBlock();

    	moltenCoalFluid = registerFluid("coal");
    	moltenCoal = moltenCoalFluid.getBlock();
    	
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
    	
    	moltenEdhelmirFluid = registerFluid("edhelmir");
    	moltenEdhelmir = moltenEdhelmirFluid.getBlock();

    	moltenMorgulSteelFluid = registerFluid("morgul_steel");
    	moltenMorgulSteel = moltenMorgulSteelFluid.getBlock();

    	moltenOrcSteelFluid = registerFluid("orc_steel");
    	moltenOrcSteel = moltenOrcSteelFluid.getBlock();

    	moltenUrukSteelFluid = registerFluid("uruk_steel");
    	moltenUrukSteel = moltenUrukSteelFluid.getBlock();

    	moltenBlackUrukSteelFluid = registerFluid("black_uruk_steel");
    	moltenBlackUrukSteel = moltenBlackUrukSteelFluid.getBlock();
    	
    	moltenDurnaurFluid = registerFluid("durnaur");
    	moltenDurnaur = moltenDurnaurFluid.getBlock();
    	
    	moltenGuldurilFluid = registerFluid("gulduril");
    	moltenGulduril = moltenGuldurilFluid.getBlock();
    	
    	FluidType.registerFluidType("Silver", LOTRMod.blockOreStorage, 3, 500, TinkerSmeltery.moltenSilverFluid, false);
    	FluidType.registerFluidType("Sarlluin", LOTRMod.rock, 3, 800, moltenSarlluinFluid, false);
    	FluidType.registerFluidType("Coal", Blocks.coal_block, 0, 310, moltenCoalFluid, false);
    	FluidType.registerFluidType("MithrilLotR", LOTRMod.blockOreStorage, 4, 1000, moltenMithrilFluid, true);
    	FluidType.registerFluidType("BlueDwarvenSteel", LOTRMod.blockOreStorage, 15, 700, moltenBlueDwarvenSteelFluid, true);
    	FluidType.registerFluidType("DwarvenSteel", LOTRMod.blockOreStorage, 7, 900, moltenDwarvenSteelFluid, true);
    	FluidType.registerFluidType("Galvorn", LOTRMod.blockOreStorage, 8, 800, moltenGalvornFluid, false);
    	FluidType.registerFluidType("ElvenSteel", LOTRMod.blockOreStorage2, 1, 750, moltenElvenSteelFluid, true);
    	FluidType.registerFluidType("Edhelmir", LOTRMod.blockOreStorage, 6, 500, moltenEdhelmirFluid, false);
    	FluidType.registerFluidType("MorgulSteel", LOTRMod.blockOreStorage, 12, 800, moltenMorgulSteelFluid, false);
    	FluidType.registerFluidType("OrcSteel", LOTRMod.blockOreStorage, 5, 600, moltenOrcSteelFluid, true);
    	FluidType.registerFluidType("UrukSteel", LOTRMod.blockOreStorage, 9, 700, moltenUrukSteelFluid, true);
    	FluidType.registerFluidType("BlackUrukSteel", LOTRMod.blockOreStorage2, 0, 800, moltenBlackUrukSteelFluid, true);
    	FluidType.registerFluidType("Durnaur", LOTRMod.blockOreStorage, 10, 600, moltenDurnaurFluid, false);
    	FluidType.registerFluidType("Gulduril", LOTRMod.blockOreStorage, 11, 500, moltenGuldurilFluid, false);
    }

    public static void init(FMLInitializationEvent e) {}

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
                LotRTCIntegrator.logger.error("Couldn't register fluid container for " + name + "! Please report this to the mod author!");
        }

        return fluid;
}
}
