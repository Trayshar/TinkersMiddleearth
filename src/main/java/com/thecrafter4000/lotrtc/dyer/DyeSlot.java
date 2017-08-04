package com.thecrafter4000.lotrtc.dyer;

import lotr.common.item.LOTRItemDye;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DyeSlot extends Slot {

	public DyeSlot(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return LOTRItemDye.isItemDye(stack) != -1;
	}
}
