package com.headleaderboards.headleaderboards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;

import com.headleaderboards.headleaderboards.database.Database;

public class HeadSQL {

	ArrayList<String> namelist;
	ArrayList<Integer> statlist;
	String nameTable;
	String sepNameCol;
	String sepIdCol;
	String worldCol;
	String worldName;
	String customCol;
	String rowValues;
	String customCol2;
	String rowValues2;
	String startText;
	String worldText;
	String customColText;
	String customColText2;
	String endText;
	String middleText;
	
	public void dataQuery() {
		List<String> lbs = HeadLeaderBoards.get().getConfig().getStringList("leaderboards");
    	for (int i = 0; i < lbs.size(); i++) {
        	HeadLeaderBoards.get();
			Database db = HeadLeaderBoards.getDB();
    		namelist = new ArrayList<String>();
        	statlist = new ArrayList<Integer>();
    		String leaderboard = lbs.get(i);
    		Boolean enabled = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".enabled");
    		Boolean debugMode = HeadLeaderBoards.get().getConfig().getBoolean("headsleaderboards.debugMode");
        	Boolean separateNameTable = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".separateNameTable.enabled");
        	if (separateNameTable) {
            	nameTable = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".separateNameTable.nameTable");
            	sepNameCol = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".separateNameTable.sepNameCol");
            	sepIdCol = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".separateNameTable.sepIdCol");
        	}
        	Boolean sortByWorld = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".sortByWorld.enabled");
        	if (sortByWorld) {
            	worldCol = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".sortByWorld.worldCol");
            	worldName = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".sortByWorld.worldName");
        	}
        	Boolean customColumn = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".customColumn.enabled");
        	if (customColumn) {
        		customCol = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".customColumn.customCol");
        		rowValues = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".customColumn.rowValues");
        	}
        	Boolean customColumn2 = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".customColumn2.enabled");
        	if (customColumn2) {
        		customCol2 = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".customColumn2.customCol");
        		rowValues2 = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".customColumn2.rowValues");
        	}
        	String statTable = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".statTable");
        	String statName = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".statName");
        	String nameColumn = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".nameColumn");
        	int size = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".hlbSize");
	        Boolean pluginenabled = HeadLeaderBoards.get().getConfig().getBoolean("headsleaderboards.enabled");
	        String orderMethod = "DESC";
        	Connection conn = null;
        	PreparedStatement st = null;
        	if (enabled && pluginenabled) {
        		try {
            	    if (db.checkConnection()) {
            	    	conn = db.getConnection();
            	    }
            	    if (HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(leaderboard + ".reverseOrder")) {
            	    	orderMethod = "ASC";
            	    }
            	    if (separateNameTable) {
            	    	startText = "select ps." + sepNameCol + ", SUM(ks." + statName + ") AS value FROM " + nameTable + " ps INNER"
            	    			+ " JOIN " + statTable + " ks ON ps." + sepIdCol + " = ks." + nameColumn;
            	    	endText = " GROUP BY ps." + sepNameCol + " ORDER BY "
            	    			+ "SUM(ks." + statName + ") " + orderMethod + " LIMIT " + size;
                	    if (sortByWorld) {
                	    	worldText = "ks." + worldCol + "='" + worldName + "'";
                	    } else {
                	    	worldText = "";
                	    }
                	    if (customColumn) {
                	    	List<String> values = Arrays.asList(rowValues.split("\\s*,\\s*"));
                	    	customColText = "(ks." + customCol + "='" + values.get(0) + "'";
                	    	for (int j = 1; j < values.size(); j++) {
                	    		customColText = customColText + " OR ks." + customCol + "='" + values.get(j) + "'";
                	    	}
                	    	customColText = customColText + ")";
                	    } else {
                	    	customColText = "";
                	    }
                	    if (customColumn2) {
                	    	List<String> values = Arrays.asList(rowValues2.split("\\s*,\\s*"));
                	    	customColText2 = "(ks." + customCol2 + "='" + values.get(0) + "'";
                	    	for (int j = 1; j < values.size(); j++) {
                	    		customColText2 = customColText2 + " OR ks." + customCol2 + "='" + values.get(j) + "'";
                	    	}
                	    	customColText2 = customColText2 + ")";
                	    } else {
                	    	customColText2 = "";
                	    }

            	    } else {
            	    	startText = "select " + nameColumn + ", SUM(" + statName + ") AS value FROM " + statTable;
            	    	endText = " GROUP BY " + nameColumn + " ORDER BY SUM(" 
            	    			+ statName + ") " + orderMethod + " LIMIT " + size;
                	    if (sortByWorld) {
                	    	worldText = worldCol + "='" + worldName + "'";
                	    } else {
                	    	worldText = "";
                	    }
                	    if (customColumn) {
                	    	List<String> values = Arrays.asList(rowValues.split("\\s*,\\s*"));
                	    	customColText = "(" + customCol + "='" + values.get(0) + "'";
                	    	for (int j = 1; j < values.size(); j++) {
                	    		customColText = customColText + " OR " + customCol + "='" + values.get(j) + "'";
                	    	}
                	    	customColText = customColText + ")";
                	    } else {
                	    	customColText = "";
                	    }
                	    if (customColumn2) {
                	    	List<String> values = Arrays.asList(rowValues2.split("\\s*,\\s*"));
                	    	customColText2 = "(" + customCol2 + "='" + values.get(0) + "'";
                	    	for (int j = 1; j < values.size(); j++) {
                	    		customColText2 = customColText2 + " OR " + customCol2 + "='" + values.get(j) + "'";
                	    	}
                	    	customColText2 = customColText2 + ")";
                	    } else {
                	    	customColText2 = "";
                	    }
            	    }
            	    if (sortByWorld && customColumn && customColumn2) {
            	    	middleText = " WHERE " + worldText + " AND " + customColText + " AND " + customColText2;
            	    } else if ((sortByWorld && customColumn && !customColumn2) || (sortByWorld && !customColumn && customColumn2) ) {
            	    	middleText = " WHERE " + worldText + " AND " + customColText + customColText2;
            	    } else if (!sortByWorld && customColumn && customColumn2) {
            	    	middleText = " WHERE " + customColText + " AND " + customColText2;
            	    } else if ((!sortByWorld && !customColumn && customColumn2) || (!sortByWorld && customColumn && !customColumn2) || (sortByWorld && !customColumn && !customColumn2)) {
            	    	middleText = " WHERE " + worldText + customColText + customColText2;
            	    } else if (!sortByWorld && !customColumn && !customColumn2) {
            	    	middleText = "";
            	    }
            	    if (debugMode) {
            	    	HeadLeaderBoards.get().getLogger().info(startText + middleText + endText);
            	    }
        	    	st = conn.prepareStatement(startText + middleText + endText);  	    
        	    	ResultSet rs=st.executeQuery();
        	    	while (rs.next()) { 
       	        	  String name = "";
                   	  name = rs.getString(1); // read 1st column as text
        	          int stat = rs.getInt(2); // read 2nd column as int
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
