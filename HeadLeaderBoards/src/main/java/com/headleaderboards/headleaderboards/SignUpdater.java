package com.headleaderboards.headleaderboards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

import com.google.common.collect.Maps;

public class SignUpdater {
	
	private Map<Integer, String> playerNames = Maps.newHashMap();
	
    public void signUpdater() {
        try {
        		List<String> lbs = HeadLeaderBoards.get().getConfig().getStringList("leaderboards");
            	for (int i = 0; i < lbs.size(); i++) {
            		String leaderboard = lbs.get(i);
                		Boolean enabled = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".enabled");
                		Boolean separateNameTable = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".separateNameTable");
                		String nameTable = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".nameTable");
                		String table = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".table");
                		String statname = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".statName");
                		String statdisplay = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".statDisplay");
                		String uuidname = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".nameColumn");
                		int size = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".hlbSize");
                		Boolean order = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".reverseOrder");
        	        	Boolean pluginenabled = HeadLeaderBoards.get().getConfig().getBoolean("headsleaderboards.enabled");
    	        		String hostname = HeadLeaderBoards.get().getConfig().getString("database.hostname");
    	        		String port = HeadLeaderBoards.get().getConfig().getString("database.port");
    	        		String database = HeadLeaderBoards.get().getConfig().getString("database.database");
    	        		String username = HeadLeaderBoards.get().getConfig().getString("database.username");
    	        		String password = HeadLeaderBoards.get().getConfig().getString("database.password");
    	        		Connection conn = null;
                		String orderMethod = "DESC";
                		if (enabled == true) {
                			try {
                		    if (order == true) {
                		    	orderMethod = "ASC";
                		    }
                		    if (pluginenabled == true) {
           	            		conn = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, username, password);
                	        }
                			if (separateNameTable == true) {
                    		    PreparedStatement names = conn.prepareStatement("select " + "player_id" + ", " + "name" + " from " + nameTable + " order by " + "player_id");
                    	        ResultSet namess=names.executeQuery();
                 	            while (namess.next()) 
             	    	        { 
                 	            	String name = namess.getString(2); // read 1st column as text
      	                            int playerID = namess.getInt(1); // read 3rd column as int
      	                            playerNames.put(playerID, name);
             	    	        }
                			}
                		    PreparedStatement st = conn.prepareStatement("select " + uuidname + ", " + statname + " from " + table + " order by " + statname + " " + orderMethod + " LIMIT " + size);
                	        ResultSet rs=st.executeQuery();
                    	           int j = 0;
                    	           while (rs.next()) 
                	    	        { 
                	    	          j++;
                	    	          String t = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".signs." + j + ".world");
                	    	          if (t != null) {
                	                    	World w = HeadLeaderBoards.get().getServer().getWorld(HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".signs." + j + ".world"));
                	                    	int x = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".x");
                	                    	int y = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".y");
                	                    	int z = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".z");
                	                    	String facing = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".signs." + j + ".facing");
                	                    	String line0Color = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".line0Color");
                	                    	String line1Color = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".line1Color");
                	                    	String line2Color = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".line2Color");
                	                    	String line3Color = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".line3Color");
                	                    	Block b = w.getBlockAt(x, y, z);
                	                    	String name = "";
                	                        if(b.getType() == Material.WALL_SIGN){
                	                          if (separateNameTable == true) {
                	                        	  int playerID = rs.getInt(1);
                	                        	  name = playerNames.get(playerID);
                	                          } else {
                	                        	  name = rs.getString(1); // read 1st column as text
                	                          }
                	                          int stat = rs.getInt(2); // read 3rd column as int
                			    	          Sign s  = (Sign) b.getState();
                			    	          try {
                			    	        	  s.setLine(0, ChatColor.getByChar(line0Color) + "" + ChatColor.BOLD + Integer.toString(j));
                    			    	          s.setLine(1, ChatColor.getByChar(line1Color) + name);
                    			    	          s.setLine(2, ChatColor.getByChar(line2Color) + "" + ChatColor.BOLD +  statdisplay);
                    			    	          s.setLine(3, ChatColor.getByChar(line3Color) + "" + ChatColor.BOLD +  String.valueOf(stat)); 
                			    	          } catch (IllegalArgumentException iae) {  
                			    	          }
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
                	    	        }
                	        }
                    	    rs.close();
                    	    conn.close();
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
