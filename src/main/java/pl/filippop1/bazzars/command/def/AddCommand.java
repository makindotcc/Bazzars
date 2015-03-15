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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.filippop1.bazzars.api.Bazar;
import pl.filippop1.bazzars.api.BazarManager;
import pl.filippop1.bazzars.api.OfferBuilder;
import pl.filippop1.bazzars.command.Command;
import pl.filippop1.bazzars.command.CommandException;

public class AddCommand extends Command {
    public AddCommand() {
        super("dodaj", "dodaje oferte", "<sprzedaz> <kupno>",
                new String[] {"add"});
    }
    
    @Override
    public void execute(Player player, String[] args) throws CommandException {
        if (args.length != 3) {
            throw new CommandException("Uzycie: " + this.getUsage() + ". Uzyj -1 jezeli nie chcesz sprzedawac/kupowac!");
        }
        
        Bazar bazar = BazarManager.getBazar(player.getName());
        if (bazar == null) {
            throw new CommandException("Stworz bazar, aby dodac do niego oferte uzywajac /bazar stworz!");
        } else if (bazar.isOpen()) {
            throw new CommandException("Nie mozesz dodawac ofert gdy Twoj bazar jest otwarty!");
        } else if (player.getItemInHand().getType() == Material.AIR) {
            throw new CommandException("Musisz trzymac w reku przedmiot, ktory chcesz wystawic.");
        }

        int costBuy, costSell;
        try {
            costBuy = Integer.valueOf(args[2]);
        } catch (NumberFormatException ex) {
            throw new CommandException("Podaj cene kupna!");
        }
        try {
            costSell = Integer.valueOf(args[1]);
        } catch (NumberFormatException ex) {
            throw new CommandException("Podaj cene sprzedazy!");
        }
        if (costBuy < 0 && costSell < 0) {
            throw new CommandException("Przedmiot ktory wystawiles/as nie posiada cen! ");
        }
        
        int id = bazar.getOffers().size() + 1;
        if (id >= 53) {
            throw new CommandException("Maksymalna ilosc ofert to 43!");
        }
        
        OfferBuilder builder = new OfferBuilder();
        builder.amount(player.getItemInHand().getAmount());
        builder.costBuy(costBuy).costSell(costSell);
        builder.item(player.getItemInHand());
        builder.numericID(id);
        
        bazar.addOffer(builder.toOffer());
        player.sendMessage(ChatColor.GREEN + "Dodano oferte. " + ChatColor.GRAY + "(ID: " + id + ")");
    }
}
