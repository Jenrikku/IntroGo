package jkku.introgo.resource;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import jkku.introgo.Go;

public class Events {
	private Go plugin;
	
	public Events(Go plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void atJoin(PlayerJoinEvent event){
		FileConfiguration login = plugin.getLogin();
		if(login.getBoolean("Spawn-allowed")) {
			event.getPlayer().teleport(new Location(
					plugin.getServer().getWorld(login.getString("Spawn.world")),
					Double.valueOf(login.getString("Spawn.x")),
					Double.valueOf(login.getString("Spawn.y")),
					Double.valueOf(login.getString("Spawn.z")),
					Float.valueOf(login.getString("Spawn.yaw")),
					Float.valueOf(login.getString("Spawn.pitch"))));
		}
		if(login.getBoolean("Login-message-allowed")) {
			for(int i=0;i<login.getStringList("Login-message").size();i++){
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', login.getStringList("Login-message").get(i).replace("<player>", event.getPlayer().getName())));
			} 
		}
		return;
	}
}
