/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.ja731j.TreasureChest.Utils;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 *
 * @author
 * ja731j
 */
public class TimeManager extends Manager implements ConfigurationSerializable{
    private ArrayList<HashMap<String,Object>> accessInfo = new ArrayList<HashMap<String, Object>>();

    public Map<String, Object> serialize() {
        HashMap<String,Object> accessInfoMap = new HashMap<String, Object>();
        for(HashMap<String,Object> map:accessInfo){
            String name = (String)map.get("Name");
            Location loc = (Location)map.get("Location");
            long time = (Long)map.get("Time");
            if(time<System.currentTimeMillis()){
                continue;
            }
            HashMap<String,Object> mapInfoContents = new HashMap<String, Object>();
            mapInfoContents.put("Name", name);
            mapInfoContents.put("Location", Utils.serializeLocation(loc));
            mapInfoContents.put("Time", Long.toString(time));
            accessInfoMap.put(Integer.toString(accessInfo.indexOf(map)), mapInfoContents);
        }
        return accessInfoMap;
    }
    
    public void addAccessInfo(long t,Location l,String playerName){
        HashMap<String,Object> info = new HashMap<String, Object>();
        info.put("Name", playerName.toLowerCase());
        info.put("Location", l);
        info.put("Time", t);
        accessInfo.add(info);
    }
    
    public boolean isAccessable(Location l,String playerName){
        removeOldInfo();
        for(HashMap<String,Object> map:accessInfo){
            if(map.containsValue(playerName.toLowerCase())){
                Location storedLoc = (Location) map.get("Location");
                if(storedLoc.distance(l)==0){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void removeOldInfo(){
        for(HashMap<String,Object> map:accessInfo){
            if(!map.containsKey("Time")){
                Long time = (Long)map.get("Time");
                if(time<System.currentTimeMillis()){
                    accessInfo.remove(map);
                }
            }
        }
    }
    
    public static TimeManager deserialize(Map<String, Object> args) {
        TimeManager timeManager = new TimeManager();
        for(Entry<String,Object> e:args.entrySet()){
            if(e.getValue() instanceof Map){
                Map infoMap = (Map)e.getValue();
                if(infoMap.containsKey("Name")&&infoMap.containsKey("Location")&&infoMap.containsKey("Time")){
                    String name = (String)infoMap.get("Name");
                    Map<String,Object> serializeLoc = (Map<String,Object>)infoMap.get("Location");
                    Location loc = Utils.deserializeLocation(serializeLoc);
                    long time = Long.parseLong((String)infoMap.get("Time"));
                    timeManager.addAccessInfo(time, loc, name);
                }
            }
        }
        return timeManager;
    }
}
