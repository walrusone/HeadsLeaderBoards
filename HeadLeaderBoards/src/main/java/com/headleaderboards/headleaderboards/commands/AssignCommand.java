package com.headleaderboards.headleaderboards.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;

public class AssignCommand implements CommandExecutor {

		
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	List<String> subcommands = Arrays.asList("hostname", "port", "username", "password", "database", "table", "statname", "statdisplay", "namecolumn", "hlbsize", "reverseorder", "updateinterval");
    	if (args.length < 2 || args.length > 4) {
        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign <subcommand>");
        	sender.sendMessage(ChatColor.RED + "SubCommands: updateInterval, hostname, port, username, password, database, table, statname, statdisplay, namecolumn, hlbsize, reverseorder, updateinterval");
            return true;
        }
            String scommand = args[1].toLowerCase();
            if (subcommands.contains(scommand)) {
            	if (scommand.equalsIgnoreCase("hostname")) {
            		if (args.length < 3 || args.length > 3) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign hostname <hostname>");
                    	sender.sendMessage(ChatColor.RED + "Where <hostname> is your mysql server IP -- Usually localhost");
                        return true;	
            		}
            		HeadLeaderBoards.get().getConfig().set("database.hostname", args[2].toLowerCase());
                	HeadLeaderBoards.get().saveConfig();
            		sender.sendMessage(ChatColor.GREEN + "mySQL Hostname has been set to " + ChatColor.BLUE + args[2].toLowerCase());
            		return true;
            	}
            	if (scommand.equalsIgnoreCase("port")) {
            		if (args.length < 3 || args.length > 3) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign port <port>");
                    	sender.sendMessage(ChatColor.RED + "Where <port> is your mysql server PORT -- Usually 3306");
                        return true;	
            		}
            		if (isInteger(args[2])) {
            			HeadLeaderBoards.get().getConfig().set("database.port", Integer.parseInt(args[2]));
                    	HeadLeaderBoards.get().saveConfig();
                		sender.sendMessage(ChatColor.GREEN + "mySQL Port has been set to " + ChatColor.BLUE + args[2]);
                		return true;
            		} else {
                    	sender.sendMessage(ChatColor.RED + "ERROR: <port> must be a number!");
            		}
            	}
            	if (scommand.equalsIgnoreCase("username")) {
            		if (args.length < 3 || args.length > 3) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign username <username>");
                    	sender.sendMessage(ChatColor.RED + "Where <username> is your mysql server username");
                        return true;	
            		}
            		HeadLeaderBoards.get().getConfig().set("database.username", args[2].toLowerCase());
                	HeadLeaderBoards.get().saveConfig();
            		sender.sendMessage(ChatColor.GREEN + "mySQL Username has been set to " + ChatColor.BLUE + args[2].toLowerCase());
            		return true;
            	}
            	if (scommand.equalsIgnoreCase("password")) {
            		if (args.length < 3 || args.length > 3) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign password <password>");
                    	sender.sendMessage(ChatColor.RED + "Where <password> is your mysql server password");
                        return true;	
            		}
            		HeadLeaderBoards.get().getConfig().set("database.password", args[2].toLowerCase());
                	HeadLeaderBoards.get().saveConfig();
            		sender.sendMessage(ChatColor.GREEN + "mySQL Password has been set to " + ChatColor.BLUE + args[2].toLowerCase());
            		return true;
            	}
            	if (scommand.equalsIgnoreCase("database")) {
            		if (args.length < 3) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign database <database>");
                    	sender.sendMessage(ChatColor.RED + "Where <database> is the database name within/ your mysql server");
                        return true;	
            		}
            		HeadLeaderBoards.get().getConfig().set("database.database", args[2].toLowerCase());
                	HeadLeaderBoards.get().saveConfig();
            		sender.sendMessage(ChatColor.GREEN + "mySQL Database has been set to " + ChatColor.BLUE + args[2].toLowerCase());
            		return true;
            	}
            	if (scommand.equalsIgnoreCase("table")) {
            		if (args.length < 4) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign table <leaderboard> <table>");
                    	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                    	sender.sendMessage(ChatColor.RED + "Where <table> is the table name within mysql database");
                        return true;	
            		}
            		List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
                	if (lbs.contains(args[2].toLowerCase())) {
                		HeadLeaderBoards.get().fileClass.getCustomConfig().set(args[2].toLowerCase() + ".table", args[3].toLowerCase());
                    	HeadLeaderBoards.get().fileClass.saveCustomConfig();
                		sender.sendMessage(ChatColor.GREEN + "mySQL Table for the leaderboard " + ChatColor.BLUE + args[2].toLowerCase() + ChatColor.GREEN + " has been set to " + ChatColor.BLUE + args[3].toLowerCase());
                    	return true;
                		} else {
                        	sender.sendMessage(ChatColor.RED + "ERROR: " + args[2].toLowerCase() + " is not a valid leaderboard!");
                        	sender.sendMessage(ChatColor.RED + "ERROR: You must create the leaderboard before assigning a database");
                        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign table <leaderboard> <table>");
                        	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                        	sender.sendMessage(ChatColor.RED + "Where <table> is the table name within mysql database");
                		}
            	}
            	if (scommand.equalsIgnoreCase("statname")) {
            		if (args.length < 4) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign statname <leaderboard> <statname>");
                    	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                    	sender.sendMessage(ChatColor.RED + "Where <statname> is the name of the column in the table");
                        return true;	
            		}
            		List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
                	if (lbs.contains(args[2].toLowerCase())) {
                		HeadLeaderBoards.get().fileClass.getCustomConfig().set(args[2].toLowerCase() + ".statName", args[3].toLowerCase());
                    	HeadLeaderBoards.get().fileClass.saveCustomConfig();
                		sender.sendMessage(ChatColor.GREEN + "mySQL Statname for the leaderboard " + ChatColor.BLUE + args[2].toLowerCase() + ChatColor.GREEN + " has been set to " + ChatColor.BLUE + args[3].toLowerCase());
                    	return true;
                		} else {
                        	sender.sendMessage(ChatColor.RED + "ERROR: " + args[2].toLowerCase() + " is not a valid leaderboard!");
                        	sender.sendMessage(ChatColor.RED + "ERROR: You must create the leaderboard before assigning a database");
                        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign statname <leaderboard> <statname>");
                        	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                        	sender.sendMessage(ChatColor.RED + "Where <statname> is the name of the column in the table");
                		}
            	}
            	if (scommand.equalsIgnoreCase("statdisplay")) {
            		if (args.length < 4) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign statdisplay <leaderboard> <statdisplay>");
                    	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                    	sender.sendMessage(ChatColor.RED + "Where <statdisplay> is how to want the stat written on the sign. For example Kills");
                        return true;	
            		}
            		List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
                	if (lbs.contains(args[2].toLowerCase())) {
                		if (args[3].length() > 11) {
                    		sender.sendMessage(ChatColor.RED + "ERROR: Stat Display Names Must be Less Than 11 Characters!");
                    		return true;
                		}
                		HeadLeaderBoards.get().fileClass.getCustomConfig().set(args[2].toLowerCase() + ".statDisplay", args[3]);
                    	HeadLeaderBoards.get().fileClass.saveCustomConfig();
                		sender.sendMessage(ChatColor.GREEN + "Statdisplay for the leaderboard " + ChatColor.BLUE + args[2].toLowerCase() + ChatColor.GREEN + " has been set to " + ChatColor.BLUE + args[3]);
                    	return true;
                		} else {
                        	sender.sendMessage(ChatColor.RED + "ERROR: " + args[2].toLowerCase() + " is not a valid leaderboard!");
                        	sender.sendMessage(ChatColor.RED + "ERROR: You must create the leaderboard before assigning a database");
                        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign statdisplay <leaderboard> <statdisplay>");
                        	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                        	sender.sendMessage(ChatColor.RED + "Where <statdisplay> is how to want the stat written on the sign. For example Kills");
                		}
            	}
            	if (scommand.equalsIgnoreCase("namecolumn")) {
            		if (args.length < 4) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign namecolumn <leaderboard> <namecolumn>");
                    	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                    	sender.sendMessage(ChatColor.RED + "Where <namecolumn> is the column name that represents the usernames in the table");
                        return true;	
            		}
            		List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
                	if (lbs.contains(args[2].toLowerCase())) {
                		HeadLeaderBoards.get().fileClass.getCustomConfig().set(args[2].toLowerCase() + ".nameColumn", args[3]);
                    	HeadLeaderBoards.get().fileClass.saveCustomConfig();
                		sender.sendMessage(ChatColor.GREEN + "mySQL nameColumn for the leaderboard " + ChatColor.BLUE + args[2].toLowerCase() + ChatColor.GREEN + " has been set to " + ChatColor.BLUE + args[3]);
                    	return true;
                		} else {
                        	sender.sendMessage(ChatColor.RED + "ERROR: " + args[2].toLowerCase() + " is not a valid leaderboard!");
                        	sender.sendMessage(ChatColor.RED + "ERROR: You must create the leaderboard before assigning a database");
                        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign namecolumn <leaderboard> <namecolumn>");
                        	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                        	sender.sendMessage(ChatColor.RED + "Where <namecolumn> is the column name that represents the usernames in the table");
                		}
            	}
            	if (scommand.equalsIgnoreCase("hlbsize")) {
            		if (args.length < 4) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign hlbsize <leaderboard> <hlbsize>");
                    	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                    	sender.sendMessage(ChatColor.RED + "Where <hlbsize> is the number of places you want in the leaderboard");
                        return true;	
            		}
            		List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
                	if (lbs.contains(args[2].toLowerCase())) {
                		if (isInteger(args[3])) {
                			HeadLeaderBoards.get().fileClass.getCustomConfig().set(args[2].toLowerCase() + ".hlbSize", Integer.parseInt(args[3]));
                        	HeadLeaderBoards.get().fileClass.saveCustomConfig();
                    		sender.sendMessage(ChatColor.GREEN + "hlbsize for the leaderboard " + ChatColor.BLUE + args[2].toLowerCase() + ChatColor.GREEN + " has been set to " + ChatColor.BLUE + args[3]);
                        	return true;
                		} else {
                        	sender.sendMessage(ChatColor.RED + "ERROR: <hlbsize> must be a number!");			
                		}
                		} else {
                        	sender.sendMessage(ChatColor.RED + "ERROR: " + args[2].toLowerCase() + " is not a valid leaderboard!");
                        	sender.sendMessage(ChatColor.RED + "ERROR: You must create the leaderboard before assigning a database");
                        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign hlbsize <leaderboard> <hlbsize>");
                        	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                        	sender.sendMessage(ChatColor.RED + "Where <hlbsize> is the number of places you want in the leaderboard");
                		}
            	}
            	if (scommand.equalsIgnoreCase("reverseOrder")) {
            		if (args.length < 4) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign reverseOrder <leaderboard> <state>");
                    	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                    	sender.sendMessage(ChatColor.RED + "Where <state> is the true or false. When state is true, leaderboard will go from lowest to highest.");
                        return true;	
            		}
            		List<String> lbs = (HeadLeaderBoards.get().getConfig().getStringList("leaderboards"));
                	if (lbs.contains(args[2].toLowerCase())) {
                		if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false")) {
                		HeadLeaderBoards.get().fileClass.getCustomConfig().set(args[2].toLowerCase() + ".reverseOrder", Boolean.valueOf(args[3]));
                    	HeadLeaderBoards.get().fileClass.saveCustomConfig();
                		sender.sendMessage(ChatColor.GREEN + "reverseOrder for the leaderboard " + ChatColor.BLUE + args[2].toLowerCase() + ChatColor.GREEN + " has been set to " + ChatColor.BLUE + args[3]);
                    	return true;
                		}
                		} else {
                        	sender.sendMessage(ChatColor.RED + "ERROR: " + args[2].toLowerCase() + " is not a valid leaderboard!");
                        	sender.sendMessage(ChatColor.RED + "ERROR: You must create the leaderboard before assigning a database");
                        	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign hlbsize <leaderboard> <hlbsize>");
                        	sender.sendMessage(ChatColor.RED + "Where <leaderboard> is the leaderboard you are setting the database for");
                        	sender.sendMessage(ChatColor.RED + "Where <state> is the true or false. When state is true, leaderboard will go from lowest to highest.");
                		}
            	}
            	if (scommand.equalsIgnoreCase("updateinterval")) {
            		if (args.length < 3) {
                    	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign updateinterval <time>");
                    	sender.sendMessage(ChatColor.RED + "Where <time> is the time in seconds between leaderboard updates");
                        return true;	
            		}
            		if (isInteger(args[2])) {
                		HeadLeaderBoards.get().getConfig().set("headsleaderboards.updateInterval", Integer.parseInt(args[2]));
                    	HeadLeaderBoards.get().saveConfig();
                		sender.sendMessage(ChatColor.GREEN + "Leaderboards update interval has been set to " + ChatColor.BLUE + args[2] + " seconds");
                		return true;
            		} else {
                    	sender.sendMessage(ChatColor.RED + "ERROR: <updateinterval> must be a number!");
            		}
            	}
            } else {
               	sender.sendMessage(ChatColor.RED + "USAGE: /hlb assign <subcommand>");
            	sender.sendMessage(ChatColor.RED + "SubCommands: updateInterval, hostname, port, username, password, database, table, statname, statdisplay, namecolumn, hlbsize, reverseorder, updateinterval");
                return true;
            }
            return true;
    }


    public static boolean isInteger(String str)  
    {  
      try  
      {  
        int d = Integer.parseInt(str); 
        if (d == 0) {
        }
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
    
}