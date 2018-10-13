package com.cjburkey.vanniets.block;

public final class ModBlocks {
    
    public static final BlockSieve blockSieve = new BlockSieve();
    
    public static void addBlocks() {
        BlockHandler.registerBlock("sieve", blockSieve);
    }
    
}
