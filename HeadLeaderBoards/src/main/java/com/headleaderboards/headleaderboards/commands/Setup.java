package com.headleaderboards.headleaderboards.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;
import com.headleaderboards.headleaderboards.SyncData;
import com.headleaderboards.headleaderboards.SyncMessage;

public class Setup extends Thread {
	private CommandSender player;
	private Boolean properFlow;
	private int current = 0;
	private String variable;
	private Boolean quit = false;
	
	Setup(CommandSender sender) {
		this.player = sender;
	}
	
	public void run() {
		properFlow = true;
		while (properFlow && current <= 4) {
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
				} else if (current == 1) {
					if (isInteger(variable)){
						Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncData(variable, current, "dummy"));
						current++;
					}
				} else {
					Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncData(variable, current, "dummy"));
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
