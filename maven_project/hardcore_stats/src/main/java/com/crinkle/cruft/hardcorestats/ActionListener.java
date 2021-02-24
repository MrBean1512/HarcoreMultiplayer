package com.crinkle.cruft.hardcorestats;

import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.util.Vector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class ActionListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        player.sendMessage("Death Event Triggered");
    }
}