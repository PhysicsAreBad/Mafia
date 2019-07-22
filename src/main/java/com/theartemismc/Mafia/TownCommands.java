package com.theartemismc.Mafia;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TownCommands {
	private Message pretty = new Message();
	
	public TownCommands() {
		
	}
	
	public HashMap<Player, String> Bodyguard(HashMap<Player, String> save, Player protect) {
		save.put(protect, "Bodyguard");
		return save;
	}
	
	public String Investigator(HashMap<Player, String> roles, Player player) {
		String role = roles.get(player);
		if (role  == "Investigator" || role == "Lookout" || role == "Consigliere") {
			return "The player knows how to investigate. Their either a Investigator, Lookout, or Consigliere.";
		}
		Bukkit.getLogger().severe(pretty.prettyConsoleError("Unable to find role for player: " + player.getName()));
		return "Error!, unable to find role!";
	}
}
