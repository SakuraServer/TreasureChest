/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author ja731j
 */
public class InventoryManager {

    private static InventoryManager instance = null;
    private HashMap<String, Inventory> inventories = new HashMap<String, Inventory>();

    private InventoryManager() {
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public String createInventory(){
        return addInventory(null);
    }
    
    public String createInventory(String id){
        addInventory(null,id);
        return id;
    }
    
    public String addInventory(Inventory inv) {
        String id = generateID();
        addInventory(inv, id);
        return id;
    }

    public boolean addInventory(Inventory inv, String id) {
        if (inv == null) {
            inv = Bukkit.createInventory(null, InventoryType.CHEST);
        }
        if (id.length() > 15) {
            return false;
        }
        inventories.put(id, inv);
        return true;
    }
    public Inventory getInventory(String id) {
        if (id.length() > 15) {
            return null;
        } else {
            return inventories.get(id);
        }
    }

    public boolean removeInventory(String id) {
        if (id.length() > 15) {
            return false;
        }
        inventories.remove(id);
        return true;
    }

    public List getInventoryIDs(){
         return Arrays.asList(inventories.keySet().toArray());
    }
    
    private String generateID() {
        String id = RandomStringUtils.randomAlphanumeric(15);
        while (inventories.containsKey(id)) {
            id = RandomStringUtils.randomAlphanumeric(15);
        }
        return id;
    }
}
