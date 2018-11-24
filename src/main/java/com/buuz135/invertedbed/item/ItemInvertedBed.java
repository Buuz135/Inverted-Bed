package com.buuz135.invertedbed.item;

import com.buuz135.invertedbed.Invertedbed;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemInvertedBed extends Item {

    public ItemInvertedBed() {
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setRegistryName(Invertedbed.MOD_ID, "bed_item");
        this.setTranslationKey("invertedbed.bed");
        setTileEntityItemStackRenderer(new ItemInvertedBedTESR());
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return EnumActionResult.SUCCESS;
        } else if (facing != EnumFacing.UP) {
            return EnumActionResult.FAIL;
        } else {
            IBlockState current = worldIn.getBlockState(pos);
            Block block = current.getBlock();
            boolean currentCanBeReplaced = block.isReplaceable(worldIn, pos);
            if (!currentCanBeReplaced) pos = pos.up();

            EnumFacing playerFacing = player.getHorizontalFacing();
            BlockPos blockpos = pos.offset(playerFacing);
            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack) && player.canPlayerEdit(blockpos, facing, itemstack)) {
                IBlockState top = worldIn.getBlockState(blockpos);
                boolean currentIsValid = currentCanBeReplaced || worldIn.isAirBlock(pos);
                boolean topIsValid = top.getBlock().isReplaceable(worldIn, blockpos) || worldIn.isAirBlock(blockpos);

                if (currentIsValid && topIsValid && worldIn.getBlockState(pos.down()).isTopSolid() && worldIn.getBlockState(blockpos.down()).isTopSolid()) {
                    IBlockState currentReplaced = Invertedbed.BLOCK_INVERTED_BED.getDefaultState().withProperty(BlockBed.OCCUPIED, false).withProperty(BlockBed.FACING, playerFacing).withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
                    worldIn.setBlockState(pos, currentReplaced, 10);
                    worldIn.setBlockState(blockpos, currentReplaced.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 10);
                    SoundType soundtype = currentReplaced.getBlock().getSoundType(currentReplaced, worldIn, pos, player);
                    worldIn.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    //TileEntity tileentity = worldIn.getTileEntity(blockpos);
//
//                    if (tileentity instanceof TileEntityBed) {
//                        ((TileEntityBed) tileentity).setItemValues(itemstack);
//                    }
//                    TileEntity tileentity1 = worldIn.getTileEntity(pos);
//                    if (tileentity1 instanceof TileEntityBed) {
//                        ((TileEntityBed) tileentity1).setItemValues(itemstack);
//                    }
                    worldIn.notifyNeighborsRespectDebug(pos, block, false);
                    worldIn.notifyNeighborsRespectDebug(blockpos, top.getBlock(), false);
                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                } else {
                    return EnumActionResult.FAIL;
                }
            } else {
                return EnumActionResult.FAIL;
            }

        }
    }
}
