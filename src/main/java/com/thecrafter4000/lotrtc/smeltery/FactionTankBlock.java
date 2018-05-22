package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.CommonProxy;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import tconstruct.smeltery.blocks.LavaTankBlock;

public class FactionTankBlock extends LavaTankBlock {

	public final String factionName;

	public FactionTankBlock(String factionName) {
		super();
		this.factionName = factionName;
		this.setCreativeTab(CommonProxy.LotRTiCTab);
		this.setBlockName("lotrtc.Tank" + factionName);
		this.setStepSound(soundTypeGlass);
	}
	
    public String[] getTextureNames ()
    {
        String[] textureNames = { "lavatank_side", "lavatank_top", "searedgague_top", "searedgague_side", "searedgague_bottom", "searedwindow_top", "searedwindow_side", "searedwindow_bottom" };
		for (int i = 0; i < textureNames.length; i++) textureNames[i] = textureNames[i] + "_" + factionName;
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
