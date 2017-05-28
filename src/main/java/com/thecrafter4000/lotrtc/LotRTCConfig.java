package com.thecrafter4000.lotrtc;

import tconstruct.TConstruct;
import tconstruct.util.config.PHConstruct;

public class LotRTCConfig {

	public static int sarlluinLiquidValue = TConstruct.stoneLiquidValue;
	public static int barLiquidValue = (TConstruct.ingotLiquidValue * 6 / 16);
	public static boolean shouldUseNormalSmelteryBlocks = false;
	
	public static float handleModifierMithril = 2f;
	public static float handleModifierDwarf = 2f;
	public static float handleModifierBlueDwarf = 2f;
	public static float handleModifierElf = 2f;	
	public static float handleModifierOrc = 2f;
	public static float handleModifierUruk = 2f;
	public static float handleModifierBlackUruk = 2f;
	public static float handleModifierMallorn = 2f;
	
	public static class LotRMaterialID{
		public static int MithrilLotR = 50, DwarvenSteel = 51, BlueDwarvenSteel = 52, ElvenSteel = 53, OrcSteel = 54, UrukSteel = 55, BlackUrukSteel = 56;
		public static int Mallorn = 57;
	}
}
