package com.thecrafter4000.lotrtc;

import java.lang.reflect.Field;
import java.util.Locale;

import org.apache.logging.log4j.Level;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import tconstruct.TConstruct;

public class TinkersMEConfig {

	public static Configuration config;
	public static final String CategorySmeltery = "smeltery";
	public static final String CategoryTools = "tools";
	public static final String CategoryMaterials = "materialids";
	
	public static void load(){
		try{
			config.load();
			
			Property canUseNormalSmelteryBlocks = config.get(CategorySmeltery, "canUseNormalSmelteryBlocks", true);
			Property sarlluinLiquidValue = config.get(CategorySmeltery, "sarlluinLiquidValue", TConstruct.stoneLiquidValue);
			TinkersMEConfig.canUseNormalSmelteryBlocks = canUseNormalSmelteryBlocks.getBoolean();
			TinkersMEConfig.sarlluinLiquidValue = sarlluinLiquidValue.getInt();
			
			Property material;
			for(Field f : LotRMaterialID.class.getFields()) {
				material = config.get(CategoryMaterials, f.getName().toLowerCase(Locale.ENGLISH), f.getInt(null));
				f.setInt(null, material.getInt());
			}
			
			Property stat;
			for(Field f : LotRToolStats.class.getFields()) {
				stat = config.get(CategoryTools, f.getName().toLowerCase(Locale.ENGLISH), f.getFloat(null));
				f.setFloat(null, (float) stat.getDouble());
			}
		}catch(Exception e){
			TinkersMiddleearth.logger.catching(Level.WARN, e);
		}finally{
			if(config.hasChanged()) config.save();
		}
	}
	
	public static int sarlluinLiquidValue = TConstruct.stoneLiquidValue;
	public static boolean canUseNormalSmelteryBlocks = false;
	
	public static class LotRToolStats{
		public static float handleModifierMithril = 3f;
		public static float handleModifierDwarf = 1.5f;
		public static float handleModifierBlueDwarf = 1.3f;
		public static float handleModifierElf = 1.7f;	
		public static float handleModifierOrc = 1.1f;
		public static float handleModifierUruk = 1.3f;
		public static float handleModifierBlackUruk = 1.5f;
		public static float handleModifierMallorn = 1.3f;
	}
	
	public static class LotRMaterialID{
		public static int MithrilLotR = 50, DwarvenSteel = 51, BlueDwarvenSteel = 52, ElvenSteel = 53, OrcSteel = 54, UrukSteel = 55, BlackUrukSteel = 56;
//		public static int Mallorn = 57;
	}
}
