/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Command;

import java.util.Arrays;
import java.util.List;
import net.ja731j.TreasureChest.Manager.InventoryManager;
import net.ja731j.TreasureChest.TreasureChestMain;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author
 * ja731j
 */
public class InventoryCommandExecutor extends AbstractCommandExecutor {

    InventoryManager invManager = plugin.getManager(InventoryManager.class);
    
    public InventoryCommandExecutor(TreasureChestMain plugin) {
        super(plugin);
    }

    public boolean execCmd(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (args.length < 1) {//needs to be at least 1
            return false;
        }
        String cmd = args[0];
        String[] subCmdArgs;
            subCmdArgs = Arrays.copyOfRange(args, 1, args.length);
        if (cmd.equalsIgnoreCase("create")) {
            return create(sender, subCmdArgs);
        } else if (cmd.equalsIgnoreCase("list")) {
            return list(sender, subCmdArgs);
        } else if (cmd.equalsIgnoreCase("edit")) {
            return edit(sender, subCmdArgs);
        } else if (cmd.equalsIgnoreCase("remove")) {
            return remove(sender, subCmdArgs);

        }

        return false;
    }

    private boolean create(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        String reqInvID;
        String invID;
        if (args.length < 1) {
            invID = invManager.createInventory();
        } else {
            reqInvID = args[0];
            invID = invManager.createInventory(reqInvID);
        }
        p.sendMessage("Inventory Created:" + invID);
        p.openInventory(invManager.getInventory(invID));
        return true;
    }

    private boolean list(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        List<String> idlist = invManager.getInventoryIDs();
        p.sendMessage("Showing list of inventories");
        p.sendMessage(StringUtils.join(idlist.toArray(), ", "));
        return true;
    }

    private boolean edit(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        String invID = args[0];
        Inventory inv = invManager.getInventory(invID);
        if (inv != null) {
            p.openInventory(inv);
            p.sendMessage("Editing inventory:" + invID);
        } else {
            p.sendMessage("The inventory does not exist");
        }
        return true;
    }

    private boolean remove(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        String invID = args[0];
        invManager.removeInventory(invID);
        p.sendMessage("Removed inventory:" + invID);
        return true;
    }

    @Override
    public String getCommandName() {
        return "inventory";
    }
}
