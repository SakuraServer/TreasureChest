/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author ja731j
 */
public abstract class AbstractListener implements Listener {

    protected Plugin plugin;

    public AbstractListener(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerListener() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
