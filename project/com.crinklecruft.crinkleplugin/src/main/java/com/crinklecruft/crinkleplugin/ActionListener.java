/*
spigot event API link: https://www.spigotmc.org/wiki/using-the-event-api/

This class handles the listener for in-game events
*/

package com.crinklecruft.crinkleplugin;

import java.util.Set;
import org.bukkit.event.Listener;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.util.Vector;

public class ActionListener implements Listener{

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //Command Class
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //this is where all command inputs are handled
    //this command worked when everything was in the main ("app") class
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        switch(cmd.getName()) {
        case "testAction":
            //testAction is used to test features of the action class
            if (!(sender instanceof Player)) 
            {
                sender.sendMessage("You must be a player to execute that command.");
                return true;
            }
            Player player = (Player) sender;

            action(player, args[0]);
            
            sender.sendMessage("This command is designed for testing purposes. Use the arguments to make use of this command\nlightning");
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //onAction function listens for player events such as clicking and performs the appropriate action
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @EventHandler
    public void onAction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        if (player.isSneaking() == true) {
        //if the player is sneaking...
            if (event.getAction() == Action.LEFT_CLICK_AIR)
                action(player, "lightning");
        }
        else if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_AIR)) {
        //if the player right or left-clicks on air...{
            if (player.getInventory().getItemInMainHand().getType() == (Material.STICK)) {
            //if the item in the player's hand is one of the 3 major classes' weapon types ()...
                action(player, "mage_standard");
            }
        }
    }

    @Eventhandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        player.sendMessage("Death Event Triggered");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //the action function performs player based actions such as attacks or assist moves
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public void action(Player player, String action) {
        switch (action) {
        case "lightning":
        //strike lightning where the player is looking
            Block targetBlock = (player.getTargetBlock((Set<Material>) null, 100));
            player.getWorld().strikeLightning(targetBlock.getLocation()).getLocation();
            return;
        case "mage_standard":
        //fire a projectile from the player
            int speed = 2;
            Vector direction = (player.getEyeLocation().getDirection().multiply(speed));
            Projectile projectile = (Projectile)player.getWorld().spawn(player.getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), LargeFireball.class);
            projectile.setShooter(player);
            projectile.setVelocity(direction);
            return;
        }
        player.sendMessage("An unregistered action was performed");
        return;
    }
}
