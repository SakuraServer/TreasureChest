/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Manager;

import java.io.Serializable;
import java.util.ArrayList;
import net.ja731j.TreasureChest.TreasureChestMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 *
 * @author
 * ja731j
 */
public class TimeManager extends Manager{

    private ArrayList<AccessInfo> accessInfo = new ArrayList<AccessInfo>();
    
    public TimeManager(TreasureChestMain plugin) {
        super(plugin);
    }
    
    public void addAccessInfo(long t, Location l, String playerName) {
        AccessInfo info = new AccessInfo(playerName, l, t);
        accessInfo.add(info);
    }

    public void removeOldInfo() {
        for (AccessInfo info : accessInfo) {
            if (info.getTime() < System.currentTimeMillis()) {
                accessInfo.remove(info);
            }
        }
    }

    @Override
    public Object save() {
        removeOldInfo();
        return accessInfo;
    }

    @Override
    public void load(Object obj) {
        if(obj instanceof ArrayList){
            accessInfo = (ArrayList<AccessInfo>)obj;
        }
        removeOldInfo();
    }

    public boolean isAccessable(Location location, String palyerName) {
        removeOldInfo();
        for(AccessInfo info:accessInfo){
            if(info.getLocation().distance(location)==0){
                return true;
            }
        }
        return false;
    }

    public class AccessInfo implements Serializable {

        private String playerName;
        private String world;
        private int x;
        private int y;
        private int z;
        private long time;

        AccessInfo(String playerName, Location l, long t) {
            this.playerName = playerName;
            this.world = l.getWorld().getName();
            this.x = l.getBlockX();
            this.y = l.getBlockY();
            this.z = l.getBlockZ();
            this.time = t;
        }

        String getName() {
            return playerName;
        }

        Location getLocation() {
            return new Location(Bukkit.getWorld(world), x, y, z);
        }

        Long getTime() {
            return time;
        }
    }
}
