package com.cjburkey.vanniets.block;

import com.cjburkey.vanniets.ModInfo;
import com.cjburkey.vanniets.item.ItemHandler;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public final class BlockHandler {
    
    private static final Object2ObjectOpenHashMap<String, Block> registeredBlocks = new Object2ObjectOpenHashMap<>();
    
    public static Block registerBlock(String name, Block block) {
        registeredBlocks.put(name, block.setUnlocalizedName(name).setRegistryName(ModInfo.MODID, name));
        ItemHandler.registerItem(name, new ItemBlock(block));
        return block;
    }
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e) {
        ModBlocks.addBlocks();

        for (Block block : registeredBlocks.values()) {
            e.getRegistry().register(block);
        }
    }
    
    public static Block getBlock(String name) {
        if (registeredBlocks.containsKey(name)) {
            return registeredBlocks.get(name);
        }
        return null;
    }
    
}
