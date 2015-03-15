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

public class BazarManager {
    private static final List<Bazar> bazzars = new ArrayList<>();
    
    public static Bazar getBazar(String player) {
        for (Bazar b : bazzars) {
            if (b.getOwner().equalsIgnoreCase(player)) {
                return b;
            }
        }
        return null;
    }
    
    public static void addBazar(Bazar bazar) {
        bazzars.add(bazar);
    }
    
    public static void removeBazar(Bazar bazar) {
        bazzars.remove(bazar);
    }
    
    public static boolean removeBazar(String player) {
       Bazar bazar = BazarManager.getBazar(player);
       if (bazar != null) {
           return bazzars.remove(bazar);
       } else {
           return false;
       }
    }
    
    public static List<Bazar> getBazars() {
        return bazzars;
    }
}
