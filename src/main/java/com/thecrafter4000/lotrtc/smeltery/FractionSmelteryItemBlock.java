package com.thecrafter4000.lotrtc.smeltery;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mantle.blocks.abstracts.MultiItemBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import tconstruct.smeltery.itemblocks.SmelteryItemBlock;

public class FractionSmelteryItemBlock extends MultiItemBlock {
	public final static String[] blockTypes = { "Controller", "Drain", "Brick" };
	
	public FractionSmelteryItemBlock(Block b) {
		super(b, "", blockTypes);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		switch (stack.getItemDamage()) {
			case 0: 
				list.add(StatCollector.translateToLocal("smeltery.controller.tooltip"));
				break;
			case 1: 
				list.add(StatCollector.translateToLocal("smeltery.drain.tooltip1"));
				list.add(StatCollector.translateToLocal("smeltery.drain.tooltip2"));
				break;
			case 2: default: 
				list.add(StatCollector.translateToLocal("smeltery.brick.tooltip1"));
				list.add(StatCollector.translateToLocal("smeltery.brick.tooltip2"));
		}
	}
}
