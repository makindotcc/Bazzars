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

package pl.filippop1.bazzars.api;

import java.util.UUID;
import org.bukkit.inventory.ItemStack;

public class Offer {
    private ItemStack item;
    private int amount, costBuy, costSell, numericID;
    private final UUID uuid;
    
    public Offer(ItemStack item, int amount, int costBuy, int costSell, int numericID) {
        this.item = item;
        this.amount = amount;
        this.costBuy = costBuy;
        this.costSell = costSell;
        this.numericID = numericID;
        this.uuid = UUID.randomUUID();
    }
    
    public boolean canBuy() {
        return this.costBuy >= 0;
    }
    
    public boolean canSell() {
        return this.costSell >= 0;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public int getCostBuy() {
        return this.costBuy;
    }
    
    public int getCostSell() {
        return this.costSell;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public int getNumericID() {
        return this.numericID;
    }
    
    public UUID getUUID() {
        return this.uuid;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public void setCostBuy(int cost) {
        this.costBuy = cost;
    }
    
    public void setCostSell(int cost) {
        this.costSell = cost;
    }
    
    public void setItem(ItemStack item) {
        this.item = item;
    }
    
    public void setNumericID(int id) {
        this.numericID = id;
    }
}