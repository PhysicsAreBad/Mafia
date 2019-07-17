package com.theartemismc.Mafia;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Mafia extends JavaPlugin {
	Message pretty = new Message();
	
	@Override
    public void onEnable() {
		this.saveDefaultConfig();
		this.getConfig();
		getServer().getPluginManager().registerEvents(new Lobby(this), this);
        getLogger().info(pretty.prettyConsoleInfo("Started Mafia V0.01, Written by PhysicsAreBad for The Artemis Network"));
    }
	
	@Override
	public void onDisable() {
		this.saveConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mafiaadmin")) { // If the player typed /mafiaadmin then do the following, note: If you only registered this executor for one command, you don't need this
			if (args.length == 0){
				sender.sendMessage(pretty.prettyChat("XD"));
			} if (args.length == 2 && args[0].equalsIgnoreCase("setLocation")) {
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
				return true;
			} //If this has happened the function will return true. 
	        // If this hasn't happened the value of false will be returned. 
		}
		return false;
	}
}