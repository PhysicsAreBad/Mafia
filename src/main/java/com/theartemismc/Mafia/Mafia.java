package com.theartemismc.Mafia;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Mafia extends JavaPlugin {
	Message pretty = new Message();
	GameController control = null;
	TownCommands town = new TownCommands();
	
	@Override
    public void onEnable() {
		this.saveDefaultConfig();
		this.getConfig();
		control = new GameController(this);
		getServer().getPluginManager().registerEvents(new Lobby(this), this);
        getLogger().info(pretty.prettyConsoleInfo("Started Mafia V0.01, Written by PhysicsAreBad & Averageone23_YT"));
    }
	
	@Override
	public void onDisable() {
		this.saveConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mafiaadmin")) { // If the player typed /mafiaadmin then do the following, note: If you only registered this executor for one command, you don't need this
			if (args.length == 0){
				//Basic Plugin Info
				sender.sendMessage(pretty.prettyChat("Mafia V0.01\nDeveloped by PhysicsAreBad and Averageone23_YT\nTo see more commands type /mafiaadmin help"));
			} else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
				//Send Command Help message
				sender.sendMessage(pretty.prettyChat("Mafia Admin Help\n/mafiaadmin - Information about the plugin\n/mafiaadmin setLocation [lobby,execution] - Sets the location of the place at that point."));
			} else if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
				if (sender.hasPermission("mafia.start")) {
					if (Bukkit.getOnlinePlayers().size() >= 12 ) {
						control.startGame();
					} else sender.sendMessage(pretty.prettyChat("You need at least 12 players to play Mafia!"));
				} else sender.sendMessage(pretty.prettyChat("You don't have permission to do that!"));
			} else if (args.length == 2 && args[0].equalsIgnoreCase("setLocation")) {
				//
				if (sender.hasPermission("mafia.admin")) {
					if (args[1].equalsIgnoreCase("lobby")) {
						if (sender instanceof Player) {
							Player player = (Player) sender;
							this.getConfig().set("lobby.x", player.getLocation().getBlockX());
							this.getConfig().set("lobby.y", player.getLocation().getBlockY());
							this.getConfig().set("lobby.z", player.getLocation().getBlockZ());
							player.sendMessage(pretty.prettyChat("Set lobby location"));
							getLogger().info(pretty.prettyConsoleInfo("set lobby location"));
						} else {
							sender.sendMessage(pretty.prettyChat("You must be a player to use this command."));
						}
					} else if (args[1].equalsIgnoreCase("execution")) {
						if (sender instanceof Player) {
							Player player = (Player) sender;
							this.getConfig().set("execution.x", player.getLocation().getBlockX());
							this.getConfig().set("execution.y", player.getLocation().getBlockY());
							this.getConfig().set("execution.z", player.getLocation().getBlockZ());
							player.sendMessage(pretty.prettyChat("Set execution watching location"));
							getLogger().info(pretty.prettyConsoleInfo("set execution watching location"));
						} else {
							sender.sendMessage(pretty.prettyChat("You must be a player to use this command."));
						}
					}
				} else {
					sender.sendMessage(pretty.prettyChat("You don't have permission to do that!"));
				}
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("mafia")) {
			return true;
		} else if (cmd.getName().equalsIgnoreCase("town")) {
			if (args.length == 0) {
				sender.sendMessage(pretty.prettyChat("Not enough arguments! Do /mafia to see the commands for your role."));
			} else if (!(sender instanceof Player)) {
				sender.sendMessage(pretty.prettyChat("You must be a player to use this command."));
			} else if (args.length == 2 && args[0].equalsIgnoreCase("guard")) {
				Player player = (Player) sender;
				if (!(control.roles.get(player) == "Bodyguard")) {
					sender.sendMessage(pretty.prettyChat("You must be the bodyguard to use this command."));
					return true;
				} else if (control.grave.containsKey(player)) {
					sender.sendMessage(pretty.prettyChat("You must be alive to use this command."));
					return true;
				}
				Player protect = Bukkit.getPlayer(args[1]);
				if (!protect.isOnline()) {
					sender.sendMessage(pretty.prettyChat("Player must be online to use this command."));
					return true;
				} else if (control.grave.containsKey(protect)) {
					sender.sendMessage(pretty.prettyChat("Player must be alive to use this command."));
					return true;
				}
				HashMap<Player, String> save = town.Bodyguard(control.save, protect);
				control.save = save;	 
			} else if (args.length == 2 && args[0].equalsIgnoreCase("investigate")) {
				Player player = (Player) sender;
				if (!(control.roles.get(player) == "Investigator")) {
					sender.sendMessage(pretty.prettyChat("You must be the investigator to use this command."));
					return true;
				} else if (control.grave.containsKey(player)) {
					sender.sendMessage(pretty.prettyChat("You must be alive to use this command."));
					return true;
				}
				Player protect = Bukkit.getPlayer(args[1]);
				if (!protect.isOnline()) {
					sender.sendMessage(pretty.prettyChat("Player must be online to use this command."));
					return true;
				} else if (control.grave.containsKey(protect)) {
					sender.sendMessage(pretty.prettyChat("Player must be alive to use this command."));
					return true;
				}
				String message = town.Investigator(control.roles, protect);
				sender.sendMessage(pretty.prettyChat(message));
			}
			return true;
		}
		return false;
	}
}