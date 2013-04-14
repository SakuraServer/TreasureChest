package net.ja731j.TreasureChest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import net.ja731j.TreasureChest.Command.AbstractCommandExecutor;
import net.ja731j.TreasureChest.Command.ConfigCommandExecutor;
import net.ja731j.TreasureChest.Command.InventoryCommandExecutor;
import net.ja731j.TreasureChest.Listener.AbstractListener;
import net.ja731j.TreasureChest.Listener.ChestListener;
import net.ja731j.TreasureChest.Listener.SignListener;
import net.ja731j.TreasureChest.Manager.ConfigManager;
import net.ja731j.TreasureChest.Manager.InventoryManager;
import net.ja731j.TreasureChest.Manager.Manager;
import net.ja731j.TreasureChest.Manager.TimeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class TreasureChestMain extends JavaPlugin {
    
    private final ArrayList<AbstractListener> listeners;
    private final HashMap<String, AbstractCommandExecutor> executorMap;
    private HashMap<String, Manager> managerMap;
    
    public TreasureChestMain() {
        //register managers
        ArrayList<Manager> managers = new ArrayList<Manager>(Arrays.asList(new Manager[]{
                    new ConfigManager(this),
                    new InventoryManager(this),
                    new TimeManager(this)}));
        
        managerMap = new HashMap<String, Manager>();
        for (Manager manager : managers) {
            managerMap.put(manager.getClass().getName().toLowerCase(), manager);
        }

        //register listeners
        listeners = new ArrayList<AbstractListener>(Arrays.asList(new AbstractListener[]{
                    new ChestListener(this),
                    new SignListener(this)}));

        //register command executors
        ArrayList<AbstractCommandExecutor> executors = new ArrayList<AbstractCommandExecutor>(Arrays.asList(new AbstractCommandExecutor[]{
                    new ConfigCommandExecutor(this),
                    new InventoryCommandExecutor(this),}));
        
        executorMap = new HashMap<String, AbstractCommandExecutor>();
        for (AbstractCommandExecutor executor : executors) {
            executorMap.put(executor.getCommandName().toLowerCase(), executor);
        }
        
    }
    
    @Override
    public void onEnable() {
        for (AbstractListener listener : listeners) {
            listener.registerListener();
        }
        load();
        getLogger().info("The TreasureChest plugin has been loaded");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("The TreasureChest plugin has been unloaded");
        save();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("TreasureChest")) {
            if (args.length > 1) {
                String subCmd = args[0].toLowerCase();
                AbstractCommandExecutor executor;
                if ((executor = executorMap.get(subCmd)) != null) {
                    String[] subCmdArgs = Arrays.copyOfRange(args, 1, args.length);
                    return executor.execCmd(sender, subCmdArgs);
                }
            }
        }
        return false;
    }
    
    public <T extends Manager> T getManager(Class<T> managerClass) {
        Collection<Manager> managers = managerMap.values();
        for (Manager m : managers) {
            if (managerClass.isInstance(m)) {
                return managerClass.cast(m);
            }
        }
        return null;
    }
    
    private void load() {
        try {
            File folder = this.getDataFolder();
            File savefile = new File(folder, "save.dat");
            if (savefile.exists()) {
                FileInputStream fis = new FileInputStream(savefile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
                Object configObj = ois.readObject();
                Object inventoryObj = ois.readObject();
                Object timeObj = ois.readObject();
                ois.close();
                bis.close();
                fis.close();
                managerMap.get(ConfigManager.class.getName().toLowerCase()).load(configObj);
                managerMap.get(InventoryManager.class.getName().toLowerCase()).load(inventoryObj);
                managerMap.get(TimeManager.class.getName().toLowerCase()).load(timeObj);
            } else {
                getLogger().info("No save file found");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void save() {
        try {
            File folder = this.getDataFolder();
            if (!folder.exists()) {
                folder.mkdir();
                getLogger().info("Creating plugin folder");
            }
            File savefile = new File(folder, "save.dat");
            if (!savefile.exists()) {
                savefile.createNewFile();
                getLogger().info("Creating new save file");
            }
            FileOutputStream fos = new FileOutputStream(savefile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(managerMap.get(ConfigManager.class.getName().toLowerCase()).save());
            oos.writeObject(managerMap.get(InventoryManager.class.getName().toLowerCase()).save());
            oos.writeObject(managerMap.get(TimeManager.class.getName().toLowerCase()).save());
            oos.close();
            bos.close();
            fos.close();
            getLogger().info("Successfully saved data");
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe("Failed to save");
        }
    }
}
