package com.thecrafter4000.lotrtc.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.TransformerExclusions({"com.thecrafter4000.lotrtc."})
public class TinkersMECoremod implements IFMLLoadingPlugin {
	static final Logger logger = LogManager.getLogger("Tinker's Middleearth Coremod");
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"com.thecrafter4000.lotrtc.asm.TinkersMEClassTransformer"};
	}

	@Override
	public String getSetupClass() {
		return "com.thecrafter4000.lotrtc.asm.TinkersMECoremodSetup";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		if(data != null && data.get("runtimeDeobfuscationEnabled") instanceof Boolean) {
			boolean b = (boolean) data.get("runtimeDeobfuscationEnabled");
			logger.info("Detected " + (b ? "" : "de") + "obfuscated environment");
			TinkersMEClassTransformer.isObfuscated = b;
		} else {
			logger.error("Failed to detect obfuscation status. THIS SHOULD NOT HAPPEN!");
		}
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public String getModContainerClass() {
		return null;
	}
}
