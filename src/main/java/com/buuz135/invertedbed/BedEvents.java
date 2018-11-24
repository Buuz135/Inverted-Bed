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
