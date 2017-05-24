package com.thecrafter4000.lotrtc.smeltery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mantle.utils.ItemMetaWrapper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.FluidType;

	/** Melting and hacking, churn and burn */
	public class SmelteryRecipeHandler
	{
	    private static final Map<SmelteryFraction, SmelteryRecipeHandler> instanceMap = new HashMap<SmelteryFraction, SmelteryRecipeHandler>();

	    private final Map<ItemMetaWrapper, FluidStack> smeltingList = new HashMap<ItemMetaWrapper, FluidStack>();
	    private final Map<ItemMetaWrapper, Integer> temperatureList = new HashMap<ItemMetaWrapper, Integer>();
	    private final Map<ItemMetaWrapper, ItemStack> renderIndex = new HashMap<ItemMetaWrapper, ItemStack>();
	    private final List<AlloyMix> alloys = new ArrayList<AlloyMix>();
	    private final Map<Fluid, Integer[]> smelteryFuels = new HashMap<Fluid, Integer[]>(); // fluid -> [power, duration]
	    
	    private static SmelteryRecipeHandler getInstance(FractionSmeltery fraction){
	    	return instanceMap.get(fraction);
	    }
	    
	    /**
	     * Add a new fluid as a valid Smeltery fuel.
	     * @param fluid The fluid.
	     * @param power The temperature of the fluid. This also influences the melting speed. Lava is 1000.
	     * @param duration How long one "portion" of liquid fuels the smeltery. Lava is 10.
	     */
	    public static void addSmelteryFuel (FractionSmeltery fraction, Fluid fluid, int power, int duration)
	    {
	    	getInstance(fraction).smelteryFuels.put(fluid, new Integer[] { power, duration });
	    }

	    /**
	     * Returns true if the liquid is a valid smeltery fuel.
	     */
	    public static boolean isSmelteryFuel (FractionSmeltery fraction, Fluid fluid)
	    {
	        return getInstance(fraction).smelteryFuels.containsKey(fluid);
	    }

	    /**
	     * Returns the power of a smeltery fuel or 0 if it's not a fuel.
	     */
	    public static int getFuelPower (FractionSmeltery fraction, Fluid fluid)
	    {
	        Integer[] power = getInstance(fraction).smelteryFuels.get(fluid);
	        return power == null ? 0 : power[0];
	    }

	    /**
	     * Returns the duration of a smeltery fuel or 0 if it's not a fuel.
	     */
	    public static int getFuelDuration (FractionSmeltery fraction, Fluid fluid)
	    {
	        Integer[] power = getInstance(fraction).smelteryFuels.get(fluid);
	        return power == null ? 0 : power[1];
	    }

	    /**
	     * Adds mappings between an itemstack and an output liquid Example:
	     * Smeltery.addMelting(Block.oreIron, 0, 600, new
	     * FluidStack(liquidMetalStill.blockID, TConstruct.ingotLiquidValue * 2, 0));
	     * 
	     * @param stack The itemstack to liquify. Must hold a block.
	     * @param temperature How hot the block should be before liquifying. Max temp in the Smeltery is 800, other structures may vary
	     * @param output The result of the process in liquid form
	     */
	    public static void addMelting (FractionSmeltery fraction, ItemStack stack, int temperature, FluidStack output)
	    {
	        if (stack.getItem() instanceof ItemBlock)
	            addMelting(fraction, stack, ((ItemBlock) stack.getItem()).field_150939_a, stack.getItemDamage(), temperature, output);
	        else
	            throw new IllegalArgumentException("ItemStack must house a block.");
	    }

	    /**
	     * Adds mappings between a block and its liquid Example:
	     * Smeltery.addMelting(Block.oreIron, 0, 600, new
	     * FluidStack(liquidMetalStill.blockID, TConstruct.ingotLiquidValue * 2, 0));
	     * 
	     * @param block The block to liquify and render
	     * @param metadata The metadata of the block to liquify and render
	     * @param temperature How hot the block should be before liquifying. Max temp in the Smeltery is 800, other structures may vary
	     * @param output The result of the process in liquid form
	     */
	    public static void addMelting (FractionSmeltery fraction, Block block, int metadata, int temperature, FluidStack output)
	    {
	        addMelting(fraction, new ItemStack(block, 1, metadata), block, metadata, temperature, output);
	    }

	    /**
	     * Adds mappings between an input and its liquid. Renders with the given
	     * input's block ID and metadata Example: Smeltery.addMelting(Block.oreIron,
	     * 0, 600, new FluidStack(liquidMetalStill.blockID,
	     * TConstruct.ingotLiquidValue * 2, 0));
	     * 
	     * @param input The item to liquify
	     * @param block The block to render
	     * @param metadata The metadata of the block to render
	     * @param temperature How hot the block should be before liquifying
	     * @param liquid The result of the process
	     */
	    public static void addMelting (FractionSmeltery fraction, ItemStack input, Block block, int metadata, int temperature, FluidStack liquid)
	    {
	    	SmelteryRecipeHandler inst = getInstance(fraction);
	        ItemMetaWrapper in = new ItemMetaWrapper(input);
	        inst.smeltingList.put(in, liquid);
	        inst.temperatureList.put(in, temperature);
	        inst.renderIndex.put(in, new ItemStack(block, input.stackSize, metadata));
	    }

	    /**
	     * Adds an alloy mixing recipe. Example: Smeltery.addAlloyMixing(new
	     * FluidStack(bronzeID, 2, 0), new FluidStack(copperID, 3, 0), new
	     * FluidStack(tinID, 1, 0)); The example mixes 3 copper with 1 tin to make 2
	     * bronze
	     * 
	     * @param result The output of the combination of mixers. The quantity is used for amount of a successful mix
	     * @param mixers the liquids to be mixed. Quantities are used as ratios
	     */
	    public static void addAlloyMixing (FractionSmeltery fraction, FluidStack result, FluidStack... mixers)
	    {
	        ArrayList inputs = new ArrayList();
	        for (FluidStack liquid : mixers)
	            inputs.add(liquid);

	        getInstance(fraction).alloys.add(new AlloyMix(result, inputs));
	    }

	    /**
	     * Used to get the resulting temperature from a source ItemStack
	     * 
	     * @param item The Source ItemStack
	     * @return The result temperature
	     */
	    public static Integer getLiquifyTemperature (FractionSmeltery fraction, ItemStack item)
	    {
	        if (item == null)
	            return 20;

	        Integer temp = getInstance(fraction).temperatureList.get(new ItemMetaWrapper(item));
	        if (temp == null)
	            return 20;
	        else
	            return temp;
	    }

	    /**
	     * Used to get the resulting temperature from a source Block
	     * 
	     * @param block The Source Block
	     * @return The result ItemStack
	     */
	    public static Integer getLiquifyTemperature (FractionSmeltery fraction, Block block, int metadata)
	    {
	        return getInstance(fraction).getLiquifyTemperature(fraction, new ItemStack(block, 1, metadata));
	    }

	    /**
	     * Used to get the resulting ItemStack from a source ItemStack
	     * 
	     * @param item The Source ItemStack
	     * @return The result ItemStack
	     */
	    public static FluidStack getSmelteryResult (FractionSmeltery fraction, ItemStack item)
	    {
	        if (item == null)
	            return null;

	        FluidStack stack = getInstance(fraction).smeltingList.get(new ItemMetaWrapper(item));
	        if (stack == null)
	            return null;
	        return stack.copy();
	    }

	    /**
	     * Used to get the resulting ItemStack from a source Block
	     * 
	     * @param block The Source Block
	     * @return The result ItemStack
	     */
	    public static FluidStack getSmelteryResult (FractionSmeltery fraction, Block block, int metadata)
	    {
	        return getInstance(fraction).getSmelteryResult(fraction, new ItemStack(block, 1, metadata));
	    }

	    public static ItemStack getRenderIndex (FractionSmeltery fraction, ItemStack input)
	    {
	        return getInstance(fraction).renderIndex.get(new ItemMetaWrapper(input));
	    }

	    public static ArrayList mixMetals (FractionSmeltery fraction, ArrayList<FluidStack> moltenMetal)
	    {
	        ArrayList liquids = new ArrayList();
	        for (AlloyMix alloy : getInstance(fraction).alloys)
	        {
	            FluidStack liquid = alloy.mix(moltenMetal);
	            if (liquid != null)
	                liquids.add(liquid);
	        }
	        return liquids;
	    }

	    public static Map<ItemMetaWrapper, FluidStack> getSmeltingList (FractionSmeltery fraction)
	    {
	        return getInstance(fraction).smeltingList;
	    }

	    public static Map<ItemMetaWrapper, Integer> getTemperatureList (FractionSmeltery fraction)
	    {
	        return getInstance(fraction).temperatureList;
	    }

	    public static Map<ItemMetaWrapper, ItemStack> getRenderIndex (FractionSmeltery fraction)
	    {
	        return getInstance(fraction).renderIndex;
	    }

	    public static List<AlloyMix> getAlloyList (FractionSmeltery fraction)
	    {
	        return getInstance(fraction).alloys;
	    }

	    /**
	     * Adds a mapping between FluidType and ItemStack
	     * 
	     * @author samtrion
	     * 
	     * @param type Type of Fluid
	     * @param input The item to liquify
	     * @param temperatureDifference  Difference between FluidType BaseTemperature
	     * @param fluidAmount Amount of Fluid
	     */
	    public static void addMelting (FractionSmeltery fraction, FluidType type, ItemStack input, int temperatureDifference, int fluidAmount)
	    {
	        int temp = type.baseTemperature + temperatureDifference;
	        if (temp <= 20)
	            temp = type.baseTemperature;

	        if (input.getItem() instanceof ItemBlock)
	            addMelting(fraction, input, ((ItemBlock) input.getItem()).field_150939_a, input.getItemDamage(), type.baseTemperature + temperatureDifference, new FluidStack(type.fluid, fluidAmount));
	        else
	            addMelting(fraction, input, type.renderBlock, type.renderMeta, type.baseTemperature + temperatureDifference, new FluidStack(type.fluid, fluidAmount));
	    }

	    /**
	     * Adds all Items to the Smeltery based on the oreDictionary Name
	     * 
	     * @author samtrion
	     * 
	     * @param oreName oreDictionary name e.g. oreIron
	     * @param type Type of Fluid
	     * @param temperatureDifference Difference between FluidType BaseTemperature
	     * @param fluidAmount Amount of Fluid
	     */
	    public static void addDictionaryMelting (FractionSmeltery fraction, String oreName, FluidType type, int temperatureDifference, int fluidAmount)
	    {
	        for (ItemStack is : OreDictionary.getOres(oreName))
	            addMelting(fraction, type, is, temperatureDifference, fluidAmount);
	    }
	}
