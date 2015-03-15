/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.filippop1.bazzars.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.filippop1.bazzars.BazzarsPlugin;
import pl.filippop1.bazzars.api.BazarManager;

public class PlayerCommandPreprocessListener implements Listener {
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/plugins") || e.getMessage().equalsIgnoreCase("/pl")) {
            e.getPlayer().sendMessage(ChatColor.GOLD + "Ten serwer posiada plugin na bazary (Bazzars) by filippop1");
            return;
        }
        if (BazarManager.getBazar(e.getPlayer().getName()) == null) {
            return;
        }
        
        String split = e.getMessage().split(" ")[0];
        for (String command : BazzarsPlugin.getConfiguration().getBlockedCommands()) {
            if (command.toLowerCase().equals(split.toLowerCase())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "Nie mozesz uzywac tej komendy, gdy masz zalozony bazar!");
                return;
            }
        }
    }
}
