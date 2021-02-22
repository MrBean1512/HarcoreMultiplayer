/*
bukkit documentation link: https://bukkit.gamepedia.com/Scheduler_Programming

This class creates a synchronous loop which runs the given methods on every tick of the server
*/

package com.crinklecruft.crinkleplugin;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.*;

public class TickLoop extends BukkitRunnable {

    private final JavaPlugin plugin;
    private int counter = 0;

    public TickLoop(JavaPlugin plugin) {
        this.plugin = plugin;
    }



    @Override
    public void run() {
        if (counter == 0) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give @a minecraft:saturation 10 1 1");
            counter = 60;
        }
        counter = counter-1;
    }
}
