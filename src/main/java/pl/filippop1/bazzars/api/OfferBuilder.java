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

import org.bukkit.inventory.ItemStack;

public class OfferBuilder {
    private ItemStack item;
    private int amount, costBuy, costSell, numericID;
    
    public OfferBuilder item(ItemStack item) {
        this.item = item;
        return this;
    }
    
    public OfferBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }
    
    public OfferBuilder costBuy(int cost) {
        this.costBuy = cost;
        return this;
    }
    
    public OfferBuilder costSell(int cost) {
        this.costSell = cost;
        return this;
    }
    
    public OfferBuilder numericID(int id) {
        this.numericID = id;
        return this;
    }
    
    public Offer toOffer() {
        return new Offer(this.item, this.amount, this.costBuy, this.costSell, this.numericID);
    }
}
