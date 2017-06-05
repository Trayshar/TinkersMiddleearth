package com.thecrafter4000.lotrtc;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.event.ToolBuildEvent;
import tconstruct.library.tools.AbilityHelper;
import tconstruct.tools.TinkerTools;

public class TinkersMEEvents {

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
        if (playerData != null)
        {
            stats.copy(playerData);
        }

        stats.init(event.player, event.player.worldObj);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void buildTool (ToolBuildEvent event) {
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
}
