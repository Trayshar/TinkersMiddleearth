package com.thecrafter4000.lotrtc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thecrafter4000.lotrtc.smeltery.FractionSmelteryLogic;
import com.thecrafter4000.lotrtc.smeltery.LotrFilledBucket;
import com.thecrafter4000.lotrtc.smeltery.LotrSmelteryFraction;
import com.thecrafter4000.lotrtc.smeltery.SmelteryRecipeHandler;
import com.thecrafter4000.lotrtc.smeltery.SmelteryRecipes;
import com.thecrafter4000.lotrtc.tools.ToolRecipes;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockCraftingTable;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.library.crafting.Smeltery;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.smeltery.items.FilledBucket;
import tconstruct.tools.TinkerTools;
import tconstruct.util.config.PHConstruct;
import tconstruct.world.TinkerWorld;

public class CommonProxy {

	public static final CreativeTabs LotRTiCTab = new CreativeTabs("lotrtc") {
		
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ModBlocks.smelteryHighElves);
		}
	};
	
	public static Item buckets;
	
    public void preInit(FMLPreInitializationEvent e) {
    	GameRegistry.registerItem(buckets = new LotrFilledBucket(mantle.blocks.BlockUtils.getBlockFromItem(buckets)), "buckets");
    	ModBlocks.preInit(e);
    	MaterialRegistry.setup();
    	ToolRecipes.registerMaterials();
    }

    public void init(FMLInitializationEvent e) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(LotRTCIntegrator.instance, new GuiHandler());
    	GameRegistry.registerTileEntity(FractionSmelteryLogic.class, "lotrtc:fractionsmelterylogic");
    	patchLotrOres();
    	SmelteryRecipes.registerCasting();
    	ToolRecipes.registerToolCasting();
    	SmelteryRecipes.registerAlloys();
    	registerSmelteryBlock(LOTRRecipes.angmarRecipes, ModBlocks.smelteryAngmar);
    	registerSmelteryBlock(LOTRRecipes.dwarvenRecipes, ModBlocks.smelteryDwarven);
    	registerSmelteryBlock(LOTRRecipes.highElvenRecipes, ModBlocks.smelteryHighElves);
    }

    public void postInit(FMLPostInitializationEvent e) {
    	SmelteryRecipes.registerSmelteryStuff();
    	ToolRecipes.registerMetals();
    }
    
    public static void patchLotrOres(){
    	OreDictionary.registerOre("blockCopper", new ItemStack(LOTRMod.blockOreStorage, 1, 0));
    	OreDictionary.registerOre("blockTin", new ItemStack(LOTRMod.blockOreStorage, 1, 1));
    	OreDictionary.registerOre("blockBronze", new ItemStack(LOTRMod.blockOreStorage, 1, 2));
    	OreDictionary.registerOre("blockSilver", new ItemStack(LOTRMod.blockOreStorage, 1, 3));
    	OreDictionary.registerOre("nuggetObsidian", LOTRMod.obsidianShard);
    }
    
    private void registerSmelteryBlock(List<IRecipe> recipeList, Block smeltery){
    	ItemStack searedBrick = new ItemStack(TinkerTools.materials, 1, 2);
    	recipeList.add(new ShapedRecipes(3, 3, new ItemStack[]{searedBrick, searedBrick, searedBrick, searedBrick, null, searedBrick, searedBrick, searedBrick, searedBrick}, new ItemStack(smeltery, 1, 0)));
    	recipeList.add(new ShapedRecipes(3, 3, new ItemStack[]{searedBrick, null, searedBrick, searedBrick, null, searedBrick, searedBrick, null, searedBrick}, new ItemStack(smeltery, 1, 1)));
    	recipeList.add(new ShapedRecipes(2, 2, new ItemStack[]{searedBrick, searedBrick, searedBrick, searedBrick}, new ItemStack(smeltery, 1, 2)));

    }
     
}
