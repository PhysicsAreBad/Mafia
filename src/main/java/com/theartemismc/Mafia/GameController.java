package com.theartemismc.Mafia;

import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GameController {
	public Message pretty = new Message();
	
	private JavaPlugin plugin = null;
	
	private HashMap<Player, String> roles = new HashMap<>();
	private HashMap<String, Player> mafia = new HashMap<>();
	private String[] roleList = {"Lookout", "Investigator", "Jailor", "RT", "Doctor", "Jester", "Mafiaso", "Godfather", "Vigilante", "Conierge", "Serial"};
	private String[] townRoles = {"Lookout", "Investigator", "Doctor", "Mayor", "Vigilante", "Veteran"};
	
	private boolean mayorExists = false;
	
	private boolean day = false;
	private int dayNum = 0;
	
	public GameController() {
		//Nothing
	}
	
	public GameController(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void startGame() {
		for (int i = 1; i < 13; i++) {
			Player player = (Player) Bukkit.getOnlinePlayers().toArray()[i-1];
			Random rand = new Random();
			int k = i - 1;
			int n = rand.nextInt(11 - k);
			String role = roleList[n];
			ArrayUtils.remove(roleList, n);
			if (role == "RT") {
				int j = rand.nextInt(8);
				role = townRoles[j];
				if (role == "Mayor") {
					mayorExists = true;
				}
			}
			
			if (role == "Mafiaso" || role == "Conierge" || role == "Godfather") {
				mafia.put(role, player);
			}
			
			roles.put(player, role);
			player.sendMessage(pretty.prettyChat(""));
		}
		dayNum = 1;
		day = true;
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
			Location exe = new Location(player.getWorld(), plugin.getConfig().getDouble("execution.x"), plugin.getConfig().getDouble("execution.y"), plugin.getConfig().getDouble("execution.z"));
			player.teleport(exe);
		}
		Bukkit.broadcastMessage(pretty.prettyChat(""));
	}
	
	public void dayCycle() {
		
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
			Location exe = new Location(player.getWorld(), plugin.getConfig().getDouble("execution.x"), plugin.getConfig().getDouble("execution.y"), plugin.getConfig().getDouble("execution.z"));
			player.teleport(exe);
		}
	}
}
