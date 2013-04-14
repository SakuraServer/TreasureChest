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
import net.ja731j.TreasureChest.TreasureChestMain;
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
public class InventoryManager extends Manager {

    public InventoryManager(TreasureChestMain plugin) {
        super(plugin);
    }
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

    @Override
    public Object save() {
        HashMap<String, Map<Integer, Map<String, Object>>> result = new HashMap<String, Map<Integer, Map<String, Object>>>();
        for (Entry<String, Inventory> e : inventories.entrySet()) {
            ListIterator<ItemStack> invItr = e.getValue().iterator();
            Map<Integer, Map<String, Object>> invMap = new HashMap<Integer, Map<String, Object>>();
            while (invItr.hasNext()) {
                int index = invItr.nextIndex();
                ItemStack stack = invItr.next();
                if (stack == null) {
                    continue;
                }
                invMap.put(index, stack.serialize());
            }
            result.put(e.getKey(), invMap);
        }
        return result;
    }

    @Override
    public void load(Object obj) {
        if (obj instanceof HashMap) {
            HashMap<String, Map<Integer, Map<String, Object>>> source = (HashMap<String, Map<Integer, Map<String, Object>>>)obj;
            HashMap<String, Inventory> result = new HashMap<String, Inventory>();
            for(Entry<String, Map<Integer, Map<String, Object>>> invEntry:source.entrySet()){
                Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST);
                for(Entry<Integer, Map<String, Object>> stackEntry: invEntry.getValue().entrySet()){
                    inv.setItem(stackEntry.getKey(), ItemStack.deserialize(stackEntry.getValue()));
                }
                result.put(invEntry.getKey(), inv);
            }
            inventories = result;
        }
    }
}
