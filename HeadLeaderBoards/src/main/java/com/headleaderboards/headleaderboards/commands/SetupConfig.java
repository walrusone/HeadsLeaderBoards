package com.headleaderboards.headleaderboards.commands;

import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;
import com.headleaderboards.headleaderboards.listeners.ChatListener;

public class SetupConfig extends Thread {
	private CommandSender player;
	private Boolean properFlow;
	private int current = 0;
	private String variable;
	private Boolean quit = false;
	
	SetupConfig(CommandSender sender) {
		this.player = sender;
	}
	
	public void run() {
		properFlow = true;
		while (properFlow && current <= 4) {
			Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncMessage());
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
						Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncData());
						current++;
					}
				} else {
					Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncData());
					current++;
				}
			}
		}
		if (properFlow) {
			if (quit) {
				current = 98;
				Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncMessage());
			} else {
				current = 99;
				Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncMessage());	
			}
		} else {
			current = 100;
			Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncMessage());
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
    
    public class SyncMessage implements Callable<Object> {

    	public SyncMessage() {
    	}
    	
    	private String getMessage() {
    		String text = "";
    		switch (current) {
    		case 0: text = "Enter the MySQL Server IP. Usually \"localhost\"";
    				break;
    		case 1: text = "Enter the MySQL Server Port. Usually \"3306\"";
    				break;
    		case 2: text = "Enter the MySQL Server Database.";
    				break;
    		case 3: text = "Enter the MySQL Server Username. Usually \"root\"";
    				break;
    		case 4: text = "Enter the MySQL Server password.";
    				break;
    		case 98: text = "Exiting Setup!";
    				break;	
    		case 99: text = "Setup Completed Succesfully!!";
    				break;
    		case 100: text = "No Response For 30 Seconds! Setup Terminated!";
    				break;
    		default: break;
    		}
    		return text;
    	}
    	
    	@Override
    	public Object call() throws Exception {
    		if (current <= 4) {
    			player.sendMessage(ChatColor.RED + getMessage());
    			ChatListener.setOne(player.getName(), System.currentTimeMillis());
    		} else {
    			player.sendMessage(ChatColor.GREEN + getMessage());
    		}
    		return null;
    	}
    }
    
    public class SyncData implements Callable<Object> {

    	private String location;
    	
    	public SyncData() {
    		location = getLocation(current);
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
    		default: break;
    		}
    		return location;
    	}
    	
    	@Override
    	public Object call() throws Exception {
    		if (current < 5) {
    			if (current == 1) {
    				HeadLeaderBoards.get().getConfig().set(location, Integer.parseInt(variable));
    			} else {
    				HeadLeaderBoards.get().getConfig().set(location, variable);
    			}
    			HeadLeaderBoards.get().saveConfig();
    		}
    		return null;
    	}
    	
    }
}
