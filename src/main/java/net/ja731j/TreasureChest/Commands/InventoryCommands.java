/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Commands;

import java.util.List;
import net.ja731j.TreasureChest.Managers.InventoryManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author ja731j
 */
public class InventoryCommands{

    public boolean execCommand(CommandSender sender, Command cmd, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        if(args.length<2){//needs to be at least 2
            return false;
        }
        if(args[1].equalsIgnoreCase("create")){
            return create(sender,cmd,args);
        }else if(args[1].equalsIgnoreCase("list")){
                        return list(sender,cmd,args);
        }else if(args[1].equalsIgnoreCase("edit")){
                        return edit(sender,cmd,args);
        }else if(args[1].equalsIgnoreCase("remove")){
                                    return remove(sender,cmd,args);
        
        }
        
        return false;
    }

    private boolean create(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player)sender;
        String id;
        if(args.length==2){
            id = InventoryManager.getInstance().createInventory();
        }else{
            id= InventoryManager.getInstance().createInventory(args[2]);
        }
            p.sendMessage("Inventory Created:"+id);
            p.openInventory(InventoryManager.getInstance().getInventory(id));
            return true;
    }

    private boolean list(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player)sender;
        List<String> idlist = InventoryManager.getInstance().getInventoryIDs();
        p.sendMessage("Showing list of inventories");
        p.sendMessage(StringUtils.join(idlist.toArray(), ", "));
        return true;
    }

    private boolean edit(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player)sender;
        Inventory inv= InventoryManager.getInstance().getInventory(args[2]);
        if(inv!=null){
            p.openInventory(inv);
            p.sendMessage("Editing inventory:"+args[2]);
        }else{
            p.sendMessage("The inventory does not exist");
        }
            return true;
    }

    private boolean remove(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player)sender;
        InventoryManager.getInstance().removeInventory(args[2]);
        p.sendMessage("Removed inventory:"+args[2]);
        return true;
    }
}
