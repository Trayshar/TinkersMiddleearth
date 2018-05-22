package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.items.TinkersMEBlocks;
import net.minecraft.block.Block;

//FIXME: Think of an other structure to hold smeltery "races"
public enum SmelteryMainFaction {

	Elf, Dwarf, Orc;
	
	public boolean isValidBlock(Block block){
		switch(this){
			case Elf: return TinkersMEBlocks.smelteryHighElves == block;
			case Orc: return TinkersMEBlocks.smelteryAngmar == block;
			case Dwarf: return TinkersMEBlocks.smelteryDwarven == block;
		}
		return false;
	}
	
	public boolean isValidTank(Block block){
		switch(this){
			case Elf: return TinkersMEBlocks.tankHighElves == block;
			case Orc: return TinkersMEBlocks.tankAngmar == block;
			case Dwarf: return TinkersMEBlocks.tankDwarven == block;
		}
		return false;
	}
}
