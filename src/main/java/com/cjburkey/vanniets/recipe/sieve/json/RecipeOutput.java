package com.cjburkey.vanniets.recipe.sieve.json;

import java.util.Objects;

public class RecipeOutput {
    
    public final RecipeItem item;
    public final int count;
    public final float probability;
    
    public RecipeOutput(RecipeItem item, int count, float probability) {
        this.item = item;
        this.count = count;
        this.probability = probability;
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecipeOutput that = (RecipeOutput) o;
        return Float.compare(that.probability, probability) == 0 && that.count == count && Objects.equals(item, that.item);
    }
    
    public int hashCode() {
        return Objects.hash(item, count, probability);
    }
    
}
