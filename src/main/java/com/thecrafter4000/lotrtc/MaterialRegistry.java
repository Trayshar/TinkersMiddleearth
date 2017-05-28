package com.thecrafter4000.lotrtc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thecrafter4000.lotrtc.LotRTCConfig.LotRMaterialID;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class MaterialRegistry {

	public static Map<Integer, Fluid>  mapfluids= new HashMap<Integer, Fluid>();
	public static Map<Integer, String> mapIdName = new HashMap<Integer, String>(20);
	public static Map<String, Integer> mapNameId = new HashMap<String, Integer>(20);
	
	private MaterialRegistry() {}
	
	public static final void setup(){
		registerMaterial(LotRMaterialID.MithrilLotR, "MithrilLotR", ModBlocks.moltenMithrilFluid);
		registerMaterial(LotRMaterialID.DwarvenSteel, "DwarvenSteel", ModBlocks.moltenDwarvenSteelFluid);
		registerMaterial(LotRMaterialID.BlueDwarvenSteel, "BlueDwarvenSteel", ModBlocks.moltenBlueDwarvenSteelFluid);
		registerMaterial(LotRMaterialID.ElvenSteel, "ElvenSteel", ModBlocks.moltenElvenSteelFluid);
		registerMaterial(LotRMaterialID.OrcSteel, "OrcSteel", ModBlocks.moltenOrcSteelFluid);
		registerMaterial(LotRMaterialID.UrukSteel, "UrukSteel", ModBlocks.moltenUrukSteelFluid);
		registerMaterial(LotRMaterialID.BlackUrukSteel, "BlackUrukSteel", ModBlocks.moltenBlackUrukSteelFluid);
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
