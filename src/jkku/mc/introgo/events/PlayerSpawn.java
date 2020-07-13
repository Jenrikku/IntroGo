package jkku.mc.introgo.events;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

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
	public void atJoin(PlayerJoinEvent event) {
		if(config.getBoolean("initialMessage.enabled")) {
			final List<String> message = config.getStringList("initialMessage.message");
			for(byte x = 0; x != message.size(); x++) event.getPlayer().sendMessage(
					ChatColor.translateAlternateColorCodes('&', message.get(x))
					.replace("%player%", event.getPlayer().getName())
					.replace("%online%", String.valueOf(server.getOnlinePlayers().size()))
					.replace("%max%", String.valueOf(server.getMaxPlayers()))
					.replace("%available%", String.valueOf(server.getMaxPlayers() - server.getOnlinePlayers().size()))
					.replace("\\\"", "\'"));
		}
		
		return;
	}
	
	@EventHandler
	public void atJoinSpawn(PlayerSpawnLocationEvent event) {
		if(config.getString("initialSpawn.enabled").contains("true")) {
			event.setSpawnLocation(new Location(
					server.getWorld((config.getString("initialSpawn.world"))),
					config.getDouble("initialSpawn.x"),
					config.getDouble("initialSpawn.y"),
					config.getDouble("initialSpawn.z"),
					Float.valueOf(config.getString("initialSpawn.yaw")),
					Float.valueOf(config.getString("initialSpawn.pitch"))));
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void atDie(PlayerRespawnEvent event) {
		if(config.getString("initialSpawn.enabledWhenRespawn").contains("true")) {
			event.setRespawnLocation(new Location(
					server.getWorld((config.getString("initialSpawn.world"))),
					config.getDouble("initialSpawn.x"),
					config.getDouble("initialSpawn.y"),
					config.getDouble("initialSpawn.z"),
					Float.valueOf(config.getString("initialSpawn.yaw")),
					Float.valueOf(config.getString("initialSpawn.pitch"))));
		}
	}
}
