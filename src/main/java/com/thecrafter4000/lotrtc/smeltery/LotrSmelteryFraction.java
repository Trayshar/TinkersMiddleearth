package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.ModBlocks;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;

public enum LotrSmelteryFraction {

	Elf, Dwarf, Orc;
	
	public boolean isValidBlock(Block block){
		switch(this){
			case Elf: return ModBlocks.smelteryHighElves == block;
			case Orc: return ModBlocks.smelteryAngmar == block;
			case Dwarf: return ModBlocks.smelteryDwarven == block;
		}
		return false;
	}
	
	public boolean isValidTank(Block block){
		switch(this){
			case Elf: return ModBlocks.tankHighElves == block;
			case Orc: return ModBlocks.tankAngmar == block;
			case Dwarf: return ModBlocks.tankDwarven == block;
		}
		return false;
	}
}
