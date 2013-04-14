/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest.Manager;

import java.util.Map;
import net.ja731j.TreasureChest.TreasureChestMain;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 *
 * @author
 * ja731j
 */
public abstract class Manager{
   protected TreasureChestMain plugin;
   
   public Manager(TreasureChestMain plugin){
       this.plugin=plugin;
   }
    
   public abstract Object save();
   
   public abstract void load(Object obj);
}
