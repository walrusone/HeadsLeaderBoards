package com.headleaderboards.headleaderboards.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;

public class CreateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
        	sender.sendMessage(ChatColor.RED + "USAGE: /hbl create <leaderboard name>");
            return true;
        }
    	List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
    	String hlbname = args[1].toLowerCase();
    	if (hlbname.length() > 13) {
    		sender.sendMessage(ChatColor.RED + "ERROR: Leaderboard Names Must be Less Than 12 Characters!");
    		return true;
    	}
    	if (lbs.contains(hlbname.toLowerCase())) {
    		sender.sendMessage(ChatColor.RED + "ERROR: A Leaderboard Already Exists With This Name!");
    		return true;
    	}
    	lbs.add(hlbname);
    	HeadLeaderBoards.get().getConfig().set("leaderboards", lbs);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".enabled", false);
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".table", "");
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".statName", "");
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".statDisplay", "");
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".statColumn", "");
    	HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".nameColumn", "");
    	int i = 1;
    	while (i <= 20) {
    		HeadLeaderBoards.get().fileClass.getCustomConfig().set(hlbname + ".signs." + i + ".world", null);
    		i++;
    	}
		sender.sendMessage(ChatColor.GREEN + "Leaderboard " + hlbname + " Successfully Created!");
    	HeadLeaderBoards.get().fileClass.saveCustomConfig();
    	HeadLeaderBoards.get().saveConfig();
        return true;
    }
}
