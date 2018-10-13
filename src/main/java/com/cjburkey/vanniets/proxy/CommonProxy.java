package com.cjburkey.vanniets.proxy;

import com.cjburkey.vanniets.ModInfo;
import com.cjburkey.vanniets.VanNiets;
import com.cjburkey.vanniets.recipe.sieve.SieveRecipes;
import com.cjburkey.vanniets.recipe.sieve.json.RecipeItem;
import com.cjburkey.vanniets.recipe.sieve.json.RecipeOutput;
import com.cjburkey.vanniets.recipe.sieve.json.SieveRecipeJson;
import com.cjburkey.vanniets.tile.TileEntitySieve;
import com.cjburkey.vanniets.util.Util;
import java.io.File;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    
    public static final String configFolder = ModInfo.MODID + '/';
    public static final String sieveRecipeFile = "recipes/sieve.json";
    
    public void construct(FMLConstructionEvent e) {
        
    }
    
    public void preinit(FMLPreInitializationEvent e) {
        File configFolder = new File(e.getModConfigurationDirectory(), CommonProxy.configFolder);
        File sieveRecipeFile = new File(configFolder, CommonProxy.sieveRecipeFile);
        
        sieveIO(sieveRecipeFile);
        
//        SieveRecipes.registerRecipe(new ItemStack(Blocks.GRASS), 5, new ItemStack[] { new ItemStack(Items.APPLE) }, new float[] { 0.5f });
//        SieveRecipes.registerRecipe(new ItemStack(Blocks.STONE), 3, new ItemStack[] { new ItemStack(Items.APPLE), new ItemStack(Items.STICK) }, new float[] { 0.15f, 0.75f });
//        SieveRecipes.registerRecipe(new ItemStack(Blocks.STONE, 1, 1), 3, new ItemStack[] { new ItemStack(Items.DIAMOND) }, new float[] { 0.05f });
        
        TileEntity.register("tile_sieve", TileEntitySieve.class);
    }
    
    public void init(FMLInitializationEvent e) {
        
    }
    
    public void postinit(FMLPostInitializationEvent e) {
        
    }
    
    private void sieveIO(File file) {
        String readIn = null;
        if (!file.exists() || (readIn = Util.readFile(file)) == null || readIn.trim().isEmpty()) {
            SieveRecipeJson exampleRecipe = new SieveRecipeJson(RecipeItem.parse("sand"), 4,
                    new RecipeOutput(RecipeItem.parse("stick"), 1, 0.65f),
                    new RecipeOutput(RecipeItem.parse("deadbush"), 1, 0.15f));
            SieveRecipeJson.SieveRecipesList list = new SieveRecipeJson.SieveRecipesList(exampleRecipe);
            Util.writeFile(file, readIn = list.toJson(VanNiets.gson));
            VanNiets.logger.info("Created sieve.json");
        }
        SieveRecipes.registerRecipes(SieveRecipeJson.SieveRecipesList.fromJson(VanNiets.gson, readIn));
    }
    
}
