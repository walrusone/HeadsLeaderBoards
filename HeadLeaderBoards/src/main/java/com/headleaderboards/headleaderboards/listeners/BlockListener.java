package com.headleaderboards.headleaderboards.listeners;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.material.Sign;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;

public class BlockListener implements Listener {
	
    @EventHandler
    public void signPlaced(SignChangeEvent event) {
        String[] a= event.getLines();
        if (a[0].equals("[hlb]")) {
        	if (event.getPlayer().hasPermission("hlb.signs")) {
                Location signLocation = event.getBlock().getLocation();
                World w = signLocation.getWorld();
            	Block b = w.getBlockAt(signLocation);
            	Sign sign = (Sign) b.getState().getData();
            	BlockFace directionFacing = sign.getFacing();
            	if(b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST){
            	   String hlbname = event.getLine(1);
             	   String signnumber = event.getLine(2);
             	   Boolean exists = false;
                	   List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
                	   for (int i = 0; i < lbs.size(); i++) {
                		   if (lbs.get(i).equalsIgnoreCase(hlbname)) {
                			   exists = true;
                		   }
                	   }
                   	   if (exists == true) {
                           HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".signs." + signnumber + ".world", w.getName());
                           HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".signs." + signnumber + ".x", b.getX());
                           HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".signs." + signnumber + ".y", b.getY());
                           HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".signs." + signnumber + ".z", b.getZ());
                           HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".signs." + signnumber + ".facing", directionFacing.name());
                           HeadLeaderBoards.get().fileClass.saveCustomConfig();
                    	   } else {
                       		event.getPlayer().sendMessage(ChatColor.RED + "ERROR: That is not a valid LeaderBoard Name!");
                        		event.getPlayer().sendMessage(ChatColor.RED + "ERROR: Create the LeaderBoard before Placing Signs");
                        		}
                   	   }
            	} else {
            		event.getPlayer().sendMessage(ChatColor.RED + "YOU DO NOT HAVE PERMISSION TO CREATE HLB SIGNS");
        			event.setCancelled(true);
        	} 
        }
    }
    
    @EventHandler
    public void signRemoved(BlockBreakEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        World w = blockLocation.getWorld();
    	Block b = w.getBlockAt(blockLocation);
		if(b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST){
    		int x = b.getX();
    		int y = b.getY();
    		int z = b.getZ();
    		List<String> lbs = HeadLeaderBoards.get().getConfig().getStringList("leaderboards");
        	for (int i = 0; i < lbs.size(); i++) {
        		String leaderboard = lbs.get(i);
            	for (int j = 0; j < 21; j++) {
            		String t = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".signs." + j + ".world");
        	          if (t != null) {
                        	World w2 = HeadLeaderBoards.get().getServer().getWorld(HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".signs." + j + ".world"));
                        	int x2 = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".x");
                        	int y2 = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".y");
                        	int z2 = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".z");
                              	if ((w == w2 && x == x2 && y == y2 && z == z2)) {
                        		if (event.getPlayer().hasPermission("hlb.signs")) {
                                    HeadLeaderBoards.get().fileClass.getCustomConfig().set(leaderboard + ".signs." + j, null);
                                    HeadLeaderBoards.get().fileClass.saveCustomConfig();
                        		} else {
                            		event.getPlayer().sendMessage(ChatColor.RED + "YOU DO NOT HAVE PERMISSION TO DESTROY HLB SIGNS");
                        			event.setCancelled(true);
                        		}
                        	}
        	          }
            	}

        	}
    	}
    }
}
