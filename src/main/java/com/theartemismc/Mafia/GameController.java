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
	
	HashMap<Player, String> roles = new HashMap<>();
	private HashMap<String, Player> mafia = new HashMap<>();
	private String[] roleList = {"Lookout", "Investigator", "Jailor", "RT", "Doctor", "Jester", "Mafiaso", "Godfather", "Vigilante", "Consigliere", "Serial", "Veteran"};
	private String[] townRoles = {"Lookout", "Investigator", "Doctor", "Mayor", "Vigilante", "Bodyguard"};
	
	private boolean mayorExists = false;
	
	private boolean day = false;
	private int dayNum = 0;
	
	HashMap<Player, String> dead = new HashMap<>();
	HashMap<Player, String> grave = new HashMap<>();
	HashMap<Player, String> save = new HashMap<>();
	
	public GameController() {
		//Nothing
	}
	
	public GameController(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void startGame() {
		for (int i = 1; i < 13; i++) {
			int k = i - 1;
			Player player = (Player) Bukkit.getOnlinePlayers().toArray()[k];
			Random rand = new Random();
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
			
			if (role == "Mafiaso" || role == "Consigliere" || role == "Godfather") {
				mafia.put(role, player);
			}
			
			roles.put(player, role);
			if (role == "Lookout") {
				player.sendMessage(pretty.prettyChat("Your role is Lookout. Every night, you are able to pick one house and see who visits that house.\nAt night do /town lookout [player to spy on] to pick the person to spy on."));
			} else if (role == "Investigator") {
				player.sendMessage(pretty.prettyChat("Your role is Investigator. Every night, you can investigate one player to learn one aspect of their role.\nAt night do /town investigate [player to investigate] to investigate that player and get a list of three roles they could be."));
			} else if (role == "Jailor") {
				player.sendMessage(pretty.prettyChat("Your role is Jailor. Every night, you can jail one player and can choose to kill them if you deem them working against the town.\nIn the day do /town jail [player to jail] to jail that player at night and and repeat that command at night to kill that person."));
			} else if (role == "Doctor") {
				player.sendMessage(pretty.prettyChat("Your role is Doctor. Every night, you can heal one player, so if they are attacked, they will still survive. You can heal yourself once.\nAt night do /town heal [player to heal] to heal that player for the morning."));
			} else if (role == "Mayor") {
				player.sendMessage(pretty.prettyChat("Your role is Mayor. You can reveal yourself in the day and your votes for execution count as 4 instead of just 1.\nTo reveal yourself do /town mayor during the voting time to gain your extra votes."));
			} else if (role == "Vigilante") {
				player.sendMessage(pretty.prettyChat("Your role is Vigilante. Over the course of the game, you can shoot 3 people, one every night.\nBut if you shoot a town, yo udie the next day out of regret.\nAt night do /town vig [player to kill] to shoot that player."));
			} else if (role == "Veteran") {
				player.sendMessage(pretty.prettyChat("Your role is Veteran. At night, you can go on alert a total of three times and kill anyone who visits you\nAt night do /town alert to go on alert."));
			} else if (role == "Bodyguard") {
				player.sendMessage(pretty.prettyChat("Your role is Bodyguard. Every night, you can choose to guard one player,a nd you will die if they are attacked\nAt night do /town guard [player to guard] to guard that player."));
			} else if (role == "Jester") {
				player.sendMessage(pretty.prettyChat("Your role is Jester. Your goal is to get the town to execute you.\nIf the town executes you, you will be given a list of people that you can choose from to kill, and you can choose your kill using /mafia jester [player to kill]"));
			} else if (role == "Mafiaso") {
				player.sendMessage(pretty.prettyChat("Your role is Mafiaso. You do the mafia's bidding, and if the Godfather dies, you become the Godfather\nEvery night, you can vote on one player to kill, but it is up to the Godfather.\nAt night do /mafia kill [player to kill] to vote for the player you would like to kill."));
			} else if (role == "Godfather") {
				player.sendMessage(pretty.prettyChat("Your role is Godfather. You are the head of the mafia!\nEvery night, you can choose one player for the mafiaso to kill\nAt night do /mafia kill [player to kill] to choose the player you would like the mafiaso to kill."));
			} else if (role == "Consigliere") {
				player.sendMessage(pretty.prettyChat("Your role is Consigliere. Every night, you can investigate one player and figure out their role\\nAt night do /mafia investigate [player to investigate] to investigate that player and get their role."));
			} else if (role == "Serial") {
				player.sendMessage(pretty.prettyChat("Your role is Serial Killer. Every night, you can kill one player.\\nAt night do /mafia sk [player to kill] to kill that player."));
			}
		}
		dayNum = 1;
		day = true;
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
			Location exe = new Location(player.getWorld(), plugin.getConfig().getDouble("execution.x"), plugin.getConfig().getDouble("execution.y"), plugin.getConfig().getDouble("execution.z"));
			player.teleport(exe);
		}
		Bukkit.broadcastMessage(pretty.prettyChat("Welcome to Mafia! Your goal is to help your team win. If you are town, you want to rid the town of the evil mafia and insane Serial Killer.\nIf yo"));
	}
	
	public void dayCycle() {
		
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
			Location exe = new Location(player.getWorld(), plugin.getConfig().getDouble("execution.x"), plugin.getConfig().getDouble("execution.y"), plugin.getConfig().getDouble("execution.z"));
			player.teleport(exe);
		}
	}
}
