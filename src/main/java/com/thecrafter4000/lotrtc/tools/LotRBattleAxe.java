package com.thecrafter4000.lotrtc.tools;

import com.thecrafter4000.lotrtc.items.TinkersMEItems;

import cpw.mods.fml.common.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tconstruct.items.tools.Battleaxe;
import tconstruct.library.ActiveToolMod;
import tconstruct.library.TConstructRegistry;

public class LotRBattleAxe extends Battleaxe {

	public LotRBattleAxe() {
		super();
		this.damageVsEntity = 5;
	}
	
	@Override
	public String getLocalizedToolName() {
		return StatCollector.translateToLocal("tool.battleaxe");
	}
	
//	@Override
//	public float getDurabilityModifier() {
//		return 1f;
//	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        for (ActiveToolMod mod : TConstructRegistry.activeModifiers)
        {
            mod.updateTool(this, stack, world, entity);
        }
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        boolean used = false;
        int hotbarSlot = player.inventory.currentItem;
        int itemSlot = hotbarSlot == 0 ? 8 : hotbarSlot + 1;
        ItemStack nearbyStack = null;

        if (hotbarSlot < 8)
        {
            nearbyStack = player.inventory.getStackInSlot(itemSlot);
            if (nearbyStack != null)
            {
                Item item = nearbyStack.getItem();
                if (item instanceof ItemPotion && ((ItemPotion) item).isSplash(nearbyStack.getItemDamage()))
                {
                    nearbyStack = item.onItemRightClick(nearbyStack, world, player);
                    if (nearbyStack.stackSize < 1)
                    {
                        nearbyStack = null;
                        player.inventory.setInventorySlotContents(itemSlot, null);
                    }
                }
            }
        }
        return stack;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.none;
	}
	
	@Override public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useCount) {}
	
	/** No need for extra knockback here, I use LotR Combat system*/
	@Override public boolean hitEntity(ItemStack stack, EntityLivingBase mob, EntityLivingBase player) {
		return true;
	}
	
    @Override
    @Optional.Method(modid = "battlegear2")
    public boolean allowOffhand(ItemStack mainhand, ItemStack offhand) {
        if(offhand == null)
            return true;

        return (mainhand != null && mainhand.getItem() != TinkersMEItems.battleaxe) && (offhand.getItem() != TinkersMEItems.battleaxe);
    }
}
