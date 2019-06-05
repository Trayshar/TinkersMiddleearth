package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.TinkersMEConfig;
import mantle.blocks.abstracts.MultiServantLogic;
import mantle.world.CoordTuple;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import tconstruct.smeltery.SmelteryDamageSource;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.smeltery.logic.LavaTankLogic;
import tconstruct.smeltery.logic.SmelteryLogic;
import tconstruct.util.config.PHConstruct;

import java.util.ArrayList;
import java.util.Random;

public class FactionSmelteryLogic extends SmelteryLogic {

	public SmelteryMainFaction faction;
	
	private boolean needsUpdate;
	private int tick;

	public FactionSmelteryLogic(SmelteryMainFaction faction) {
		super();
		this.faction = faction;
	}

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
		//TODO: Remove this after a couple of releases. Old versions had a typo here... oof
		if (faction.isEmpty()) faction = tags.getString("Fraction");
		this.faction = SmelteryMainFaction.valueOf(faction);
	}

	/** Actually overwrites {@link SmelteryLogic#validBlockID(Block)} via ASM. What could go wrong? */
	public boolean validBlockID(Block blockID) {
		if (this.faction.isValidBlock(blockID)) return true;
		else if(TinkersMEConfig.canUseNormalSmelteryBlocks) return ((blockID == TinkerSmeltery.smeltery) || (blockID == TinkerSmeltery.smelteryNether));
		return false;
	}

	public boolean validTankID(Block blockID) {
		if (this.faction.isValidTank(blockID)) return true;
		else if(TinkersMEConfig.canUseNormalSmelteryBlocks) return ((blockID == TinkerSmeltery.lavaTank) || (blockID == TinkerSmeltery.lavaTankNether));
		return false;
	}
}
