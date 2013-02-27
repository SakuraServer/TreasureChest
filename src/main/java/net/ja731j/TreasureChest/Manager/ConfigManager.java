/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.ja731j.TreasureChest.Config;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 *
 * @author
 * ja731j
 */
public class ConfigManager extends Manager implements ConfigurationSerializable{

    private HashMap<String, Config> configs = new HashMap<String, Config>();

    public String createConfig() {
        return addConfig(new Config());
    }

    public String createConfig(String id) {
        return addConfig(null, id);
    }

    public String addConfig(Config cfg) {
        return addConfig(cfg, generateID());
    }

    public String addConfig(Config cfg, String id) {
        if (cfg == null) {
            cfg = new Config();
        }
        if (id.length() > 15) {
            return null;
        }
        configs.put(id, cfg);
        return id;
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

    private String generateID() {
        String id = RandomStringUtils.randomAlphanumeric(15);
        while (configs.containsKey(id)) {
            id = RandomStringUtils.randomAlphanumeric(15);
        }
        return id;
    }

    public List<String> getConfigIDs() {
        String[] str = configs.keySet().toArray(new String[0]);
        return new ArrayList<String>(Arrays.asList(str));
    }

    public boolean exists(String id) {
        return configs.containsKey(id);
    }

    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<String, Object>();
        for (Entry<String, Config> e : configs.entrySet()) {
            result.put(e.getKey(), e.getValue().serialize());
        }
        return result;
    }
        public static ConfigManager deserialize(Map<String, Object> args) {
        ConfigManager result = new ConfigManager();
        for (Entry<String, Object> e : args.entrySet()) {
            if (e.getValue() instanceof Map) {
                Map<String, Object> map = (Map) e.getValue();
                result.addConfig(new Config(map), e.getKey());
            }
        }
        return result;
    }

}
