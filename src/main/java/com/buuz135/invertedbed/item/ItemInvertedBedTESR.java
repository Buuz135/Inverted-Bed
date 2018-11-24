package com.buuz135.invertedbed.item;

import com.buuz135.invertedbed.tile.TileEntityInvertedBed;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class ItemInvertedBedTESR extends TileEntityItemStackRenderer {

    private static final TileEntityInvertedBed BED = new TileEntityInvertedBed();

    @Override
    public void renderByItem(ItemStack itemStackIn, float partialTicks) {
        TileEntityRendererDispatcher.instance.render(BED, 0.0D, 0.0D, 0.0D, 0.0F);
    }
}
