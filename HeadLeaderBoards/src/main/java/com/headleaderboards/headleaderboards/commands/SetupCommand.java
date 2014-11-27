package com.headleaderboards.headleaderboards.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;

public class SetupCommand implements CommandExecutor {
	public static Setup setupRun;
	public static SetupLeaderboards setupLRun;
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 1 || args.length > 2) {
			sender.sendMessage(ChatColor.RED + "USAGE: /hlb setup    -   Basic Database Setup");
			sender.sendMessage(ChatColor.RED + "USAGE: /hlb setup <leaderboard name>   -   Leaderboard Setup");
		}
		if (args.length == 1) {
			sender.sendMessage(ChatColor.GREEN + "Entering Setup: To exit type \"exit\". To leave a entry at it current value type \"t\". ");
			setupRun = new Setup(sender);
			setupRun.start();
			return true;
		} else {
			List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
			if (lbs.contains(args[1].toLowerCase())) {
				String leader = args[1];
				sender.sendMessage(ChatColor.GREEN + "Entering Leaderboard Setup: To exit type \"exit\". To leave a entry at it current value type \"t\". ");
				setupLRun = new SetupLeaderboards(sender, leader);
				setupLRun.start();
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "There is no Leaderboard named \"" + args[1] + "\"");
			}
		}
		return true;

	}

}
