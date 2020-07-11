package jkku.mc.introgo;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import jkku.mc.introgo.commands.MainC;
import jkku.mc.introgo.events.Join;

/**
 * IntroGo 
 * @author Jenrikku
 */
public class Go extends JavaPlugin {
	PluginDescriptionFile desc = getDescription();
	public final String version = desc.getVersion();
	public final String sufix = "[IntroGo] ";
	
	private final File configFile = new File(getDataFolder(), "config.yml");
	
	public void onEnable() {
		if(!configFile.exists()) {
			getConfig().options().copyDefaults(true);
			saveConfig();
		}
		
		//Commands
		getCommand("go-adm").setExecutor(new MainC(this));
		
		//PermissionMessages
		getCommand("go-adm").setPermissionMessage(sufix+ChatColor.RED+"You don't have permissions!");
		
		//Events
		getServer().getPluginManager().registerEvents(new Join(this), this);
	}
}
