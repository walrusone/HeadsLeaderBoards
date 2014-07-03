package com.headleaderboards.headleaderboards.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;

public class DeleteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
        	sender.sendMessage(ChatColor.RED + "USAGE: /hbl delete <leaderboard name>");
            return true;
        }
    	List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
    	String hlbname = args[1].toLowerCase();
    	if (lbs.contains(hlbname)) {
    		lbs.remove(hlbname);
    		HeadLeaderBoards.get().getConfig().set("leaderboards", lbs);
    		HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname, null);
        	HeadLeaderBoards.get().fileClass.saveCustomConfig();
        	HeadLeaderBoards.get().saveConfig();
    		sender.sendMessage(ChatColor.GREEN + "Leaderboard " + hlbname + " Has Been Removed!");
    		return true;
    	} else {
    		sender.sendMessage(ChatColor.RED + "ERROR: There is No Leaderboard With That Name!");
    		return true;
    	}
    }
}
