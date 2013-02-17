package net.ja731j.TreasureChest;

import java.util.ArrayList;
import java.util.Arrays;
import net.ja731j.TreasureChest.Commands.ConfigCommands;
import net.ja731j.TreasureChest.Commands.InventoryCommands;
import net.ja731j.TreasureChest.Listeners.AbstractListener;
import net.ja731j.TreasureChest.Listeners.ChestListener;
import net.ja731j.TreasureChest.Listeners.SignListener;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Hello world!
 *
 */
public class TreasureChestMain extends JavaPlugin {
    private ArrayList<AbstractListener> listeners = new ArrayList<AbstractListener>(Arrays.asList(new AbstractListener[] {
        new ChestListener(this),
        new SignListener(this)}));

    @Override
    public void onEnable() {
        for(AbstractListener listener:listeners){
            listener.registerListener();
        }        
        getLogger().info("The TreasureChest plugin has been loaded");
    }

    @Override
    public void onDisable() {
        getLogger().info("The TreasureChest plugin has been unloaded");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("TreasureChest")){
            if(args.length>1){
                if(args[0].equalsIgnoreCase("inventory")){
                    return new InventoryCommands().execCommand(sender,cmd,args);
                }else if(args[0].equalsIgnoreCase("config")){
                    return new ConfigCommands().execCommand(sender,cmd,args);
                }
            }
        }
        return false;
    }
}
