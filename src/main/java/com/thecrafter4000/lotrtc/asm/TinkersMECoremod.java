package com.thecrafter4000.lotrtc.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({"lotrtc."})
public class TinkersMECoremod implements IFMLLoadingPlugin{
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"com.thecrafter4000.lotrtc.asm.TinkersMEClassTransformer"};
	}

	@Override
	public String getSetupClass() {
		return "com.thecrafter4000.lotrtc.asm.TinkersMECoremodSetup";
	}

	@Override
	public void injectData(Map<String, Object> data) {}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public String getModContainerClass() {
		return null;
	}
}
