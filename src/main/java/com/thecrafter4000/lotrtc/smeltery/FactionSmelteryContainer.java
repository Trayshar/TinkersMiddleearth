package com.thecrafter4000.lotrtc.smeltery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import tconstruct.smeltery.inventory.SmelteryContainer;
import tconstruct.smeltery.logic.SmelteryLogic;

public class FactionSmelteryContainer extends SmelteryContainer {

	public FactionSmelteryContainer(InventoryPlayer inventoryplayer, SmelteryLogic smeltery) {
		super(inventoryplayer, smeltery);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		int meta = this.logic.getWorldObj().getBlockMetadata(this.logic.xCoord, this.logic.yCoord, this.logic.zCoord);
		if(meta != 0) return false;
		return this.logic.isUseableByPlayer(entityplayer);
	}
}
