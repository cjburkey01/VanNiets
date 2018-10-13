package com.cjburkey.vanniets.util;

import com.cjburkey.vanniets.VanNiets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Util {
    
    public static EntityItem spawnItem(float x, float y, float z, ItemStack item, World world, boolean zeroVelocity) {
        EntityItem entity = new EntityItem(world, x, y, z, item);
        if (zeroVelocity) {
            entity.setVelocity(0.0d, 0.0d, 0.0d);
        }
        world.spawnEntity(entity);
        return entity;
    }
    
    public static EntityItem spawnItem(BlockPos pos, ItemStack item, World world, boolean zeroVelocity) {
        return spawnItem(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, item, world, zeroVelocity);
    }
    
    public static String readFile(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
                output.append('\n');
            }
            return output.toString();
        } catch (Exception e) {
            VanNiets.logger.error("Failed to read file: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                VanNiets.logger.error("Failed to close BufferedReader: {}", e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static void writeFile(File file, String contents) {
        FileWriter writer = null;
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, false);
            writer.write(contents);
        } catch (Exception e) {
            VanNiets.logger.error("Failed to write file: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                VanNiets.logger.error("Failed to close FileWriter: {}", e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
}
