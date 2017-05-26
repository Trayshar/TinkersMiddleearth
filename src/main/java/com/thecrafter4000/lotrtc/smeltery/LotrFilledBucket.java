package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.LotRTCIntegrator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mantle.world.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import tconstruct.TConstruct;
import tconstruct.library.crafting.FluidType;
import tconstruct.smeltery.TinkerSmeltery;
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
    
    public static final String[] materialNames2 = new String[] { "Sarlluin", "MithrilLotR", "BlueDwarvenSteel", "DwarvenSteel", "Galvorn", "ElvenSteel", "MorgulSteel", "OrcSteel", "UrukSteel", "BlackUrukSteel", "Edhelmir", "Durnaur", "Gulduril" };

    public static final String[] textureNames2 = new String[] { "sarlluin", "mithril_lotr", "blue_dwarven_steel", "dwarven_steel", "galvorn", "elven_steel", "morgul_steel", "orc_steel", "uruk_steel", "black_uruk_steel", "edhelmir", "durnaur", "gulduril" };

    public boolean tryPlaceContainedLiquid (World world, int clickX, int clickY, int clickZ, int type)
    {
        if (!WorldHelper.isAirBlock(world, clickX, clickY, clickZ) && world.getBlock(clickX, clickY, clickZ).getMaterial().isSolid())
        {
            return false;
        }
        else
        {
            try
            {
            	Block fluid = FluidType.getFluidType(materialNames2[type]).fluid.getBlock();
                if (fluid == null)
                    return false;

                int metadata = 0;
                if (fluid instanceof BlockFluidFinite)
                    metadata = 7;

                world.setBlock(clickX, clickY, clickZ, fluid, metadata, 3); // TODO: Merge liquids
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
                LotRTCIntegrator.logger.warn("AIOBE occured when placing bucket into world; " + ex);
                return false;
            }

            return true;
        }
    }
}