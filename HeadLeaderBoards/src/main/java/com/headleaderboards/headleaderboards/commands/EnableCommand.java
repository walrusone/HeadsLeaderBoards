package com.headleaderboards.headleaderboards.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;

public class EnableCommand implements CommandExecutor {

	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 1 || args.length > 2) {
			sender.sendMessage(ChatColor.RED + "USAGE: /hlb enable    -   Enables/Disables the Plugin");
			sender.sendMessage(ChatColor.RED + "USAGE: /hlb enable <leaderboard name>   -   Enables/Disables the Leaderboard");
			return true;
		}
		if (args.length == 1) {
			Boolean enabled = HeadLeaderBoards.get().getConfig().getBoolean("headsleaderboards.enabled");
			if (enabled) {
    			HeadLeaderBoards.get().getConfig().set("headsleaderboards.enabled", Boolean.valueOf("false"));
        		sender.sendMessage(ChatColor.GREEN + "HeadsLeaderboards Has Been Disabled");
			} else {
    			HeadLeaderBoards.get().getConfig().set("headsleaderboards.enabled", Boolean.valueOf("true"));
        		sender.sendMessage(ChatColor.GREEN + "HeadsLeaderboards Has Been Enabled");
			}
			HeadLeaderBoards.get().saveConfig();
			return true;
		} else {
			List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
			if (lbs.contains(args[1].toLowerCase())) {
				Boolean enabled = HeadLeaderBoards.get().fileClass.getCustomConfig().getBoolean(args[1].toLowerCase() + ".enabled");
				if (enabled) {
	    			HeadLeaderBoards.get().fileClass.getCustomConfig().set(args[1].toLowerCase() + ".enabled", Boolean.valueOf("false"));
	        		sender.sendMessage(ChatColor.GREEN + "The Leaderboard " + args[1] + " Has Been Disabled");
				} else {
	    			HeadLeaderBoards.get().fileClass.getCustomConfig().set(args[1].toLowerCase() + ".enabled", Boolean.valueOf("true"));
	        		sender.sendMessage(ChatColor.GREEN + "The Leaderboard " + args[1] + " Has Been Enabled");
				}
            	HeadLeaderBoards.get().fileClass.saveCustomConfig();
    			return true;
			} else {
				sender.sendMessage(ChatColor.RED + "There is no Leaderboard names \"" + args[1] + "\"");
				return true;
			}
		}
	}
}