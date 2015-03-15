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
import org.bukkit.inventory.Inventory;

public class GUIManager {
    private static final List<GUIShop> guiShop = new ArrayList<>();
    private static final List<GUIConfirm> guiConfirm = new ArrayList<>();
    
    public static void addGUIConfirm(GUIConfirm gui) {
        guiConfirm.add(gui);
    }
    
    public static void addGUIShop(GUIShop gui) {
        guiShop.add(gui);
    }
    
    public static GUIConfirm getGUIConfirm(Inventory player) {
        for (GUIConfirm gui : guiConfirm) {
            if (gui.getInventory().equals(player)) {
                return gui;
            }
        }
        return null;
    }
    
    public static GUIShop getGUIShop(Inventory player) {
        for (GUIShop gui : guiShop) {
            if (gui.getInventory().equals(player)) {
                return gui;
            }
        }
        return null;
    }
    
    public static void removeGUIConfirm(GUIConfirm gui) {
        guiConfirm.remove(gui);
    }
    
    public static void removeGUIShop(GUIShop gui) {
        guiShop.remove(gui);
    }
}
