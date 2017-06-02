package com.thecrafter4000.lotrtc;

import java.lang.reflect.Field;

import org.apache.logging.log4j.Level;

import com.thecrafter4000.lotrtc.TinkersMEConfig.LotRToolStats.*;

import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import tconstruct.library.tools.ToolMaterial;

public class TinkersMEConfig {

	public static Configuration config;
	public static final String CategorySmeltery = "smeltery";
	public static final String CategoryMaterials = "materialids";
	
	public static void load(){
		try{
			config.load();
			
			Property canUseNormalSmelteryBlocks = config.get(CategorySmeltery, "canUseNormalSmelteryBlocks", true);
			TinkersMEConfig.canUseNormalSmelteryBlocks = canUseNormalSmelteryBlocks.getBoolean();

/*			Removed cause this will crash your server if the player uses other values. Maybe that could be changed, but not yet.
			Property material;
			for(Field f : LotRMaterialID.class.getFields()) {
				material = config.get(CategoryMaterials, f.getName().toLowerCase(Locale.ENGLISH), f.getInt(null));
				f.setInt(null, material.getInt());
			}

			Property stat;
			for(Class c : LotRToolStats.class.getDeclaredClasses()){
				for(Field f : c.getFields()){
					if(f.getType() == int.class) {
						stat = config.get(c.getSimpleName(), f.getName().toLowerCase(Locale.ENGLISH), f.getInt(null));
						f.setInt(null, stat.getInt());
					}
					else if(f.getType() == float.class) {
						stat = config.get(c.getSimpleName(), f.getName().toLowerCase(Locale.ENGLISH), f.getFloat(null));
						f.setFloat(null, (float)stat.getDouble());
					}
				}
			}
*/
		}catch(Exception e){
			TinkersMiddleearth.logger.catching(Level.WARN, e);
		}finally{
			if(config.hasChanged()) config.save();
		}
	}
	
	public static boolean canUseNormalSmelteryBlocks = false;
	
	static class LotRToolStats{
		private static class MiningLevel{
			public static int MithrilLotR = 5;
			public static int DwarvenSteel = 3;
			public static int BlueDwarvenSteel = 2;
			public static int ElvenSteel = 3;
			public static int OrcSteel = 2;
			public static int UrukSteel = 3;
			public static int BlackUrukSteel = 4;
		}
		
		private static class Durability{
			public static int MithrilLotR = 2400;
			public static int DwarvenSteel = 750;
			public static int BlueDwarvenSteel = 650;
			public static int ElvenSteel = 700;
			public static int OrcSteel = 400;
			public static int UrukSteel = 550;
			public static int BlackUrukSteel = 675;
		}
		
		private static class MiningSpeed{
			public static int MithrilLotR = 900;
			public static int DwarvenSteel = 900;
			public static int BlueDwarvenSteel = 700;
			public static int ElvenSteel = 800;
			public static int OrcSteel = 600;
			public static int UrukSteel = 700;
			public static int BlackUrukSteel = 825;
		}
		
		private static class Damage{
			public static int MithrilLotR = 5;
			public static int DwarvenSteel = 3;
			public static int BlueDwarvenSteel = 2;
			public static int ElvenSteel = 3;
			public static int OrcSteel = 2;
			public static int UrukSteel = 3;
			public static int BlackUrukSteel = 3;
		}

		private static class HandleModifier{
			public static float MithrilLotR = 3f;
			public static float DwarvenSteel = 1.5f;
			public static float BlueDwarvenSteel = 1.3f;
			public static float ElvenSteel = 1.7f;	
			public static float OrcSteel = 1.1f;
			public static float UrukSteel = 1.3f;
			public static float BlackUrukSteel = 1.5f;
		}

		private static class Reinforcement{
			public static int MithrilLotR = 3;
			public static int DwarvenSteel = 2;
			public static int BlueDwarvenSteel = 1;
			public static int ElvenSteel = 2;
			public static int OrcSteel = 0;
			public static int UrukSteel = 1;
			public static int BlackUrukSteel = 2;
		}

		private static class StoneBound{
			public static float MithrilLotR = 0f;
			public static float DwarvenSteel = 0f;
			public static float BlueDwarvenSteel = 0f;
			public static float ElvenSteel = 0f;	
			public static float OrcSteel = 0.2f;
			public static float UrukSteel = 0f;
			public static float BlackUrukSteel = 0f;
		}
	}
	
	private static class StyleColor{
		public static String MithrilLotR = EnumChatFormatting.YELLOW.toString();
		public static String DwarvenSteel = EnumChatFormatting.GRAY.toString();
		public static String BlueDwarvenSteel = EnumChatFormatting.BLUE.toString();
		public static String ElvenSteel = EnumChatFormatting.AQUA.toString();
		public static String OrcSteel = EnumChatFormatting.DARK_GRAY.toString();
		public static String UrukSteel = EnumChatFormatting.DARK_GRAY.toString();
		public static String BlackUrukSteel = EnumChatFormatting.DARK_RED.toString();
	}
	
	private static class Color{
		public static int MithrilLotR = 0xededed;
		public static int DwarvenSteel = 0x3d3e44;
		public static int BlueDwarvenSteel = 0x113b7f;
		public static int ElvenSteel = 0xd1cccc;
		public static int OrcSteel = 0x3a3838;
		public static int UrukSteel = 0x4c2409;
		public static int BlackUrukSteel = 0x000000;
	}
	
	public static ToolMaterial getToolMaterial(String materialName) {
		ToolMaterial material = null;
		int level = -1, durability = -1, speed = -1, damage = -1, reinforced = -1, color = -1;
		float handle = -1f, stonebound = -1f;
		String style = null;
		
		try{
			for(Field f : MiningLevel.class.getDeclaredFields()) if(f.getName().equals(materialName)) level = f.getInt(null);
			for(Field f : Durability.class.getDeclaredFields()) if(f.getName().equals(materialName)) durability = f.getInt(null);
			for(Field f : MiningSpeed.class.getDeclaredFields()) if(f.getName().equals(materialName)) speed = f.getInt(null);
			for(Field f : Damage.class.getDeclaredFields()) if(f.getName().equals(materialName)) damage = f.getInt(null);
			for(Field f : HandleModifier.class.getDeclaredFields()) if(f.getName().equals(materialName)) handle = f.getFloat(null);
			for(Field f : Reinforcement.class.getDeclaredFields()) if(f.getName().equals(materialName)) reinforced = f.getInt(null);
			for(Field f : StoneBound.class.getDeclaredFields()) if(f.getName().equals(materialName)) stonebound = f.getFloat(null);
			for(Field f : StyleColor.class.getDeclaredFields()) if(f.getName().equals(materialName)) style = (String)f.get(null);
			for(Field f : Color.class.getDeclaredFields()) if(f.getName().equals(materialName)) color = f.getInt(null);
			
			if(level == -1 || durability == -1 || speed == -1 || damage == -1|| reinforced == -1 || color == -1 || handle == -1f || stonebound == -1f || style == null) throw new RuntimeException("Error loading toolmaterials from config for material " + materialName + ": Field not present!"); 
		}catch(Exception e){
			TinkersMiddleearth.logger.catching(Level.WARN, e);
		}finally {
			material = new ToolMaterial(materialName, level, durability, speed, damage, handle, reinforced, stonebound, style, color);
		}
		return material;
	}
	
	public static class LotRMaterialID{
		public static int MithrilLotR = 50, DwarvenSteel = 51, BlueDwarvenSteel = 52, ElvenSteel = 53, OrcSteel = 54, UrukSteel = 55, BlackUrukSteel = 56;
//		public static int Mallorn = 57;
	}
}
