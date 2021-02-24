package com.crinkle.cruft.hardcorestats;

import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ActionListener(), this);
        getLogger().info("Hello, SpigotMC!");
    }

    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }


}
