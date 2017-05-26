package com.thecrafter4000.lotrtc.smeltery;

import java.util.List;

import com.thecrafter4000.lotrtc.LotRTCIntegrator;
import com.thecrafter4000.lotrtc.smeltery.client.FractionSmelteryRender;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mantle.blocks.abstracts.MultiServantLogic;
import mantle.blocks.iface.IFacingLogic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tconstruct.smeltery.blocks.SmelteryBlock;
import tconstruct.smeltery.logic.SmelteryDrainLogic;

public class FractionSmeltery extends SmelteryBlock {

	public final LotrSmelteryFraction fraction;
	
	public FractionSmeltery(LotrSmelteryFraction fraction) {
		super();
		this.fraction = fraction;
		this.setBlockName("lotrtc.smelterty_" + fraction.getFractionString());
	}
	
	@Override
	public int getRenderType() {
		return FractionSmelteryRender.renderId;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
//		System.out.println("[" + FMLCommonHandler.instance().getEffectiveSide() + "] TEMeta:" + metadata);
		if(metadata == 0) return new FractionSmelteryLogic(fraction);
		else if(metadata == 1) return new SmelteryDrainLogic();
//		System.out.println("[" + FMLCommonHandler.instance().getEffectiveSide() + "] Returns MultiServantLogic");
		return new MultiServantLogic();
	}
	
	@Override
	public String getTextureDomain(int textureNameIndex) {
		return "lotrtc";
	}
	
	@Override
	public String[] getTextureNames() {
		String[] textureNames = { "searedbrick", "smeltery_inactive", "smeltery_active", "drain_out", "drain_basine" ,"searedbrickcracked" };
		for(int i = 0; i < textureNames.length; i++){
			textureNames[i] = textureNames[i] + "_" + fraction.getFractionString();
		}
		return textureNames;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		switch(meta){
			case 0:
				return side == 3 ? this.icons[1] : this.icons[0];
			case 1:
				return side == 3 ? this.icons[3] : this.icons[0];
			case 3:
				return this.icons[5];
			case 2: default:
				return this.icons[0];
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntity logic = world.getTileEntity(x, y, z);
		short direction = (logic instanceof IFacingLogic) ? (short)((IFacingLogic)logic).getRenderDirection() : 0;
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0) {
			if (side == direction) {
				if (isActive(world, x, y, z)) {
					return this.icons[2];
				}
				return this.icons[1];
			}
			return this.icons[0];
		}
		if (meta == 1) {
			if (side == direction)
				return this.icons[4];
			if (side / 2 == direction / 2) {
				return this.icons[3];
			}
			return this.icons[0];
		}
		if (meta == 2) {
			return this.icons[0];
		}
		if (meta == 3) return this.icons[5];
		return null;
	}
	
	@Override
	public void getSubBlocks(Item id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
		list.add(new ItemStack(id, 1, 2));
		list.add(new ItemStack(id, 1, 3));
	}
}
