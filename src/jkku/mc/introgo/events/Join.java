package jkku.mc.introgo.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import jkku.mc.introgo.Go;

/**
 * IntroGo / Events / Join
 * @author Jenrikku
 */
public class Join implements Listener {
	private Server server;
	private FileConfiguration config;
	
	public Join(Go go) {
		server = go.getServer();
		config = go.getConfig();
	}
	
	@EventHandler
	public void atJoin(PlayerJoinEvent event) {
		Bukkit.getConsoleSender().sendMessage("Player loged");
		if(config.getBoolean("initialSpawn.enabled")) {
			event.getPlayer().teleport(new Location(
					server.getWorld((config.getString("initialSpawn.world"))),
					config.getDouble("initialSpawn.x"),
					config.getDouble("initialSpawn.y"),
					config.getDouble("initialSpawn.z"),
					Float.valueOf(config.getString("initialSpawn.yaw")),
					Float.valueOf(config.getString("initialSpawn.pitch"))));
		}
		
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
}
