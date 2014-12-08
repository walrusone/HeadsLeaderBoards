package com.headleaderboards.headleaderboards;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomYML {

	public static HeadLeaderBoards plugin;
	public CustomYML(HeadLeaderBoards plugin){
		CustomYML.plugin = plugin;
	}

	private FileConfiguration leaderboardsConfig = null;
	private File leaderboardsFile = null;

	public void reloadCustomConfig(){
		if (leaderboardsFile == null){
			leaderboardsFile = new File(plugin.getDataFolder(), "leaderboards.yml");
		}

		leaderboardsConfig = YamlConfiguration.loadConfiguration(leaderboardsFile);

		InputStream defConfigStream = plugin.getResource("leaderboards.yml");
		if(defConfigStream != null){
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            leaderboardsConfig.setDefaults(defConfig);
		}
	}

	public FileConfiguration getCustomConfig(){
		if(leaderboardsConfig == null){
			reloadCustomConfig();
		}
		return leaderboardsConfig;
	}

	public void saveCustomConfig(){
		if(leaderboardsConfig == null || leaderboardsFile == null){
			return;
		}
		try{
			getCustomConfig().save(leaderboardsFile);
		} catch (IOException ex){
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + leaderboardsFile, ex);
		}
	}
}
