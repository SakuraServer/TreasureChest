/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.ja731j.TreasureChest.Config;
import net.ja731j.TreasureChest.Manager.ConfigManager;
import net.ja731j.TreasureChest.Manager.InventoryManager;
import net.ja731j.TreasureChest.TreasureChestMain;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author
 * ja731j
 */
public class ConfigCommandExecutor extends AbstractCommandExecutor {

    ConfigManager cfgManager = plugin.getManager(ConfigManager.class);
    InventoryManager invManager = plugin.getManager(InventoryManager.class);

    public ConfigCommandExecutor(TreasureChestMain plugin) {
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
        } else if (cmd.equalsIgnoreCase("show")) {
            return show(sender, subCmdArgs);
        } else if (cmd.equalsIgnoreCase("addSetting")) {
            return addSetting(sender, subCmdArgs);
        } else if (cmd.equalsIgnoreCase("enable")) {
            return enable(sender, subCmdArgs);
        } else if (cmd.equalsIgnoreCase("reset")) {
            return reset(sender, subCmdArgs);
        } else if (cmd.equalsIgnoreCase("removeSetting")) {
            return removeSetting(sender, subCmdArgs);
        } else if (cmd.equalsIgnoreCase("remove")) {
            return remove(sender, subCmdArgs);
        }
        return true;
    }

    private boolean create(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        String reqID;
        String id;
        if (args.length < 1) {
            id = cfgManager.createConfig();
        } else {
            reqID = args[0];
            id = cfgManager.createConfig(reqID);
        }
        p.sendMessage("Config created:" + id);
        return true;
    }

    private boolean list(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        List<String> idlist = cfgManager.getConfigIDs();
        p.sendMessage("Showing list of configs");
        p.sendMessage(StringUtils.join(idlist.toArray(), ", "));
        return true;
    }

    private boolean addSetting(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        Short priority = 1;
        String invID;
        String cfgID;
        if (args.length < 2) {
            return false;
        } else if (args.length > 2) {
            priority = Short.parseShort(args[2]);
        }
        invID = args[1];
        cfgID = args[0];
        Config cfg = cfgManager.getConfig(cfgID);
        if (cfg == null) {
            return false;
        }
        if (!invManager.exists(invID)) {
            return false;
        }
        cfg.addInventorySetting(invID, priority);
        p.sendMessage("Added new setting(Inv ID:" + invID + ") to config:" + cfgID);
        return true;
    }

    private boolean remove(CommandSender sender, String[] args) {
        if (args.length < 1) {
            return false;
        }
        Player p = (Player) sender;
        cfgManager.removeConfig(args[0]);
        p.sendMessage("Removed config:" + args[0]);
        return true;
    }

    private boolean show(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length < 1) {
            return false;
        }
        String cfgID = args[0];
        Config cfg = cfgManager.getConfig(cfgID);
        if (cfg == null) {
            return false;
        }
        HashMap<String, Short> settings = cfg.getInventorySettings();
        Iterator<Entry<String, Short>> itr = settings.entrySet().iterator();
        p.sendMessage("Displaying settings for config:" + cfgID);
        while (itr.hasNext()) {
            Entry e = itr.next();
            p.sendMessage(e.getKey() + ":" + e.getValue());
        }
        return true;
    }

    private boolean removeSetting(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length < 2) {
            return false;
        }
        String cfgID = args[0];
        String invID = args[1];
        Config cfg = cfgManager.getConfig(cfgID);
        if (cfg == null) {
            p.sendMessage("Config " + cfgID + " does not exist!");
            return true;
        }
        if (!invManager.exists(invID)) {
            p.sendMessage("Inventory " + invID + " does not exist!");
            return true;
        }
        cfg.removeInventorySetting(invID);
        p.sendMessage("Removed setting(Inv ID:" + invID + ") from config:" + cfgID);
        return true;
    }

    @Override
    public String getCommandName() {
        return "config";
    }

    private boolean enable(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return false;
        }
        String cfgID = args[0];
        String enable = args[1];

        Config cfg = cfgManager.getConfig(cfgID);
        if (cfg == null) {
            sender.sendMessage("Config " + cfgID + " does not exist!");
            return true;
        }

        if (enable.equalsIgnoreCase("true")) {
            cfg.setEnable(true);
            sender.sendMessage("Config " + cfgID + " is enabled!");
        } else {
            cfg.setEnable(true);
            sender.sendMessage("Config " + cfgID + " is disabled!");
        }
        
        return true;
    }

    private boolean reset(CommandSender sender, String[] args) {
                if (args.length < 2) {
            return false;
        }
        String cfgID = args[0];
        String willreset = args[1];

        Config cfg = cfgManager.getConfig(cfgID);
        if (cfg == null) {
            sender.sendMessage("Config " + cfgID + " does not exist!");
            return true;
        }

        if (willreset.equalsIgnoreCase("false")) {
            cfg.setEnable(true);
            sender.sendMessage("Config " + cfgID + " will reset its contents when opened!");
        } else {
            cfg.setEnable(true);
            sender.sendMessage("Config " + cfgID + " will not reset its contents when opened!");
        }
        
        return true;
    }
}
