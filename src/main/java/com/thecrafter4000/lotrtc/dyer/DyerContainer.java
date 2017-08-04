package com.thecrafter4000.lotrtc.dyer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tconstruct.library.tools.ToolCore;
import tconstruct.tools.inventory.SlotTool;

public class DyerContainer extends Container {

	public DyerLogic logic;
	public InventoryPlayer player;
	public Slot toolSlot;
	public DyeSlot[] slots;
	
	public DyerContainer(InventoryPlayer player, DyerLogic logic) {
		this.logic = logic;
		this.player = player;
		
        toolSlot = new Slot(logic, 0, 56, 28){
        	@Override
        	public boolean isItemValid(ItemStack stack) {
        		return stack != null && stack.getItem() instanceof ToolCore;
        	}
        };
        this.addSlotToContainer(toolSlot);

        slots = new DyeSlot[] { new DyeSlot(logic, 1, 80, 1), new DyeSlot(logic, 2, 80, 19), new DyeSlot(logic, 3, 80, 37), new DyeSlot(logic, 4, 80, 55) };
        for (int iter = 0; iter < 4; iter++)
            this.addSlotToContainer(slots[iter]);

		
        /* Player inventory */
        for (int column = 0; column < 3; column++)
        {
            for (int row = 0; row < 9; row++)
            {
                this.addSlotToContainer(new Slot(player, row + column * 9 + 9, 8 + row * 18, 84 + column * 18));
            }
        }

        for (int column = 0; column < 9; column++)
        {
            this.addSlotToContainer(new Slot(player, column, 8 + column * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return logic.isUseableByPlayer(p_75145_1_);
	}
}