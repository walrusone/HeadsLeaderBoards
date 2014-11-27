package com.headleaderboards.headleaderboards.commands;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Maps;

public class MainCommand implements CommandExecutor {

    private Map<String, CommandExecutor> subCommandMap = Maps.newHashMap();

    public MainCommand() {
        subCommandMap.put("reload", new ReloadCommand());
        subCommandMap.put("create", new CreateCommand());
        subCommandMap.put("delete", new DeleteCommand());
        subCommandMap.put("list", new ListCommand());
        subCommandMap.put("enable", new EnableCommand());
        subCommandMap.put("setup", new SetupCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender.hasPermission("hlb.commands")) {
        	if (args.length == 0) {
            	sender.sendMessage(ChatColor.RED + "USAGE: /hlb <subcommand>");
            	sender.sendMessage(ChatColor.RED + "Subcommands: create, delete, list, reload, setup, enable");
                return true;
            }

            String subCommandName = args[0].toLowerCase();
            if (!subCommandMap.containsKey(subCommandName)) {
            	return true;
            }

            CommandExecutor subCommand = subCommandMap.get(subCommandName);

            return subCommand.onCommand(sender, command, label, args);	
    	} else {
    		sender.sendMessage(ChatColor.RED + "YOU DO NOT HAVE PERMISSION TO USE THAT COMMAND");
    		return true;
    	}

    }

}
