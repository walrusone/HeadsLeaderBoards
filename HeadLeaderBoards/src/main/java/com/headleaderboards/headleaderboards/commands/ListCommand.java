package com.headleaderboards.headleaderboards.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;
import com.headleaderboards.headleaderboards.LeaderController;

public class ListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb list");
            return true;
        }
       	LeaderController lc = HeadLeaderBoards.getLC();
       	ArrayList<String> leaderboards = lc.getNames();
       	sender.sendMessage(ChatColor.GREEN + "LEADERBOARDS:");
       	for (String name : leaderboards) {
           	sender.sendMessage(ChatColor.GREEN + "   -" + name);
       	}
        return true;
    }
}
