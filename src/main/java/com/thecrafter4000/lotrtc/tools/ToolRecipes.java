package com.thecrafter4000.lotrtc.tools;

import com.thecrafter4000.lotrtc.TinkersMEConfig.LotRMaterialID;
import com.thecrafter4000.lotrtc.TinkersMiddleearth;
import com.thecrafter4000.lotrtc.items.MaterialRegistry;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.util.IPattern;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;

public class ToolRecipes {

	public static void registerMetals(){
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.mithril), 2, "MithrilLotR");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.blockOreStorage, 1, 4), 18, "MithrilLotR");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.dwarfSteel), 2, "DwarvenSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.blockOreStorage, 1, 7), 18, "DwarvenSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.blueDwarfSteel), 2, "BlueDwarfenSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.blockOreStorage, 1, 15), 18, "BlueDwarfenSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.elfSteel), 2, "ElvenSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.blockOreStorage2, 1, 1), 18, "ElvenSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.orcSteel), 2, "OrcSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.blockOreStorage, 1, 5), 18, "OrcSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.urukSteel), 2, "UrukSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.blockOreStorage, 1, 9), 18, "UrukSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.blackUrukSteel), 2, "BlackUrukSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.blockOreStorage2), 18, "BlackUrukSteel");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.planks, 1, 1), 2, "Mallorn");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.mallornStick), 1, "Mallorn");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.blackroot), 2, "Blackroot");
		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.blackrootStick), 1, "Blackroot");
	}
	
	public static void registerMaterials(){
		for(Integer i : MaterialRegistry.mapIdName.keySet()){
			TConstructRegistry.addtoolMaterial(i, MaterialRegistry.mapTool.get(i));
			TConstructRegistry.addDefaultToolPartMaterial(i);
			if(MaterialRegistry.mapfluids.containsKey(i)) PatternBuilder.instance.registerMaterialSet(MaterialRegistry.mapIdName.get(i), new ItemStack(TinkerTools.toolShard, 1, i), new ItemStack(TinkerTools.toolRod, 1, i), i);
		}
		PatternBuilder.instance.registerFullMaterial(new ItemStack(LOTRMod.planks, 1, 1), 2, "Mallorn", new ItemStack(LOTRMod.mallornStick), new ItemStack(LOTRMod.mallornStick), LotRMaterialID.Mallorn);
		PatternBuilder.instance.registerFullMaterial(new ItemStack(LOTRMod.blackroot), 2, "Blackroot", new ItemStack(LOTRMod.blackrootStick), new ItemStack(LOTRMod.blackrootStick), LotRMaterialID.Blackroot);
	}
	
	public static void registerToolCasting(){
		LiquidCasting tableCasting = TConstructRegistry.instance.getTableCasting();

        for (int iter = 0; iter < TinkerTools.patternOutputs.length; iter++)
        {
            if (TinkerTools.patternOutputs[iter] != null)
            {
                ItemStack cast = new ItemStack(TinkerSmeltery.metalPattern, 1, iter + 1);

                tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.ingotLiquidValue), new ItemStack(TinkerTools.patternOutputs[iter], 1, Short.MAX_VALUE), false, 50);
                tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenGoldFluid, TConstruct.ingotLiquidValue * 2), new ItemStack(TinkerTools.patternOutputs[iter], 1, Short.MAX_VALUE), false, 50);

                for (Integer id : MaterialRegistry.mapIdName.keySet())
                {
                	Fluid fs = MaterialRegistry.mapfluids.get(id);
                	if(fs != null){
                		int fluidAmount = ((IPattern) TinkerSmeltery.metalPattern).getPatternCost(cast) * TConstruct.ingotLiquidValue / 2;
	                    ItemStack metalCast = new ItemStack(TinkerTools.patternOutputs[iter], 1, id);
	                    tableCasting.addCastingRecipe(metalCast, new FluidStack(fs, fluidAmount), cast, 50);
	                    Smeltery.addMelting(FluidType.getFluidType(fs), metalCast, 0, fluidAmount);
                	}else {
                        TConstructRegistry.addPartMapping(TinkerTools.woodPattern, iter + 1, id, new ItemStack(TinkerTools.patternOutputs[iter], 1, id));
                	}
                }
            }
        }
	}
}
