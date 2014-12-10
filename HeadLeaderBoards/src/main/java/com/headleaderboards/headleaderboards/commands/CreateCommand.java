package com.headleaderboards.headleaderboards.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;
import com.headleaderboards.headleaderboards.LeaderController;

public class CreateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
        	sender.sendMessage(ChatColor.RED + "USAGE: /hbl create <leaderboard name>");
            return true;
        }
    	String hlbname = args[1].toLowerCase();
    	if (hlbname.length() > 13) {
    		sender.sendMessage(ChatColor.RED + "ERROR: Leaderboard Names Must be Less Than 12 Characters!");
    		return true;
    	}
       	LeaderController lc = HeadLeaderBoards.getLC();
    	if (lc.leaderBoardExists(hlbname)) {
    		sender.sendMessage(ChatColor.RED + "ERROR: A Leaderboard Already Exists With This Name!");
    		return true;
    	}
    	lc.createLeaderBoard(hlbname);
		sender.sendMessage(ChatColor.GREEN + "Leaderboard " + hlbname + " Has Been Created!");
        return true;
    }
}
