/*
 * This file is part of Industrial Foregoing.
 *
 * Copyright 2018, Buuz135
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.buuz135.invertedbed.block;

import com.buuz135.invertedbed.Invertedbed;
import com.buuz135.invertedbed.tile.TileEntityInvertedBed;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockInvertedBed extends BlockBed {

    public BlockInvertedBed() {
        this.setRegistryName(Invertedbed.MOD_ID, "bed");
        this.setTranslationKey("invertedbed.bed");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityInvertedBed();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else {
            if (state.getValue(PART) != BlockBed.EnumPartType.HEAD) {
                pos = pos.offset(state.getValue(FACING));
                state = worldIn.getBlockState(pos);

                if (state.getBlock() != this) {
                    return true;
                }
            }

            net.minecraft.world.WorldProvider.WorldSleepResult sleepResult = worldIn.provider.canSleepAt(playerIn, pos);
            if (sleepResult != net.minecraft.world.WorldProvider.WorldSleepResult.BED_EXPLODES) {
                if (sleepResult == net.minecraft.world.WorldProvider.WorldSleepResult.DENY) return true;
                if ((state.getValue(OCCUPIED))) {
                    EntityPlayer entityplayer = this.getPlayerInBed(worldIn, pos);

                    if (entityplayer != null) {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.occupied", new Object[0]), true);
                        return true;
                    }

                    state = state.withProperty(OCCUPIED, false);
                    worldIn.setBlockState(pos, state, 4);
                }

                EntityPlayer.SleepResult entityplayer$sleepresult = playerIn.trySleep(pos);

                if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK) {
                    state = state.withProperty(OCCUPIED, true);
                    worldIn.setBlockState(pos, state, 4);
                    return true;
                } else {
                    if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW) {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.inverted.day", new Object[0]), true);
                    } else if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_SAFE) {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.notSafe", new Object[0]), true);
                    } else if (entityplayer$sleepresult == EntityPlayer.SleepResult.TOO_FAR_AWAY) {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.tooFarAway", new Object[0]), true);
                    }

                    return true;
                }
            } else {
                worldIn.setBlockToAir(pos);
                BlockPos blockpos = pos.offset(state.getValue(FACING).getOpposite());

                if (worldIn.getBlockState(blockpos).getBlock() == this) {
                    worldIn.setBlockToAir(blockpos);
                }

                worldIn.newExplosion(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 5.0F, true, true);
                return true;
            }
        }
    }

    @Override
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player) {
        return state.getBlock().equals(this);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Invertedbed.ITEM_INVERTED_BED);
    }

    @Nullable
    private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos) {
        for (EntityPlayer entityplayer : worldIn.playerEntities) {
            if (entityplayer.isPlayerSleeping() && entityplayer.bedLocation.equals(pos)) {
                return entityplayer;
            }
        }
        return null;
    }
}
