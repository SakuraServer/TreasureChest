/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author
 * ja731j
 */
public class InventoryManager extends Manager implements ConfigurationSerializable {

    private HashMap<String, Inventory> inventories = new HashMap<String, Inventory>();

    public String createInventory() {
        return addInventory(null);
    }

    public String createInventory(String id) {
        addInventory(null, id);
        return id;
    }

    public String addInventory(Inventory inv) {
        String id = generateID();
        addInventory(inv, id);
        return id;
    }

    public String addInventory(Inventory inv, String id) {
        if (inv == null) {
            inv = Bukkit.createInventory(null, InventoryType.CHEST);
        }
        if (id.length() > 15) {
            return null;
        }
        inventories.put(id, inv);
        return id;
    }

    public Inventory getInventory(String id) {
        if (id.length() > 15) {
            return null;
        } else {
            return inventories.get(id);
        }
    }

    public Inventory getInventoryCopy(String id) {
        if (id.length() > 15) {
            return null;
        } else {
            Inventory original = getInventory(id);
            Inventory copy = Bukkit.createInventory(null, InventoryType.CHEST);

            for (ItemStack stack : original.getContents()) {
                if (stack != null) {
                    copy.addItem(stack);
                }
            }
            return copy;
        }
    }

    public boolean removeInventory(String id) {
        if (id.length() > 15) {
            return false;
        }
        inventories.remove(id);
        return true;
    }

    public List<String> getInventoryIDs() {
        String[] str = inventories.keySet().toArray(new String[0]);
        return new ArrayList<String>(Arrays.asList(str));
    }

    private String generateID() {
        String id = RandomStringUtils.randomAlphanumeric(15);
        while (inventories.containsKey(id)) {
            id = RandomStringUtils.randomAlphanumeric(15);
        }
        return id;
    }

    public boolean exists(String id) {
        return inventories.containsKey(id);
    }

    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<String, Object>();
        for (Entry<String, Inventory> e : inventories.entrySet()) {
            ListIterator<ItemStack> invItr = e.getValue().iterator();
            Map<String, Object> invMap = new HashMap<String, Object>();
            while (invItr.hasNext()) {
                invMap.put(Integer.toString(invItr.nextIndex()), invItr.next().serialize());
            }
            result.put(e.getKey(), invMap);
        }
        return result;
    }

    public static InventoryManager deserialize(Map<String, Object> args) {
        InventoryManager invManager = new InventoryManager();
        //for each inventory
        for (Entry<String, Object> managerEntry : args.entrySet()) {
            String invId = managerEntry.getKey();
            //deserialize inventory
            Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST);
            if (!(managerEntry instanceof Map)) {
                break;
            }
            Map<String, Object> invMap = (Map) managerEntry;
            for (Entry<String, Object> invEntry : invMap.entrySet()) { 
                if (!(invEntry.getValue() instanceof Map)) {
                    break;
                }
                Map<String, Object> stack = (Map<String, Object>) invEntry.getValue();
                if (inventory.firstEmpty() != -1 && stack != null) {
                    inventory.addItem(ItemStack.deserialize(stack));

                }
            }
            invManager.addInventory(inventory, invId);
        }
        return invManager;

    }
}
