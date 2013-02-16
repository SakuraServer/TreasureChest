/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest;

import java.util.HashMap;

/**
 *
 * @author ja731j
 */
public class Config {

    private HashMap<String, Short> priorityMap;
    private static Config instance = null;

    private Config() {
    }

    public Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
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
    
    public String chooseInventoryID() {
        return null;
        //TODO calculate the likeability of the inventories of getting choosed
    }
}
