package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.dyer.DyerContainer;
import com.thecrafter4000.lotrtc.dyer.DyerGui;
import com.thecrafter4000.lotrtc.dyer.DyerLogic;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class TinkersMEGuiHandler implements IGuiHandler{

	public static final Integer DYER = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == DYER){
			return new DyerContainer(player.inventory, (DyerLogic) world.getTileEntity(x, y, z));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == DYER){
			return new DyerGui((DyerLogic) world.getTileEntity(x, y, z));
		}
		return null;
	}

}
