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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.filippop1.bazzars.BazzarsPlugin;
import pl.filippop1.bazzars.Utils;
import pl.filippop1.bazzars.api.Bazar;
import pl.filippop1.bazzars.api.BazarManager;
import pl.filippop1.bazzars.api.Offer;
import pl.filippop1.bazzars.gui.GUIConfirm;
import pl.filippop1.bazzars.gui.GUIManager;
import pl.filippop1.bazzars.gui.GUIShop;
import pl.filippop1.bazzars.gui.GUIShop.TypeGUI;

public class InventoryClickListener implements Listener {
    /*
        ta klasa jest slabo napisana
        zostanie poprawiona w przyszlosci
    */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack clicked = e.getCurrentItem();
        Inventory inventory = e.getInventory();
        Player player = (Player) e.getWhoClicked();
        
        if(clicked == null || clicked.getType() == Material.AIR || inventory.getName() == null){
            return;
        } else if (inventory.getName().equalsIgnoreCase(GUIShop.NAME_INVENTORY)) {
            GUIShop gui = GUIManager.getGUIShop(e.getInventory());
            if (gui == null) {
                player.closeInventory();
                return;
            }
            
            Bazar bazar = BazarManager.getBazar(gui.getOwner());
            e.setCancelled(true);
            player.openInventory(e.getInventory());
            
            if (bazar.getOffers().size() < e.getRawSlot()) {
                GUIManager.addGUIShop(gui);
                return;
            }
            
            TypeGUI type;
            String title;
            if (e.getClick() == ClickType.LEFT) {
                type = TypeGUI.BUY;
                title = ChatColor.RED + "Kupuje to?";
            } else if (e.getClick() == ClickType.RIGHT) {
                type = TypeGUI.SELL;
                title = ChatColor.RED + "Sprzedaje to?";
            } else {
                GUIManager.addGUIShop(gui);
                return;
            }
            GUIConfirm guiConfirm = new GUIConfirm(Bukkit.createInventory(null, 27, title),
                    bazar.getOffers().get(e.getSlot()).getItem(), 
                    gui.getOwner(),
                    type,
                    player.getName(),
                    e.getSlot());
            GUIManager.removeGUIShop(gui);
            GUIManager.addGUIConfirm(guiConfirm);
            guiConfirm.openConfirm(player);
        } else if (inventory.getName().contains(ChatColor.RED + "Kupuje to?") || inventory.getName().contains(ChatColor.RED + "Sprzedaje to?")) {
            GUIConfirm guiConfirm = GUIManager.getGUIConfirm(e.getInventory());
            if (guiConfirm == null) {
                player.closeInventory();
                return;
            }
            
            e.setCancelled(true);
            player.openInventory(e.getInventory());
            if (e.getClick() != ClickType.LEFT || e.getClick() != ClickType.RIGHT) {
                GUIManager.removeGUIConfirm(guiConfirm);
                player.closeInventory();
                return;
            }
            
            Bazar bazar = BazarManager.getBazar(guiConfirm.getOwner());
            if (bazar == null || !bazar.isOpen()) {
                player.sendMessage(ChatColor.RED + "Bazar jest zamkniety.");
                player.closeInventory();
                return;
            }
            Player target = Bukkit.getPlayer(bazar.getOwner());
            
            Inventory targetInventory = target.getInventory();
            Inventory playerInventory = player.getInventory();
            if (clicked.getType() == Material.EMERALD_BLOCK
                    && clicked.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + String.valueOf(ChatColor.BOLD) + "TAK")) {
                Offer offer = bazar.getOffers().get(guiConfirm.getSlot());
                GUIManager.removeGUIConfirm(guiConfirm);
                
                if (guiConfirm.getType() == TypeGUI.BUY) {
                    if (!offer.canBuy()) {
                        player.sendMessage(ChatColor.RED + "Ten przedmiot mozesz tylko sprzedac w tym bazarze!");
                        player.closeInventory();
                    } else if (!targetInventory.containsAtLeast(offer.getItem(), offer.getAmount())) {
                        player.sendMessage(ChatColor.RED + "Przedmiot zostal wyprzedany!");
                        player.closeInventory();
                    } else if (!playerInventory.containsAtLeast(new ItemStack(BazzarsPlugin.getConfiguration().getItemPay()), offer.getCostBuy())) {
                        player.sendMessage(ChatColor.RED + "Nie posiadasz tyle " + BazzarsPlugin.getConfiguration().getCurrency() + " (" + offer.getCostBuy() + ")");
                        player.closeInventory();
                    } else {
                        this.trade(new ItemStack(offer.getItem()), playerInventory, targetInventory, offer.getCostBuy());
                        player.sendMessage(ChatColor.GREEN + "Zakupiles przedmiot " + this.getOfferName(offer));
                        target.sendMessage(ChatColor.GREEN + player.getName() + " zakupil od Ciebie przedmiot " + this.getOfferName(offer));
                        player.closeInventory();
                    }
                } else if (guiConfirm.getType() == TypeGUI.SELL) {
                    if (!offer.canSell()) {
                        player.sendMessage(ChatColor.RED + "Ten przedmiot mozesz tylko kupic w tym bazarze!");
                        player.closeInventory();
                    } else if (!playerInventory.containsAtLeast(offer.getItem(), offer.getAmount())) {
                        player.sendMessage(ChatColor.RED + "Nie posiadasz " + Utils.getFriendlyName(offer.getItem().getType()) + "!");
                        player.closeInventory();
                    } else if (!targetInventory.containsAtLeast(new ItemStack(BazzarsPlugin.getConfiguration().getItemPay()), offer.getCostSell())) {
                        player.sendMessage(ChatColor.RED + target.getName() + " nie posiada tylu " + BazzarsPlugin.getConfiguration().getCurrency());
                        player.closeInventory();
                    } else {
                        this.trade(new ItemStack(offer.getItem()), targetInventory, playerInventory, offer.getCostSell());
                        player.sendMessage(ChatColor.GREEN + "Sprzedales przedmiot " + this.getOfferName(offer));
                        target.sendMessage(ChatColor.GREEN + player.getName() + " sprzedal Ci przedmiot " + this.getOfferName(offer));
                        player.closeInventory();
                    }
                }
            } else if (clicked.getType() == Material.REDSTONE_BLOCK && clicked.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + String.valueOf(ChatColor.BOLD) + "NIE")) {
                player.closeInventory();
                GUIManager.removeGUIConfirm(guiConfirm);
            } else {
                GUIManager.addGUIConfirm(guiConfirm);
            }
        }
    }
    
    private String getOfferName(Offer offer) {
        return offer.getItem().getType().toString().toLowerCase().replace("_", "") 
                + ChatColor.GRAY + " (ilosc: " + offer.getAmount() + ")";
    }
    
    private void trade(ItemStack offer, Inventory playerInventory, Inventory targetInventory, int cost) {
        ItemStack item = new ItemStack(BazzarsPlugin.getConfiguration().getItemPay(), cost);
        targetInventory.removeItem(offer);
        targetInventory.addItem(item);
        playerInventory.removeItem(item);
        playerInventory.addItem(offer);
    }
}