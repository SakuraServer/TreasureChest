/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Listener;

import java.util.ArrayList;
import net.ja731j.TreasureChest.Config;
import net.ja731j.TreasureChest.Manager.ConfigManager;
import net.ja731j.TreasureChest.Manager.InventoryManager;
import net.ja731j.TreasureChest.Manager.TimeManager;
import net.ja731j.TreasureChest.TreasureChestMain;
import net.ja731j.TreasureChest.Utils;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author
 * ja731j
 */
public class ChestListener extends AbstractListener {

    ConfigManager cfgManager = plugin.getManager(ConfigManager.class);
    InventoryManager invManager = plugin.getManager(InventoryManager.class);
    TimeManager timeManager = plugin.getManager(TimeManager.class);

    public ChestListener(TreasureChestMain plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Chest) {
            Chest chest = (Chest) holder;
            String palyerName = event.getPlayer().getName();

            //Get neighboring signs containing "[TreasureChest]" in the first line
            ArrayList<Sign> signs = new ArrayList<Sign>();
            for (Sign sign : Utils.getTouchingSigns(chest.getBlock())) {
                if (sign.getLine(0).equalsIgnoreCase("[TreasureChest]")) {
                    signs.add(sign);
                }
            }

            if (signs.isEmpty()) { //Only proceed if it's a Treasure Chest
                return;
            }

            Sign sign = signs.get(0); //Theoretically, there should be only one sign anyway
            if (sign.getLine(1).isEmpty()) { //Only proceed if a ID is specified
                return;
            }

            String configId = sign.getLine(1);
            if (!cfgManager.exists(configId)) { //Don't do anything if the config doesn't exist
                return;
            }
            
            Config cfg = cfgManager.getConfig(configId);
            if(!cfg.isEnabled()){ //Don't proceed if it's disabled
                return;
            }
            
            if(!timeManager.isAccessable(sign.getLocation(), palyerName)){
                return;
            }
            
            String masterInvId = cfg.chooseInventoryID();
            Inventory targetInv = chest.getBlockInventory();
            Inventory tcInv = invManager.getInventoryCopy(masterInvId);

            //count empty slots;
            int emptySlots = 0;
            for (ItemStack stack : targetInv.getContents()) {
                if (stack == null) {
                    emptySlots++;
                }
            }

            //count contents
            int contents = 0;
            for (ItemStack stack : tcInv) {
                if (stack != null) {
                    emptySlots++;
                }
            }

            //If the target chest doesn't have enough empty slots just simply delete all of the contents
            if(cfg.isReset() ||(contents>emptySlots)){
                targetInv.clear();
            }
            
            for(ItemStack stack:tcInv){
                targetInv.addItem(stack);
            }
            
            //Add chest access info
            if(cfg.hasInterval()){
                timeManager.addAccessInfo(System.currentTimeMillis()+cfg.getInterval()*60*1000, sign.getLocation(), palyerName);
            }
            
        }
    }
}
