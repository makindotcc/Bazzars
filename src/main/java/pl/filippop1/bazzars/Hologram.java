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

package pl.filippop1.bazzars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Hologram {
    /* Holograms
    * By
    * Dzikoysk
    */
    public static List<Hologram> holograms = new ArrayList<>();
    private static final double distance = 3;
    private String id;
    private List<String> lines;
    private List<Integer> ids;
    private Location location;

    public Hologram(String id, Location location) {
        this.id = id;
        this.lines = new ArrayList<>();
        this.ids = new ArrayList<>();
        this.location = location;
        holograms.add(this);
    }

    public static Hologram get(String id){
        for(Hologram h : holograms) if(h.getID().equals(id)) {
            return h;
        }
        return null;
    }

    public void change(String[] lines){
        this.lines.clear();
        try {
                destroy();
        } catch (Exception e) {
                e.printStackTrace();
        }
        this.lines.addAll(Arrays.asList(lines));
        if(this.location != null) show(this.location);
    }

    public Location getLocation() {
        return this.location;
    }
    
    public void show(Location loc) {
      if(this.lines == null) return;
      Location first = loc.clone().add(0, (this.lines.size() / 2) * distance, 0);
      for (int i = 0; i < this.lines.size(); i++) {
         ids.addAll(showLine(first.clone(), this.lines.get(i)));
         first.subtract(0, distance, 0);
      }
      this.location = loc;
    }
    
    public void show(Location loc, Player player) {
        if(this.lines == null) return;
        Location first = loc.clone().add(0, (this.lines.size() / 2) * distance, 0);
        for (String line : this.lines) {
            ids.addAll(showLine(first.clone(), line, player));
            first.subtract(0, distance, 0);
        }
        this.location = loc;
    }

    public void show(Location loc, int sec) {
      show(loc);
      new BukkitRunnable() {
         @Override
         public void run() {
            try {
                destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
         }
      }.runTaskLater(BazzarsPlugin.getInstance(), sec*20);
    }

    public void destroy() throws Exception {
       int[] ints = new int[ids.size()];
       for (int j = 0; j < ints.length; j++) if(j != 0) ints[j] = ids.get(j);
       Class<?> packetDestroy = ReflectionUtils.getCraftClass("PacketPlayOutEntityDestroy");
       Object packet = packetDestroy.getConstructor(new Class<?>[]{ int[].class }).newInstance(ints);
       for (Player p : Bukkit.getOnlinePlayers()) PacketUtils.sendPacket(p, packet);
       this.ids.clear();
    }

    private static List<Integer> showLine(Location loc, String text, Player player) {
        Class<?> Entity = ReflectionUtils.getCraftClass("Entity");
        Class<?> EntityLiving = ReflectionUtils.getCraftClass("EntityLiving");
        Class<?> EntityWitherSkull = ReflectionUtils.getCraftClass("EntityWitherSkull");
        Class<?> EntityHorse = ReflectionUtils.getCraftClass("EntityHorse");
        Class<?> packetOnlyClass = ReflectionUtils.getCraftClass("PacketPlayOutSpawnEntity");
        Class<?> packetLivingClass = ReflectionUtils.getCraftClass("PacketPlayOutSpawnEntityLiving");
        Class<?> packetAttachClass = ReflectionUtils.getCraftClass("PacketPlayOutAttachEntity");
        try {
            Object world = ReflectionUtils.getHandle(loc.getWorld());		  
            Object skull = EntityWitherSkull.getConstructor(ReflectionUtils.getCraftClass("World")).newInstance(world);  
            ReflectionUtils.getMethod(EntityWitherSkull, "setLocation", double.class, double.class, double.class, float.class, float.class).invoke(skull, loc.getX(), loc.getY() + 1 + 55, loc.getZ(), 0, 0);
            Object skull_packet = packetOnlyClass.getConstructor(new Class<?>[]{ Entity, int.class }).newInstance(skull, 64);

            Object horse = EntityHorse.getConstructor(ReflectionUtils.getCraftClass("World")).newInstance(world);  
            ReflectionUtils.getMethod(EntityHorse, "setLocation", double.class, double.class, double.class, float.class, float.class).invoke(horse, loc.getX(), loc.getY() + 55, loc.getZ(), 0, 0);
            ReflectionUtils.getMethod(EntityHorse, "setAge", int.class).invoke(horse, -1700000);
            ReflectionUtils.getMethod(EntityHorse, "setCustomName", String.class).invoke(horse, text);
            ReflectionUtils.getMethod(EntityHorse, "setCustomNameVisible", boolean.class).invoke(horse, true);
            Object packedt = packetLivingClass.getConstructor(new Class<?>[]{ EntityLiving }).newInstance(horse);
            PacketUtils.sendPacket(player, packedt);
            PacketUtils.sendPacket(player, skull_packet);
            Object pa = packetAttachClass.getConstructor(new Class<?>[]{ int.class, Entity, Entity }).newInstance(0, horse, skull);
            PacketUtils.sendPacket(player, pa);
            int sid = (int)ReflectionUtils.getMethod(EntityWitherSkull, "getId").invoke(skull);
            int hid = (int)ReflectionUtils.getMethod(EntityHorse, "getId").invoke(horse);
            return Arrays.asList(sid, hid);
        } catch(Exception e) { e.printStackTrace(); }
        return null; 
    }
    
    private static List<Integer> showLine(Location loc, String text) {
        Class<?> Entity = ReflectionUtils.getCraftClass("Entity");
        Class<?> EntityLiving = ReflectionUtils.getCraftClass("EntityLiving");
        Class<?> EntityWitherSkull = ReflectionUtils.getCraftClass("EntityWitherSkull");
        Class<?> EntityHorse = ReflectionUtils.getCraftClass("EntityHorse");
        Class<?> packetOnlyClass = ReflectionUtils.getCraftClass("PacketPlayOutSpawnEntity");
        Class<?> packetLivingClass = ReflectionUtils.getCraftClass("PacketPlayOutSpawnEntityLiving");
        Class<?> packetAttachClass = ReflectionUtils.getCraftClass("PacketPlayOutAttachEntity");
        try {
            Object world = ReflectionUtils.getHandle(loc.getWorld());		  
            Object skull = EntityWitherSkull.getConstructor(ReflectionUtils.getCraftClass("World")).newInstance(world);  
            ReflectionUtils.getMethod(EntityWitherSkull, "setLocation", double.class, double.class, double.class, float.class, float.class).invoke(skull, loc.getX(), loc.getY() + 1 + 55, loc.getZ(), 0, 0);
            Object skull_packet = packetOnlyClass.getConstructor(new Class<?>[]{ Entity, int.class }).newInstance(skull, 64);

            Object horse = EntityHorse.getConstructor(ReflectionUtils.getCraftClass("World")).newInstance(world);  
            ReflectionUtils.getMethod(EntityHorse, "setLocation", double.class, double.class, double.class, float.class, float.class).invoke(horse, loc.getX(), loc.getY() + 55, loc.getZ(), 0, 0);
            ReflectionUtils.getMethod(EntityHorse, "setAge", int.class).invoke(horse, -1700000);
            ReflectionUtils.getMethod(EntityHorse, "setCustomName", String.class).invoke(horse, text);
            ReflectionUtils.getMethod(EntityHorse, "setCustomNameVisible", boolean.class).invoke(horse, true);
            Object packedt = packetLivingClass.getConstructor(new Class<?>[]{ EntityLiving }).newInstance(horse);
            for (Player player : loc.getWorld().getPlayers()) {
                    PacketUtils.sendPacket(player, packedt);
                    PacketUtils.sendPacket(player, skull_packet);
                    Object pa = packetAttachClass.getConstructor(new Class<?>[]{ int.class, Entity, Entity }).newInstance(0, horse, skull);
                    PacketUtils.sendPacket(player, pa);
            }      
            int sid = (int)ReflectionUtils.getMethod(EntityWitherSkull, "getId").invoke(skull);
            int hid = (int)ReflectionUtils.getMethod(EntityHorse, "getId").invoke(horse);
            return Arrays.asList(sid, hid);
        } catch(Exception e) { e.printStackTrace(); }
        return null; 
    }

    public void delete(){
        holograms.remove(this); 
        try {
            destroy();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getID(){
        return this.id;
    }
}
