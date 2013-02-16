/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Commands;

import net.ja731j.TreasureChest.Managers.InventoryManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author ja731j
 */
public class InventoryCommands{

    public boolean execCommand(CommandSender sender, Command cmd, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        if(args.length>2){//needs to be at least 3
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
        String[] str = (String[])InventoryManager.getInstance().getInventoryIDs().toArray();
        p.sendMessage("Showing list of inventories");
        p.sendMessage(StringUtils.join(str, ", "));
        return true;
    }

    private boolean edit(CommandSender sender, Command cmd, String[] args) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private boolean remove(CommandSender sender, Command cmd, String[] args) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private String generateID(){
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
