package com.theartemismc.Mafia;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GameController {
	private JavaPlugin plugin = null;
	
	public GameController(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void startGame() {
		//TODO: Role Selection 
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
			Location exe = new Location(player.getWorld(), plugin.getConfig().getDouble("execution.x"), plugin.getConfig().getDouble("execution.y"), plugin.getConfig().getDouble("execution.z"));
			player.teleport(exe);
		}
	}
}
