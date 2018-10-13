package com.cjburkey.vanniets;

import static com.cjburkey.vanniets.ModInfo.*;
import com.cjburkey.vanniets.proxy.CommonProxy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name = NAME, modid = MODID, version = MC_VERSION + '_' + VERSION, acceptedMinecraftVersions = '[' + MC_VERSION + ']')
public final class VanNiets {
    
    private static final String proxyBase = "com.cjburkey.vanniets.proxy.";
    
    public static final Logger logger = LogManager.getLogger(ModInfo.MODID);
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Mod.Instance(owner = ModInfo.MODID)
    public static VanNiets instance;
    
    @SidedProxy(modId = MODID, clientSide = proxyBase + "ClientProxy", serverSide = proxyBase + "ServerProxy")
    public static CommonProxy proxy;
    
    @Mod.EventHandler
    public void construct(FMLConstructionEvent e) {
        proxy.construct(e);
    }
    
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent e) {
        proxy.preinit(e);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }
    
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent e) {
        proxy.postinit(e);
    }
    
}
