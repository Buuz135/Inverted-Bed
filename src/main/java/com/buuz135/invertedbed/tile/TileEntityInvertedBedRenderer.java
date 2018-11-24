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
package com.buuz135.invertedbed.tile;

import net.minecraft.client.model.ModelBed;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityBedRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityInvertedBedRenderer extends TileEntityBedRenderer {

    private ModelBed model = new ModelBed();

    @Override
    public void renderPiece(boolean p_193847_1_, double x, double y, double z, int facingIndex, float alpha) {
        this.model.preparePiece(p_193847_1_);
        GlStateManager.pushMatrix();
        float f = 0.0F;
        float xOffset = 0.0F;
        float zOffset = 0.0F;

        if (facingIndex == EnumFacing.NORTH.getHorizontalIndex()) {
            f = 0.0F;
            xOffset = 1;
        } else if (facingIndex == EnumFacing.SOUTH.getHorizontalIndex()) {
            f = 180.0F;
            zOffset = 1.0F;
        } else if (facingIndex == EnumFacing.WEST.getHorizontalIndex()) {
            f = 90.0F;
        } else if (facingIndex == EnumFacing.EAST.getHorizontalIndex()) {
            f = -90;
            xOffset = 1;
            zOffset = 1;
        }

        GlStateManager.translate((float) x + xOffset, (float) y, (float) z + zOffset);
        GlStateManager.rotate(180, 0, 0, 1);
        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        this.model.render();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        GlStateManager.popMatrix();
    }
}
