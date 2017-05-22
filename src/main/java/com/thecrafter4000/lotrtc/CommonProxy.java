package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.smeltery.FractionSmelteryLogic;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
    	ModBlocks.preInit(e);
    }

    public void init(FMLInitializationEvent e) {
    	ModBlocks.init(e);
    	NetworkRegistry.INSTANCE.registerGuiHandler(LotRTCIntegrator.instance, new GuiHandler());
    	GameRegistry.registerTileEntity(FractionSmelteryLogic.class, "FractionSmelteryLogic");
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
