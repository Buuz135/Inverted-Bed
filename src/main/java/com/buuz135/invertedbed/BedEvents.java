package com.buuz135.invertedbed;

import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.buuz135.invertedbed.Invertedbed.BLOCK_INVERTED_BED;

@Mod.EventBusSubscriber(modid = Invertedbed.MOD_ID)
public class BedEvents {

    @SubscribeEvent
    public static void onTrySleep(PlayerWakeUpEvent event) {
        if (event.getEntityPlayer().sleepTimer >= 99 && event.getEntityPlayer().world.getBlockState(event.getEntityPlayer().bedLocation).getBlock().equals(BLOCK_INVERTED_BED)) {
            event.getEntityPlayer().world.setWorldTime(13000);
        }
    }

    @SubscribeEvent
    public static void onCheckTime(SleepingTimeCheckEvent event) {
        if (event.getEntityPlayer().world.getBlockState(event.getSleepingLocation()).getBlock().equals(BLOCK_INVERTED_BED)) {
            event.setResult(event.getEntityPlayer().world.isDaytime() ? Event.Result.ALLOW : Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onBedCheck(SleepingLocationCheckEvent event) {
        if (event.getEntityPlayer().world.getBlockState(event.getSleepingLocation()).getBlock().equals(BLOCK_INVERTED_BED))
            event.setResult(Event.Result.ALLOW);
    }
}
