/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ja731j.TreasureChest;

import java.util.ArrayList;
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
    
    
}
