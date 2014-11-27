package com.headleaderboards.headleaderboards.listeners;

import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.google.common.collect.Maps;
import com.headleaderboards.headleaderboards.commands.SetupCommand;

public class ChatListener implements Listener {

	public static Map<String, Long> chatList = Maps.newHashMap();
	public static Map<String, Long> chatList2 = Maps.newHashMap();
	
	@EventHandler
    public void signPlaced(AsyncPlayerChatEvent event) {
		if (chatList.containsKey(event.getPlayer().getName())) {
			if (Math.abs((System.currentTimeMillis() - chatList.get(event.getPlayer().getName()))) < 30000) {
				SetupCommand.setupRun.setVariable(event.getMessage());
				ChatListener.chatList.remove(event.getPlayer().getName());
				SetupCommand.setupRun.interrupt();
				event.setCancelled(true);
			} else {
				ChatListener.chatList.remove(event.getPlayer().getName());
			}
		}
		if (chatList2.containsKey(event.getPlayer().getName())) {
			if (Math.abs((System.currentTimeMillis() - chatList2.get(event.getPlayer().getName()))) < 30000) {
				SetupCommand.setupLRun.setVariable(event.getMessage());
				ChatListener.chatList2.remove(event.getPlayer().getName());
				SetupCommand.setupLRun.interrupt();
				event.setCancelled(true);
			} else {
				ChatListener.chatList2.remove(event.getPlayer().getName());
			}
		}
	}
	
	public static void setOne(String name, long time) {
		chatList.put(name,  time);
	}
	
	public static void setTwo(String name, long time) {
		chatList2.put(name,  time);
	}
	
}
