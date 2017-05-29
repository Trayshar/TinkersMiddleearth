package com.thecrafter4000.lotrtc.items;

import java.util.HashMap;
import java.util.Map;

import com.thecrafter4000.lotrtc.TinkersMEConfig.LotRMaterialID;

import net.minecraftforge.fluids.Fluid;

public class MaterialRegistry {

	public static Map<Integer, Fluid>  mapfluids= new HashMap<Integer, Fluid>();
	public static Map<Integer, String> mapIdName = new HashMap<Integer, String>(20);
	public static Map<String, Integer> mapNameId = new HashMap<String, Integer>(20);
	
	private MaterialRegistry() {}
	
	public static final void setup(){
		registerMaterial(LotRMaterialID.MithrilLotR, "MithrilLotR", TinkersMEBlocks.moltenMithrilFluid);
		registerMaterial(LotRMaterialID.DwarvenSteel, "DwarvenSteel", TinkersMEBlocks.moltenDwarvenSteelFluid);
		registerMaterial(LotRMaterialID.BlueDwarvenSteel, "BlueDwarvenSteel", TinkersMEBlocks.moltenBlueDwarvenSteelFluid);
		registerMaterial(LotRMaterialID.ElvenSteel, "ElvenSteel", TinkersMEBlocks.moltenElvenSteelFluid);
		registerMaterial(LotRMaterialID.OrcSteel, "OrcSteel", TinkersMEBlocks.moltenOrcSteelFluid);
		registerMaterial(LotRMaterialID.UrukSteel, "UrukSteel", TinkersMEBlocks.moltenUrukSteelFluid);
		registerMaterial(LotRMaterialID.BlackUrukSteel, "BlackUrukSteel", TinkersMEBlocks.moltenBlackUrukSteelFluid);
		registerMaterial(LotRMaterialID.Mallorn, "Mallorn");
	}
	
	private static void registerMaterial(int materialid, String materialName, Fluid fluid){
		mapIdName.put(materialid, materialName);
		mapNameId.put(materialName, materialid);
		mapfluids.put(materialid, fluid);
	}
	
	private static void registerMaterial(int materialid, String materialName){
		registerMaterial(materialid, materialName, null);
	}
}
