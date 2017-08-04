package com.thecrafter4000.lotrtc.tools;

import lotr.common.item.LOTRWeaponStats;
import tconstruct.items.tools.*;

public class HitDelayPatcher {

	public static void patch(){
		LOTRWeaponStats.registerMeleeSpeed(Rapier.class, 1.5f);
		LOTRWeaponStats.registerMeleeSpeed(Dagger.class, 0.667f);
		LOTRWeaponStats.registerMeleeSpeed(Cutlass.class, 1.2f);
		LOTRWeaponStats.registerMeleeSpeed(FryingPan.class, 1.667f);
		LOTRWeaponStats.registerMeleeSpeed(Cleaver.class, 1.667f);
		LOTRWeaponStats.registerMeleeSpeed(Hammer.class, 1.5f);
		LOTRWeaponStats.registerMeleeSpeed(LotRBattleAxe.class, 1.4f);
		
//		LOTRWeaponStats.registerMeleeReach(Rapier.class, 1f);
		LOTRWeaponStats.registerMeleeReach(Dagger.class, 0.75f);
//		LOTRWeaponStats.registerMeleeReach(Cutlass.class, 1f);
//		LOTRWeaponStats.registerMeleeReach(FryingPan.class, 1f);
		LOTRWeaponStats.registerMeleeReach(Cleaver.class, 1.25f);
//		LOTRWeaponStats.registerMeleeReach(Hammer.class, 1f);
		LOTRWeaponStats.registerMeleeReach(LotRBattleAxe.class, 1.25f);
		
		LOTRWeaponStats.registerMeleeExtraKnockback(LotRBattleAxe.class, 1);
		LOTRWeaponStats.registerMeleeExtraKnockback(Warhammer.class, 2);
	}
}
