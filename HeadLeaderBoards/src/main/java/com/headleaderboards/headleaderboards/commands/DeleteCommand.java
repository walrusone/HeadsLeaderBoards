package com.headleaderboards.headleaderboards.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;
import com.headleaderboards.headleaderboards.LeaderController;

public class DeleteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
        	sender.sendMessage(ChatColor.RED + "USAGE: /hbl delete <leaderboard name>");
            return true;
        }
    	String hlbname = args[1].toLowerCase();
    	LeaderController lc = HeadLeaderBoards.getLC();
    	if (lc.leaderBoardExists(hlbname)) {
    		lc.deleteLeaderBoard(hlbname);
    		sender.sendMessage(ChatColor.GREEN + "Leaderboard " + hlbname + " Has Been Removed!");
    		return true;
    	} else {
    		sender.sendMessage(ChatColor.RED + "ERROR: There is No Leaderboard With That Name!");
    		return true;
    	}
    }
}
