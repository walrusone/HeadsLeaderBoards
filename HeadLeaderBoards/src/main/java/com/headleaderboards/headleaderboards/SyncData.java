package com.headleaderboards.headleaderboards;

import java.util.concurrent.Callable;

public class SyncData implements Callable<Object> {

	private String variable;
	private String location;
	private String leaderboard;
	private int var;
	
	public SyncData(String y, int x, String leader) {
		variable = y;
		var = x;
		leaderboard = leader;
		location = getLocation(x);
	}
	
	private String getLocation(int current) {
		switch (current) {
		case 0: location = "database.hostname";
				break;
		case 1: location = "database.port";
				break;
		case 2: location = "database.database";
				break;
		case 3: location = "database.username";
				break;
		case 4: location = "database.password";
				break;
		case 5: location = "" + leaderboard + ".separateNameTable.enabled";
				break;
		case 6: location = "" + leaderboard + ".separateNameTable.nameTable";
				break;
		case 7: location = "" + leaderboard + ".separateNameTable.sepNameCol";
				break;
		case 8: location = "" + leaderboard + ".separateNameTable.sepIdCol";
				break;
		case 9: location = "" + leaderboard + ".sortByWorld.enabled";
				break;
		case 10: location = "" + leaderboard + ".sortByWorld.worldCol";
				break;			
		case 11: location = "" + leaderboard + ".sortByWorld.worldName";
				break;
		case 12: location = "" + leaderboard + ".customColumn.enabled";
				break;
		case 13: location = "" + leaderboard + ".customColumn.customCol";
				break;			
		case 14: location = "" + leaderboard + ".customColumn.rowValues";
				break;
		case 15: location = "" + leaderboard + ".customColumn2.enabled";
				break;
		case 16: location = "" + leaderboard + ".customColumn2.customCol";
				break;			
		case 17: location = "" + leaderboard + ".customColumn2.rowValues";
				break;	
		case 18: location = "" + leaderboard + ".statTable";
				break;
		case 19: location = "" + leaderboard + ".statName";
				break;
		case 20: location = "" + leaderboard + ".statDisplay";
				break;
		case 21: location = "" + leaderboard + ".nameColumn";
				break;
		case 22: location = "" + leaderboard + ".reverseOrder";
				break;
		case 23: location = "" + leaderboard + ".hlbSize";
				break;
		case 24: location = "" + leaderboard + ".line0Color";
				break;
		case 25: location = "" + leaderboard + ".line1Color";
				break;
		case 26: location = "" + leaderboard + ".line2Color";
				break;
		case 27: location = "" + leaderboard + ".line3Color";
				break;
		default: break;
		}
		return location;
	}
	
	@Override
	public Object call() throws Exception {
		if (var < 5) {
			if (var == 1) {
				HeadLeaderBoards.get().getConfig().set(location, Integer.parseInt(variable));
			} else {
				HeadLeaderBoards.get().getConfig().set(location, variable);
			}
			HeadLeaderBoards.get().saveConfig();
		}
		if (var > 4) {
			if (var == 23) {
				HeadLeaderBoards.get().fileClass.getCustomConfig().set(location, Integer.parseInt(variable));
			} else if (var == 5 || var == 9 || var == 12 || var == 15 || var == 22) {
				HeadLeaderBoards.get().fileClass.getCustomConfig().set(location, Boolean.valueOf(variable));
			} else {
				HeadLeaderBoards.get().fileClass.getCustomConfig().set(location, variable);
			}
			HeadLeaderBoards.get().fileClass.saveCustomConfig();
		}
		return null;
	}

	
}

