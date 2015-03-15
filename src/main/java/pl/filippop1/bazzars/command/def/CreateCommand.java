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

package pl.filippop1.bazzars.command.def;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import pl.filippop1.bazzars.BazzarsPlugin;
import pl.filippop1.bazzars.api.Bazar;
import pl.filippop1.bazzars.api.BazarManager;
import pl.filippop1.bazzars.command.Command;
import pl.filippop1.bazzars.command.CommandException;

public class CreateCommand extends Command {
    public CreateCommand() {
        super("stworz", "tworzy bazar", new String[] {"create", "stwórz"});
    }
    
    @Override
    public void execute(Player player, String[] args) throws CommandException {
        Bazar bazar = BazarManager.getBazar(player.getName());
        if (bazar != null) {
            throw new CommandException("Masz juz stworzony bazar!");
        }
        
        Location spawnLocation = BazzarsPlugin.getConfiguration().getSpawnLocation();
        spawnLocation.setY(player.getLocation().getY());
        if (player.getLocation().distance(spawnLocation) > BazzarsPlugin.getConfiguration().getDistanceSpawn()) {
            throw new CommandException("Nie mozesz zalozyc bazaru dalej niz " + BazzarsPlugin.getConfiguration().getDistanceSpawn() + " kratek od miejsca spawnu!");
        } else {
            bazar = new Bazar(player.getName());
        }
        
        Location location = player.getLocation();
        location.setX(location.getBlockX() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);
        player.teleport(location);
        
        BazarManager.addBazar(bazar);
        player.sendMessage(ChatColor.GREEN + "Stworzono bazar.");
        player.playSound(player.getLocation(), Sound.ANVIL_USE, 2.0F, 1.0F);
    }
}
