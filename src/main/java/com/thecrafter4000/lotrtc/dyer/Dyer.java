package com.thecrafter4000.lotrtc.dyer;

import com.thecrafter4000.lotrtc.CommonProxy;
import com.thecrafter4000.lotrtc.TinkersMEGuiHandler;
import com.thecrafter4000.lotrtc.TinkersMiddleearth;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mantle.blocks.abstracts.InventoryBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tconstruct.tools.model.TableRender;

public class Dyer extends InventoryBlock{

	public Dyer(Material material) {
		super(material);
		this.setCreativeTab(CommonProxy.LotRTiCTab);
		this.setHardness(2f);
		this.setStepSound(soundTypeWood);
	}

	@Override
	public TileEntity createNewTileEntity(World paramWorld, int paramInt) {
		return new DyerLogic();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[meta * 3 + (side == 0 ? 2 : side == 1 ? 0 : 1)];
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side == ForgeDirection.UP;
	}
	
	@Override
	public int getRenderType() {
		return TableRender.model;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return true;
	}
	
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool (World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (metadata == 5)
            return AxisAlignedBB.getBoundingBox((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY - 0.125, (double) z + this.maxZ);
        return AxisAlignedBB.getBoundingBox((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }
	
	@Override
	public Integer getGui(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer) {
		return TinkersMEGuiHandler.DYER;
	}

	@Override
	public Object getModInstance() {
		return TinkersMiddleearth.instance;
	}

	@Override
	public String[] getTextureNames() {
		return new String[]{ "dyer_top", "dyer_side", "dyer_bottom" };
	}

	@Override
	public String getTextureDomain(int paramInt) {
		return "lotrtc";
	}
}
