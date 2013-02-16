/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Listeners;

import java.util.ArrayList;
import net.ja731j.TreasureChest.Utils;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author ja731j
 */
public class SignListener extends AbstractListener {

    public SignListener(Plugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("[TreasureChest]")) {
            int i = 0;

            ArrayList<Chest> chests = Utils.getTouchingChests(event.getBlock());
            if (chests.size() > 1) {
                this.cancellEvent(event, "You can only place one sign per chest!");
                return;
            } else if (chests.size() < 0) {
                return;
            }
            Chest chest = chests.get(0);


            ArrayList<Sign> signs = Utils.getTouchingSigns(chest.getBlock());
            for (Sign sign : signs) {
                if (sign.getLine(0).equalsIgnoreCase("[TreasureChest]")) {
                    i++;
                }
            }
            if (i >= 1) {
                this.cancellEvent(event, "You can't place multiple signs to chests!");
            }
        }
    }
    
    private void cancellEvent(SignChangeEvent event,String reason){
        event.setCancelled(true);
        Player player = event.getPlayer();
        player.sendMessage(reason);
        event.getBlock().breakNaturally();
    }
}
