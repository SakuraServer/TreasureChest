/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ja731j.TreasureChest.Config;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author
 * ja731j
 */
public class ConfigManager {

    private HashMap<String, Config> configs=new HashMap<String,Config>();
    private static ConfigManager instance = null;

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

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
}
