package com.headleaderboards.headleaderboards;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

public class SignUpdater implements Callable<Object> {

	ArrayList<String> namelist;
	ArrayList<Integer> statlist;
	String leaderboard;

	public SignUpdater (ArrayList<String> nlist, ArrayList<Integer> slist, String leader) {
		namelist = new ArrayList<String>(nlist);
		statlist = new ArrayList<Integer>(slist);
		leaderboard = leader;
	}
	
    public char getColor(String leaderboard, String lineNumber) {
    	String lineColor = (HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + "." + lineNumber)).toLowerCase();
    	switch (lineColor) {
    	case "black": return '0';
    	case "dark blue": return '1';
    	case "dark green": return '2';
    	case "dark aqua": return '3';
    	case "dark red": return '4';
    	case "dark purple": return  '5';
    	case "gold": return '6';
    	case "gray": return '7';
    	case "dark gray": return '8';
    	case "blue": return '9';
    	case "green": return 'a';
    	case "aqua": return 'b';
    	case "red": return 'c';
    	case "light purple": return 'd';
    	case "yellow": return 'e';
    	case "white": return 'f';
    	case "0": return '0';
    	case "1": return '1';
    	case "2": return '2';
    	case "3": return '3';
    	case "4": return '4';
    	case "5": return  '5';
    	case "6": return '6';
    	case "7": return '7';
    	case "8": return '8';
    	case "9": return '9';
    	case "a": return 'a';
    	case "b": return 'b';
    	case "c": return 'c';
    	case "d": return 'd';
    	case "e": return 'e';
    	case "f": return 'f';
    	default: return '0';
       	}
    }

	@Override
	public Object call() throws Exception {
    	for (int i = 0; i < namelist.size(); i++) {
    		int j = i + 1;
    		String t = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".signs." + j + ".world");
	          if (t != null) {
             	World w = HeadLeaderBoards.get().getServer().getWorld(HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".signs." + j + ".world"));
             	int x = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".x");
             	int y = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".y");
             	int z = HeadLeaderBoards.get().fileClass.getCustomConfig().getInt(leaderboard + ".signs." + j + ".z");
             	String facing = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".signs." + j + ".facing");
            	String statdisplay = HeadLeaderBoards.get().fileClass.getCustomConfig().getString(leaderboard + ".statDisplay");
             	char line0Color = getColor(leaderboard, "line0Color");
             	char line1Color = getColor(leaderboard, "line1Color");
             	char line2Color = getColor(leaderboard, "line2Color");
             	char line3Color = getColor(leaderboard, "line3Color");
             	Block b = w.getBlockAt(x, y, z);
             	String name = "";
             		if(b.getType() == Material.WALL_SIGN){
             			name = namelist.get(i);
             			int stat = statlist.get(i);
             			Sign s  = (Sign) b.getState();
	    	        	s.setLine(0, ChatColor.getByChar(line0Color) + "" + ChatColor.BOLD + Integer.toString(j));
		    	        s.setLine(1, ChatColor.getByChar(line1Color) + name);
		    	        s.setLine(2, ChatColor.getByChar(line2Color) + "" + ChatColor.BOLD +  statdisplay);
		    	        s.setLine(3, ChatColor.getByChar(line3Color) + "" + ChatColor.BOLD +  String.valueOf(stat)); 
		    	        s.update();
		    	        Block h1 = b.getRelative(BlockFace.UP, 1);
		    	        Block h2 = b.getRelative(BlockFace.UP, 1);
		    	        if (facing.equalsIgnoreCase("east")) {
                        	h2 = b.getRelative(-1, 1, 0);	
		    	        }
		    	        if (facing.equalsIgnoreCase("west")) {
                         	h2 = b.getRelative(1, 1, 0);	
		    	        }
		    	        if (facing.equalsIgnoreCase("south")) {
                         	h2 = b.getRelative(0, 1, -1);	
		    	        }
		    	        if (facing.equalsIgnoreCase("north")) {
                         	h2 = b.getRelative(0, 1, 1);	
		    	        }
		    	        if(h1.getType() == Material.SKULL) {
		    	        	Skull skull = (Skull) h1.getState();
                     	    skull.setSkullType(SkullType.PLAYER); 
                     	    skull.setOwner(name);
                     	    skull.update();
		    	        }
		    	        if(h2.getType() == Material.SKULL) {
                     	    Skull skull = (Skull) h2.getState(); 
                     	    skull.setSkullType(SkullType.PLAYER);
                     	    skull.setOwner(name);
                     	    skull.update();
		    	        }
             		}
	        }
    	}
		return true;
	}
}
