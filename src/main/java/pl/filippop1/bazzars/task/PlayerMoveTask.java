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

package pl.filippop1.bazzars.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.filippop1.bazzars.api.Bazar;
import pl.filippop1.bazzars.api.BazarManager;

public class PlayerMoveTask implements Runnable {
    @Override
    public void run() {
        for (Bazar bazar : BazarManager.getBazars()) {
            Player player = Bukkit.getPlayer(bazar.getOwner());
            if (!player.getLocation().getWorld().equals(bazar.getLocation().getWorld())
                    || player.getLocation().distance(bazar.getLocation()) >= 0.5) {
                player.teleport(bazar.getLocation());
            }
        }
    }
}
