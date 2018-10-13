package com.cjburkey.vanniets.recipe.sieve.json;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.Objects;

public class SieveRecipeJson {
    
    public final RecipeItem input;
    public final int clicks;
    public final RecipeOutput[] outputs;
    
    public SieveRecipeJson(RecipeItem input, int clicks, RecipeOutput... outputs) {
        this.input = input;
        this.clicks = clicks;
        this.outputs = outputs;
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SieveRecipeJson that = (SieveRecipeJson) o;
        return clicks == that.clicks && Objects.equals(input, that.input) && Arrays.equals(outputs, that.outputs);
    }
    
    public int hashCode() {
        int result = Objects.hash(input, clicks);
        result = 31 * result + Arrays.hashCode(outputs);
        return result;
    }
    
    public static final class SieveRecipesList {
        
        public final SieveRecipeJson[] recipes;
        
        public SieveRecipesList(SieveRecipeJson... recipes) {
            this.recipes = recipes;
        }
        
        public String toJson(Gson gson) {
            return gson.toJson(this);
        }
        
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SieveRecipesList that = (SieveRecipesList) o;
            return Arrays.equals(recipes, that.recipes);
        }
        
        public int hashCode() {
            return Arrays.hashCode(recipes);
        }
        
        public static SieveRecipesList fromJson(Gson gson, String json) {
            return gson.fromJson(json, SieveRecipesList.class);
        }
        
    }
    
}
