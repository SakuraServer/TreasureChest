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
 * @author
 * ja731j
 */
public class Config implements ConfigurationSerializable {

    private HashMap<String, Short> priorityMap = new HashMap<String, Short>();
    private boolean enable = false;
    private boolean reset = false;
    private int interval = 0; //interval in minutes
    static private final SecureRandom rnd = new SecureRandom();

    public Config() {
    }

    public Config(Map<String, Object> args) {
        if (args instanceof Map) {
            if (args.containsKey("PriorityMap") && (args.get("PriorityMap") instanceof Map)) {
                HashMap<String, Object> tmpMap = (HashMap<String, Object>) args.get("PriorityMap");
                for (Entry<String, Object> e : args.entrySet()) {
                    if (e.getValue() instanceof Short);
                    this.addInventorySetting(e.getKey(), (Short) e.getValue());
                }
            }
            if (args.containsKey("Interval")) {
                interval = Integer.parseInt((String) args.get("Interval"));
            }
            if (args.containsKey("Enable")) {
                enable = Boolean.parseBoolean((String) args.get("Enable"));
            }
            if (args.containsKey("Reset")) {
                reset = Boolean.parseBoolean((String) args.get("Reset"));
            }
        }
    }

    public boolean addInventorySetting(String id, Short priority) {
        if (id.length() > 15) {
            return false;
        }
        priorityMap.put(id, priority);
        return true;
    }

    public Short getInventorySetting(String id) {
        if (id.length() > 15) {
            return null;
        }
        return priorityMap.get(id);
    }

    public Boolean removeInventorySetting(String id) {
        if (id.length() > 15) {
            return false;
        }
        priorityMap.remove(id);
        return true;
    }

    public HashMap<String, Short> getInventorySettings() {
        return (HashMap<String, Short>) priorityMap.clone();
    }

    public String chooseInventoryID() {
        HashMap<String, Integer[]> map = new HashMap<String, Integer[]>();
        int sum = 0;
        for (Entry<String, Short> e : priorityMap.entrySet()) {
            Integer range[] = {sum, sum += e.getValue()};
            map.put(e.getKey(), range);
        }
        double value = rnd.nextDouble() * sum;
        for (Entry<String, Integer[]> e : map.entrySet()) {
            if (e.getValue()[0] <= value && e.getValue()[1] > value) {
                return e.getKey();
            }
        }
        throw new RuntimeException();
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("PriorityMap", priorityMap);
        map.put("Interval", Integer.toString(interval));
        map.put("Enable", Boolean.toString(enable));
        map.put("Reset", Boolean.toString(reset));
        return map;
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

    public int getInterval() {
        return this.interval;
    }

    public boolean setInterval(int interval) {
        if (interval < 0) {
            return false;
        }
        this.interval = interval;
        return true;
    }

    public boolean hasInterval() {
        if (interval == 0) {
            return true;
        } else {
            return false;
        }
    }
}
