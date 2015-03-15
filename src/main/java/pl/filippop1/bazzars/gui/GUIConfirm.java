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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.filippop1.bazzars.gui.GUIShop.TypeGUI;

public class GUIConfirm {
    public static final String NAME_INVENTORY = ChatColor.BLUE + "Sklep gracza $player";
    
    private final Inventory inventory;
    private final ItemStack itemConfirm;
    private final String owner;
    private final String player;
    private final int slot;
    private TypeGUI type;
    
    public GUIConfirm(Inventory inventory, ItemStack itemConfirm, String owner, TypeGUI type, String player, int slot) {
        this.inventory = inventory;
        this.itemConfirm = itemConfirm;
        this.owner = owner;
        this.type = type;
        this.player = player;
        this.slot = slot;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    public ItemStack getItemConfirm() {
        return this.itemConfirm;
    }
    
    public String getOwner() {
        return this.owner;
    }
    
    public String getPlayer() {
        return this.player;
    }
    
    public TypeGUI getType() {
        return this.type;
    }
    
    public void setType(TypeGUI type) {
        this.type = type;
    }
    
    public void openConfirm(Player player) {
        ItemStack yes = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta im = yes.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + String.valueOf(ChatColor.BOLD) + "TAK");
        yes.setItemMeta(im);
        ItemStack no = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta imNo = no.getItemMeta();
        imNo.setDisplayName(ChatColor.RED + String.valueOf(ChatColor.BOLD) + "NIE");
        no.setItemMeta(imNo);
        
        this.inventory.setItem(4, itemConfirm);
        this.inventory.setItem(9, yes);
        this.inventory.setItem(10, yes);
        this.inventory.setItem(18, yes);
        this.inventory.setItem(19, yes);
        this.inventory.setItem(16, no);
        this.inventory.setItem(17, no);
        this.inventory.setItem(25, no);
        this.inventory.setItem(26, no);
        player.openInventory(this.inventory);
    }
}