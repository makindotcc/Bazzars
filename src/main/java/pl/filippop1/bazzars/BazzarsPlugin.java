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
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import pl.filippop1.bazzars.api.Bazar;
import pl.filippop1.bazzars.api.BazarManager;
import pl.filippop1.bazzars.command.CommandExecutor;
import pl.filippop1.bazzars.listeners.InventoryClickListener;
import pl.filippop1.bazzars.listeners.InventoryCloseListener;
import pl.filippop1.bazzars.listeners.PlayerCommandPreprocessListener;
import pl.filippop1.bazzars.listeners.PlayerInteractListener;
import pl.filippop1.bazzars.listeners.PlayerMoveListener;
import pl.filippop1.bazzars.listeners.PlayerQuitListener;

public class BazzarsPlugin extends JavaPlugin {
    public static final String AUTHORS = "filippop1";
    public static final String SOURCE_URL = "https://github.com/Thefilippop1PL/Bazzars";
    private static Configuration configuration;
    private static BazzarsPlugin instance;
    private static String version;
    
    @Override
    public void onEnable() {
        instance = this;
        version = this.getDescription().getVersion();
        
        if (!BazzarsPlugin.getInstance().getDescription().getAuthors().contains(BazzarsPlugin.AUTHORS)) {
            this.getLogger().log(Level.WARNING, "Oj nie ladnie sie podszywac pod autora ;)");
            this.getServer().shutdown();
        }
        
        // Holograms
        if (!this.getServer().getPluginManager().isPluginEnabled("HolographicDisplays")) {
            this.getLogger().severe("*** Nie odnaleziono pluginu HolographicDisplays ***");
            this.getLogger().severe("*** Plugin Bazzars zostal wylaczony ***");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        
        // Metrics
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        // Commands and listeners
        this.getCommand("bazar").setExecutor(new CommandExecutor());
        CommandExecutor.get().registerDefaults();
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        
        // Configuration
        this.loadConfiguration();
        
        this.getLogger().log(Level.INFO, "Bazzars zostal zaladowany");
    }
    
    @Override
    public void onDisable() {
        for (Bazar bazar : BazarManager.getBazars()) {
            if (bazar != null && bazar.isOpen()) {
                bazar.getHologram().delete();
            }
        }
        this.getLogger().log(Level.INFO, "Bazzars zostal wylaczony");
    }
    
    private void loadConfiguration() {
        this.saveDefaultConfig();
        configuration = new Configuration(this.getConfig());
    }
    
    public static Configuration getConfiguration() {
        return configuration;
    }
    
    public static BazzarsPlugin getInstance() {
        return instance;
    }
    
    public static String getVersion() {
        return version;
    }
}
