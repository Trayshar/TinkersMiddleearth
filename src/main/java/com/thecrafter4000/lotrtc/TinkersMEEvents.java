package com.thecrafter4000.lotrtc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.thecrafter4000.lotrtc.TinkersMEConfig.LotRMaterialID;
import com.thecrafter4000.lotrtc.items.TinkersMEItems;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.event.ToolBuildEvent;
import tconstruct.library.event.ToolCraftEvent;
import tconstruct.library.tools.AbilityHelper;
import tconstruct.library.tools.ToolCore;
import tconstruct.tools.TinkerTools;

public class TinkersMEEvents {

	/* Book stuff */
	
    private ConcurrentHashMap<UUID, ExtraPlayerInfo> playerStats = new ConcurrentHashMap<UUID, ExtraPlayerInfo>();
	
	@SubscribeEvent
    public void onEntityConstructing (EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && ExtraPlayerInfo.get((EntityPlayer) event.entity) == null) {
        	ExtraPlayerInfo.register((EntityPlayer) event.entity);
        }
    }
	
    @SubscribeEvent
    public void PlayerLoggedInEvent (PlayerLoggedInEvent event){
        ExtraPlayerInfo data = ExtraPlayerInfo.get(event.player);
        
        if(!data.hasBook){
        	data.hasBook = true;
        	ItemStack book = new ItemStack(TinkersMEItems.manual);
            if (!event.player.inventory.addItemStackToInventory(book))
            {
                AbilityHelper.spawnItemAtPlayer(event.player, book);
            }
        }
    }
    
    public void PlayerRespawn(PlayerRespawnEvent event){
        // Boom! Boom!!! Booom!!!!!!!
    	// Sorry about coping this. But the code is so fu*king smart.... 
    	ExtraPlayerInfo playerData = playerStats.remove(event.player.getPersistentID());
    	ExtraPlayerInfo stats = ExtraPlayerInfo.get(event.player);
        if (playerData != null) {
            stats.copy(playerData);
        }

        stats.init(event.player, event.player.worldObj);
    }
    
    /* Adds an tooltip */
    
    @SubscribeEvent
    public void addTooltip(ItemTooltipEvent event){
    	if(event.itemStack != null && event.itemStack.getItem() != null && event.itemStack.getItem() instanceof ToolCore){
        	float meleeSpeed = LOTRWeaponStats.getMeleeSpeed(event.itemStack);
        	int pcSpeed = Math.round(meleeSpeed * 100.0F);
        	event.toolTip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.meleeSpeed", new Object[] { Integer.valueOf(pcSpeed) }));

        	float reach = LOTRWeaponStats.getMeleeReachFactor(event.itemStack);
        	int pcReach = Math.round(reach * 100.0F);
        	event.toolTip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.reach", new Object[] { Integer.valueOf(pcReach) }));

        	int kb = LOTRWeaponStats.getTotalKnockback(event.itemStack);
        	if (kb > 0) {
        		event.toolTip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.kb", new Object[] { Integer.valueOf(kb) }));
        	}
    	}
    }
    
    /* Adds bones, mallorn and blackroot as materials*/
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void buildToolPre(ToolBuildEvent event) {
        for (ItemStack bone : OreDictionary.getOres("bone")) {
            if (OreDictionary.itemMatches(bone, event.handleStack, false)) {
                event.handleStack = new ItemStack(TinkerTools.toolRod, 1, 5); // bone tool rod
                return;
            }
        }
        
        if(event.handleStack != null && event.handleStack.getItem() != null){
        	Item item = event.handleStack.getItem();
        	if(item == LOTRMod.mallornStick) {
        		event.handleStack = new ItemStack(TinkerTools.toolRod, 1, LotRMaterialID.Mallorn);
        		return;
        	}
        	if(item == LOTRMod.blackrootStick) {
        		event.handleStack = new ItemStack(TinkerTools.toolRod, 1, LotRMaterialID.Blackroot);
        		return;
        	}
        }
    }
    
    /* Add some attack damage to some tools */
    
    @SubscribeEvent
    public void buildToolPost(ToolCraftEvent.NormalTool event) {
    	if( event.tool == TinkersMEItems.battleaxe ){
    		increaseDamage(event, 1);
    	}
    }
    
    public void increaseDamage(ToolCraftEvent.NormalTool event, int damage){
        NBTTagCompound tag = event.toolTag;
        int attack = tag.getCompoundTag("InfiTool").getInteger("Attack") + 1;
        tag.getCompoundTag("InfiTool").setInteger("Attack", attack);
        tag.getCompoundTag("InfiTool").setInteger("BaseAttack", attack);
        
		ItemStack stack = new ItemStack(event.tool);
		stack.setTagCompound(tag);
		event.overrideResult(stack);
    }
}
