package com.headleaderboards.headleaderboards.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;
import com.headleaderboards.headleaderboards.LeaderController;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb reload");
            return true;
        }
    	HeadLeaderBoards.get().reloadConfig();
    	HeadLeaderBoards.get().fileClass.reloadCustomConfig();
       	LeaderController lc = HeadLeaderBoards.getLC();
       	lc.loadLeaderBoards();
       	HeadLeaderBoards.reloadDB();
    	sender.sendMessage(ChatColor.GREEN + "Reload Complete!");
        return true;
    }
}
