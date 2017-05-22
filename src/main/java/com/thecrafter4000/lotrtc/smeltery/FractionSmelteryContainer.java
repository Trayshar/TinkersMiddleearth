package com.thecrafter4000.lotrtc.smeltery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import tconstruct.smeltery.inventory.SmelteryContainer;
import tconstruct.smeltery.logic.SmelteryLogic;

public class FractionSmelteryContainer extends SmelteryContainer {

	public FractionSmelteryContainer(InventoryPlayer inventoryplayer, SmelteryLogic smeltery) {
		super(inventoryplayer, smeltery);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.logic.isUseableByPlayer(entityplayer);
	}
}
