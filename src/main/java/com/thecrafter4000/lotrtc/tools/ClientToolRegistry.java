package com.thecrafter4000.lotrtc.tools;

import com.thecrafter4000.lotrtc.client.ExtendedToolGuiElement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tconstruct.library.client.TConstructClientRegistry;

@SideOnly(Side.CLIENT)
public class ClientToolRegistry {
    public static void postInit() {
        for (ToolRegistry.ToolPartEntry entry : ToolRegistry.parts.values()) {
            tconstruct.library.client.TConstructClientRegistry
                    .addStencilButton2(entry.xButton, entry.yButton, entry.guiID, entry.domain, entry.textureButton);
        }

        ToolRegistry.tools.forEach((tool, data) -> {
            if (data.addGui) {
                String desc = String.format("gui.toolstation.%s.desc", tool.getToolName().toLowerCase());
                if (data.tierTwo) {
                    TConstructClientRegistry.addTierTwoButton(
                            new ExtendedToolGuiElement(
                                    data.guiType, data.xButton, data.yButton,
                                    data.xIcon, data.yIcon, tool.getLocalizedToolName(),
                                    desc, data.domain, data.textureButton, data.usesCustomTexture)
                    );
                } else {
                    TConstructClientRegistry.addToolButton(
                            new ExtendedToolGuiElement(
                                    data.guiType, data.xButton, data.yButton,
                                    data.xIcon, data.yIcon, tool.getLocalizedToolName(),
                                    desc, data.domain, data.textureButton, data.usesCustomTexture)
                    );
                }
            }
        });
    }
}
