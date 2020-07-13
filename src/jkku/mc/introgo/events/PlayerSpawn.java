package jkku.mc.introgo.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import jkku.mc.introgo.Go;

/**
 * IntroGo / Events / Join
 * @author Jenrikku
 */
public class PlayerSpawn implements Listener {
	private Server server;
	private FileConfiguration config;
	
	public PlayerSpawn(Go go) {
		server = go.getServer();
		config = go.getConfig();
	}
	
	@EventHandler
	public void PlayerSpawnLocationEvent(Player player, Location spawnLocation) {
		Bukkit.getConsoleSender().sendMessage(player.getName() + " loged in.");
		if(Boolean.getBoolean(config.getString("initialSpawn.enabled"))) {
			teleport2Spawn(player);
		}
		
		if(config.getBoolean("initialMessage.enabled")) {
			final List<String> message = config.getStringList("initialMessage.message");
			for(byte x = 0; x != message.size(); x++) player.sendMessage(
					ChatColor.translateAlternateColorCodes('&', message.get(x))
					.replace("%player%", player.getName())
					.replace("%online%", String.valueOf(server.getOnlinePlayers().size()))
					.replace("%max%", String.valueOf(server.getMaxPlayers()))
					.replace("%available%", String.valueOf(server.getMaxPlayers() - server.getOnlinePlayers().size()))
					.replace("\\\"", "\'"));
		}
		
		return;
	}
	
	@EventHandler
	public void PlayerRespawnEvent(Player player, Location respawnLocation, boolean isBedSpawn, boolean isAnchorSpawn) {
		if(Boolean.getBoolean(config.getString("initialSpawn.enabledWhenRespawn"))) {
			teleport2Spawn(player);
		}
	}
	
	private void teleport2Spawn(Player player) {
		player.teleport(new Location(
				server.getWorld((config.getString("initialSpawn.world"))),
				config.getDouble("initialSpawn.x"),
				config.getDouble("initialSpawn.y"),
				config.getDouble("initialSpawn.z"),
				Float.valueOf(config.getString("initialSpawn.yaw")),
				Float.valueOf(config.getString("initialSpawn.pitch"))));
	}
}
