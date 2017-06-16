package com.thecrafter4000.lotrtc.tools;

import lotr.common.item.LOTRWeaponStats;
import tconstruct.items.tools.*;


public class HitDelayPatcher {

	public static void patch(){
		LOTRWeaponStats.registerMeleeTime(Rapier.class, 0.5f);
		LOTRWeaponStats.registerMeleeTime(Dagger.class, 0.667f);
		LOTRWeaponStats.registerMeleeTime(Cutlass.class, 1.2f);
		LOTRWeaponStats.registerMeleeTime(FryingPan.class, 1.667f);
		LOTRWeaponStats.registerMeleeTime(Cleaver.class, 1.667f);
		LOTRWeaponStats.registerMeleeTime(Hammer.class, 1.5f);
		LOTRWeaponStats.registerMeleeTime(LotRBattleAxe.class, 1.3f);
		
		LOTRWeaponStats.registerMeleeReach(Rapier.class, 1.5f);
		LOTRWeaponStats.registerMeleeReach(Dagger.class, 0.75f);
//		LOTRWeaponStats.registerMeleeReach(Cutlass.class, 1f);
//		LOTRWeaponStats.registerMeleeReach(FryingPan.class, 1f);
		LOTRWeaponStats.registerMeleeReach(Cleaver.class, 1.25f);
//		LOTRWeaponStats.registerMeleeReach(Hammer.class, 1f);
//		LOTRWeaponStats.registerMeleeReach(LotRBattleAxe.class, 1f);
		
		LOTRWeaponStats.registerMeleeExtraKnockback(LotRBattleAxe.class, 1);
	}
}
