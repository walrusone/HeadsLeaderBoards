package com.headleaderboards.headleaderboards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

public class SignUpdater {
	
    public void signUpdater(Connection conn) {
        try {
        		List<String> lbs = HeadLeaderBoards.get().getConfig().getStringList("leaderboards");
            	for (int i = 0; i < lbs.size(); i++) {
            		String leaderboard = lbs.get(i);
                		Boolean enabled = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".enabled");
                		String table = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".table");
                		String statname = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".statName");
                		String statdisplay = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".statDisplay");
                		int statcolumn = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".statColumn");
                		int uuidcolumn = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".nameColumn");
                		if (enabled == true) {
                			try {
                			PreparedStatement st = conn.prepareStatement("select * from " + table + " order by " + statname);
                	        ResultSet rs=st.executeQuery();
                	        rs.afterLast();
                    	           int j = 0;
                    	           while (rs.previous()) 
                	    	        { 
                	    	          j++;
                	    	          String t = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".signs." + j + ".world");
                	    	          if (t != null) {
                	                    	World w = HeadLeaderBoards.get().getServer().getWorld(HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".signs." + j + ".world"));
                	                    	int x = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".x");
                	                    	int y = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".y");
                	                    	int z = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".z");
                	                    	String facing = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".signs." + j + ".facing");
                	                    	Block b = w.getBlockAt(x, y, z);
                	                        if(b.getType() == Material.WALL_SIGN){
                	                          String name = rs.getString(uuidcolumn); // read 1st column as text
                	  		    	          int stat = rs.getInt(statcolumn); // read 3rd column as int 
                			    	          Sign s  = (Sign) b.getState();
                			    	          s.setLine(0, ChatColor.BOLD + "" + ChatColor.BLACK + Integer.toString(j));                          
                			    	          s.setLine(1, ChatColor.DARK_BLUE + name);
                			    	          s.setLine(2, "");
                			    	          s.setLine(3, ChatColor.BOLD + "" + ChatColor.DARK_BLUE + statdisplay + ": " + ChatColor.DARK_GREEN + Integer.toString(stat)); 
                	                          s.update();
                	                          try {       
                	                          	Block h1 = b.getRelative(BlockFace.UP, 1);
                	                          	Block h2 = b.getRelative(BlockFace.UP, 1);
                	                          	if (facing.equalsIgnoreCase("east")) {
                    	                          	h2 = b.getRelative(-1, 1, 0);	
                	                          	}
                	                          	if (facing.equalsIgnoreCase("west")) {
                    	                          	h2 = b.getRelative(1, 1, 0);	
                	                          	}
                	                          	if (facing.equalsIgnoreCase("south")) {
                    	                          	h2 = b.getRelative(0, 1, -1);	
                	                          	}
                	                          	if (facing.equalsIgnoreCase("north")) {
                    	                          	h2 = b.getRelative(0, 1, 1);	
                	                          	}
                	                          	if(h1.getType() == Material.SKULL) {
                	                            	Skull skull = (Skull) h1.getState();
                	                          	    skull.setSkullType(SkullType.PLAYER); 
                	                          	    skull.setOwner(name);
                	                          	    skull.update();
                	                            }
                	                            if(h2.getType() == Material.SKULL) {
                	                          	    Skull skull = (Skull) h2.getState(); 
                	                          	    skull.setSkullType(SkullType.PLAYER);
                	                          	    skull.setOwner(name);
                	                          	    skull.update();
                	                            }
                	                          } catch (NullPointerException e) {
                	                          }
                	                        }
                                      if (i == 20) { 
                                    	  break;
                                      }
                	    	        }
                	        }
                    	    rs.close();
                    	}   catch (SQLException e) {
                	        // TODO Auto-generated catch block
                	        e.printStackTrace();
                            }
                		}
        	}
        } catch (NullPointerException e) {
        }
    }
}
