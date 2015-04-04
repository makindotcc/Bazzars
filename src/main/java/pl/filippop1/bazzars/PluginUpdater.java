/*
 * Copyright 2015 Filip.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.filippop1.bazzars;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Filip
 */
public class PluginUpdater implements Runnable {
    private static final List<String> versions = new ArrayList<>();
    
    @Override
    public void run() {
        try {
            URL url = new URL(BazzarsPlugin.UPDATER);
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                versions.add(line.toLowerCase());
            }
            
            print(Bukkit.getConsoleSender());
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.isOp()) {
                    print(player);
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(PluginUpdater.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PluginUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<String> getVersions() {
        return versions;
    }
    
    public static boolean hasVersion(String version) {
        if (version.toLowerCase().equals("unknown")) {
            return true;
        } else {
            return versions.contains(version.toLowerCase());
        }
    }
    
    public static void print(CommandSender sender) {
        String version = BazzarsPlugin.getVersion();
        if (!hasVersion(version)) {
            String label = ChatColor.YELLOW + "+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-";
            String text = ChatColor.BLUE + "Aktualizacja pluginu " + ChatColor.WHITE +
                    "Ba" + ChatColor.GOLD + "zz" + ChatColor.WHITE + "ars" + ChatColor.BLUE + ".";
            String ver = ChatColor.BLUE + "Twoja wersja " + ChatColor.WHITE + version +
                    ChatColor.BLUE + " - najnowsza " + ChatColor.GREEN + getVersions().get(0);
            
            sender.sendMessage(label + "\n" + text + ver + "\n" + label);
            sender.sendMessage(label + "\n" + text + ver + "\n" + label);
        }
    }
}
