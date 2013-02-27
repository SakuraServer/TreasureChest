/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;

/**
 *
 * @author ja731j
 */
public class Utils {

    static final public BlockFace[] touchingBlockFaces = {BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

    static public ArrayList<Block> getTouchingBlocks(Block block) {
        ArrayList<Block> list = new ArrayList<Block>();
        for (BlockFace blockface : touchingBlockFaces) {
            list.add(block.getRelative(blockface));
        }
        return list;
    }

    static public ArrayList<Sign> getTouchingSigns(Block block) {
        ArrayList<Sign> list = new ArrayList<Sign>();
        for (Block touchingBlock : Utils.getTouchingBlocks(block)) {
            BlockState state = touchingBlock.getState();
            if (state instanceof Sign) {
                list.add((Sign) state);
            }
        }
        return list;
    }

    static public ArrayList<Chest> getTouchingChests(Block block) {
        ArrayList<Chest> list = new ArrayList<Chest>();
        for (Block touchingBlock : Utils.getTouchingBlocks(block)) {
            BlockState state = touchingBlock.getState();
            if (state instanceof Chest) {
                list.add((Chest) state);
            }
        }
        return list;
    }
    
    static public Map<String,Object> serializeLocation(Location loc){
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("World", loc.getWorld().getName());
        result.put("X", Double.toString(loc.getX()));
        result.put("Y", Double.toString(loc.getY()));
        result.put("Z", Double.toString(loc.getZ()));
        return result;
    }
    
    static public Location deserializeLocation(Map<String,Object> map){
        String worldName = (String)map.get("World");
        World w = Bukkit.getWorld(worldName);
        double x = Double.parseDouble((String)map.get("X"));
        double y = Double.parseDouble((String)map.get("Y"));
        double z = Double.parseDouble((String)map.get("Z"));
        return new Location(w, x, y, z);
    }
}
