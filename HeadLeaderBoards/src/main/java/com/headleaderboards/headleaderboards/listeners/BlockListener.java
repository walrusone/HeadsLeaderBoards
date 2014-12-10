package com.headleaderboards.headleaderboards.listeners;

import java.util.ArrayList;

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
import com.headleaderboards.headleaderboards.LeaderBoard;
import com.headleaderboards.headleaderboards.LeaderController;

public class BlockListener implements Listener {
	
    @EventHandler
    public void signPlaced(SignChangeEvent event) {
        String[] a = event.getLines();
        String line0 = ChatColor.stripColor(a[0]);
        if (line0.equals("[hlb]")) {
        	if (event.getPlayer().hasPermission("hlb.signs")) {
                Location signLocation = event.getBlock().getLocation();
                World w = signLocation.getWorld();
            	Block b = w.getBlockAt(signLocation);
            	Sign sign = (Sign) b.getState().getData();
            	BlockFace directionFacing = sign.getFacing();
            	if(b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST){
            	   String hlbname = ChatColor.stripColor(a[1]);
             	   String signnumber = ChatColor.stripColor(a[2]);
             	   if (isInteger(signnumber)) {
                	   LeaderController lc = HeadLeaderBoards.getLC();
                 	   if (lc.leaderBoardExists(hlbname)) {
                 		   LeaderBoard lb = lc.getLeaderBoard(hlbname);
                 		   lb.addSign(Integer.valueOf(signnumber), w.getName(), b.getX(), b.getY(), b.getZ(), directionFacing.name());
                 	   } else {
                 		   event.getPlayer().sendMessage(ChatColor.RED + "ERROR: That is not a valid LeaderBoard Name!");
                           event.getPlayer().sendMessage(ChatColor.RED + "ERROR: Create the LeaderBoard before Placing Signs");
               			   event.setCancelled(true);
                       } 
             	   } else {
             		   event.getPlayer().sendMessage(ChatColor.RED + "ERROR: Postion Number must be a Integer!");
             		   event.setCancelled(true);
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
    	Sign sign = (Sign) b.getState().getData();
    	BlockFace directionFacing = sign.getFacing();
    	String f = directionFacing.name();
		if(b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST){
    		int x = b.getX();
    		int y = b.getY();
    		int z = b.getZ();
    		LeaderController lc = HeadLeaderBoards.getLC();
    		ArrayList<String> lbs = lc.getNames();
    		for (String name : lbs) {
               	LeaderBoard lb = lc.getLeaderBoard(name);
               	if (lb.containsSign(w.getName(), x, y, z, f)) {
               		if (event.getPlayer().hasPermission("hlb.signs")) {
                   		lb.removeSign(w.getName(), x, y, z, f);
               		} else {
                		event.getPlayer().sendMessage(ChatColor.RED + "YOU DO NOT HAVE PERMISSION TO DESTROY HLB SIGNS");
            			event.setCancelled(true);
               		}

               	}
               	
           	}
		}
    }
    
    private static boolean isInteger(String str)  
    {  
      try  
      {  
        int d = Integer.parseInt(str); 
        if (d == 0) {
        }
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
    
}
