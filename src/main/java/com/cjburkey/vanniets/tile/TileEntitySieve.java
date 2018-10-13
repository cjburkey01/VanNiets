package com.cjburkey.vanniets.tile;

import com.cjburkey.vanniets.VanNiets;
import com.cjburkey.vanniets.recipe.sieve.SieveRecipe;
import com.cjburkey.vanniets.recipe.sieve.SieveRecipes;
import com.cjburkey.vanniets.util.Util;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public final class TileEntitySieve extends TileEntity {
    
    private ItemStack currentStack;
    private SieveRecipe currentRecipe;
    private int uses;
    private final Random random = new Random();
    
    public boolean rightClick(ItemStack item, boolean remove) {
        if (currentRecipe != null) {
            uses --;
            if (uses <= 0) {
                finish();
            }
            markDirty();
            return true;
        }
        if (item == null || item.equals(ItemStack.EMPTY)) {
            return false;
        }
        SieveRecipe recipe = SieveRecipes.getRecipe(item);
        if (recipe == null) {
            VanNiets.logger.info("No recipe for: {}", item.getDisplayName());
            return false;
        }
        setRecipe(recipe);
        currentStack = item.copy();
        if (remove) {
            item.setCount(item.getCount() - 1);
        }
        markDirty();
        return true;
    }
    
    private void setRecipe(SieveRecipe recipe) {
        currentRecipe = recipe;
        uses = recipe.uses;
    }
    
    private void finish() {
        for (ItemStack item : SieveRecipes.getOutput(currentRecipe, random)) {
            Util.spawnItem(getPos().getX() + 0.5f, getPos().getY() + 1.0f, getPos().getZ() + 0.5f, item, getWorld(), false);
        }
        currentRecipe = null;
    }
    
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        
        if (nbt.hasKey("current_item") && nbt.hasKey("uses")) {
            ItemStack input = new ItemStack(nbt.getCompoundTag("current_item"));
            if (input.equals(ItemStack.EMPTY)) {
                return;
            }
            SieveRecipe recipe = SieveRecipes.getRecipe(input);
            if (recipe == null) {
                return;
            }
            int uses = nbt.getInteger("uses");
            if (uses <= 0) {
                return;
            }
            
            currentRecipe = recipe;
            this.uses = uses;
        }
    }
    
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        
        NBTTagCompound item = new NBTTagCompound();
        if (currentStack != null) {
            currentStack.writeToNBT(item);
        }
        nbt.setTag("current_item", item);
        nbt.setInteger("uses", uses);
        
        return nbt;
    }
    
}
