package com.headleaderboards.headleaderboards;

import java.util.concurrent.Callable;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.listeners.ChatListener;

public class SyncMessage implements Callable<Object> {

	private static CommandSender sender;
	private int current;
	
	public SyncMessage(CommandSender player, int x) {
		sender = player;
		current = x;
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
		case 5: text = "Does your MySQL database store player names in a separate table?";
				break;
		case 6: text = "Enter the name of the Table that contains the Player Names!";
				break;
		case 7: text = "Enter the name of Column that contains the Player Names!";
				break;
		case 8: text = "Enter the name of Column that contains the Player Ids!";
				break;
		case 9: text = "Enter the name of the MySQL Table that contains the Stat Data!";
				break;
		case 10: text = "Enter the name of the Column that contains the Stat Data!";
				break;
		case 11: text = "Enter the Title you would like to see displayed on the Sign! The Display name of the Stat!";
				break;
		case 12: text = "Enter the name of the Column that contains the Player Names! (If your MySQL Database has a separate Column for Player Names, then this is the name of the Column that contains Player Ids!)";
				break;
		case 13: text = "Do you wish to have the Data sorted in Reverse Order?";
				break;
		case 14: text = "Enter the desired length of the Leaderboard";
				break;
		case 15: text = "What color do you want Line 1 to Appear (Minecraft Color Codes)!";
				break;
		case 16: text = "What color do you want Line 2 to Appear (Minecraft Color Codes)!";
				break;
		case 17: text = "What color do you want Line 3 to Appear (Minecraft Color Codes)!";
				break;
		case 18: text = "What color do you want Line 4 to Appear (Minecraft Color Codes)!";
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
			sender.sendMessage(ChatColor.RED + getMessage());
			ChatListener.setOne(sender.getName(), System.currentTimeMillis());
		} else if (current <= 18 && current > 4) {
			sender.sendMessage(ChatColor.RED + getMessage());
			ChatListener.setTwo(sender.getName(), System.currentTimeMillis());
		} else {
			sender.sendMessage(ChatColor.GREEN + getMessage());
		}
		return null;
	}

	
}
