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
		case 5: location = "" + leaderboard + ".separateNameTable";
				break;
		case 6: location = "" + leaderboard + ".NameTable";
				break;
		case 7: location = "" + leaderboard + ".sepmameCol";
				break;
		case 8: location = "" + leaderboard + ".sepIdCol";
				break;
		case 9: location = "" + leaderboard + ".table";
				break;
		case 10: location = "" + leaderboard + ".statName";
				break;
		case 11: location = "" + leaderboard + ".statDisplay";
				break;
		case 12: location = "" + leaderboard + ".nameColumn";
				break;
		case 13: location = "" + leaderboard + ".reverseOrder";
				break;
		case 14: location = "" + leaderboard + ".hlbSize";
				break;
		case 15: location = "" + leaderboard + ".line0Color";
				break;
		case 16: location = "" + leaderboard + ".line1Color";
				break;
		case 17: location = "" + leaderboard + ".line2Color";
				break;
		case 18: location = "" + leaderboard + ".line3Color";
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
			if (var == 14) {
				HeadLeaderBoards.get().fileClass.getCustomConfig().set(location, Integer.parseInt(variable));
			} else if (var == 5 || var == 13) {
				HeadLeaderBoards.get().fileClass.getCustomConfig().set(location, Boolean.valueOf(variable));
			} else {
				HeadLeaderBoards.get().fileClass.getCustomConfig().set(location, variable);
			}
			HeadLeaderBoards.get().fileClass.saveCustomConfig();
		}
		return null;
	}

	
}

