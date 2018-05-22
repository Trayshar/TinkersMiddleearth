package com.thecrafter4000.lotrtc.items;

import com.thecrafter4000.lotrtc.smeltery.FactionTankBlock;
import mantle.blocks.abstracts.MultiItemBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

public class TankItemBlock extends MultiItemBlock {

    public static final String blockTypes[] = { "Tank", "Gague", "Window" };

    public TankItemBlock(Block b)
    {
        super(b, getName(b), blockTypes);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    private static String getName(Block b){
		FactionTankBlock block = (FactionTankBlock) b;
		return "Tank" + block.factionName;
    }
    
    @Override
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound liquidTag = stack.getTagCompound().getCompoundTag("Fluid");
            if (liquidTag != null)
            {
                list.add(StatCollector.translateToLocal("searedtank1.tooltip") + " " + StatCollector.translateToLocal(liquidTag.getString("FluidName")));
                list.add(liquidTag.getInteger("Amount") + " mB");
            }
        }
        else
        {
            list.add(StatCollector.translateToLocal("searedtank3.tooltip"));
            list.add(StatCollector.translateToLocal("searedtank2.tooltip"));
        }
    }
}
