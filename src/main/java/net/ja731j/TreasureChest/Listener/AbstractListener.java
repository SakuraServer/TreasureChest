/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Listener;

import net.ja731j.TreasureChest.TreasureChestMain;
import org.bukkit.event.Listener;

/**
 *
 * @author ja731j
 */
public abstract class AbstractListener implements Listener {

    protected TreasureChestMain plugin;

    public AbstractListener(TreasureChestMain plugin) {
        this.plugin = plugin;
    }

    public void registerListener() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
