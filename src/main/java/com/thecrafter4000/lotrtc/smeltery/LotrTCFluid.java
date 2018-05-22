package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.CommonProxy;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.fluids.Fluid;
import tconstruct.smeltery.blocks.TConstructFluid;

/* Also copied from TiC with few changes */

public class LotrTCFluid extends TConstructFluid {

	public Fluid fluid = null;
	public String texture;
	public boolean overwriteFluidIcons = true;
	
	public LotrTCFluid(Fluid fluid, Material material, String texture) {
		super(fluid, material, texture);
		this.fluid = fluid;
		this.texture = texture;
		this.setCreativeTab(CommonProxy.LotRTiCTab);
	}
	
    @Override
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        stillIcon = iconRegister.registerIcon("lotrtc:" + texture);
        flowIcon = iconRegister.registerIcon("lotrtc:" + texture + "_flow");

        if (overwriteFluidIcons)
            this.getFluid().setIcons(stillIcon, flowIcon);

        if(this.getFluid().getBlock() != this && fluid != null)
            fluid.setIcons(stillIcon, flowIcon);
    }
    
    @Override
    public void suppressOverwritingFluidIcons() {
    	this.overwriteFluidIcons = false;
    }
    
    @Override
    public void setFluid(Fluid fluid) {
    	this.fluid = fluid;
    }
}
