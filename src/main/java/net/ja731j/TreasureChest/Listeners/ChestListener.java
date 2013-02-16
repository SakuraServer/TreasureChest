/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Listeners;

import java.util.ArrayList;
import net.ja731j.TreasureChest.Utils;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author ja731j
 */
public class ChestListener extends AbstractListener {

    public ChestListener(Plugin plugin){
        super(plugin);
    }
    
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Chest) {
            Chest chest = (Chest) holder;

            //Get neighboring signs containing "[TreasureChest]" in the first line
            ArrayList<Sign> signs = new ArrayList<Sign>();
            for(Sign sign:Utils.getTouchingSigns(chest.getBlock())){
                if(sign.getLine(0).equalsIgnoreCase("[TreasureChest]")){
                    signs.add(sign);
                }
            }
            
            if (!signs.isEmpty()) {
                event.setCancelled(true);
                Inventory original = event.getInventory();
                Inventory copy = Bukkit.createInventory(null, InventoryType.CHEST);

                for (ItemStack stack : original.getContents()) {
                    if (stack != null) {
                        copy.addItem(stack);
                    }
                }
                event.getPlayer().openInventory(copy);
            }

        }
    }
}
