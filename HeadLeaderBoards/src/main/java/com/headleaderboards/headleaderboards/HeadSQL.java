package com.headleaderboards.headleaderboards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;

import com.google.common.collect.Maps;

public class HeadSQL {

	private Map<Integer, String> playerNames = Maps.newHashMap();
	ArrayList<String> namelist;
	ArrayList<Integer> statlist;
	
	public void dataQuery() {
		List<String> lbs = HeadLeaderBoards.get().getConfig().getStringList("leaderboards");
    	for (int i = 0; i < lbs.size(); i++) {
        	namelist = new ArrayList<String>();
        	statlist = new ArrayList<Integer>();
    		String leaderboard = lbs.get(i);
    		Boolean enabled = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".enabled");
        	Boolean separateNameTable = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".separateNameTable");
        	String nameTable = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".nameTable");
        	String table = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".table");
        	String statname = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".statName");
        	String uuidname = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".nameColumn");
        	int size = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".hlbSize");
        	Boolean order = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".reverseOrder");
	        Boolean pluginenabled = HeadLeaderBoards.get().getConfig().getBoolean("headsleaderboards.enabled");
        	String hostname = HeadLeaderBoards.get().getConfig().getString("database.hostname");
        	String port = HeadLeaderBoards.get().getConfig().getString("database.port");
        	String database = HeadLeaderBoards.get().getConfig().getString("database.database");
        	String username = HeadLeaderBoards.get().getConfig().getString("database.username");
        	String password = HeadLeaderBoards.get().getConfig().getString("database.password");
        	String nameCol = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".sepnameCol");
        	String sepIdCol = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".sepIdCol");
        	Connection conn = null;
        	String orderMethod = "DESC";
        	if (enabled && pluginenabled) {
        		try {
        	    if (order) {
        	    	orderMethod = "ASC";
        	    }
        	    if (pluginenabled) {
   	           		conn = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, username, password);
        	    }
        		if (separateNameTable == true) {
            	    playerNames = Maps.newHashMap();
        			PreparedStatement names = conn.prepareStatement("select " + sepIdCol + ", " + nameCol + " from " + nameTable + " order by " + sepIdCol);
                    ResultSet namess=names.executeQuery();
         	        while (namess.next()) { 
         	            	String name = namess.getString(2); // read 1st column as text
	                            int playerID = namess.getInt(1); // read 3rd column as int
	                            playerNames.put(playerID, name);
     	    	    }
        		}
        		PreparedStatement st = conn.prepareStatement("select " + uuidname + ", " + statname + " from " + table + " order by " + statname + " " + orderMethod + " LIMIT " + size);
        	    ResultSet rs=st.executeQuery();
       	           while (rs.next()) { 
       	        	  String name = "";
                      if (separateNameTable == true) {
                     	  int playerID = rs.getInt(1);
                    	  name = playerNames.get(playerID);
                      } else {
                    	  name = rs.getString(1); // read 1st column as text
                      }
        	          int stat = rs.getInt(2); // read 3rd column as int
        	          namelist.add(name);
        	          statlist.add(stat);
   	    	        }
            	    rs.close();
            	    conn.close();
            	}   catch (SQLException e) {
        	        // TODO Auto-generated catch block
        	        e.printStackTrace();
                    }
            	Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SignUpdater(namelist, statlist, leaderboard));
        		}
    	}
	}
}
