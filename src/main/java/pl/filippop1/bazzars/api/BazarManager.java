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

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BazarManager {
    private static final List<Bazar> bazzars = new ArrayList<>();
    private final static Cache<UUID, Bazar> uuidBazarCache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
    
    public static Bazar getBazar(UUID player) {
        Bazar bazar = uuidBazarCache.getIfPresent(player);
        if (bazar != null)
            return bazar;

        for (Bazar b : bazzars) {
            if (b.getOwner().equals(player)) {
                uuidBazarCache.put(player, b);
                return b;
            }
        }
        return null;
    }
    
    public static void addBazar(Bazar bazar) {
        bazzars.add(bazar);
    }
    
    public static void removeBazar(Bazar bazar) {
        uuidBazarCache.invalidate(bazar.getOwner());
        bazzars.remove(bazar);
    }
    
    public static boolean removeBazar(UUID player) {
        uuidBazarCache.invalidate(player);
        Bazar bazar = BazarManager.getBazar(player);
        return bazar != null && bazzars.remove(bazar);
    }
    
    public static List<Bazar> getBazars() {
        return bazzars;
    }
}
