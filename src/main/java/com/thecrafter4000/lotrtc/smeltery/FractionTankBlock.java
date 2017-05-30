package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.CommonProxy;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import tconstruct.smeltery.blocks.LavaTankBlock;

public class FractionTankBlock extends LavaTankBlock {
	
	public final String fractionName;
	
	public FractionTankBlock(String fractionName) {
		super();
		this.fractionName = fractionName;
		this.setCreativeTab(CommonProxy.LotRTiCTab);
		this.setBlockName("lotrtc.Tank" + fractionName);
		this.setStepSound(soundTypeGlass);
	}
	
    public String[] getTextureNames ()
    {
        String[] textureNames = { "lavatank_side", "lavatank_top", "searedgague_top", "searedgague_side", "searedgague_bottom", "searedwindow_top", "searedwindow_side", "searedwindow_bottom" };
        for (int i = 0; i < textureNames.length; i++) textureNames[i] = textureNames[i] + "_" + fractionName;
        return textureNames;
    }
    
    @Override
    public void registerBlockIcons (IIconRegister IIconRegister)
    {
        String[] textureNames = getTextureNames();
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = IIconRegister.registerIcon("lotrtc:" + textureNames[i]);
        }
    }
}
