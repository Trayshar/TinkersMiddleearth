package com.thecrafter4000.lotrtc.manual;

import com.thecrafter4000.lotrtc.CommonProxy;
import com.thecrafter4000.lotrtc.manual.ManualRegistry.BookRegEntry;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mantle.books.BookData;
import mantle.client.gui.GuiManual;
import mantle.items.abstracts.CraftingItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tconstruct.TConstruct;
import tconstruct.achievements.TAchievements;

import java.util.List;

public class ManualItem extends CraftingItem {

	public ManualItem() {
		super(getNames(), getNames(), "", "lotrtc", CommonProxy.LotRTiCTab);
		this.setUnlocalizedName("lotrtc.manual");
	}
	
	private static String[] getNames(){
		String[] s = new String[ManualRegistry.list.size()];
		int i = 0;
		for(BookRegEntry r : ManualRegistry.list) {
			s[i] = r.name;
			i++;
		}
		return s;
	}

    @Override
    public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player)
    {
		TAchievements.triggerAchievement(player, "tconstruct.beginner");

        if(world.isRemote) {
            openBook(stack, world, player);
        }
        return stack;
    }

    @SideOnly(Side.CLIENT)
    public void openBook(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(TConstruct.instance, mantle.client.MProxyClient.manualGuiID, world, 0, 0, 0);
        FMLClientHandler.instance().displayGuiScreen(player, new GuiManual(stack, getData(stack)));
    }


    private BookData getData (ItemStack stack)
    {
    	return ManualRegistry.list.get(stack.getItemDamage()).data;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
    	list.add(ManualRegistry.list.get(stack.getItemDamage()).toolTip);
    }
}
