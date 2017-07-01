package com.thecrafter4000.lotrtc.client;

import tconstruct.library.client.ToolGuiElement;
import tconstruct.library.tools.ToolCore;

public class ExtendedToolGuiElement extends ToolGuiElement {

	public final ToolCore tool;
	
	public ExtendedToolGuiElement(int st, int bx, int by, int[] xi, int[] yi, String t, String b, String d, String tex, ToolCore tool) {
		super(st, bx, by, xi, yi, t, b, d, tex);
		this.tool = tool;
	}

}
