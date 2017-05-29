package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.smeltery.FractionSmelteryRender;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		RenderingRegistry.registerBlockHandler(new FractionSmelteryRender());
	}
}
