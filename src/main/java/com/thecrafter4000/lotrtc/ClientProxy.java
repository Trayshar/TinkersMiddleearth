package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.manual.ManualContentRegistry;
import com.thecrafter4000.lotrtc.manual.ManualRegistry;
import com.thecrafter4000.lotrtc.smeltery.FractionSmelteryRender;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		ManualRegistry.preInitClient();
		super.preInit(e);
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		RenderingRegistry.registerBlockHandler(new FractionSmelteryRender());
		ManualRegistry.initClient();
		ManualContentRegistry.init();
	}
}
