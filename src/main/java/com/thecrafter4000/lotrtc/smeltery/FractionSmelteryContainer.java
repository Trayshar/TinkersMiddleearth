package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.ModBlocks;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import tconstruct.smeltery.inventory.SmelteryContainer;
import tconstruct.smeltery.logic.SmelteryLogic;

public class FractionSmelteryContainer extends SmelteryContainer {

	public FractionSmelteryContainer(InventoryPlayer inventoryplayer, SmelteryLogic smeltery) {
		super(inventoryplayer, smeltery);
//		System.out.println(FMLCommonHandler.instance().getEffectiveSide() + ", type: " + smeltery.getClass());
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		int meta = this.logic.getWorldObj().getBlockMetadata(this.logic.xCoord, this.logic.yCoord, this.logic.zCoord);
		if(meta != 0) return false;
		return this.logic.isUseableByPlayer(entityplayer);
	}
}
