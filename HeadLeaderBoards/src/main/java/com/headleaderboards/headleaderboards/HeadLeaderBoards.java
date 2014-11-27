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
 
public class HeadLeaderBoards extends JavaPlugin {

    private static HeadLeaderBoards instance;
    HeadSQL updater = new HeadSQL();
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
        fileClass.reloadCustomConfig();
    	int timer = HeadLeaderBoards.get().getConfig().getInt("headsleaderboards.updateInterval");
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
                	if (pluginenabled == true) {
                		Bukkit.getScheduler().runTaskAsynchronously(HeadLeaderBoards.get(), new Runnable() {
                			public void run() {
                		        updater.dataQuery();
                        }});
                		updater.dataQuery();
                        return true;
                	}
            	} else {
            		sender.sendMessage(ChatColor.RED + "YOU DO NOT HAVE PERMISSION TO USE THAT COMMAND");
            		return true;
            	}
            	return true;
            }
        });
          		this.getServer().getScheduler();
        		this.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
        			public void run() {
        		        updater.dataQuery();
                }}, 0, (20 * timer));
        	}
    
    public void onDisable() {
        getLogger().info("HeadLeaderBoards Disabled");
        saveConfig();
        fileClass.saveCustomConfig();
    }
    
    public static HeadLeaderBoards get() {
        return instance;
    }
}


