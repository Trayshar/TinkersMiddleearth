package com.thecrafter4000.lotrtc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import tconstruct.smeltery.items.FilledBucket;

public class LotrFilledBucket extends FilledBucket {

	public LotrFilledBucket(Block b) {
		super(b);
		setUnlocalizedName("lotrtc.bucket");
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames2.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("lotrtc:materials/bucket_" + textureNames2[i]);
        }
    }

    @Override
    public String getUnlocalizedName (ItemStack stack)
    {
        int arr = MathHelper.clamp_int(stack.getItemDamage(), 0, materialNames2.length);
        return getUnlocalizedName() + "." + materialNames2[arr];
    }
    
    public static final String[] materialNames2 = new String[] { "Silver", "Sarlluin", "Mithril", "BlueDwarvenSteel", "DwarvenSteel", "Galvorn", "ElvenSteel", "MorgulSteel", "OrcSteel", "UrukSteel", "BlackUrukSteel" };

    public static final String[] textureNames2 = new String[] { "silver_lotr", "sarlluin", "mithril_lotr", "blue_dwarven_steel", "dwarven_steel", "galvorn", "elven_steel", "morgul_steel", "orc_steel", "uruk_steel", "black_uruk_steel" };
}