package com.theartemismc.Mafia;

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
    //TODO: Commands
    
}