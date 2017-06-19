package com.thecrafter4000.lotrtc.tools;

import java.util.List;
import java.util.Locale;

import com.thecrafter4000.lotrtc.CommonProxy;
import com.thecrafter4000.lotrtc.tools.ToolRegistry.ToolPartEntry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tconstruct.tools.items.Pattern;
import tconstruct.util.Reference;

public class LotRPattern extends Pattern {
	
	/* patternType -> Texture string, sets material */
	public LotRPattern(String patternType, String name) {
        super(getPatternTextures(patternType), getPatternNames(patternType), "patterns/");
		this.setCreativeTab(CommonProxy.LotRTiCTab);
		this.setUnlocalizedName(name);
		this.modTexPrefix = "lotrtc";
	}

	private static String[] getPatternTextures(String patternType){
		String[] array = new String[ToolRegistry.parts.size()];
		for(Integer i : ToolRegistry.parts.keySet()){
			ToolPartEntry e = ToolRegistry.parts.get(i);
			if(e.addSmelteryCasting || !patternType.equals("cast_")){
				array[i] = ToolRegistry.parts.get(i).name.toLowerCase(Locale.ENGLISH);
			}
			else {
				array[i] = "";
			}
		}
		return array;
	}
	
    public static String[] getPatternNames (String patternType) {
    	String[] patternName = getPatternTextures(patternType);
        String[] names = new String[patternName.length];
        for (int i = 0; i < patternName.length; i++) {
        	if(!patternName[i].equals("")) {
        		names[i] = patternType + patternName[i];        	
        	}else{
        		names[i] = "";
        	}
        }
        return names;
    }

    @Override
    public void getSubItems (Item b, CreativeTabs tab, List list) {
        for (int i = 0; i < this.unlocalizedNames.length; i++) {
            if(!this.unlocalizedNames[i].equals("")) list.add(new ItemStack(b, 1, i));
        }
    }
    
    @Override
    public int getPatternCost(ItemStack pattern) {
    	return ToolRegistry.getPatternCost(pattern.getItemDamage());
    }
}
