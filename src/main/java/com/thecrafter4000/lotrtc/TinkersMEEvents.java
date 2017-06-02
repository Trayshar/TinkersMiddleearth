package com.thecrafter4000.lotrtc;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.thecrafter4000.lotrtc.items.TinkersMEItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import tconstruct.library.tools.AbilityHelper;

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
    	// Sorry about copieng this. But the code is so fu*king smart.... 
    	ExtraPlayerInfo playerData = playerStats.remove(event.player.getPersistentID());
    	ExtraPlayerInfo stats = ExtraPlayerInfo.get(event.player);
        if (playerData != null)
        {
            stats.copy(playerData);
        }

        stats.init(event.player, event.player.worldObj);
    }
}
