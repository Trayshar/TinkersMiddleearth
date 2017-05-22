package com.thecrafter4000.lotrtc.smeltery;

import com.thecrafter4000.lotrtc.ModBlocks;

import mantle.blocks.abstracts.MultiServantLogic;
import mantle.world.CoordTuple;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.smeltery.logic.LavaTankLogic;
import tconstruct.smeltery.logic.SmelteryLogic;

public class FractionSmelteryLogic extends SmelteryLogic {

	@Override
	public Container getGuiContainer(InventoryPlayer inventoryplayer, World world, int x, int y, int z) {
		return new FractionSmelteryContainer(inventoryplayer, this);
	}
	
	public boolean isValidBlockID(Block blockID){
		if(this.blockType == ModBlocks.smelteryElves && blockID == ModBlocks.smelteryElves) return true;
		return ((blockID == TinkerSmeltery.smeltery) || (blockID == TinkerSmeltery.smelteryNether));
	}
	
	public boolean isValidTankID(Block blockID){
		return ((blockID == TinkerSmeltery.lavaTank) || (blockID == TinkerSmeltery.lavaTankNether));
	}
	
	// Only copied cause validBlockId(Block) is not visible :d
	
	@Override
	public boolean checkBricksOnLevel(int x, int y, int z, int[] sides) {
			/*  876 */     int numBricks = 0;
			/*      */     
			/*  878 */     int xMin = x - sides[0];
			/*  879 */     int xMax = x + sides[1];
			/*  880 */     int zMin = z - sides[2];
			/*  881 */     int zMax = z + sides[3];
			/*      */     
			/*      */ 
			/*  884 */     for (int xPos = xMin + 1; xPos <= xMax - 1; xPos++)
			/*      */     {
			/*  886 */       for (int zPos = zMin + 1; zPos <= zMax - 1; zPos++)
			/*      */       {
			/*  888 */         Block block = this.worldObj.getBlock(xPos, y, zPos);
			/*  889 */         if ((block != null) && (!this.worldObj.isAirBlock(xPos, y, zPos))) {
			/*  890 */           return false;
			/*      */         }
			/*      */       }
			/*      */     }
			/*      */     
			/*  895 */     for (int xPos = xMin + 1; xPos <= xMax - 1; xPos++)
			/*      */     {
			/*  897 */       numBricks += checkBricks2(xPos, y, zMin);
			/*  898 */       numBricks += checkBricks2(xPos, y, zMax);
			/*      */     }
			/*      */     
			/*  901 */     for (int zPos = zMin + 1; zPos <= zMax - 1; zPos++)
			/*      */     {
			/*  903 */       numBricks += checkBricks2(xMin, y, zPos);
			/*  904 */       numBricks += checkBricks2(xMax, y, zPos);
			/*      */     }
			/*      */     
			/*  907 */     int neededBricks = (xMax - xMin) * 2 + (zMax - zMin) * 2 - 4;
			/*      */     
			/*  909 */     return numBricks == neededBricks;
			/*      */   }
	
	private int checkBricks2(int x, int y, int z)
	/*      */   {
	/* 1001 */     int tempBricks = 0;
	/* 1002 */     Block blockID = this.worldObj.getBlock(x, y, z);
	/* 1003 */     if ((isValidBlockID(blockID) || (isValidTankID(blockID))))
	/*      */     {
	/* 1005 */       TileEntity te = this.worldObj.getTileEntity(x, y, z);
	/* 1006 */       if (te == this)
	/*      */       {
	/* 1008 */         tempBricks++;
	/*      */       }
	/* 1010 */       else if ((te instanceof MultiServantLogic))
	/*      */       {
	/* 1012 */         MultiServantLogic servant = (MultiServantLogic)te;
	/*      */         
	/* 1014 */         if (servant.hasValidMaster())
	/*      */         {
	/* 1016 */           if (servant.verifyMaster(this, this.worldObj, this.xCoord, this.yCoord, this.zCoord)) {
	/* 1017 */             tempBricks++;
	/*      */           }
	/*      */         }
	/*      */         else {
	/* 1021 */           servant.overrideMaster(this.xCoord, this.yCoord, this.zCoord);
	/* 1022 */           tempBricks++;
	/*      */         }
	/*      */         
	/* 1025 */         if ((te instanceof LavaTankLogic))
	/*      */         {
	/* 1027 */           this.lavaTanks.add(new CoordTuple(x, y, z));
	/*      */         }
	/*      */       }
	/*      */     }
	/* 1031 */     return tempBricks;
	/*      */   }

	public int recurseStructureDown(int x, int y, int z, int[] sides, int count)
	/*      */   {
	/*  937 */     boolean check = checkBricksOnLevel(x, y, z, sides);
	/*      */     
	/*  939 */     if (!check)
	/*      */     {
	/*      */ 
	/*  942 */       Block block = this.worldObj.getBlock(x, y, z);
	/*  943 */       if ((block != null) && (!this.worldObj.isAirBlock(x, y, z)) && 
	/*  944 */         (isValidBlockID(block))) {
	/*  945 */         return validateBottom(x, y, z, sides, count);
	/*      */       }
	/*  947 */       return count;
	/*      */     }
	/*      */     
	/*  950 */     count++;
	/*  951 */     return recurseStructureDown(x, y - 1, z, sides, count);
	/*      */   }
 
	public int validateBottom(int x, int y, int z, int[] sides, int count)
	/*      */   {
	/*  956 */     int bottomBricks = 0;
	/*  957 */     int xMin = x - sides[0] + 1;
	/*  958 */     int xMax = x + sides[1] - 1;
	/*  959 */     int zMin = z - sides[2] + 1;
	/*  960 */     int zMax = z + sides[3] - 1;
	/*      */     
	/*      */ 
	/*  963 */     for (int xPos = xMin; xPos <= xMax; xPos++)
	/*      */     {
	/*  965 */       for (int zPos = zMin; zPos <= zMax; zPos++)
	/*      */       {
	/*  967 */         if ((isValidBlockID(this.worldObj.getBlock(xPos, y, zPos))) && (this.worldObj.getBlockMetadata(xPos, y, zPos) >= 2)) {
	/*  968 */           TileEntity te = this.worldObj.getTileEntity(xPos, y, zPos);
	/*      */           
	/*  970 */           if ((te instanceof MultiServantLogic)) {
	/*  971 */             MultiServantLogic servant = (MultiServantLogic)te;
	/*  972 */             if (servant.hasValidMaster()) {
	/*  973 */               if (servant.verifyMaster(this, this.worldObj, this.xCoord, this.yCoord, this.zCoord))
	/*  974 */                 bottomBricks++;
	/*      */             } else {
	/*  976 */               servant.overrideMaster(this.xCoord, this.yCoord, this.zCoord);
	/*  977 */               bottomBricks++;
	/*      */             }
	/*      */           }
	/*      */         }
	/*      */       }
	/*      */     }
	/*      */     
	/*  984 */     int neededBricks = (xMax + 1 - xMin) * (zMax + 1 - zMin);
	/*      */     
	/*  986 */     if (bottomBricks == neededBricks)
	/*      */     {
	/*  988 */       this.tempValidStructure = true;
	/*  989 */       this.minPos = new CoordTuple(xMin, y + 1, zMin);
	/*  990 */       this.maxPos = new CoordTuple(xMax, y + 1, zMax);
	/*      */     }
	/*  992 */     return count;
	/*      */   }

}
