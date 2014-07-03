package com.headleaderboards.headleaderboards.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;

public class ListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb list");
            return true;
        }
        List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
		sender.sendMessage(ChatColor.GREEN + "LEADERBOARDS:");
        for (int i = 0; i < lbs.size(); i++) {
        	sender.sendMessage(ChatColor.BLUE + "  - " + lbs.get(i));
        }
        return true;
    }
}
