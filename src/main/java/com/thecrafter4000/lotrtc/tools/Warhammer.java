package com.thecrafter4000.lotrtc.tools;

import com.thecrafter4000.lotrtc.items.TinkersMEItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import tconstruct.library.tools.Weapon;
import tconstruct.tools.TinkerTools;

public class Warhammer extends Weapon {

	public Warhammer() {
		super(6);
	}

	@Override
	public String getIconSuffix(int partType) {
        switch (partType)
        {
        case 0:
            return "_warhammer_head";
        case 1:
            return "_warhammer_handle_broken";
        case 2:
            return "_warhammer_handle";
        case 3:
            return "_warhammer_binding";
        default:
            return "";
        }
	}
	
	
    @Override
    public float getDurabilityModifier ()
    {
        return 4.5f;
    }

    @Override public IIcon getIcon (ItemStack stack, int renderPass)
    {
        NBTTagCompound tags = stack.getTagCompound();

        if (tags != null) {
            tags = stack.getTagCompound().getCompoundTag("InfiTool");
            if (renderPass == 0) // Handle
            {
                if (tags.getBoolean("Broken"))
                    return getCorrectIcon(brokenIcons, tags.getInteger("RenderHandle"));
                return getCorrectIcon(handleIcons, tags.getInteger("RenderHandle"));
            }
            else if(renderPass == 1)
                return getCorrectIcon(headIcons, tags.getInteger("RenderHead"));
        }

        return super.getIcon(stack, renderPass);
    }

	@Override
	public String getEffectSuffix() {
		return "_warhammer_effect";
	}

	@Override
	public String getDefaultFolder() {
		return "warhammer";
	}
	
	@Override
	public int getPartAmount() {
		return 4;
	}
	
	@Override
	public float getRepairCost() {
		return super.getRepairCost();
	}
	
	@Override
	public String getDefaultTexturePath() {
		return "lotrtc:" + getDefaultFolder();
	}

	@Override
	public Item getHeadItem() {
		return TinkersMEItems.warHammerHead;
	}

	@Override
	public Item getAccessoryItem() {
		return TinkerTools.toughBinding;
	}
	
    @Override
    public Item getHandleItem () {
        return TinkerTools.toughRod;
    }
}
