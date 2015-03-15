/*
 * Copyright 2014 Filip.
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

import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {
    private String bazarName, currency;
    private List<String> blockedCommands;
    private int distanceSpawn;
    private final FileConfiguration file;
    private Material itemPay;
    private Location spawnLocation;
    
    public Configuration(FileConfiguration file) {
        this.file = file;
        this.loadConfiguration();
    }
    
    public String getBazarDefaultName(String nicknameVariable) {
        return this.bazarName.replace("$name", nicknameVariable);
    }
    
    public List<String> getBlockedCommands() {
        return this.blockedCommands;
    }
    
    public String getCurrency() {
        return this.currency;
    }
    
    public int getDistanceSpawn() {
        return this.distanceSpawn;
    }
    
    public Material getItemPay() {
        return this.itemPay;
    }
    
    public Location getSpawnLocation() {
        return this.spawnLocation;
    }
    
    private void loadConfiguration() {
        // Blocked commands when player has bazaar
        this.blockedCommands = this.file.getStringList("blocked-commands");
        
        // Distance from spawn in which you can create bazaar
        this.distanceSpawn = this.file.getInt("bazar-spawn", 30);
        String worldName = this.file.getString("locations.spawn.world");
        if (worldName.equals("__DEFAULT__")) {
            worldName = Bukkit.getWorlds().get(0).getName();
        }
        if (!Bukkit.getWorlds().contains(Bukkit.getWorld(worldName))) {
            WorldCreator wc = new WorldCreator(worldName);
            Bukkit.createWorld(wc);
        }
        this.spawnLocation = new Location(
                Bukkit.getWorld(worldName),
                this.file.getInt("locations.spawn.x") + 0.5,
                64,
                this.file.getInt("locations.spawn.z") + 0.5
        );
        
        // Bazaars default name over the players head
        this.bazarName = this.file.getString("bazars-name", "Sklep $name");
        
        // Currency
        this.currency = this.file.getString("currency", "zlota");
        
        // Item pay
        String item = this.file.getString("item-pay", "GOLD_INGOT");
        try {
            this.itemPay = Material.valueOf(item);
        } catch (Exception ex) {
            BazzarsPlugin.getInstance().getLogger().log(Level.WARNING,
                    "Nie odnaleziono przedmiotu: {0}.", item);
        }
    }
}
