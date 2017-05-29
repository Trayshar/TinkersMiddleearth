package com.thecrafter4000.lotrtc.tools;

import com.thecrafter4000.lotrtc.TinkersMEConfig;
import com.thecrafter4000.lotrtc.TinkersMEConfig.LotRMaterialID;
import com.thecrafter4000.lotrtc.TinkersMEConfig.LotRToolStats;
import com.thecrafter4000.lotrtc.TinkersMiddleearth;
import com.thecrafter4000.lotrtc.items.MaterialRegistry;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
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
//		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.planks, 1, 1), 2, "Malorn");
//		PatternBuilder.instance.registerMaterial(new ItemStack(LOTRMod.mallornStick), 1, "Malorn");
	}
	
	public static void registerMaterials(){
		// Tool Materials: id, name, harvestlevel, durability, speed, damage, handlemodifier, reinforced, shoddy, style color, primary color for block use
		TConstructRegistry.addToolMaterial(LotRMaterialID.MithrilLotR, "MithrilLotR", 5, 2400, 900, 5, LotRToolStats.handleModifierMithril, 3, 0f, EnumChatFormatting.WHITE.toString(), 0xededed);
		TConstructRegistry.addToolMaterial(LotRMaterialID.DwarvenSteel, "DwarvenSteel", 3, 700, 900, 3, LotRToolStats.handleModifierDwarf, 2, 0f, EnumChatFormatting.GRAY.toString(), 0x3d3e44);
		TConstructRegistry.addToolMaterial(LotRMaterialID.BlueDwarvenSteel, "BlueDwarvenSteel", 3, 650, 700, 3, LotRToolStats.handleModifierBlueDwarf, 1, 0f, EnumChatFormatting.BLUE.toString(), 0x113b7f);
		TConstructRegistry.addToolMaterial(LotRMaterialID.ElvenSteel, "ElvenSteel", 2, 700, 800, 3, LotRToolStats.handleModifierElf, 2, 0f, EnumChatFormatting.WHITE.toString(), 0xd1cccc);
		TConstructRegistry.addToolMaterial(LotRMaterialID.OrcSteel, "OrcSteel", 2, 400, 600, 2, LotRToolStats.handleModifierOrc, 0, 0.3f, EnumChatFormatting.DARK_GRAY.toString(), 0x3a3838);
		TConstructRegistry.addToolMaterial(LotRMaterialID.UrukSteel, "UrukSteel", 2, 550, 600, 3, LotRToolStats.handleModifierUruk, 1, 0f, EnumChatFormatting.DARK_GRAY.toString(), 0x4c2409);
		TConstructRegistry.addToolMaterial(LotRMaterialID.BlackUrukSteel, "BlackUrukSteel", 3, 570, 700, 3, LotRToolStats.handleModifierBlackUruk, 2, 0f, EnumChatFormatting.BLACK.toString(), 0x000000);
//		TConstructRegistry.addToolMaterial(LotRMaterialID.Mallorn, "Mallorn", 1, 200, 400, 1, LotRToolStats.handleModifierMallorn, 0, 0f, EnumChatFormatting.GOLD.toString(), 0xd1cf8a);
		
//		TConstructRegistry.addDefaultToolPartMaterial(LotRMaterialID.Mallorn);
		
		PatternBuilder pb = PatternBuilder.instance;
//		pb.registerMaterialSet("Mallorn", new ItemStack(LOTRMod.mallornStick, 2), new ItemStack(LOTRMod.mallornStick), LotRMaterialID.Mallorn);
//		pb.registerFullMaterial(new ItemStack(LOTRMod.planks, 1, 1), 2, "Mallorn", new ItemStack(LOTRMod.mallornStick), new ItemStack(LOTRMod.mallornStick), LotRMaterialID.Mallorn);
		for(Integer i : MaterialRegistry.mapIdName.keySet()){
			TConstructRegistry.addDefaultToolPartMaterial(i);
			pb.registerMaterialSet(MaterialRegistry.mapIdName.get(i), new ItemStack(TinkerTools.toolShard, 1, i), new ItemStack(TinkerTools.toolRod, 1, i), i);
		}
	}
	
	public static void registerToolCasting(){
		LiquidCasting tableCasting = TConstructRegistry.instance.getTableCasting();
		
        int fluidAmount = 0;
        Fluid fs = null;

        for (int iter = 0; iter < TinkerTools.patternOutputs.length; iter++)
        {
            if (TinkerTools.patternOutputs[iter] != null)
            {
                ItemStack cast = new ItemStack(TinkerSmeltery.metalPattern, 1, iter + 1);

                tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.ingotLiquidValue), new ItemStack(TinkerTools.patternOutputs[iter], 1, Short.MAX_VALUE), false, 50);
                tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenGoldFluid, TConstruct.ingotLiquidValue * 2), new ItemStack(TinkerTools.patternOutputs[iter], 1, Short.MAX_VALUE), false, 50);

                for (Integer id : MaterialRegistry.mapIdName.keySet())
                {
                	fs = MaterialRegistry.mapfluids.get(id);
                	if(fs != null){
	                    fluidAmount = ((IPattern) TinkerSmeltery.metalPattern).getPatternCost(cast) * TConstruct.ingotLiquidValue / 2;
	                    ItemStack metalCast = new ItemStack(TinkerTools.patternOutputs[iter], 1, id);
	                    tableCasting.addCastingRecipe(metalCast, new FluidStack(fs, fluidAmount), cast, 50);
	                    Smeltery.addMelting(FluidType.getFluidType(fs), metalCast, 0, fluidAmount);
                	}else 
//                		if(id != LotRMaterialID.Mallorn) 
                		TinkersMiddleearth.logger.warn("MaterialRegistry: ID " + id + " has no fluid accosiated!");
                }
            }
        }
	}
}
