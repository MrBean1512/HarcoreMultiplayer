package com.crinkle.cruft.hardcorestats;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ActionListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent pde) {
        Player player = pde.getEntity();
        String playerName = player.getName();
        player.sendMessage("Death Event Triggered");
        String message = pde.getDeathMessage();

        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader("server_info.json"));
            JSONObject jsonObject = (JSONObject)object;
            JSONArray death_events = (JSONArray) jsonObject.get("death_events");
            JSONObject newEvent = new JSONObject();
            newEvent.put(playerName, message);
            death_events.add(newEvent);

            try {
                FileWriter file = new FileWriter("server_info.json");
                file.write(jsonObject.toJSONString());
                file.close();
             } catch (IOException e) {
                e.printStackTrace();
             }
        }
        catch(FileNotFoundException fe)
        {
            fe.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        // everbody to spectator
        // wait 1 minute
        // kill server
    }
}