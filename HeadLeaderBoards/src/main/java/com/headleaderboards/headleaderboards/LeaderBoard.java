package com.headleaderboards.headleaderboards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

import com.google.common.collect.Maps;
import com.headleaderboards.headleaderboards.database.Database;

public class LeaderBoard {

	private String startText;
	private String worldText;
	private String customColText;
	private String customColText2;
	private String endText;
	private String middleText;
	private Boolean enabled = false;
	private Boolean sntEnabled = false;
	private String nameTable = "stats_players";
	private String sepNameCol = "name";
	private String sepIdCol = "player_id";
	private Boolean sbWorld = false; 
	private String worldCol = "world";
	private String worldName = "world";
	private Boolean cColumn1 = false;
	private String customCol1 = "customCol";
	private String rowValues1 = "rowValues";
	private Boolean cColumn2 = false;
	private String customCol2 = "customCol";
	private String rowValues2 = "rowValues";
	private String statTable = "";
	private String statName = "";
	private String statDisplay = "";
	private String nameColumn = "";
	private int hlbSize = 5;
	private Boolean reverseOrder = false;
	private Boolean statOnSameLine = false;
	private String line0Format = "black, bold, header";
	private String line1Format = "dark blue, normal, name";
	private String line2Format = "dark red, bold, statdisplay";
	private String line3Format = "dark purple, bold, stat";
	private String hlbname;
	private final Map<Integer, Signs> signs = Maps.newHashMap();
	private ArrayList<String> namelist;
	private ArrayList<Integer> statlist;
	private List<String> headerList;
	
	public LeaderBoard(String name) {
		hlbname = name;
		updateLeader();
		loadLeader();
		saveLeader();
	}
	
	public void loadLeader() {
		enabled = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(hlbname + ".enabled");
    	sntEnabled = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(hlbname + ".separateNameTable.enabled");
    	nameTable = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".separateNameTable.nameTable");
        sepNameCol = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".separateNameTable.sepNameCol");
        sepIdCol = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".separateNameTable.sepIdCol");
    	sbWorld = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(hlbname + ".sortByWorld.enabled");
       	worldCol = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".sortByWorld.worldCol");
       	worldName = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".sortByWorld.worldName");
       	cColumn1 = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(hlbname + ".customColumn.enabled");
   		customCol1 = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".customColumn.customCol");
   		rowValues1 = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".customColumn.rowValues");
   		cColumn2 = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(hlbname + ".customColumn2.enabled");
   		customCol2 = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".customColumn2.customCol");
    	rowValues2 = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".customColumn2.rowValues");
    	statTable = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".statTable");
    	statName = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".statName");
    	statDisplay = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".statDisplay");
    	nameColumn = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".nameColumn");
    	hlbSize = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(hlbname + ".hlbSize");
    	reverseOrder = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(hlbname + ".reverseOrder");
    	statOnSameLine = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(hlbname + ".statOnSameLine");
    	line0Format = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".line0Format");
    	line1Format = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".line1Format");
    	line2Format = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".line2Format");
    	line3Format = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".line3Format");
    	for (int i = 1; i <= hlbSize; i++) {
    		String t = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".signs." + i + ".world");
	          	if (t != null) {
	        		String w = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".signs." + i + ".world");
	             	int x = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(hlbname + ".signs." + i + ".x");
	             	int y = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(hlbname + ".signs." + i + ".y");
	             	int z = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(hlbname + ".signs." + i + ".z");
	             	String facing = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".signs." + i + ".facing");
	             	signs.put(i, new Signs(w, x, y, z, facing));
	          	}
    	}
		headerList = HeadLeaderBoards.get().getConfig().getStringList("header");
	}
	
	private void updateLeader() {
		String test = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".enabled");
		String test2 = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".separateNameTable");
		String test3 = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".line0Color");
		if (test == null) {
			saveLeader();
		} else if (test2.equalsIgnoreCase("true") || test2.equalsIgnoreCase("false")) {
			sntEnabled = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(hlbname + ".separateNameTable");
			nameTable = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".nameTable");
			sepNameCol = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".sepnameCol");
			sepIdCol = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".sepIdCol");
			statTable = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + ".table");
			HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".table", null);
			HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".statTable", statTable);
			HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".separateNameTable", null);
			HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".separateNameTable.enabled", sntEnabled);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".separateNameTable.nameTable", nameTable);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".separateNameTable.sepNameCol", sepNameCol);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".separateNameTable.sepIdCol", sepIdCol);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".sortByWorld.enabled", sbWorld);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".sortByWorld.worldCol", worldCol);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".sortByWorld.worldName", worldName);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn.enabled", cColumn1);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn.customCol", customCol1);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn.rowValues", rowValues1);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn2.enabled", cColumn2);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn2.customCol", customCol2);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn2.rowValues", rowValues2);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line0Color", null);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line1Color", null);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line2Color", null);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line3Color", null);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".statOnSameLine", statOnSameLine);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line0Format", line0Format);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line1Format", line1Format);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line2Format", line2Format);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line3Format", line3Format);
	    	HeadLeaderBoards.get().fileClass.saveCustomConfig();
		} else if (test3 != null) {
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line0Color", null);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line1Color", null);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line2Color", null);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line3Color", null);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".statOnSameLine", statOnSameLine);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line0Format", line0Format);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line1Format", line1Format);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line2Format", line2Format);
	    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line3Format", line3Format);
	    	HeadLeaderBoards.get().fileClass.saveCustomConfig();
		}
	}
	
	public void saveLeader() {
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname, null);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".enabled", enabled);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".separateNameTable.enabled", sntEnabled);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".separateNameTable.nameTable", nameTable);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".separateNameTable.sepNameCol", sepNameCol);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".separateNameTable.sepIdCol", sepIdCol);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".sortByWorld.enabled", sbWorld);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".sortByWorld.worldCol", worldCol);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".sortByWorld.worldName", worldName);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn.enabled", cColumn1);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn.customCol", customCol1);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn.rowValues", rowValues1);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn2.enabled", cColumn2);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn2.customCol", customCol2);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".customColumn2.rowValues", rowValues2);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".statTable", statTable);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".statName", statName);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".statDisplay", statDisplay);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".nameColumn", nameColumn);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".hlbSize", hlbSize);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".reverseOrder", reverseOrder);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".statOnSameLine", statOnSameLine);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line0Format", line0Format);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line1Format", line1Format);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line2Format", line2Format);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".line3Format", line3Format);
    	for (Integer key : signs.keySet()) {
    		Signs sign = signs.get(key);
    		HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".signs." + key + ".world", sign.world);
    		HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".signs." + key + ".x", sign.x);
    		HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".signs." + key + ".y", sign.y);
    		HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".signs." + key + ".z", sign.z);
    		HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".signs." + key + ".facing", sign.facing);
            HeadLeaderBoards.get().fileClass.saveCustomConfig();
    	}
    	HeadLeaderBoards.get().fileClass.saveCustomConfig();
    	List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
    	if (!lbs.contains(hlbname)) {
        	lbs.add(hlbname);
        	HeadLeaderBoards.get().getConfig().set("leaderboards", lbs);
        	HeadLeaderBoards.get().saveConfig();
    	}
	}
	
	public void deleteLeader() {
		HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname, null);
    	HeadLeaderBoards.get().fileClass.saveCustomConfig();
    	HeadLeaderBoards.get().saveConfig();
    	List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
    	lbs.remove(hlbname);
		HeadLeaderBoards.get().getConfig().set("leaderboards", lbs);
		HeadLeaderBoards.get().saveConfig();	
	}
	
	public void setEnabled() {
		if (enabled) {
			enabled = false;
		} else {
			enabled = true;
		}
		saveLeader();
		
	}
	
	public Boolean getEnabled() {
		if (enabled) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setsntEnabled(Boolean state) {
		sntEnabled = state;
		saveLeader();
	}
	
	public void setnameTable (String x) {
		nameTable = x;
		saveLeader();
	}
	
	public void setsepNameCol (String x) {
		sepNameCol = x;
		saveLeader();
	}
	
	public void setsepIdCol (String x) {
		sepIdCol = x;
		saveLeader();
	}
	
	public void setsbWorld(Boolean state) {
		sbWorld = state;
		saveLeader();
	}
	
	public void setworldCOl (String x) {
		worldCol = x;
		saveLeader();
	}

	public void setworldName (String x) {
		worldName = x;
		saveLeader();
	}
	
	public void setcColumn1(Boolean state) {
		cColumn1 = state;
		saveLeader();
	}

	public void setcustomCol1 (String x) {
		customCol1 = x;
		saveLeader();
	}
	
	public void setrowValues1 (String x) {
		rowValues1 = x;
		saveLeader();
	}
	
	public void setcColumn2(Boolean state) {
		cColumn2 = state;
		saveLeader();
	}

	public void setcustomCol2 (String x) {
		customCol2 = x;
		saveLeader();
	}
	
	public void setrowValues2 (String x) {
		rowValues2 = x;
		saveLeader();
	}

	public void setstatTable (String x) {
		statTable = x;
		saveLeader();
	}
	
	public void setstatName (String x) {
		statName = x;
		saveLeader();
	}
	
	public void setstatDisplay (String x) {
		statDisplay = x;
		saveLeader();
	}
	
	public void setnameColumn (String x) {
		nameColumn = x;
		saveLeader();
	}
	
	public void sethlbSize (int x) {
		hlbSize = x;
		saveLeader();
	}
	
	public void setreverseOrder(Boolean state) {
		reverseOrder = state;
		saveLeader();
	}
	
	public void addSign(int pos, String w, int x, int y, int z, String f) {
		signs.put(pos, new Signs(w, x, y, z, f));
		saveLeader();
	}
	
	public void removeSign(String w, int x, int y, int z, String f) {
		int key2 = 0; 
		for (Integer key : signs.keySet()) {
			Signs sign = signs.get(key);
			if (sign.equals(w, x, y, z, f)) {
				key2 = key;
			}
		}
		signs.remove(key2);
		saveLeader();
	}
	
	public Boolean containsSign(String w, int x, int y, int z, String f) {
		for (Signs sign2 : signs.values()) {
			if (sign2.equals(w, x, y, z, f)) {
				return true;
			}
		}
		return false;
	}
	
	public void dataQuery() {
		namelist = new ArrayList<String>();
		statlist = new ArrayList<Integer>();
		Database db = HeadLeaderBoards.getDB();
		Boolean debugMode = HeadLeaderBoards.get().getConfig().getBoolean("headsleaderboards.debugMode");
	    String orderMethod = "DESC";
        Connection conn = null;
        PreparedStatement st = null;
        if (enabled) {
    		try {
        	    if (db.checkConnection()) {
        	    	conn = db.getConnection();
        	    }
        	    if (reverseOrder) {
        	    	orderMethod = "ASC";
        	    }
        	    if (sntEnabled) {
        	    	startText = "select ps." + sepNameCol + ", SUM(ks." + statName + ") AS value FROM " + nameTable + " ps INNER"
        	    			+ " JOIN " + statTable + " ks ON ps." + sepIdCol + " = ks." + nameColumn;
        	    	endText = " GROUP BY ps." + sepNameCol + " ORDER BY "
        	    			+ "SUM(ks." + statName + ") " + orderMethod + " LIMIT " + hlbSize;
            	    if (sbWorld) {
            	    	worldText = "ks." + worldCol + "='" + worldName + "'";
            	    } else {
            	    	worldText = "";
            	    }
            	    if (cColumn1) {
            	    	List<String> values = Arrays.asList(rowValues1.split("\\s*,\\s*"));
            	    	customColText = "(ks." + customCol1 + "='" + values.get(0) + "'";
            	    	for (int j = 1; j < values.size(); j++) {
            	    		customColText = customColText + " OR ks." + customCol1 + "='" + values.get(j) + "'";
            	    	}
            	    	customColText = customColText + ")";
            	    } else {
            	    	customColText = "";
            	    }
            	    if (cColumn2) {
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
        	    			+ statName + ") " + orderMethod + " LIMIT " + hlbSize;
            	    if (sbWorld) {
            	    	worldText = worldCol + "='" + worldName + "'";
            	    } else {
            	    	worldText = "";
            	    }
            	    if (cColumn1) {
            	    	List<String> values = Arrays.asList(rowValues1.split("\\s*,\\s*"));
            	    	customColText = "(" + customCol1 + "='" + values.get(0) + "'";
            	    	for (int j = 1; j < values.size(); j++) {
            	    		customColText = customColText + " OR " + customCol1 + "='" + values.get(j) + "'";
            	    	}
            	    	customColText = customColText + ")";
            	    } else {
            	    	customColText = "";
            	    }
            	    if (cColumn2) {
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
        	    if (sbWorld && cColumn1 && cColumn2) {
        	    	middleText = " WHERE " + worldText + " AND " + customColText + " AND " + customColText2;
        	    } else if ((sbWorld && cColumn1 && !cColumn2) || (sbWorld && !cColumn1 && cColumn2) ) {
        	    	middleText = " WHERE " + worldText + " AND " + customColText + customColText2;
        	    } else if (!sbWorld && cColumn1 && cColumn2) {
        	    	middleText = " WHERE " + customColText + " AND " + customColText2;
        	    } else if ((!sbWorld && !cColumn1 && cColumn2) || (!sbWorld && cColumn1 && !cColumn2) || (sbWorld && !cColumn1 && !cColumn2)) {
        	    	middleText = " WHERE " + worldText + customColText + customColText2;
        	    } else if (!sbWorld && !cColumn1 && !cColumn2) {
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
        	Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SignUpdater());
    		}
	}
	
	private class SignUpdater implements Callable<Object> {

		String header;
		String name;
		String stat;


		public SignUpdater () {
		}
		
	    private char getColor(String lineNumber) {
	    	String format = (HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + "." + lineNumber)).toLowerCase();
	    	List<String> values = Arrays.asList(format.split("\\s*,\\s*"));
	    	switch (values.get(0)) {
	    	case "black": return '0';
	    	case "dark blue": return '1';
	    	case "dark green": return '2';
	    	case "dark aqua": return '3';
	    	case "dark red": return '4';
	    	case "dark purple": return '5';
	    	case "gold": return '6';
	    	case "gray": return '7';
	    	case "dark gray": return '8';
	    	case "blue": return '9';
	    	case "green": return 'a';
	    	case "aqua": return 'b';
	    	case "red": return 'c';
	    	case "light purple": return 'd';
	    	case "yellow": return 'e';
	    	case "white": return 'f';
	    	case "bold": return 'l';
	    	case "itallic": return 'o';
	    	case "underline": return 'n';
	    	case "0": return '0';
	    	case "1": return '1';
	    	case "2": return '2';
	    	case "3": return '3';
	    	case "4": return '4';
	    	case "5": return '5';
	    	case "6": return '6';
	    	case "7": return '7';
	    	case "8": return '8';
	    	case "9": return '9';
	    	case "a": return 'a';
	    	case "b": return 'b';
	    	case "c": return 'c';
	    	case "d": return 'd';
	    	case "e": return 'e';
	    	case "f": return 'f';
	    	case "l": return 'l';
	    	case "o": return 'o';
	    	case "n": return 'n';
	    	default: return '0';
	       	}
	    }
	    
	    private char getFormat(String lineNumber) {
	    	String format = (HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + "." + lineNumber)).toLowerCase();
	    	List<String> values = Arrays.asList(format.split("\\s*,\\s*"));
	    	switch (values.get(1)) {
	    	case "bold": return 'l';
	    	case "itallic": return 'o';
	    	case "underline": return 'n';
	    	case "magic": return 'k';
	    	case "normal": return getColor(lineNumber);
	    	case "l": return 'l';
	    	case "o": return 'o';
	    	case "n": return 'n';
	    	case "k": return 'k';
	    	default: return getColor(lineNumber);
	       	}
	    }
	    
	    private String getDisplayValue(String displayValue) {
	    	String format = (HeadLeaderBoards.get().fileClass.getCustomConfig().getString(hlbname + "." + displayValue)).toLowerCase();
	    	List<String> values = Arrays.asList(format.split("\\s*,\\s*"));
	    	switch (values.get(2)) {
	    	case "name": return name;
	    	case "stat": return stat;
	    	case "statdisplay": return statDisplay;
	    	case "header": return header;
	    	case "blank": return "";
	    	case "custom": return values.get(3);
	    	default: return "";
	       	}
	    }

		@Override
		public Object call() throws Exception {
			for (Integer key : signs.keySet()) {
				if (key <= hlbSize) {
					header = headerList.get(key - 1);
					Signs sign = signs.get(key);
					World world = HeadLeaderBoards.get().getServer().getWorld(sign.world);
	             	Block b = world.getBlockAt(sign.x, sign.y, sign.z);
	             	if(b.getType() == Material.WALL_SIGN){
	        			name = namelist.get((key - 1));
	         			if (statOnSameLine) {
	         				stat = String.valueOf(statlist.get(key - 1)) + " " + statDisplay;
	         			} else {
	             			stat = String.valueOf(statlist.get(key - 1));
	         			}
	         			Sign s  = (Sign) b.getState();
	    	        	s.setLine(0, ChatColor.getByChar(getColor("line0Format")) + "" + ChatColor.getByChar(getFormat("line0Format")) + getDisplayValue("line0Format"));
	    	        	s.setLine(1, ChatColor.getByChar(getColor("line1Format")) + "" + ChatColor.getByChar(getFormat("line1Format")) + getDisplayValue("line1Format"));
	    	        	s.setLine(2, ChatColor.getByChar(getColor("line2Format")) + "" + ChatColor.getByChar(getFormat("line2Format")) + getDisplayValue("line2Format"));
	    	        	s.setLine(3, ChatColor.getByChar(getColor("line3Format")) + "" + ChatColor.getByChar(getFormat("line3Format")) + getDisplayValue("line3Format"));
	    	        	s.update();
		    	        Block h1 = b.getRelative(BlockFace.UP, 1);
		    	        Block h2 = b.getRelative(BlockFace.UP, 1);
		    	        if (sign.facing.equalsIgnoreCase("east")) {
	                    	h2 = b.getRelative(-1, 1, 0);	
		    	        }
		    	        if (sign.facing.equalsIgnoreCase("west")) {
	                     	h2 = b.getRelative(1, 1, 0);	
		    	        }
		    	        if (sign.facing.equalsIgnoreCase("south")) {
	                     	h2 = b.getRelative(0, 1, -1);	
		    	        }
		    	        if (sign.facing.equalsIgnoreCase("north")) {
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
	         		}
		    	}
				}
			return true;
		}
	}
	
	private class Signs {
		
		String world;
		int x;
		int y;
		int z;
		String facing;
		
		public Signs(String w, int x1, int y1, int z1, String f) {
			world = w;
			x = x1;
			y = y1;
			z = z1;
			facing = f;
		}
		
		public Boolean equals(String w, int x1, int y1, int z1, String f) {
			if (world.equalsIgnoreCase(w) && x == x1 && y == y1 && z ==z1 && facing.equalsIgnoreCase(f)) {
				return true;
			} else {
				return false;
			}
		
		}
	}
	
}


