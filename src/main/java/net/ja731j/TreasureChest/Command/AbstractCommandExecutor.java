/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Command;

import net.ja731j.TreasureChest.TreasureChestMain;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author
 * ja731j
 */
public abstract class AbstractCommandExecutor {
    protected TreasureChestMain plugin;
    public AbstractCommandExecutor(TreasureChestMain plugin){
        this.plugin = plugin;
    }
    
    public abstract String getCommandName();
    
    public abstract boolean execCmd(CommandSender sender, String[] args);
}
