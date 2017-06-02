package com.thecrafter4000.lotrtc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtraPlayerInfo implements IExtendedEntityProperties {

	public static final String NAME = "LotrTC";
	
	public EntityPlayer player;
	
	public boolean hasBook;
	
	public ExtraPlayerInfo() {}
	
	public ExtraPlayerInfo(EntityPlayer player) {
		this.player = player;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setBoolean("hasBook", hasBook);
		compound.setTag(NAME + "_data", tag);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound tag = compound.getCompoundTag(NAME + "_data");
		if(tag != null) {
			this.hasBook = tag.getBoolean("hasBook");
		}
	}

	@Override
	public void init(Entity entity, World world) {
		this.player = (EntityPlayer) entity;
	}
	
	public static void register( EntityPlayer player ){
		player.registerExtendedProperties(NAME, new ExtraPlayerInfo(player));
	}

	public static ExtraPlayerInfo get(EntityPlayer player){
		return (ExtraPlayerInfo) player.getExtendedProperties(NAME);
	}
	
	public void copy(ExtraPlayerInfo from){
		this.hasBook = from.hasBook;
	}
}
