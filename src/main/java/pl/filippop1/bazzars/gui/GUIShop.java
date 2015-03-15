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

package pl.filippop1.bazzars.gui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.filippop1.bazzars.BazzarsPlugin;
import pl.filippop1.bazzars.Utils;
import pl.filippop1.bazzars.api.Offer;

public class GUIShop {
    public static final String NAME_INVENTORY = ChatColor.BLUE + "Oferty";
    
    private final Inventory inventory;
    private ItemStack itemConfirm;
    private final List<Offer> offers;
    private final String owner, whoClicked;
    private final int size;
    
    public GUIShop(String owner, int size, String whoClicked, List<Offer> offers) {
        this.offers = offers;
        this.owner = owner;
        this.size = size * 9;
        this.whoClicked = whoClicked;
        this.inventory = Bukkit.createInventory(null, size * 9, NAME_INVENTORY);
        int i = 0;
        for (Offer offer : this.offers) {
            ItemStack item = new ItemStack(offer.getItem());
            ItemMeta meta = item.getItemMeta();
            List<String> lore;
            if (meta.getLore() == null) {
                lore = new ArrayList<>();
            } else {
                lore = meta.getLore();
            }
            lore.add(ChatColor.GREEN + "Przedmiot: " + offer.getAmount() + "x " + 
                    Utils.getFriendlyName(offer.getItem().getType()));
            
            if (offer.canBuy()) {
                lore.add(ChatColor.GOLD + "Cena kupna: " + ChatColor.YELLOW + offer.getCostBuy() + " " + BazzarsPlugin.getConfiguration().getCurrency());
            }
            if (offer.canSell()) {
                lore.add(ChatColor.GOLD + "Cena sprzedazy: " + ChatColor.YELLOW + offer.getCostSell() + " " + BazzarsPlugin.getConfiguration().getCurrency());   
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            this.inventory.setItem(i, item);
            i++;
        }
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    public ItemStack getItemConfirm() {
        return this.itemConfirm;
    }
    
    public List<Offer> getItems() {
        return this.offers;
    }
    
    public String getOwner() {
        return this.owner;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public String getWhoClicked() {
        return this.whoClicked;
    }
    
    public void openShop(Player player) {
        int gold = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType().equals(BazzarsPlugin.getConfiguration().getItemPay())) {
                gold += item.getAmount();
            }
        }
        
        ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Stan konta: " + gold + " " + BazzarsPlugin.getConfiguration().getCurrency());
        item.setItemMeta(meta);
        
        this.inventory.setItem(this.size - 1, item);
        player.openInventory(this.inventory);
    }
    
    public void setItemConfirm(ItemStack item) {
        this.itemConfirm = item;
    }
    
    public static enum TypeGUI {
        BUY, SELL;
    }
}