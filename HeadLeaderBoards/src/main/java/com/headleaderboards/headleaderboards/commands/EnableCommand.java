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
		if (args.length < 3 || args.length > 3) {
        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb enable plugin <state>");
        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb enable <leaderboard> <state>");
        	sender.sendMessage(ChatColor.RED + "<state> is either true or false");
        	sender.sendMessage(ChatColor.RED + "<leaderboard> is the name of your created leaderboard");
            return true;
        }
        String scommand = args[1].toLowerCase();
		if (scommand.equalsIgnoreCase("plugin")) {
    		String state = args[2].toLowerCase();
    		if (state.equalsIgnoreCase("true") || state.equalsIgnoreCase("false")) {
    			HeadLeaderBoards.get().getConfig().set("headsleaderboards.enabled", Boolean.valueOf(state));
            	HeadLeaderBoards.get().saveConfig();
        		sender.sendMessage(ChatColor.GREEN + "HeadsLeaderboards Has Been Enabled");
        		return true;
    		}
        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb enable plugin <state>");
        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb enable <leaderboard> <state>");
        	sender.sendMessage(ChatColor.RED + "<state> is either true or false");
         	sender.sendMessage(ChatColor.RED + "<leaderboard> is the name of your created leaderboard");
            return true;
		}
		List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
    	if (lbs.contains(scommand)) {
    		String state = args[2].toLowerCase();
    		if (state.equalsIgnoreCase("true") || state.equalsIgnoreCase("false")) {
    			HeadLeaderBoards.get().fileClass.getCustomConfig().set(scommand + ".enabled", Boolean.valueOf(state));
            	HeadLeaderBoards.get().fileClass.saveCustomConfig();
        		sender.sendMessage(ChatColor.GREEN + "Leaderboard " + ChatColor.BLUE + scommand + ChatColor.GREEN + " Has Been Enabled");
        		return true;
    		}
    	}
    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb enable plugin <state>");
    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb enable <leaderboard> <state>");
    	sender.sendMessage(ChatColor.RED + "<state> is either true or false");
    	sender.sendMessage(ChatColor.RED + "<leaderboard> is the name of your created leaderboard");
        return true;
    }
}