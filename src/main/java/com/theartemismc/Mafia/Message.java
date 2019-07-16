package com.theartemismc.Mafia;

import org.bukkit.ChatColor;

public class Message {
	
	public Message () {
		//Nothing, Just a constructor
	}
	
	public String prettyConsoleInfo (String message) {
		message = ChatColor.GREEN + "[Mafia] " + message; 
		return message;
	}
	
	public String prettyConsoleWarn (String message) {
		message = ChatColor.YELLOW + "[Mafia] (WARN) " + message; 
		return message;
	}
	
	public String prettyConsoleError (String message) {
		message = ChatColor.RED + "[Mafia] (ERROR) " + message; 
		return message;
	}
	
	public String prettyChat (String message) {
		message = ChatColor.RED + "[Mafia] " + message;
		return message;
	}
}
