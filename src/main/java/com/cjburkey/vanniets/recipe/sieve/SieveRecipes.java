package com.cjburkey.vanniets.recipe.sieve;

import com.cjburkey.vanniets.VanNiets;
import com.cjburkey.vanniets.recipe.sieve.json.RecipeOutput;
import com.cjburkey.vanniets.recipe.sieve.json.SieveRecipeJson;
import it.unimi.dsi.fastutil.floats.FloatLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.List;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SieveRecipes {
    
    private static final Object2ObjectOpenHashMap<Item, ObjectArrayList<SieveRecipe>> recipes = new Object2ObjectOpenHashMap<>();
    
    public static void removeAllRecipes() {
        recipes.clear();
    }
    
    public static SieveRecipe getRecipe(ItemStack input) {
        Item item = getFromStack(input);
        if (item != null && recipes.containsKey(item)) {
            for (SieveRecipe recipe : getRecipes(item)) {
                if (recipe.inputData == input.getItemDamage()) {
                    return recipe;
                }
            }
        }
        return null;
    }
    
    private static ObjectArrayList<SieveRecipe> getRecipes(Item item) {
        if (item != null && recipes.containsKey(item)) {
            return recipes.get(item);
        }
        return new ObjectArrayList<>();
    }
    
    public static void registerRecipes(SieveRecipeJson.SieveRecipesList recipes) {
        if (recipes == null) {
            VanNiets.logger.warn("No recipes supplied to sieve registry");
            return;
        }
        int pre = SieveRecipes.recipes.size();
        for (SieveRecipeJson recipe : recipes.recipes) {
            ItemStack stack = recipe.input.getStack(1);
            if (stack.equals(ItemStack.EMPTY)) {
                continue;
            }
            
            ObjectLinkedOpenHashSet<ItemStack> outputs = new ObjectLinkedOpenHashSet<>();
            FloatLinkedOpenHashSet outputsProb = new FloatLinkedOpenHashSet();
            for (RecipeOutput output : recipe.outputs) {
                if (output.probability <= 0.0f || output.probability > 1.0f) {
                    continue;
                }
                ItemStack s = output.item.getStack(output.count);
                if (s.equals(ItemStack.EMPTY)) {
                    continue;
                }
                outputs.add(s);
                outputsProb.add(output.probability);
            }
            registerRecipe(stack, recipe.clicks, outputs.toArray(new ItemStack[0]), outputsProb.toFloatArray());
        }
        VanNiets.logger.info("Added {} recipes", SieveRecipes.recipes.size() - pre);
    }
    
    public static boolean registerRecipe(ItemStack input, int count, ItemStack[] outputs, float[] probabilities) {
        VanNiets.logger.info("Register {}: {} for {} possible outputs", input.getDisplayName(), count, outputs.length);
        
        Item item = getFromStack(input);
        ObjectArrayList<SieveRecipe> at = getRecipes(item);
        if (at.size() < 1) {
            recipes.put(item, at);
        }
        if (getRecipe(input) != null) {
            return false;
        }
        at.add(new SieveRecipe(item, input.getItemDamage(), count, outputs, probabilities));
        return true;
    }
    
    private static Item getFromStack(ItemStack stack) {
        if (stack.equals(ItemStack.EMPTY)) {
            return null;
        }
        return stack.getItem();
    }
    
    public static List<ItemStack> getOutput(SieveRecipe recipe, Random random) {
        ObjectArrayList<ItemStack> output = new ObjectArrayList<>();
        for (int i = 0; i < recipe.probabilities.length && i < recipe.outputs.length; i ++) {
            if (random.nextFloat() <= recipe.probabilities[i]) {
                output.add(recipe.outputs[i].copy());
            }
        }
        return output;
    }
    
}
