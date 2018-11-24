package com.buuz135.invertedbed;

import com.buuz135.invertedbed.block.BlockInvertedBed;
import com.buuz135.invertedbed.item.ItemInvertedBed;
import com.buuz135.invertedbed.tile.TileEntityInvertedBed;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(
        modid = Invertedbed.MOD_ID,
        name = Invertedbed.MOD_NAME,
        version = Invertedbed.VERSION
)
public class Invertedbed {

    public static final String MOD_ID = "invertedbed";
    public static final String MOD_NAME = "InvertedBed";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MOD_ID)
    public static Invertedbed INSTANCE;
    public static BlockInvertedBed BLOCK_INVERTED_BED = new BlockInvertedBed();
    public static ItemInvertedBed ITEM_INVERTED_BED = new ItemInvertedBed();

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        GameRegistry.registerTileEntity(TileEntityInvertedBed.class, new ResourceLocation(MOD_ID, "bed_tile"));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInvertedBed.class, new TileEntityInvertedBedRenderer());
        }
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {

        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(ITEM_INVERTED_BED);
        }

        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(BLOCK_INVERTED_BED);
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void modelRegistryEvent(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(ITEM_INVERTED_BED, 0, new ModelResourceLocation(ITEM_INVERTED_BED.getRegistryName(), "inventory"));
        }
    }

}
