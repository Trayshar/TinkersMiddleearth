package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.TinkersMEConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.smeltery.logic.SmelteryLogic;

import java.util.ArrayList;

public class FactionSmelteryLogic extends SmelteryLogic {

	public SmelteryMainFaction faction;

	public FactionSmelteryLogic(SmelteryMainFaction faction) {
		super();
		this.faction = faction;
	}

	// Necessary for TE creation, populated later
	public FactionSmelteryLogic() {
		super();
	}

	@Override public Container getGuiContainer(InventoryPlayer inventoryplayer, World world, int x, int y, int z) {
		return new FactionSmelteryContainer(inventoryplayer, this);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		tags.setString("Faction", faction.name());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);

		String faction = tags.getString("Faction");
		// Legacy field, old mod versions had a typo
		if (faction.isEmpty()) faction = tags.getString("Fraction");
		this.faction = SmelteryMainFaction.valueOf(faction);
	}

	/** Overwrites {@link SmelteryLogic#validBlockID(Block)} */
	public boolean validBlockID(Block blockID) {
		if (this.faction.isValidBlock(blockID)) return true;
		else if(TinkersMEConfig.canUseNormalSmelteryBlocks) return ((blockID == TinkerSmeltery.smeltery) || (blockID == TinkerSmeltery.smelteryNether));
		return false;
	}

	/** Overwrites {@link SmelteryLogic#validTankID(Block)} */
	public boolean validTankID(Block blockID) {
		if (this.faction.isValidTank(blockID)) return true;
		else if(TinkersMEConfig.canUseNormalSmelteryBlocks) return ((blockID == TinkerSmeltery.lavaTank) || (blockID == TinkerSmeltery.lavaTankNether));
		return false;
	}

	/** Overwrites {@link SmelteryLogic#getResultFor(ItemStack)} */
	public FluidStack getResultFor(ItemStack stack) {
		return SmelteryRecipeHandler.getSmelteryResult(faction, stack);
	}

	/** Overwrites {@link SmelteryLogic#updateTemperatures()} */
	public void updateTemperatures () {
		for (int i = 0; i < maxBlockCapacity && i < meltingTemps.length; i++)
		{
			meltingTemps[i] = SmelteryRecipeHandler.getLiquifyTemperature(faction, inventory[i]) * 10;
		}
	}

	/** Overwrites {@link com.thecrafter4000.lotrtc.asm.overlays.SmelteryLogic#getAlloys()} */
	public ArrayList<FluidStack> getAlloys() {
		return SmelteryRecipeHandler.mixMetals(faction, moltenMetal);
	}

	// TODO: Introduce faction-specific smeltery fuels; See
	// public void updateFuelGague()
	// public void verifyFuelTank()
	// public void checkValidStructure(int x, int y, int z, int[] sides)
}
