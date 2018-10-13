package com.cjburkey.vanniets.recipe.sieve;

import java.util.Objects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SieveRecipe {
    
    public final Item input;
    public final int inputData;
    public final int uses;
    public final ItemStack[] outputs;
    public final float[] probabilities;
    
    public SieveRecipe(Item input, int inputData, int uses, ItemStack[] outputs, float[] probabilities) {
        this.input = input;
        this.inputData = inputData;
        this.uses = uses;
        this.outputs = outputs;
        this.probabilities = probabilities;
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SieveRecipe that = (SieveRecipe) o;
        return inputData == that.inputData && uses == that.uses && Objects.equals(input, that.input);
    }
    
    public int hashCode() {
        return Objects.hash(input, inputData, uses);
    }
    
}
