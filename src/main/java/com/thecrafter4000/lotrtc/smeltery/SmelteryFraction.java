package com.thecrafter4000.lotrtc.smeltery;

public enum SmelteryFraction {

	Elf, Dwarf, Ork;
	
	public String getFractionString(){
		switch(this){
			case Elf: return "elves";
			case Ork: return "ork";
			case Dwarf: return "dwarven";
		}
		throw new RuntimeException("This is a bug! Please report! Bug: Called getFractionString() with wrong args!");
	}
}
