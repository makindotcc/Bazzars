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

package pl.filippop1.bazzars.listeners;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import pl.filippop1.bazzars.BazzarsPlugin;
import pl.filippop1.bazzars.api.Bazar;
import pl.filippop1.bazzars.api.BazarManager;

public class WorldChangeListener implements Listener {
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        for (Bazar bazar : BazarManager.getBazars()) {
            if (bazar != null) {
                if (bazar.isOpen() && BazzarsPlugin.getConfiguration().isHologramEnabled()) {
                    try {
                        bazar.getHologram().show(bazar.getHologram().getLocation(), e.getPlayer());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
