package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.ModBlocks;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;

public enum LotrSmelteryFraction {

	Elf, Dwarf, Orc;
	
	public String getFractionString(){
		switch(this){
			case Elf: return "elves";
			case Orc: return "orc";
			case Dwarf: return "dwarven";
		}
		throw new RuntimeException("This is a bug! Please report!");
	}
	
	public boolean isValidBlock(Block block){
		switch(this){
			case Elf: return ModBlocks.smelteryHighElves == block;
			case Orc: return ModBlocks.smelteryAngmar == block;
			case Dwarf: return ModBlocks.smelteryDwarven == block;
		}
		return false;
	}
}
