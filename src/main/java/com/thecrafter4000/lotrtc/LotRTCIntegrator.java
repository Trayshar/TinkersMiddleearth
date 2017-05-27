package com.thecrafter4000.lotrtc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.SidedProxy;
import tconstruct.smeltery.blocks.SmelteryBlock;

@Mod(version=LotRTCIntegrator.VERSION, name=LotRTCIntegrator.NAME, modid=LotRTCIntegrator.MODID, acceptedMinecraftVersions="1.7.10", dependencies=LotRTCIntegrator.Dependencies )
public class LotRTCIntegrator {

	public static final String MODID = "lotrtc";
	public static final String VERSION = "1.0.2";
	public static final String NAME = "Tinker's MiddleEarth";
	public static final String Dependencies = "required-after:TConstruct;required-after:lotr";
	@Instance public static LotRTCIntegrator instance;
	
	public static final Logger logger = LogManager.getLogger("LotRTiC");
	@SidedProxy(clientSide="com.thecrafter4000.lotrtc.ClientProxy", serverSide="com.thecrafter4000.lotrtc.ServerProxy")	public static CommonProxy proxy;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
    }

	@EventHandler
    public void init(FMLInitializationEvent e) {
		proxy.init(e);
    }

	@EventHandler
    public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
    }
}
