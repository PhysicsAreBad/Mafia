package com.theartemismc.Mafia;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Lobby implements Listener{
	
	private int playerCount = 0;
	private int time = 15;
	private boolean countdownEnable = true;
	private boolean countdownActive = false;
	private Location lobbySpawn;
	
	public Message pretty = new Message();
	public GameController control = new GameController();
	private JavaPlugin plugin;
	
	private int taskID;
	
	public Lobby(JavaPlugin plugin) {
		this.plugin = plugin;
		lobbySpawn.setX(plugin.getConfig().getDouble("lobby.x"));
		lobbySpawn.setY(plugin.getConfig().getDouble("lobby.y"));
		lobbySpawn.setZ(plugin.getConfig().getDouble("lobby.z"));
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		if (countdownEnable) {
			if (plugin.getConfig().getInt("lobby.x") != 0) {
				event.getPlayer().teleport(lobbySpawn);
				event.getPlayer().setGameMode(GameMode.ADVENTURE);
			}
	    	playerCount++;
	    	Bukkit.broadcastMessage(pretty.prettyChat(event.getPlayer().getName() + " has joined! " + playerCount + "/16 players in the server."));
	    	if (playerCount >= 12) {
	    		initGame();
	    	}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		playerCount--;
		Bukkit.broadcastMessage(pretty.prettyChat(event.getPlayer().getName() + " has left! " + playerCount + "/16 players in the server."));
		if (Bukkit.getOnlinePlayers().size() < 12 && countdownActive) {
			stopTimer();
			Bukkit.broadcastMessage(pretty.prettyChat("Countdown Stopped because there isn't enough players!"));
		}
	}
	
	private void initGame() {
		taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
               public void run() {
				time--;
				if (time == 0) {
					Bukkit.broadcastMessage(pretty.prettyChat("Game Start"));
					new BukkitRunnable() {
			            @Override
			            public void run() {
			               control.startGame();
			            }
			        }.runTaskLater(plugin, 1);
			        time = 15;
			        plugin.getServer().setWhitelist(true);
					stopTimer();				
				} else {
					Bukkit.broadcastMessage(pretty.prettyChat("Game starting in ") + time + " seconds"); 
				}
               }
		}, 0L, 20L);
	}
	
	public void stopTimer() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
	void toggleCountdown() {
		countdownEnable = !countdownEnable;
	}
	
}
	