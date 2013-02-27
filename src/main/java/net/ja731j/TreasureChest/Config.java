/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 *
 * @author ja731j
 */
public class Config implements ConfigurationSerializable {

    private HashMap<String, Short> priorityMap=new HashMap<String, Short>();
    private boolean enable = false;
    private boolean reset = false;
    
    static private final SecureRandom rnd = new SecureRandom();
    public Config() {
    }
    
    public Config(Map<String,Object> args){
        for(Entry<String,Object> e: args.entrySet()){
            if(e.getValue()instanceof Short);
            this.addInventorySetting(e.getKey(), (Short)e.getValue());
        }
    }

    public boolean addInventorySetting(String id, Short priority){
        if(id.length()>15){
            return false;
        }
        priorityMap.put(id, priority);
        return true;
    }
    
    public Short getInventorySetting(String id){
        if(id.length()>15){
            return null;
        }
        return priorityMap.get(id);
    }
    
    public Boolean removeInventorySetting(String id){
        if(id.length()>15){
            return false;
        }
        priorityMap.remove(id);
        return true;
    }
    
    public HashMap<String,Short> getInventorySettings(){
        return (HashMap<String,Short>)priorityMap.clone();
    }
    
    public String chooseInventoryID() {
        HashMap<String,Integer[]> map = new HashMap<String,Integer[]>();
        int sum=0;
        for(Entry<String,Short> e:priorityMap.entrySet()){
            Integer range[] = {sum,sum+=e.getValue()};
            map.put(e.getKey(), range);
        }
            double value = rnd.nextDouble()*sum;
            for(Entry<String,Integer[]> e:map.entrySet()){
                if(e.getValue()[0]<=value&&e.getValue()[1]>value){
                    return e.getKey();
                }
            }
        throw new RuntimeException();
    }

    public Map<String, Object> serialize() {
        return (Map)priorityMap;
    }

    public boolean isEnabled() {
        return enable;
    }

    public boolean isReset() {
        return reset;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
