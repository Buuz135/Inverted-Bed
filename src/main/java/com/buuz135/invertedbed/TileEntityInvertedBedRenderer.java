package com.buuz135.invertedbed;

import net.minecraft.client.model.ModelBed;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityBedRenderer;
import net.minecraft.util.EnumFacing;

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
