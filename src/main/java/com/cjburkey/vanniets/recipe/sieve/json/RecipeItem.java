package com.cjburkey.vanniets.recipe.sieve.json;

import java.util.Objects;
import java.util.regex.Pattern;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeItem {
    
    public final String itemName;
    public final int itemData;
    
    public RecipeItem(String itemName, int itemData) {
        this.itemName = itemName;
        this.itemData = itemData;
    }
    
    public static RecipeItem parse(String input) {
        String[] spl = input.split(Pattern.quote(":"));
        if (spl.length <= 1) {
            return new RecipeItem("minecraft:" + input, 0);
        }
        if (spl.length == 2) {
            return new RecipeItem(spl[0] + spl[1], 0);
        }
        try {
            return new RecipeItem(spl[0] + spl[1], Integer.parseInt(spl[2]));
        } catch (Exception ignored) {
        }
        return null;
    }
    
    public ItemStack getStack(int count) {
        Item item = Item.getByNameOrId(itemName);
        if (item == null) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(item, count, itemData);
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecipeItem itemInfo = (RecipeItem) o;
        return itemData == itemInfo.itemData && Objects.equals(itemName, itemInfo.itemName);
    }
    
    public int hashCode() {
        return Objects.hash(itemName, itemData);
    }
    
}
