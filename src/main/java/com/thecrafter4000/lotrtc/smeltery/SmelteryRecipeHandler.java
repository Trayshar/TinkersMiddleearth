package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.TinkersMiddleearth;
import mantle.utils.ItemMetaWrapper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.Smeltery;

import java.util.*;

/** Adapted from {@link Smeltery} */
	public class SmelteryRecipeHandler {

		private static final Map<SmelteryMainFaction, SmelteryRecipeHandler> instanceMap = new HashMap<>();

		//TODO: [SmelteryV2] Rewrite to accept multiple fluid outputs from on item
	    private final Map<ItemMetaWrapper, FluidStack> smeltingList = new HashMap<>();
	    private final Map<ItemMetaWrapper, Integer> temperatureList = new HashMap<>();
	    private final List<AlloyMix> alloys = new ArrayList<>();
	    private final Map<Fluid, Integer[]> smelteryFuels = new HashMap<>(); // fluid -> [power, duration]
	    
		static{
			for (SmelteryMainFaction t : SmelteryMainFaction.class.getEnumConstants())
				instanceMap.put(t, new SmelteryRecipeHandler());
		}

		private static SmelteryRecipeHandler getInstance(SmelteryMainFaction faction) {
	    	return instanceMap.get(faction);
	    }
	    
	    /**
	     * Add a new fluid as a valid Smeltery fuel.
	     * @param fluid The fluid.
	     * @param power The temperature of the fluid. This also influences the melting speed. Lava is 1000.
	     * @param duration How long one "portion" of liquid fuels the smeltery. Lava is 10.
	     */
		public static void addSmelteryFuel(SmelteryMainFaction faction, Fluid fluid, int power, int duration)
	    {
	    	getInstance(faction).smelteryFuels.put(fluid, new Integer[] { power, duration });
	    }

	    /**
	     * Returns true if the liquid is a valid smeltery fuel.
	     */
		public static boolean isSmelteryFuel(SmelteryMainFaction faction, Fluid fluid)
	    {
	        boolean tmp = getInstance(faction).smelteryFuels.containsKey(fluid);
	        if(!tmp) return Smeltery.isSmelteryFuel(fluid);
	        return tmp;
	    }

	    /**
	     * Returns the power of a smeltery fuel or 0 if it's not a fuel.
	     */
		public static int getFuelPower(SmelteryMainFaction faction, Fluid fluid)
	    {
	        Integer[] power = getInstance(faction).smelteryFuels.get(fluid);
	        return power == null ? Smeltery.getFuelPower(fluid) : power[0];
	    }

	    /**
	     * Returns the duration of a smeltery fuel or 0 if it's not a fuel.
	     */
		public static int getFuelDuration(SmelteryMainFaction faction, Fluid fluid)
	    {
	        Integer[] power = getInstance(faction).smelteryFuels.get(fluid);
	        return power == null ? Smeltery.getFuelDuration(fluid) : power[1];
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
		public static void addMelting(SmelteryMainFaction faction, ItemStack stack, int temperature, FluidStack output)
	    {
	        if (stack.getItem() instanceof ItemBlock)
	            addMelting(faction, stack, ((ItemBlock) stack.getItem()).field_150939_a, stack.getItemDamage(), temperature, output);
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
		public static void addMelting(SmelteryMainFaction faction, Block block, int metadata, int temperature, FluidStack output)
	    {
	        addMelting(faction, new ItemStack(block, 1, metadata), block, metadata, temperature, output);
	    }

		public static void addMelting(SmelteryMainFaction faction, Block block, int metadata, int temperaturOverlay, Fluid fluid, int fluidamount)
	    {
	        addMelting(faction, new ItemStack(block, 1, metadata), block, metadata, FluidType.getTemperatureByFluid(fluid) + temperaturOverlay, new FluidStack(fluid, fluidamount));
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
		public static void addMelting(SmelteryMainFaction faction, ItemStack input, Block block, int metadata, int temperature, FluidStack liquid)
	    {
	    	SmelteryRecipeHandler inst = getInstance(faction);
	        ItemMetaWrapper in = new ItemMetaWrapper(input);
	        inst.smeltingList.put(in, liquid);
	        inst.temperatureList.put(in, temperature);

			// Allways add the render data to the main smeltery, since its client-only anyway and only rendered if the recipe works
			ItemStack prev = Smeltery.getRenderIndex().put(in, new ItemStack(block, input.stackSize, metadata));
			if(prev != null && (((ItemBlock)prev.getItem()).field_150939_a != block || prev.getItemDamage() != metadata)) {
				TinkersMiddleearth.logger.warn("[SmelteryRecipeHandler] Replaced render block for ({}:{}) with ({}:{}), previous ({}:{})", in.item, in.meta, block, metadata, prev.getItem(), prev.getItemDamage());
			}
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
		public static void addAlloyMixing(SmelteryMainFaction faction, FluidStack result, FluidStack... mixers)
	    {
	        ArrayList<FluidStack> inputs = new ArrayList<>();
			Collections.addAll(inputs, mixers);

	        getInstance(faction).alloys.add(new AlloyMix(result, inputs));
	    }

	    /**
	     * Used to get the resulting temperature from a source ItemStack
	     * 
	     * @param item The Source ItemStack
	     * @return The result temperature
	     */
		public static Integer getLiquifyTemperature(SmelteryMainFaction faction, ItemStack item)
	    {
	        if (item == null)
	            return 20;

	        Integer temp = getInstance(faction).temperatureList.get(new ItemMetaWrapper(item));
	        if (temp == null) temp = Smeltery.getLiquifyTemperature(item);
	        return temp;
	    }

	    /**
	     * Used to get the resulting temperature from a source Block
	     * 
	     * @param block The Source Block
	     * @return The result ItemStack
	     */
		public static Integer getLiquifyTemperature(SmelteryMainFaction faction, Block block, int metadata)
	    {
	        return SmelteryRecipeHandler.getLiquifyTemperature(faction, new ItemStack(block, 1, metadata));
	    }

	    /**
	     * Used to get the resulting ItemStack from a source ItemStack
	     * 
	     * @param item The Source ItemStack
	     * @return The result ItemStack
	     */
		public static FluidStack getSmelteryResult(SmelteryMainFaction faction, ItemStack item)
	    {
	        if (item == null)
	            return null;

	        FluidStack stack = getInstance(faction).smeltingList.get(new ItemMetaWrapper(item));
	        if (stack == null)
	        	stack = Smeltery.getSmelteryResult(item);
	        return stack.copy();
	    }

	    /**
	     * Used to get the resulting ItemStack from a source Block
	     * 
	     * @param block The Source Block
	     * @return The result ItemStack
	     */
		public static FluidStack getSmelteryResult(SmelteryMainFaction faction, Block block, int metadata)
	    {
	        return SmelteryRecipeHandler.getSmelteryResult(faction, new ItemStack(block, 1, metadata));
	    }

		public static ArrayList<FluidStack> mixMetals(SmelteryMainFaction faction, ArrayList<FluidStack> moltenMetal)
	    {
			ArrayList<FluidStack>  liquids = new ArrayList<>();
	        for (AlloyMix alloy : getInstance(faction).alloys)
	        {
	            FluidStack liquid = alloy.mix(moltenMetal);
	            if (liquid != null)
	                liquids.add(liquid);
	        }
	        if(liquids.isEmpty()) return Smeltery.mixMetals(moltenMetal);
	        return liquids;
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
		public static void addMelting(SmelteryMainFaction faction, FluidType type, ItemStack input, int temperatureDifference, int fluidAmount)
	    {
	        int temp = type.baseTemperature + temperatureDifference;
	        if (temp <= 20)
	            temp = type.baseTemperature;

	        if (input.getItem() instanceof ItemBlock)
	            addMelting(faction, input, ((ItemBlock) input.getItem()).field_150939_a, input.getItemDamage(), type.baseTemperature + temperatureDifference, new FluidStack(type.fluid, fluidAmount));
	        else
	            addMelting(faction, input, type.renderBlock, type.renderMeta, type.baseTemperature + temperatureDifference, new FluidStack(type.fluid, fluidAmount));
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
		public static void addDictionaryMelting(SmelteryMainFaction faction, String oreName, FluidType type, int temperatureDifference, int fluidAmount)
	    {
	        for (ItemStack is : OreDictionary.getOres(oreName))
	            addMelting(faction, type, is, temperatureDifference, fluidAmount);
	    }
	}
