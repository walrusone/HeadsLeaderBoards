package com.headleaderboards.headleaderboards.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;
import com.headleaderboards.headleaderboards.SyncData;
import com.headleaderboards.headleaderboards.SyncMessage;

public class SetupLeaderboards extends Thread {
	private CommandSender player;
	private boolean properFlow;
	private boolean quit = false;
	private int current = 5;
	private String leaderboard;
	private String variable;
	
	
	SetupLeaderboards(CommandSender sender, String leader) {
		leaderboard = leader;
		player = sender;
	}
	
	public void run() {
		properFlow = true;
		while (properFlow && current <= 18 && current > 4) {
			Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncMessage(player, current));
			try {
				properFlow = false;
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				properFlow = true;
			}
			if (properFlow) {
				if (variable.equalsIgnoreCase("end") || variable.equalsIgnoreCase("quit") || variable.equalsIgnoreCase("exit")) {
					quit = true;
					break;
				} else if (variable.equalsIgnoreCase("t")) {
					current++;
				} else if (current == 14) {
					if (isInteger(variable)){
						Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncData(variable, current, leaderboard));
						current++;
					}
				} else if (current == 5 || current == 13) {
					if (isBoolean(variable)) {
						Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncData(toBoolean(variable), current, leaderboard));
						if (current == 5 && toBoolean(variable).equalsIgnoreCase("false")) {
							current = current + 4;
						} else {
							current++;
						}
					}
				} else {
					Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncData(variable, current, leaderboard));
					current++;
				}
			}
		}
		if (properFlow) {
			if (quit) {
				Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncMessage(player, 98));
			} else {
				Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncMessage(player, 99));	
			}
		} else {
			Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncMessage(player, 100));
		}
	}

	public void setVariable(String text) {
		variable = text;
	}

    private static boolean isInteger(String str) {  
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
    
    private static boolean isBoolean(String str) {  
      if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("n") || str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("no") 
    		  || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")  || str.equalsIgnoreCase("t") || str.equalsIgnoreCase("f")) {
    	  return true;
      }
        return false;  
    }
    
    private static String toBoolean(String str) {  
        if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("t")) {
      	  return "true";
        }
          return "false";  
      }
}

