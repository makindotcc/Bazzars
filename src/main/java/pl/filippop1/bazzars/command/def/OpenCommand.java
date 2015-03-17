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
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.filippop1.bazzars.BazzarsPlugin;
import pl.filippop1.bazzars.Hologram;
import pl.filippop1.bazzars.api.Bazar;
import pl.filippop1.bazzars.api.BazarManager;
import pl.filippop1.bazzars.command.Command;
import pl.filippop1.bazzars.command.CommandException;

public class OpenCommand extends Command {
    public OpenCommand() {
        super("otworz", "otwiera bazar", new String[] {"open"});
    }
    
    @Override
    public void execute(Player player, String[] args) throws CommandException {
        Bazar bazar = BazarManager.getBazar(player.getName());
        if (bazar == null) {
            throw new CommandException("Aby otworzyc bazar musisz go najpierw stworzyc! Aby to zrobic uzyj /bazar stworz.");
        }
        
        if (bazar.getOffers().isEmpty()) {
            throw new CommandException("Aby otworzyc bazar musisz dodac minimalnie jedna oferte! Dodaj ja poprzez /bazar dodaj.");
        } else if (bazar.isOpen()) {
            throw new CommandException("Twoj bazar jest juz otwarty!");
        } else if (!player.getInventory().containsAtLeast(new ItemStack(BazzarsPlugin.getConfiguration().getItemPay()), 1)) {
            throw new CommandException("Aby otworzyc bazar musisz miec 1 " + BazzarsPlugin.getConfiguration().getCurrency());
        }
        
        player.getInventory().removeItem(new ItemStack(BazzarsPlugin.getConfiguration().getItemPay(), 1));
        bazar.setOpen(true);
        if (BazzarsPlugin.getConfiguration().isHologramEnabled()) {
            Hologram hologram = new Hologram(bazar.getName(), player.getLocation().add(0, 2, 0));
            hologram.change(new String[] { bazar.getName() });
            bazar.setHologram(hologram);
        }
        
        player.sendMessage(ChatColor.GREEN + "Stworzyles bazar!");
        player.playSound(player.getLocation(), Sound.ANVIL_USE, 2.0F, 1.0F);
        player.sendMessage(ChatColor.GREEN + "Aby usunac bazar wpisz /bazar usun");
        player.sendMessage(ChatColor.GREEN + "Aby zmienic oferte wpisz /bazar zmien");
    }
}