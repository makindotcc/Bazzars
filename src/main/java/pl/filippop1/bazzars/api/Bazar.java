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

import java.util.ArrayList;
import java.util.List;
import pl.filippop1.bazzars.BazzarsPlugin;
import pl.filippop1.bazzars.Hologram;

public class Bazar {
    public static final int MAX_NAME_LENGTH = 25;
    
    private Hologram hologram;
    private int lastID = 1;
    private String name;
    private final List<Offer> offers;
    private boolean open;
    private final String owner;
    
    public Bazar(String owner) {
        this(owner, BazzarsPlugin.getConfiguration().getBazarDefaultName(owner));
    }
    
    public Bazar(String owner, String name) {
        this.owner = owner;
        this.name = name;
        this.offers = new ArrayList<>();
    }
    
    public void addOffer(Offer offer) {
        this.lastID++;
        this.offers.add(offer);
    }
    
    public Hologram getHologram() {
        return this.hologram;
    }
    
    public int getLastID() {
        return this.lastID;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<Offer> getOffers() {
        return this.offers;
    }
    
    public String getOwner() {
        return this.owner;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public boolean remove(Offer offer) {
        return this.offers.remove(offer);
    }
    
    public int removeAll() {
        int size = this.offers.size();
        this.offers.clear();
        return size;
    }
    
    public void setHologram(Hologram hologram) {
        this.hologram = hologram;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setOpen(boolean open) {
        this.open = open;
    }
}