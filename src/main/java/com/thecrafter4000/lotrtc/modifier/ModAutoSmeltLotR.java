package com.thecrafter4000.lotrtc.modifier;

import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.modifiers.tools.ModAutoSmelt;

public class ModAutoSmeltLotR extends ModAutoSmelt {

	public ModAutoSmeltLotR(ItemStack[] items, int effect, String tag, String c, String tip) {
		super(items, effect, tag, c, tip);
	}

    @Override
    protected boolean canModify (ItemStack tool, ItemStack[] input)
    {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        if (tags.getBoolean("Silk Touch"))
            return false;
        if (LOTREnchantmentHelper.isSilkTouch(tool))
        	return false;
        return tags.getInteger("Modifiers") > 0 && !tags.getBoolean(key); //Will fail if the modifier is false or the tag doesn't exist
    }
}
