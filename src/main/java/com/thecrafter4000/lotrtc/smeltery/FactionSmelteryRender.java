package com.thecrafter4000.lotrtc.smeltery;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import tconstruct.smeltery.model.SmelteryRender;
import tconstruct.util.ItemHelper;

/** Probably not necessary anymore */
public class FactionSmelteryRender extends SmelteryRender {

	public static int renderId = RenderingRegistry.getNextAvailableRenderId();
	
    @Override
    public void renderInventoryBlock (Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if (modelID == renderId)
        {
            ItemHelper.renderStandardInvBlock(renderer, block, metadata);
        }
    }

    @Override
    public boolean renderWorldBlock (IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
    {
        if (modelID == renderId)
        {
            if (world.getBlockMetadata(x, y, z) == 0)
                return renderSmeltery(world, x, y, z, block, modelID, renderer);
            else
                renderer.renderStandardBlock(block, x, y, z);
        }
        return true;
    }
    
    public int getRenderId() {
		return renderId;
	}
}
