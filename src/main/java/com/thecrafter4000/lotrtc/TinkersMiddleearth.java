package com.thecrafter4000.lotrtc;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(version=TinkersMiddleearth.VERSION, name=TinkersMiddleearth.NAME, modid=TinkersMiddleearth.MODID, acceptedMinecraftVersions="1.7.10", dependencies=TinkersMiddleearth.Dependencies )
public class TinkersMiddleearth {

	public static final String MODID = "@modid@";
	public static final String VERSION = "@version@";
	public static final String NAME = "@name@";
	public static final String Dependencies = "required-after:TConstruct;required-after:lotr";
	@Instance public static TinkersMiddleearth instance;
	
	public static final Logger logger = LogManager.getLogger(NAME);
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
