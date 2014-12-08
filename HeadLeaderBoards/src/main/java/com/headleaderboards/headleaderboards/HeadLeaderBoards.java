package com.headleaderboards.headleaderboards;


import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

import com.headleaderboards.headleaderboards.commands.MainCommand;
import com.headleaderboards.headleaderboards.listeners.BlockListener;
import com.headleaderboards.headleaderboards.listeners.ChatListener;
import com.headleaderboards.headleaderboards.database.Database;

 
public class HeadLeaderBoards extends JavaPlugin {

    private static HeadLeaderBoards instance;
    private Database database;
    private HeadSQL updater = new HeadSQL();
    public CustomYML fileClass = new CustomYML(this);
	
    public void onEnable() {
    	instance = this;
        getLogger().info("HeadLeaderBoards Enabled");        
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();
        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }
        fileClass.getCustomConfig().options().copyDefaults(true);
        fileClass.saveCustomConfig();
        fileClass.reloadCustomConfig();
        database = new Database();
        Boolean enabled = HeadLeaderBoards.get().getConfig().getBoolean("headsleaderboards.enabled");
    	int timer = HeadLeaderBoards.get().getConfig().getInt("headsleaderboards.updateInterval");
        if (enabled) {
            if (!database.checkConnection()) {
            	getLogger().info("HeadLeaderBoards: MySQL Database Information is not setup correctly!! Please check your Config Settings!!");
            } else {
          		this.getServer().getScheduler();
        		this.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
        			public void run() {
        		        updater.dataQuery();
                }}, 0, (20 * timer));
            }
        }
        getCommand("hlb").setExecutor(new MainCommand());
        getCommand("hlbupdate").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
            	if (sender.hasPermission("hlb.update")) {
            		if (args.length > 0) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlbupdate");
                        return true;
                    }
                	Boolean pluginenabled = HeadLeaderBoards.get().getConfig().getBoolean("headsleaderboards.enabled");
                	if (pluginenabled && database.checkConnection()) {
                		sender.sendMessage(ChatColor.GREEN + "Leaderboards are being updated!");
                		Bukkit.getScheduler().runTaskAsynchronously(HeadLeaderBoards.get(), new Runnable() {
                			public void run() {
                		        updater.dataQuery();
                        }});
                	} else if (!pluginenabled) {
                		sender.sendMessage(ChatColor.RED + "The Plugin is NOT Enabled!");
                	}  else if (!database.checkConnection()) {
                		sender.sendMessage(ChatColor.RED + "HeadLeaderBoards: MySQL Database Information is not setup correctly!! Please check your Config Settings!!");
                	}
            	} else {
            		sender.sendMessage(ChatColor.RED + "YOU DO NOT HAVE PERMISSION TO USE THAT COMMAND");
            		return true;
            	}
            	return true;
            }
        });
    }
        
    
    public void onDisable() {
        getLogger().info("HeadLeaderBoards Disabled");
        saveConfig();
        fileClass.saveCustomConfig();
    }
    
    public static HeadLeaderBoards get() {
        return instance;
    }
    
    public static Database getDB() {
        return instance.database;
    }
    
}


