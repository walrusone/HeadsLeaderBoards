package com.headleaderboards.headleaderboards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;



public class LeaderController {

	private final Map<String, LeaderBoard> leaderboards = Maps.newHashMap();
	
	public LeaderController (List<String> lbs) {
		for (int i = 0; i < lbs.size(); i++) {
			leaderboards.put(lbs.get(i), (new LeaderBoard(lbs.get(i))));
		}
	}
	
	public void updateLeaderBoards() {
		  for (LeaderBoard leaderboard : leaderboards.values()) {
	            leaderboard.dataQuery();
	        }
	}
	
	public void saveLeaderBoards() {
		  for (LeaderBoard leaderboard : leaderboards.values()) {
	            leaderboard.saveLeader();
	        }
	}
	
	public void loadLeaderBoards() {
		  for (LeaderBoard leaderboard : leaderboards.values()) {
	            leaderboard.loadLeader();
	        }
	}
	
	public void createLeaderBoard(String name) {
		leaderboards.put(name, new LeaderBoard(name));
	}
	
	public void deleteLeaderBoard(String name) {
		leaderboards.get(name).deleteLeader();
		leaderboards.remove(name);
	}
	
	public Boolean leaderBoardExists (String name) {
		if (leaderboards.containsKey(name)) {
			return true;
		} else {
			return false;
		}
	}
	
	public LeaderBoard getLeaderBoard(String name) {
		if (leaderboards.containsKey(name)) {
			return leaderboards.get(name);
		}
		return null;
	}
	
	public ArrayList<String> getNames() {
		ArrayList<String> result = new ArrayList<String>();
		for (String key : leaderboards.keySet()) {
			result.add(key);
	    }
		return result;
	}
}
