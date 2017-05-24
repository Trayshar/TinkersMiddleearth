package com.thecrafter4000.lotrtc.smeltery;

import java.util.ArrayList;
import java.util.Random;

import com.thecrafter4000.lotrtc.ModBlocks;

import cpw.mods.fml.common.FMLCommonHandler;
import mantle.blocks.abstracts.MultiServantLogic;
import mantle.world.CoordTuple;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import tconstruct.smeltery.SmelteryDamageSource;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.smeltery.logic.LavaTankLogic;
import tconstruct.smeltery.logic.SmelteryLogic;
import tconstruct.util.config.PHConstruct;

public class FractionSmelteryLogic extends SmelteryLogic {

	public SmelteryFraction fraction;
	
	private boolean needsUpdate;
	private int tick;
	
	public FractionSmelteryLogic(SmelteryFraction fraction) {
		super();
		this.fraction = fraction;
	}
	
	public FractionSmelteryLogic() {
		super();
	}
	
	@Override public Container getGuiContainer(InventoryPlayer inventoryplayer, World world, int x, int y, int z) {
		return new FractionSmelteryContainer(inventoryplayer, this);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		tags.setString("Fraction", fraction.name());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		this.fraction = SmelteryFraction.valueOf(tags.getString("Fraction"));
	}
	
	public boolean isValidBlockID(Block blockID){
		if(this.getBlockType() == ModBlocks.smelteryElves && blockID == ModBlocks.smelteryElves) return true;
		return ((blockID == TinkerSmeltery.smeltery) || (blockID == TinkerSmeltery.smelteryNether));
	}
	
	public boolean isValidTankID(Block blockID){
		return ((blockID == TinkerSmeltery.lavaTank) || (blockID == TinkerSmeltery.lavaTankNether));
	}
	
	// Only copied cause validBlockId(Block) is not visible :d
	
	@Override public boolean checkBricksOnLevel(int x, int y, int z, int[] sides) {
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
//			System.out.println("[" + FMLCommonHandler.instance().getEffectiveSide() + "] CheckBricks: needed:" + neededBricks + ", num: " + numBricks);
			/*  909 */     return numBricks == neededBricks;
			/*      */   }
	
	private int checkBricks2(int x, int y, int z)
	/*      */   {
	/* 1001 */     int tempBricks = 0;
	/* 1002 */     Block blockID = this.worldObj.getBlock(x, y, z);
//	System.out.println("[" + FMLCommonHandler.instance().getEffectiveSide() + "] Block:" + blockID);
	/* 1003 */     if ((isValidBlockID(blockID) || (isValidTankID(blockID))))
	/*      */     {
	/* 1005 */       TileEntity te = this.worldObj.getTileEntity(x, y, z);
	/* 1006 */       if (te == this)
	/*      */       {
//		System.out.println("[" + FMLCommonHandler.instance().getEffectiveSide() + "] SmelteryLogic detected:" + x + ", " + y + ", " + z);
	/* 1008 */         tempBricks++;
	/*      */       }
	/* 1010 */       else if ((te instanceof MultiServantLogic))
	/*      */       {
//		System.out.println("[" + FMLCommonHandler.instance().getEffectiveSide() + "] MultiServeantLogic detected:" + x + ", " + y + ", " + z);
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

	@Override public int recurseStructureDown(int x, int y, int z, int[] sides, int count)
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
 
	@Override public int validateBottom(int x, int y, int z, int[] sides, int count)
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

	// Override func. for new recipes
	// WTF 700 Lines of code
	
	@Override
	public void updateEntity()
	/*      */   {
	/*  284 */     this.tick += 1;
	/*  285 */     if (this.tick == 60)
	/*      */     {
	/*  287 */       this.tick = 0;
	/*  288 */       detectEntities2();
	/*      */     }
	/*      */     
	/*      */ 
	/*      */ 
	/*      */ 
	/*      */ 
	/*      */ 
	/*  296 */     if (this.tick % 4 == 0) {
	/*  297 */       if (this.useTime > 0) {
	/*  298 */         this.useTime -= 4;
	/*      */       }
	/*  300 */       if (this.validStructure) {
	/*  301 */         checkHasItems();
	/*      */         
	/*      */ 
	/*  304 */         if ((this.useTime <= 0) && (this.inUse)) {
	/*  305 */           updateFuelGague();
	/*      */         }
	/*  307 */         heatItems();
	/*      */       }
	/*      */     }
	/*      */     
	/*  311 */     if (this.tick % 20 == 0)
	/*      */     {
	/*  313 */       if (!this.validStructure) {
	/*  314 */         checkValidPlacement();
	/*      */       }
	/*  316 */       if (this.needsUpdate)
	/*      */       {
	/*  318 */         this.needsUpdate = false;
	/*  319 */         this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	/*      */       }
	/*      */     }
	/*      */   }
	
	public void markDirty()
	/*      */   {
	/*  704 */     updateTemperatures();
	/*  705 */     updateEntity();
	/*      */     
	/*  707 */     super.markDirty();
	/*  708 */     this.needsUpdate = true;
	/*      */   }
	
	public void updateFuelGague()
	/*      */   {
	/*  593 */     if ((this.useTime > 0) || (!this.inUse)) {
	/*  594 */       return;
	/*      */     }
	/*      */     
	/*  597 */     verifyFuelTank();
	/*  598 */     if (this.activeLavaTank == null) {
	/*  599 */       return;
	/*      */     }
	/*      */     
	/*  602 */     IFluidHandler tankContainer = (IFluidHandler)this.worldObj.getTileEntity(this.activeLavaTank.x, this.activeLavaTank.y, this.activeLavaTank.z);
	/*      */     
	/*      */ 
	/*  605 */     FluidStack liquid = tankContainer.drain(ForgeDirection.DOWN, 15, false);
	/*  606 */     if ((liquid != null) && (SmelteryRecipeHandler.isSmelteryFuel(fraction, liquid.getFluid())))
	/*      */     {
	/*      */       do
	/*      */       {
	/*  610 */         liquid = tankContainer.drain(ForgeDirection.DOWN, 15, true);
	/*      */         
	/*  612 */         if ((liquid == null) || (liquid.amount == 0))
	/*      */           break;
	/*  614 */         this.useTime += (int)(SmelteryRecipeHandler.getFuelDuration(fraction, liquid.getFluid()) * Math.round(15.0F / liquid.amount));
	/*  615 */         this.internalTemp = SmelteryRecipeHandler.getFuelPower(fraction, liquid.getFluid());
	/*  616 */       } while (this.useTime < 0);
	/*      */       
	/*      */ 
	/*  619 */       updateFuelDisplay();
	/*      */     }
	/*      */   }

	public void verifyFuelTank()
	/*      */   {
	/*      */     TileEntity tankContainer;
	/*  626 */     if ((this.activeLavaTank != null) && (this.worldObj.blockExists(this.activeLavaTank.x, this.activeLavaTank.y, this.activeLavaTank.z)))
	/*      */     {
	/*  628 */       tankContainer = this.worldObj.getTileEntity(this.activeLavaTank.x, this.activeLavaTank.y, this.activeLavaTank.z);
	/*  629 */       if ((tankContainer instanceof IFluidHandler))
	/*      */       {
	/*  631 */         FluidStack liquid = ((IFluidHandler)tankContainer).drain(ForgeDirection.DOWN, 15, false);
	/*      */         
	/*  633 */         if ((liquid != null) && (SmelteryRecipeHandler.isSmelteryFuel(fraction, liquid.getFluid()))) {
	/*  634 */           return;
	/*      */         }
	/*      */       }
	/*      */     }
	/*      */     
	/*      */ 
	/*  640 */     this.activeLavaTank = null;
	/*  641 */     for (CoordTuple tank : this.lavaTanks)
	/*      */     {
	/*      */ 
	/*  644 */       if (this.worldObj.blockExists(tank.x, tank.y, tank.z))
	/*      */       {
	/*      */ 
	/*      */ 
	/*  648 */         TileEntity tankContainer2 = this.worldObj.getTileEntity(tank.x, tank.y, tank.z);
	/*  649 */         if ((tankContainer2 instanceof IFluidHandler))
	/*      */         {
	/*      */ 
	/*      */ 
	/*  653 */           FluidTankInfo[] info = ((IFluidHandler)tankContainer2).getTankInfo(ForgeDirection.DOWN);
	/*  654 */           if ((info.length > 0) && (info[0].fluid != null) && (info[0].fluid.amount > 0) && 
	/*      */           
	/*      */ 
	/*      */ 
	/*  658 */             (SmelteryRecipeHandler.isSmelteryFuel(fraction, info[0].fluid.getFluid())))
	/*      */           {
	/*      */ 
	/*      */ 
	/*  662 */             this.activeLavaTank = tank;
	/*  663 */             return;
	/*      */           }
	/*      */         }
	/*      */       }
	/*      */     }
	/*      */   }
	
	public FluidStack getResultFor(ItemStack stack) {
		return SmelteryRecipeHandler.getSmelteryResult(fraction, stack);
	}

	public void checkValidStructure(int x, int y, int z, int[] sides)
	/*      */   {
	/*  817 */     int checkLayers = 0;
	/*      */     
	/*      */ 
	/*      */ 
	/*  821 */     this.tempValidStructure = false;
	/*      */     
	/*  823 */     if (checkSameLevel(x, y, z, sides))
	/*      */     {
	/*  825 */       checkLayers++;
	/*  826 */       checkLayers += recurseStructureUp(x, y + 1, z, sides, 0);
	/*  827 */       checkLayers += recurseStructureDown(x, y - 1, z, sides, 0);
	/*      */     }
	/*      */     
	/*      */ 
	/*      */ 
	/*  832 */     if ((this.tempValidStructure != this.validStructure) || (checkLayers != this.layers))
	/*      */     {
	/*  834 */       if (this.tempValidStructure)
	/*      */       {
	/*      */ 
	/*  837 */         this.activeLavaTank = null;
	/*  838 */         for (CoordTuple tank : this.lavaTanks)
	/*      */         {
	/*  840 */           TileEntity tankContainer = this.worldObj.getTileEntity(tank.x, tank.y, tank.z);
	/*  841 */           if ((tankContainer instanceof IFluidHandler))
	/*      */           {
	/*      */ 
	/*  844 */             FluidStack liquid = ((IFluidHandler)tankContainer).getTankInfo(ForgeDirection.DOWN)[0].fluid;
	/*  845 */             if ((liquid != null) && 
	/*      */             
	/*  847 */               (SmelteryRecipeHandler.isSmelteryFuel(fraction, liquid.getFluid())))
	/*      */             {
	/*      */ 
	/*  850 */               this.internalTemp = SmelteryRecipeHandler.getFuelPower(fraction, liquid.getFluid());
	/*  851 */               this.activeLavaTank = tank;
	/*      */             }
	/*      */           }
	/*      */         }
	/*      */         
	/*  856 */         if (this.activeLavaTank == null) {
	/*  857 */           this.activeLavaTank = ((CoordTuple)this.lavaTanks.get(0));
	/*      */         }
	/*      */         
	/*  860 */         adjustLayers2(checkLayers, true);
	/*  861 */         this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	/*  862 */         this.validStructure = true;
	/*      */       }
	/*      */       else
	/*      */       {
	/*  866 */         this.internalTemp = 20;
	/*  867 */         if (this.validStructure)
	/*  868 */           this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	/*  869 */         this.validStructure = false;
	/*      */       }
	/*      */     }
	/*      */   }
	
	public int fill(FluidStack resource, boolean doFill)
	/*      */   {
	/* 1103 */     if (!this.validStructure) {
	/* 1104 */       return 0;
	/*      */     }
	/* 1106 */     if ((resource != null) && (this.currentLiquid < this.maxLiquid))
	/*      */     {
	/*      */ 
	/*      */ 
	/* 1110 */       if (resource.amount + this.currentLiquid > this.maxLiquid)
	/* 1111 */         resource.amount = (this.maxLiquid - this.currentLiquid);
	/* 1112 */       int amount = resource.amount;
	/*      */       
	/* 1114 */       if ((amount > 0) && (doFill))
	/*      */       {
	/* 1116 */         if (addMoltenMetal2(resource, false))
	/*      */         {
	/* 1118 */           ArrayList alloys = SmelteryRecipeHandler.mixMetals(fraction, this.moltenMetal);
	/* 1119 */           for (int al = 0; al < alloys.size(); al++)
	/*      */           {
	/* 1121 */             FluidStack liquid = (FluidStack)alloys.get(al);
	/* 1122 */             addMoltenMetal2(liquid, true);
	/*      */           }
	/*      */         }
	/* 1125 */         this.needsUpdate = true;
	/* 1126 */         this.worldObj.func_147479_m(this.xCoord, this.yCoord, this.zCoord);
	/*      */       }
	/* 1128 */       return amount;
	/*      */     }
	/*      */     
	/* 1131 */     return 0;
	/*      */   }
	
	private void checkHasItems()
	/*      */   {
	/*  447 */     this.inUse = false;
	/*  448 */     for (int i = 0; i < this.maxBlockCapacity; i++) {
	/*  449 */       if ((isStackInSlot(i)) && (this.meltingTemps[i] > 200))
	/*      */       {
	/*  451 */         this.inUse = true;
	/*  452 */         break;
	/*      */       }
	/*      */     }
	/*      */   }
	
	private void detectEntities2()
	/*      */   {
	/*  326 */     if ((this.minPos == null) || (this.maxPos == null)) {
	/*  327 */       return;
	/*      */     }
	/*  329 */     AxisAlignedBB box = AxisAlignedBB.getBoundingBox(this.minPos.x, this.minPos.y, this.minPos.z, this.maxPos.x + 1, this.minPos.y + this.layers, this.maxPos.z + 1);
	/*      */     
	/*  331 */     java.util.List list = this.worldObj.getEntitiesWithinAABB(net.minecraft.entity.Entity.class, box);
	/*  332 */     for (Object o : list)
	/*      */     {
	/*  334 */       if (this.moltenMetal.size() >= 1)
	/*      */       {
	/*  336 */         if (((o instanceof EntityVillager)) && (PHConstruct.meltableVillagers))
	/*      */         {
	/*  338 */           EntityVillager villager = (EntityVillager)o;
	/*  339 */           if (villager.attackEntityFrom(new SmelteryDamageSource(), 5.0F))
	/*      */           {
	/*  341 */             if (this.currentLiquid + 40 < this.maxLiquid)
	/*      */             {
	/*  343 */               int amount = villager.isChild() ? 5 : 40;
	/*  344 */               fill(new FluidStack(TinkerSmeltery.moltenEmeraldFluid, amount), true);
	/*      */             }
	/*      */           }
	/*      */         }
	/*  348 */         else if ((o instanceof EntityEnderman))
	/*      */         {
	/*  350 */           EntityEnderman villager = (EntityEnderman)o;
	/*  351 */           if (villager.attackEntityFrom(new SmelteryDamageSource(), 5.0F))
	/*      */           {
	/*  353 */             if (this.currentLiquid + 125 < this.maxLiquid)
	/*      */             {
	/*  355 */               fill(new FluidStack(TinkerSmeltery.moltenEnderFluid, 125), true);
	/*      */             }
	/*      */           }
	/*      */         }
	/*  359 */         else if ((o instanceof EntityIronGolem))
	/*      */         {
	/*  361 */           EntityIronGolem golem = (EntityIronGolem)o;
	/*  362 */           if (golem.attackEntityFrom(new SmelteryDamageSource(), 5.0F))
	/*      */           {
	/*  364 */             if (this.currentLiquid + 40 < this.maxLiquid)
	/*      */             {
	/*  366 */               fill(new FluidStack(TinkerSmeltery.moltenIronFluid, 40), true);
	/*      */             }
	/*      */           }
	/*      */         }
	/*  370 */         else if (((o instanceof EntityHorse)) && (PHConstruct.meltableHorses))
	/*      */         {
	/*  372 */           EntityHorse horse = (EntityHorse)o;
	/*  373 */           if (horse.attackEntityFrom(new SmelteryDamageSource(), 5.0F))
	/*      */           {
	/*  375 */             if (this.currentLiquid + 108 < this.maxLiquid)
	/*      */             {
	/*  377 */               fill(new FluidStack(TinkerSmeltery.glueFluid, 108), true);
	/*      */             }
	/*      */           }
	/*      */         }
	/*  381 */         else if ((o instanceof EntityLivingBase))
	/*      */         {
	/*  383 */           EntityLivingBase living = (EntityLivingBase)o;
	/*  384 */           if ((!living.isDead) && (living.attackEntityFrom(new SmelteryDamageSource(), 5.0F)))
	/*      */           {
	/*  386 */             if (this.currentLiquid + 40 < this.maxLiquid)
	/*      */             {
	/*  388 */               int amount = (living.isChild()) || ((living instanceof net.minecraft.entity.player.EntityPlayer)) ? 5 : 40;
	/*  389 */               fill(new FluidStack(TinkerSmeltery.bloodFluid, amount), true);
	/*      */             }
	/*      */           }
	/*      */         }
	/*      */       }
	/*  394 */       else if ((PHConstruct.throwableSmeltery) && ((o instanceof EntityItem)))
	/*      */       {
	/*  396 */         handleItemEntity((EntityItem)o);
	/*      */       }
	/*      */     }
		}
	
	private void handleItemEntity(EntityItem item)
	/*      */   {
	/*  405 */     if (this.worldObj.isRemote) {
	/*  406 */       return;
	/*      */     }
	/*  408 */     item.age = 0;
	/*  409 */     ItemStack istack = item.getEntityItem();
	/*  410 */     if ((istack == null) || (istack.stackSize <= 0))
	/*      */     {
	/*  412 */       return;
	/*      */     }
	/*  414 */     int maxSlot = getSizeInventory();
	/*  415 */     boolean itemDestroyed = false;
	/*  416 */     boolean itemAdded = false;
	/*      */     
	/*  418 */     for (int i = 0; i < maxSlot; i++)
	/*      */     {
	/*  420 */       ItemStack stack = getStackInSlot(i);
	/*  421 */       if ((stack == null) && (istack.stackSize > 0))
	/*      */       {
	/*  423 */         ItemStack copy = istack.splitStack(1);
	/*  424 */         setInventorySlotContents(i, copy);
	/*  425 */         itemAdded = true;
	/*  426 */         if (istack.stackSize <= 0)
	/*      */         {
	/*  428 */           item.setDead();
	/*  429 */           itemDestroyed = true;
	/*  430 */           break;
	/*      */         }
	/*      */       }
	/*      */     }
	/*      */     
	/*  435 */     if (!itemDestroyed)
	/*  436 */       item.setEntityItemStack(istack);
	/*  437 */     if (itemAdded)
	/*      */     {
	/*  439 */       this.needsUpdate = true;
	/*      */       
	/*  441 */       this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	/*      */     }
	/*      */   }
	
	private void heatItems() {
		/*  458 */     if (this.useTime > 0)
		/*      */     {
		/*  460 */       boolean hasUse = false;
		/*  461 */       int temperature = getInternalTemperature();
		/*  462 */       int speed = temperature / 100;
		/*  463 */       int refTemp = temperature * 10;
		/*  464 */       for (int i = 0; i < this.maxBlockCapacity; i++)
		/*      */       {
		/*  466 */         if ((this.meltingTemps[i] > 200) && (isStackInSlot(i)))
		/*      */         {
		/*  468 */           hasUse = true;
		/*  469 */           if ((this.activeTemps[i] < refTemp) && (this.activeTemps[i] < this.meltingTemps[i]))
		/*      */           {
		/*  471 */             this.activeTemps[i] += speed;
		/*      */           }
		/*  473 */           else if (this.activeTemps[i] >= this.meltingTemps[i])
		/*      */           {
		/*  475 */             if (!this.worldObj.isRemote)
		/*      */             {
		/*  477 */               FluidStack result = getResultFor(this.inventory[i]);
		/*  478 */               if (result != null)
		/*      */               {
		/*  480 */                 if (addMoltenMetal2(result, false))
		/*      */                 {
		/*  482 */                   this.inventory[i] = null;
		/*  483 */                   this.activeTemps[i] = 200;
		/*  484 */                   ArrayList alloys = SmelteryRecipeHandler.mixMetals(fraction, this.moltenMetal);
		/*  485 */                   for (int al = 0; al < alloys.size(); al++)
		/*      */                   {
		/*  487 */                     FluidStack liquid = (FluidStack)alloys.get(al);
		/*  488 */                     addMoltenMetal2(liquid, true);
		/*      */                   }
		/*  490 */                   markDirty();
		/*      */                 }
		/*      */                 
		/*      */               }
		/*      */             }
		/*      */           }
		/*      */         }
		/*      */         else
		/*      */         {
		/*  499 */           this.activeTemps[i] = 200;
		/*      */         } }
		/*  501 */       this.inUse = hasUse;
		/*      */     }
		/*      */   }
	
	private boolean addMoltenMetal2(FluidStack liquid, boolean first)
	/*      */   {
	/*  507 */     this.needsUpdate = true;
	/*  508 */     if (this.moltenMetal.size() == 0)
	/*      */     {
	/*      */ 
	/*  511 */       if (liquid.amount > getCapacity()) {
	/*  512 */         return false;
	/*      */       }
	/*  514 */       this.moltenMetal.add(liquid.copy());
	/*  515 */       updateCurrentLiquid();
	/*  516 */       return true;
	/*      */     }
	/*      */     
	/*      */ 
	/*      */ 
	/*  521 */     updateCurrentLiquid();
	/*      */     
	/*  523 */     if (liquid.amount + this.currentLiquid > this.maxLiquid) {
	/*  524 */       return false;
	/*      */     }
	/*  526 */     this.currentLiquid += liquid.amount;
	/*      */     
	/*  528 */     boolean added = false;
	/*  529 */     for (int i = 0; i < this.moltenMetal.size(); i++)
	/*      */     {
	/*  531 */       FluidStack l = (FluidStack)this.moltenMetal.get(i);
	/*      */       
	/*      */ 
	/*  534 */       if (l.isFluidEqual(liquid))
	/*      */       {
	/*  536 */         l.amount += liquid.amount;
	/*  537 */         added = true;
	/*      */       }
	/*  539 */       if (l.amount <= 0)
	/*      */       {
	/*  541 */         this.moltenMetal.remove(l);
	/*  542 */         i--;
	/*      */       }
	/*      */     }
	/*  545 */     if (!added)
	/*      */     {
	/*  547 */       if (first) {
	/*  548 */         this.moltenMetal.add(0, liquid.copy());
	/*      */       } else
	/*  550 */         this.moltenMetal.add(liquid.copy());
	/*      */     }
	/*  552 */     return true;
	/*      */   }
	
	private void updateCurrentLiquid()
	/*      */   {
	/*  557 */     this.currentLiquid = 0;
	/*  558 */     for (FluidStack liquid : this.moltenMetal) {
	/*  559 */       this.currentLiquid += liquid.amount;
	/*      */     }
	/*      */   }

	private void updateTemperatures() {
		/*  564 */     for (int i = 0; (i < this.maxBlockCapacity) && (i < this.meltingTemps.length); i++)
		/*      */     {
		/*  566 */       this.meltingTemps[i] = (SmelteryRecipeHandler.getLiquifyTemperature(fraction, this.inventory[i]).intValue() * 10);
		/*      */     }
		/*      */   }

	private void adjustLayers2(int lay, boolean forceAdjust) {
		Random rand = new Random();
	/*   95 */     if ((lay != this.layers) || (forceAdjust))
	/*      */     {
	/*   97 */       this.needsUpdate = true;
	/*   98 */       this.layers = lay;
	/*   99 */       this.maxBlockCapacity = (getBlocksPerLayer() * this.layers);
	/*  100 */       this.maxLiquid = (this.maxBlockCapacity * 1440);
	/*      */       
	/*  102 */       int[] tempActive = this.activeTemps;
	/*  103 */       this.activeTemps = new int[this.maxBlockCapacity];
	/*  104 */       int activeLength = tempActive.length > this.activeTemps.length ? this.activeTemps.length : tempActive.length;
	/*  105 */       System.arraycopy(tempActive, 0, this.activeTemps, 0, activeLength);
	/*      */       
	/*  107 */       int[] tempMelting = this.meltingTemps;
	/*  108 */       this.meltingTemps = new int[this.maxBlockCapacity];
	/*  109 */       int meltingLength = tempMelting.length > this.meltingTemps.length ? this.meltingTemps.length : tempMelting.length;
	/*  110 */       System.arraycopy(tempMelting, 0, this.meltingTemps, 0, meltingLength);
	/*      */       
	/*  112 */       ItemStack[] tempInv = this.inventory;
	/*  113 */       this.inventory = new ItemStack[this.maxBlockCapacity];
	/*  114 */       int invLength = tempInv.length > this.inventory.length ? this.inventory.length : tempInv.length;
	/*  115 */       System.arraycopy(tempInv, 0, this.inventory, 0, invLength);
	/*      */       
	/*  117 */       if ((this.activeTemps.length > 0) && (this.activeTemps.length > tempActive.length))
	/*      */       {
	/*  119 */         for (int i = tempActive.length; i < this.activeTemps.length; i++)
	/*      */         {
	/*  121 */           this.activeTemps[i] = 200;
	/*  122 */           this.meltingTemps[i] = 200;
	/*      */         }
	/*      */       }
	/*      */       
	/*  126 */       if (tempInv.length > this.inventory.length)
	/*      */       {
	/*  128 */         for (int i = this.inventory.length; i < tempInv.length; i++)
	/*      */         {
	/*  130 */           ItemStack stack = tempInv[i];
	/*  131 */           if (stack != null)
	/*      */           {
	/*  133 */             float jumpX = rand.nextFloat() * 0.8F + 0.1F;
	/*  134 */             float jumpY = rand.nextFloat() * 0.8F + 0.1F;
	/*  135 */             float jumpZ = rand.nextFloat() * 0.8F + 0.1F;
	/*      */             
	/*  137 */             int offsetX = 0;
	/*  138 */             int offsetZ = 0;
	/*  139 */             switch (getRenderDirection())
	/*      */             {
	/*      */             case 2: 
	/*  142 */               offsetZ = -1;
	/*  143 */               break;
	/*      */             case 3: 
	/*  145 */               offsetZ = 1;
	/*  146 */               break;
	/*      */             case 4: 
	/*  148 */               offsetX = -1;
	/*  149 */               break;
	/*      */             case 5: 
	/*  151 */               offsetX = 1;
	/*      */             }
	/*      */             
	/*      */             
	/*  155 */             while (stack.stackSize > 0)
	/*      */             {
	/*  157 */               int itemSize = rand.nextInt(21) + 10;
	/*      */               
	/*  159 */               if (itemSize > stack.stackSize)
	/*      */               {
	/*  161 */                 itemSize = stack.stackSize;
	/*      */               }
	/*      */               
	/*  164 */               stack.stackSize -= itemSize;
	/*  165 */               EntityItem entityitem = new EntityItem(this.worldObj, this.xCoord + jumpX + offsetX, this.yCoord + jumpY, this.zCoord + jumpZ + offsetZ, new ItemStack(stack.getItem(), itemSize, stack.getItemDamage()));
	/*      */               
	/*  167 */               if (stack.hasTagCompound())
	/*      */               {
	/*  169 */                 entityitem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
	/*      */               }
	/*      */               
	/*  172 */               float offset = 0.05F;
	/*  173 */               entityitem.motionX = ((float)rand.nextGaussian() * offset);
	/*  174 */               entityitem.motionY = ((float)rand.nextGaussian() * offset + 0.2F);
	/*  175 */               entityitem.motionZ = ((float)rand.nextGaussian() * offset);
	/*  176 */               this.worldObj.spawnEntityInWorld(entityitem);
	/*      */             }
	/*      */           }
	/*      */         }
	/*      */       }
	/*      */     }
		}

	public FluidStack drain(int maxDrain, boolean doDrain)
	/*      */   {
	/* 1059 */     if (!this.validStructure) {
	/* 1060 */       return null;
	/*      */     }
	/* 1062 */     if (this.moltenMetal.size() == 0) {
	/* 1063 */       return null;
	/*      */     }
	/* 1065 */     FluidStack liquid = (FluidStack)this.moltenMetal.get(0);
	/* 1066 */     if (liquid != null)
	/*      */     {
	/* 1068 */       if (liquid.amount - maxDrain <= 0)
	/*      */       {
	/* 1070 */         FluidStack liq = liquid.copy();
	/* 1071 */         if (doDrain)
	/*      */         {
	/*      */ 
	/* 1074 */           this.moltenMetal.remove(liquid);
	/* 1075 */           this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	/* 1076 */           this.needsUpdate = true;
	/* 1077 */           updateCurrentLiquid();
	/*      */         }
	/* 1079 */         return liq;
	/*      */       }
	/*      */       
	/*      */ 
	/* 1083 */       if ((doDrain) && (maxDrain > 0))
	/*      */       {
	/* 1085 */         liquid.amount -= maxDrain;
	/* 1086 */         this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	/* 1087 */         this.currentLiquid -= maxDrain;
	/* 1088 */         this.needsUpdate = true;
	/*      */       }
	/* 1090 */       return new FluidStack(liquid.getFluid(), maxDrain, liquid.tag);
	/*      */     }
	/*      */     
	/*      */ 
	/*      */ 
	/* 1095 */     return new FluidStack(0, 0);
	/*      */   }

	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	/*      */   {
	/* 1274 */     readFromNBT(packet.func_148857_g());
	/* 1275 */     markDirty();
	/* 1276 */     this.worldObj.func_147479_m(this.xCoord, this.yCoord, this.zCoord);
	/* 1277 */     this.needsUpdate = true;
	/*      */   }
}
