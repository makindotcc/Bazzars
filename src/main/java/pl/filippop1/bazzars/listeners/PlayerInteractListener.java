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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import pl.filippop1.bazzars.api.Bazar;
import pl.filippop1.bazzars.api.BazarManager;
import pl.filippop1.bazzars.gui.GUIManager;
import pl.filippop1.bazzars.gui.GUIShop;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player)) {
            return;
        }
        
        Player target = (Player) e.getRightClicked();
        Bazar bazar = BazarManager.getBazar(target.getName());
        if (bazar == null) {
            return;
        } else if (!bazar.isOpen()) {
            return;
        }
        
        GUIShop gui = new GUIShop(target.getName(), this.getSize(bazar.getOffers().size()), e.getPlayer().getName(), bazar.getOffers());
        GUIManager.addGUIShop(gui);
        gui.openShop(e.getPlayer());
    }
    
    private int getSize(int offers) {
        if (offers <= 25) {
            return 3;
        } else if (offers <= 34) {
            return 4;
        } else if (offers <= 43) {
            return 5;
        } else {
            return 6;
        }
    }
}
