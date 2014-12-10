package com.headleaderboards.headleaderboards.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;
import com.headleaderboards.headleaderboards.LeaderController;

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
			LeaderController lc = HeadLeaderBoards.getLC();
			String hlbname = args[1].toLowerCase();
	    	if (lc.leaderBoardExists(hlbname)) {
	    		if (lc.getLeaderBoard(hlbname).getEnabled()) {
	        		sender.sendMessage(ChatColor.GREEN + "The Leaderboard " + hlbname + " Has Been Disabled");
	    		} else {
	    			sender.sendMessage(ChatColor.GREEN + "The Leaderboard " + hlbname + " Has Been Enabled");
	    		}
				lc.getLeaderBoard(hlbname).setEnabled();
    			return true;
			} else {
				sender.sendMessage(ChatColor.RED + "There is no Leaderboard names \"" + hlbname + "\"");
				return true;
			}
		}
	}
}