package com.thecrafter4000.lotrtc.dyer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mantle.common.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class DyePacket extends AbstractPacket {

	int x, y, z;
	
	public DyePacket() {}
	
	public DyePacket(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) {
		paramByteBuf.writeInt(x);
		paramByteBuf.writeInt(y);
		paramByteBuf.writeInt(z);
	}


	@Override
	public void decodeInto(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) {
		x = paramByteBuf.readInt();
		y = paramByteBuf.readInt();
		z = paramByteBuf.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer paramEntityPlayer) {}

	@Override
	public void handleServerSide(EntityPlayer paramEntityPlayer) {
		TileEntity entity = paramEntityPlayer.worldObj.getTileEntity(x, y, z);
		if(entity instanceof DyerLogic){
			((DyerLogic)entity).dyeItem();
		}
	}
}
