package com.cjburkey.vanniets.block;

import com.cjburkey.vanniets.tile.TileEntitySieve;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockSieve extends Block implements ITileEntityProvider {
    
    public BlockSieve() {
        super(Material.WOOD);
        
        setSoundType(SoundType.WOOD);
        setHardness(1.0f);
    }
    
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float x, float y, float z) {
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntitySieve && ((TileEntitySieve) te).rightClick(player.getHeldItem(hand), !player.isCreative())) {
                return true;
            }
        }
        return true;
    }
    
    public TileEntity createNewTileEntity(@Nonnull World world, int meta) {
        return new TileEntitySieve();
    }
    
}
