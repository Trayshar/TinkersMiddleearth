package com.thecrafter4000.lotrtc.items;

import com.thecrafter4000.lotrtc.TinkersMEConfig;
import com.thecrafter4000.lotrtc.TinkersMEConfig.LotRMaterialID;
import net.minecraftforge.fluids.Fluid;
import tconstruct.library.tools.ToolMaterial;

import java.util.HashMap;
import java.util.Map;

public class MaterialRegistry {
	public static Map<Integer, Fluid> fluidMap = new HashMap<>();
	public static Map<Integer, String> nameMap = new HashMap<>();
	public static Map<Integer, ToolMaterial> toolMap = new HashMap<>();
	
	private MaterialRegistry() {}

	public static void setup() {
		registerMaterial(LotRMaterialID.MithrilLotR, "MithrilLotR", TinkersMEBlocks.moltenMithrilFluid);
		registerMaterial(LotRMaterialID.DwarvenSteel, "DwarvenSteel", TinkersMEBlocks.moltenDwarvenSteelFluid);
		registerMaterial(LotRMaterialID.BlueDwarvenSteel, "BlueDwarvenSteel", TinkersMEBlocks.moltenBlueDwarvenSteelFluid);
		registerMaterial(LotRMaterialID.ElvenSteel, "ElvenSteel", TinkersMEBlocks.moltenElvenSteelFluid);
		registerMaterial(LotRMaterialID.OrcSteel, "OrcSteel", TinkersMEBlocks.moltenOrcSteelFluid);
		registerMaterial(LotRMaterialID.UrukSteel, "UrukSteel", TinkersMEBlocks.moltenUrukSteelFluid);
		registerMaterial(LotRMaterialID.BlackUrukSteel, "BlackUrukSteel", TinkersMEBlocks.moltenBlackUrukSteelFluid);
		registerMaterial(LotRMaterialID.Mallorn, "Mallorn", null);
		registerMaterial(LotRMaterialID.Blackroot, "Blackroot", null);
	}
	
	public static void registerMaterial(int materialid, String materialName, Fluid fluid, ToolMaterial material){
		nameMap.put(materialid, materialName);
		fluidMap.put(materialid, fluid);
		toolMap.put(materialid, material);
	}
	
	public static void registerMaterial(int materialid, String materialName, Fluid fluid){
		registerMaterial(materialid, materialName, fluid, TinkersMEConfig.getToolMaterial(materialName));
	}

}
