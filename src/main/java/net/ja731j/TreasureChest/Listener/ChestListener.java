/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Listener;

import java.util.ArrayList;
import net.ja731j.TreasureChest.Manager.ConfigManager;
import net.ja731j.TreasureChest.Manager.InventoryManager;
import net.ja731j.TreasureChest.TreasureChestMain;
import net.ja731j.TreasureChest.Utils;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 *
 * @author
 * ja731j
 */
public class ChestListener extends AbstractListener {

    ConfigManager cfgManager = plugin.getManager(ConfigManager.class);
    InventoryManager invManager = plugin.getManager(InventoryManager.class);

    public ChestListener(TreasureChestMain plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Chest) {
            Chest chest = (Chest) holder;

            //Get neighboring signs containing "[TreasureChest]" in the first line
            ArrayList<Sign> signs = new ArrayList<Sign>();
            for (Sign sign : Utils.getTouchingSigns(chest.getBlock())) {
                if (sign.getLine(0).equalsIgnoreCase("[TreasureChest]")) {
                    signs.add(sign);
                }
            }

            if (!signs.isEmpty()) {
                Sign sign = signs.get(0);
                event.setCancelled(true);
                Inventory tcInv;
                if (!sign.getLine(1).isEmpty()) {
                    String configId = sign.getLine(1);
                    if (cfgManager.exists(configId)) {
                        String masterInvId = cfgManager.getConfig(configId).chooseInventoryID();
                        tcInv = invManager.getInventoryCopy(masterInvId);
                    } else {
                        tcInv = Bukkit.createInventory(null, InventoryType.CHEST);
                    }
                } else {
                    tcInv = Bukkit.createInventory(null, InventoryType.CHEST);
                }
                event.getPlayer().openInventory(tcInv);
            }

        }
    }
}
