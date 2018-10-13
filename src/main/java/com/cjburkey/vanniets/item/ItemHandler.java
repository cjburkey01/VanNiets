package com.cjburkey.vanniets.item;

import com.cjburkey.vanniets.ModInfo;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Objects;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public final class ItemHandler {
    
    private static final Object2ObjectOpenHashMap<String, Item> registeredItems = new Object2ObjectOpenHashMap<>();
    
    public static Item registerItem(String name, Item item) {
        registeredItems.put(name, item.setUnlocalizedName(name).setRegistryName(ModInfo.MODID, name));
        return item;
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e) {
        ModItems.addItems();
        
        for (Item item : registeredItems.values()) {
            e.getRegistry().register(item);
        }
    }
    
    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent ignored) {
        for (Item item : registeredItems.values()) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
        }
    }
    
    public static Item getItem(String name) {
        if (registeredItems.containsKey(name)) {
            return registeredItems.get(name);
        }
        return null;
    }
    
}
