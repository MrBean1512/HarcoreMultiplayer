/*
This is the main class for the entire Crinkle plugin
*/

package com.crinklecruft.crinkleplugin;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.scoreboard.*;
import org.bukkit.event.entity.PlayerDeathEvent;

public class App extends JavaPlugin {
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //start and stop functions
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onEnable() {
    //when the plugin is initialized... (usually on server start)
        getLogger().info("Crinkle Plugin Initialized");

        getServer().getPluginManager().registerEvents(new ActionListener(), this);
        //register the ActionListener class so that it actually listens to events

        scoreboardInit();
        //make sure that all necessary scoreboards are started on the server
    }

    @Override
    public void onDisable() {
    //when the plugin is disabled... (usually on server termination)
        getLogger().info("Crinkle Plugin Terminiated");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //scoreboard initializations will be handled here
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //if this is called on a server that is not new, there's probably something wrong with the server
    // public void scoreboardInit() {
    //     Scoreboard scoreboard = (getServer().getScoreboardManager().getMainScoreboard());
    //     if (scoreboard.getObjective("CountDown") == null) {
    //     //if the CountDown does not exist on the scoreboard...
    //         getLogger().info("Crinkle scoreboard objective does not exist! Creating scoreboard objective CountDown...");
    //         scoreboard.registerNewObjective("CountDown", "dummy", "Count_Down");
    //     }
    // }
}