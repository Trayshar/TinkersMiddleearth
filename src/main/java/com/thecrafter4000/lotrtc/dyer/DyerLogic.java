package com.thecrafter4000.lotrtc.dyer;

import com.thecrafter4000.lotrtc.TinkersMiddleearth;

import lotr.common.item.LOTRItemDye;
import mantle.blocks.abstracts.InventoryLogic;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tconstruct.library.tools.ToolCore;

public class DyerLogic extends InventoryLogic {

	public DyerLogic() {
		super(5);
	}
	
	@Override
	public Container getGuiContainer(InventoryPlayer paramInventoryPlayer, World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
		return new DyerContainer(paramInventoryPlayer, this);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}
	
	@Override
	protected String getDefaultName() {
		return "crafters.ToolDyer";
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}
	
	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	
	public void dyeItem(){
		// Getting tool
		ItemStack stack = this.getStackInSlot(0);
		if(stack == null || stack.getItem() == null) {
			TinkersMiddleearth.logger.fatal("[Dyer] The item to dye is invalid!");
		}
		NBTTagCompound tag = stack.getTagCompound().getCompoundTag("InfiTool");
		String[] parts = {"Handle", "Head", "Accessory", "Extra"}; // Part names

		ItemStack dye;
		int rgbValues[];
		float rgbColorTable[];
		int totalColor;
		boolean coloredBefore;
		for(int i = 0; i < 4; i++){
			dye = this.getStackInSlot(i+1);
			rgbValues = new int[3];
			rgbColorTable = new float[3];
			totalColor = 0;
			coloredBefore = false;

			if(dye != null && dye.getItem() != null) {
				rgbColorTable = EntitySheep.fleeceColorTable[LOTRItemDye.isItemDye(dye)];
				System.out.println(LOTRItemDye.isItemDye(dye));
				
				// Loading Color
				if(tag.hasKey(parts[i] + "Color")){ 
					int previousColor = tag.getInteger(parts[i] + "Color");
					rgbValues[0] = (previousColor >> 16 & 255);
					rgbValues[1] = (previousColor >> 8 & 255);
					rgbValues[2] = (previousColor & 255);
					totalColor = Math.max(rgbValues[0], Math.max(rgbValues[1], rgbValues[2]));
					coloredBefore = true;
				}

				// adding dye color
				int r = (int)(rgbColorTable[0] * 255.0F);
				int g = (int)(rgbColorTable[1] * 255.0F);
				int b = (int)(rgbColorTable[2] * 255.0F);
				totalColor += Math.max(r, Math.max(g, b));
				rgbValues[0] += r;
				rgbValues[1] += g;
				rgbValues[2] += b;
				
				// Setting tag
				int x = coloredBefore ? 2 : 1;
				r = rgbValues[0] / x;
				g = rgbValues[1] / x;
				b = rgbValues[2] / x;
				float averageColor = totalColor / x;
				float maxColor = Math.max(r, Math.max(g, b));
				r = (int)(r * averageColor / maxColor);
				g = (int)(g * averageColor / maxColor);
				b = (int)(b * averageColor / maxColor);
				int color = (r << 16) + (g << 8) + b;
				tag.setInteger(parts[i] + "Color", color);
				if(i == 1) tag.setInteger("BrokenColor", color); // Case 1 is a bit special: I have to set a tag for "Head" and "Broken";
			}
		}
		stack.setTagInfo("InfiTool", tag);
		this.setInventorySlotContents(0, stack);
	}
	
	/*     */   public ItemStack getCraftingResult(InventoryCrafting inv)
	/*     */   {
	/*  67 */     ItemStack armor = null;
	/*  68 */     ItemArmor itemarmor = null;
	/*  69 */     int[] rgb = new int[3];
	/*  70 */     int totalColor = 0;
	/*  71 */     int coloredItems = 0;
	/*     */     
	/*  73 */     for (int i = 0; i < inv.getSizeInventory(); i++)
	/*     */     {
	/*  75 */       ItemStack itemstack = inv.getStackInSlot(i);
	/*  76 */       if (itemstack != null)
	/*     */       {
	/*  78 */         if ((itemstack.getItem() instanceof ItemArmor))
	/*     */         {
	/*  80 */           itemarmor = (ItemArmor)itemstack.getItem();
	/*  81 */           if (armor != null)
	/*     */           {
	/*  83 */             return null;
	/*     */           }
	/*     */           
	/*     */ 
	/*  87 */           if (itemarmor.getArmorMaterial() != ItemArmor.ArmorMaterial.CLOTH)
	/*     */           {
	/*  89 */             return null;
	/*     */           }
	/*     */           
	/*     */ 
	/*  93 */           armor = itemstack.copy();
	/*  94 */           armor.stackSize = 1;
	/*     */           
	/*  96 */           if (itemarmor.hasColor(armor))
	/*     */           {
	/*  98 */             int armorColor = itemarmor.getColor(armor);
	/*  99 */             float r = (armorColor >> 16 & 0xFF) / 255.0F;
	/* 100 */             float g = (armorColor >> 8 & 0xFF) / 255.0F;
	/* 101 */             float b = (armorColor & 0xFF) / 255.0F;
	/* 102 */             totalColor = (int)(totalColor + Math.max(r, Math.max(g, b)) * 255.0F);
	/* 103 */             rgb[0] = ((int)(rgb[0] + r * 255.0F));
	/* 104 */             rgb[1] = ((int)(rgb[1] + g * 255.0F));
	/* 105 */             rgb[2] = ((int)(rgb[2] + b * 255.0F));
	/* 106 */             coloredItems++;
	/*     */           }
	/*     */         }
	/*     */         else
	/*     */         {
	/* 111 */           int dye = LOTRItemDye.isItemDye(itemstack);
	/* 112 */           if (dye == -1)
	/*     */           {
	/* 114 */             return null;
	/*     */           }
	/*     */           
	/* 117 */           float[] dyeColors = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[net.minecraft.block.BlockColored.func_150031_c(dye)];
	/* 118 */           int r = (int)(dyeColors[0] * 255.0F);
	/* 119 */           int g = (int)(dyeColors[1] * 255.0F);
	/* 120 */           int b = (int)(dyeColors[2] * 255.0F);
	/* 121 */           totalColor += Math.max(r, Math.max(g, b));
	/* 122 */           rgb[0] += r;
	/* 123 */           rgb[1] += g;
	/* 124 */           rgb[2] += b;
	/* 125 */           coloredItems++;
	/*     */         }
	/*     */       }
	/*     */     }
	/*     */     
	/* 130 */     if (armor == null)
	/*     */     {
	/* 132 */       return null;
	/*     */     }
	/*     */     
	/*     */ 
	/* 136 */     int r = rgb[0] / coloredItems;
	/* 137 */     int g = rgb[1] / coloredItems;
	/* 138 */     int b = rgb[2] / coloredItems;
	/* 139 */     float averageColor = totalColor / coloredItems;
	/* 140 */     float maxColor = Math.max(r, Math.max(g, b));
	/* 141 */     r = (int)(r * averageColor / maxColor);
	/* 142 */     g = (int)(g * averageColor / maxColor);
	/* 143 */     b = (int)(b * averageColor / maxColor);
	/* 144 */     int color = (r << 16) + (g << 8) + b;
	/* 145 */     itemarmor.func_82813_b(armor, color);
	/* 146 */     return armor;
	/*     */   }
	
	@Override public void openInventory() {}

	@Override public void closeInventory() {}
}
