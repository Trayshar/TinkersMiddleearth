package com.thecrafter4000.lotrtc.asm.overlays;

import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.crafting.Smeltery;

import java.util.ArrayList;

/**
 * Replaces some functions in {@link tconstruct.smeltery.logic.SmelteryLogic}
 */
public class SmelteryLogic extends tconstruct.smeltery.logic.SmelteryLogic {

    /** Shadows {@link tconstruct.smeltery.logic.SmelteryLogic#needsUpdate} */
    boolean needsUpdate;

    /** Shadows {@link tconstruct.smeltery.logic.SmelteryLogic#addMoltenMetal()} */
    boolean addMoltenMetal (FluidStack liquid, boolean first) {
        return false;
    }

    /** Replaces {@link tconstruct.smeltery.logic.SmelteryLogic#heatItems()} */
    private void heatItems ()
    {
        if (useTime > 0)
        {
            boolean hasUse = false;
            int temperature = this.getInternalTemperature();
            int speed = temperature / 100;
            int refTemp = temperature * 10;
            for (int i = 0; i < maxBlockCapacity; i++)
            {
                if (meltingTemps[i] > 200 && this.isStackInSlot(i))
                {
                    hasUse = true;
                    if (activeTemps[i] < refTemp && activeTemps[i] < meltingTemps[i])
                    {
                        activeTemps[i] += speed; // lava has temp of 1000. we increase by 10 per application.
                    }
                    else if (activeTemps[i] >= meltingTemps[i])
                    {
                        if (!worldObj.isRemote)
                        {
                            FluidStack result = getResultFor(inventory[i]);
                            if (result != null)
                            {
                                if (addMoltenMetal(result, false))
                                {
                                    inventory[i] = null;
                                    activeTemps[i] = 200;
                                    mixMetals();
                                    markDirty();
                                }
                            }
                        }
                    }

                }

                else
                    activeTemps[i] = 200;
            }
            inUse = hasUse;
        }
    }


    /** Replaces {@link tconstruct.smeltery.logic.SmelteryLogic#fill(FluidStack, boolean)} */
    public int fill (FluidStack resource, boolean doFill)
    {
        // don't fill if we're not complete
        if(!validStructure)
            return 0;

        if (resource != null && currentLiquid < maxLiquid)
        {
            if (resource.amount + currentLiquid > maxLiquid)
                resource.amount = maxLiquid - currentLiquid;
            int amount = resource.amount;

            if (amount > 0 && doFill)
            {
                if (addMoltenMetal(resource, false))
                {
                    mixMetals();
                }
                needsUpdate = true;
                worldObj.func_147479_m(xCoord, yCoord, zCoord);
            }
            return amount;
        }
        else
            return 0;
    }

    private void mixMetals() {
        for (FluidStack alloy : getAlloys()) {
            addMoltenMetal(alloy, true);
        }
    }

    public ArrayList<FluidStack> getAlloys() {
        return Smeltery.mixMetals(moltenMetal);
    }
}
