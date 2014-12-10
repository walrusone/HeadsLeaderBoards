package com.headleaderboards.headleaderboards.commands;

import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;
import com.headleaderboards.headleaderboards.LeaderBoard;
import com.headleaderboards.headleaderboards.LeaderController;
import com.headleaderboards.headleaderboards.listeners.ChatListener;

public class SetupLeaderboards extends Thread {
	private CommandSender player;
	private boolean properFlow;
	private boolean quit = false;
	private int current = 5;
	private String variable;
	private LeaderBoard lb;
	
	
	SetupLeaderboards(CommandSender sender, String leader) {
		LeaderController lc = HeadLeaderBoards.getLC();
		lb = lc.getLeaderBoard(leader);
		player = sender;
	}
	
	public void run() {
		properFlow = true;
		while (properFlow && current <= 23 && current > 4) {
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
				} else if (current == 22) {
					if (isInteger(variable)){
						Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncData(current));
						current++;
					}
				} else if (current == 5 || current == 9 || current == 12 || current == 15 || current == 22) {
					if (isBoolean(variable)) {
						Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncData(current));
						if (current == 5 && toBoolean(variable).equalsIgnoreCase("false")) {
							current = current + 4;
						} else if ((current == 9 || current == 15) && toBoolean(variable).equalsIgnoreCase("false")) {
							current = current + 3;
						} else if ((current == 12) && toBoolean(variable).equalsIgnoreCase("false")) {
							current = current + 6;
						} else {
							current++;
						}
					}
				} else {
					Bukkit.getScheduler().callSyncMethod(HeadLeaderBoards.get(), new SyncData(current));
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
    
	public class SyncMessage implements Callable<Object> {

		public SyncMessage() {
		}
		
		private String getMessage() {
			String text = "";
			switch (current) {
			case 5: text = "Does your MySQL database store player names in a separate table?";
					break;
			case 6: text = "Enter the name of the Table that contains the Player Names!";
					break;
			case 7: text = "Enter the name of Column that contains the Player Names!";
					break;
			case 8: text = "Enter the name of Column that contains the Player Ids!";
					break;
			case 9: text = "Do you want to sort your stats by World?";
					break;
			case 10: text = "What is the name of the Column that contains the world data?";
					break;
			case 11: text = "What world do you want to sort the data for?";
					break;
			case 12: text = "Do you wish to use a custom column to sort your stats?";
					break;
			case 13: text = "What is the name of the Column that contains the custom data?";
					break;
			case 14: text = "What custom data value (or values) do you want to sort the data for? (Separate Values with commas)";
					break;
			case 15: text = "Do you wish to use a 2nd custom column to sort your stats?";
					break;
			case 16: text = "What is the name of the Column that contains the custom data?";
					break;
			case 17: text = "What custom data value (or values) do you want to sort the data for? (Separate Values with commas)";
					break;
			case 18: text = "Enter the name of the MySQL Table that contains the Stat Data!";
					break;
			case 19: text = "Enter the name of the Column that contains the Stat Data!";
					break;
			case 20: text = "Enter the Title you would like to see displayed on the Sign! The Display name of the Stat!";
					break;
			case 21: text = "Enter the name of the Column that contains the Player Names! (If your MySQL Database has a separate Column for Player Names, then this is the name of the Column that contains Player Ids!)";
					break;
			case 22: text = "Enter the desired length of the Leaderboard";
					break;
			case 23: text = "Do you wish to have the Data sorted in Reverse Order?";
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
			if (current <= 23 && current > 4) {
				player.sendMessage(ChatColor.RED + getMessage());
				ChatListener.setTwo(player.getName(), System.currentTimeMillis());
			} else {
				player.sendMessage(ChatColor.GREEN + getMessage());
			}
			return null;
		}
	}
	
	public class SyncData implements Callable<Object> {
		
		private int x;
		
		public SyncData(int current) {
			x = current;
		}
		
		@Override
		public Object call() throws Exception {
			switch (x) {
			case 5: lb.setsntEnabled(Boolean.valueOf(toBoolean(variable)));
					break;
			case 6: lb.setnameTable(variable);
					break;
			case 7: lb.setsepNameCol(variable);
					break;
			case 8: lb.setsepIdCol(variable);
					break;
			case 9: lb.setsbWorld(Boolean.valueOf(toBoolean(variable)));
					break;
			case 10: lb.setworldCOl(variable);
					break;			
			case 11: lb.setworldName(variable);
					break;
			case 12: lb.setcColumn1(Boolean.valueOf(toBoolean(variable)));
					break;
			case 13: lb.setcustomCol1(variable);
					break;			
			case 14: lb.setrowValues1(variable);
					break;
			case 15: lb.setcColumn2(Boolean.valueOf(toBoolean(variable)));
					break;
			case 16: lb.setcustomCol2(variable);
					break;			
			case 17: lb.setrowValues2(variable);
					break;	
			case 18: lb.setstatTable(variable);
					break;
			case 19: lb.setstatName(variable);
					break;
			case 20: lb.setstatDisplay(variable);
					break;
			case 21: lb.setnameColumn(variable);
					break;
			case 22: lb.sethlbSize(Integer.valueOf(variable));
					break;
			case 23: lb.setreverseOrder(Boolean.valueOf(toBoolean(variable)));
					break;
			default: break;
			}
			return null;
		}
	}
}

