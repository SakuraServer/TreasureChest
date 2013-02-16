/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Managers;

import java.util.HashMap;
import net.ja731j.TreasureChest.Config;

/**
 *
 * @author ja731j
 */
public class ConfigManager {
    private HashMap<String,Config> configs;
    private static ConfigManager instance = null;
    
    private ConfigManager(){}
    
    public ConfigManager getInstance(){
        if(instance == null){
            instance = new ConfigManager();
        }
        return instance;
    }
    
        public boolean addConfig(Config cfg, String id) {
        if (cfg == null) {
            return false;
        }
        if (id.length() > 15) {
            return false;
        }
        configs.put(id, cfg);
        return true;
    }

    public Config getConfig(String id) {
        if (id.length() > 15) {
            return null;
        } else {
            return configs.get(id);
        }
    }

    public boolean removeConfig(String id) {
        if (id.length() > 15) {
            return false;
        }
        configs.remove(id);
        return true;
    }
}
