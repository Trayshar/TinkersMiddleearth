package com.thecrafter4000.lotrtc;

import com.thecrafter4000.lotrtc.manual.ManualContentRegistry;
import com.thecrafter4000.lotrtc.manual.ManualRegistry;
import com.thecrafter4000.lotrtc.smeltery.FactionSmelteryRender;
import com.thecrafter4000.lotrtc.tools.ClientToolRegistry;
import com.thecrafter4000.lotrtc.tools.ToolRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import tconstruct.client.FlexibleToolRenderer;
import tconstruct.library.tools.ToolCore;

public class ClientProxy extends CommonProxy {
	public static final ResourceLocation icons = new ResourceLocation("lotrtc", "textures/gui/icons.png");
	public static final ResourceLocation tc_icons = new ResourceLocation("tinker", "textures/gui/icons.png");

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		RenderingRegistry.registerBlockHandler(new FactionSmelteryRender());
		ManualRegistry.initClient();
		ManualContentRegistry.init();
		
		/* Register ItemRenderer */
		FlexibleToolRenderer r = new FlexibleToolRenderer();
		for (ToolCore key : ToolRegistry.tools.keySet()) {
			MinecraftForgeClient.registerItemRenderer(key, r);
		}
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
		ClientToolRegistry.postInit();
	}
}
