/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.ja731j.TreasureChest.Config;
import net.ja731j.TreasureChest.Managers.ConfigManager;
import net.ja731j.TreasureChest.Managers.InventoryManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author
 * ja731j
 */
public class ConfigCommands {

    public boolean execCommand(CommandSender sender, Command cmd, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (args.length < 2) {//needs to be at least 2
            return false;
        }
        if (args[1].equalsIgnoreCase("create")) {
            return create(sender, cmd, args);
        } else if (args[1].equalsIgnoreCase("list")) {
            return list(sender, cmd, args);
        } else if (args[1].equalsIgnoreCase("show")) {
            return show(sender, cmd, args);
        } else if (args[1].equalsIgnoreCase("addSetting")) {
            return addSetting(sender, cmd, args);
        } else if (args[1].equalsIgnoreCase("removeSetting")) {
            return removeSetting(sender, cmd, args);
        } else if (args[1].equalsIgnoreCase("remove")) {
            return remove(sender, cmd, args);

        }
        return true;
    }

    private boolean create(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player) sender;
        String id;
        if (args.length == 2) {
            id = ConfigManager.getInstance().createConfig();
        } else {
            id = ConfigManager.getInstance().createConfig(args[2]);
        }
        p.sendMessage("Config created:" + id);
        return true;
    }

    private boolean list(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player) sender;
        List<String> idlist = ConfigManager.getInstance().getConfigIDs();
        p.sendMessage("Showing list of configs");
        p.sendMessage(StringUtils.join(idlist.toArray(), ", "));
        return true;
    }

    private boolean addSetting(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player) sender;
        Short priority = 1;
        if (args.length < 4) {
            return false;
        } else if (args.length > 4) {
            priority = Short.parseShort(args[4]);
        }
        Config cfg = ConfigManager.getInstance().getConfig(args[2]);
        if (cfg == null) {
            return false;
        }
        if (!InventoryManager.getInstance().exists(args[3])) {
            return false;
        }
        String invID = args[3];
        cfg.addInventorySetting(invID, priority);
        p.sendMessage("Added new setting(Inv ID:" + invID + ") to config:" + args[2]);
        return true;
    }

    private boolean remove(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 3) {
            return false;
        }
        Player p = (Player) sender;
        ConfigManager.getInstance().removeConfig(args[2]);
        p.sendMessage("Removed config:" + args[2]);
        return true;
    }

    private boolean show(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player) sender;
        Config cfg = ConfigManager.getInstance().getConfig(args[2]);
        if (cfg == null) {
            return false;
        }
        HashMap<String, Short> settings = cfg.getInventorySettings();
        Iterator<Entry<String, Short>> itr = settings.entrySet().iterator();
        p.sendMessage("Displaying settings for config:" + args[2]);
        while (itr.hasNext()) {
            Entry e = itr.next();
            p.sendMessage(e.getKey() + ":" + e.getValue());
        }
        return true;
    }

    private boolean removeSetting(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player) sender;
        Short priority = 1;
        if (args.length < 4) {
            return false;
        }
        Config cfg = ConfigManager.getInstance().getConfig(args[2]);
        if (cfg == null) {
            p.sendMessage("Config "+args[2]+" does not exist!");
            return true;
        }
        if (!InventoryManager.getInstance().exists(args[3])) {
            p.sendMessage("Inventory "+args[3]+" does not exist!");
            return true;
        }
        String invID = args[3];
        cfg.removeInventorySetting(invID);
        p.sendMessage("Removed setting(Inv ID:" + invID + ") from config:" + args[2]);
        return true;
    }
}
