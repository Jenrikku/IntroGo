package jkku.introgo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import jkku.introgo.resource.Commands;
import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;
import net.gravitydevelopment.updater.Updater.UpdateType;

public class Go extends JavaPlugin {
	PluginDescriptionFile pdffile = getDescription();
	public Plugin thisPlugin;
	public File mainFile;
	public final String version = pdffile.getVersion();
	public final String name = ChatColor.GOLD+"["+ChatColor.GREEN+"Intro"+ChatColor.YELLOW+"Go"+ChatColor.GOLD+"] ";
	public final String nameLite = "[IntroGo] ";
	
	public String pathConfig;
	private FileConfiguration messages = null;
	private File messagesFile = null;
	private FileConfiguration teleports = null;
	private File teleportsFile = null;
	
	public void onEnable() {
		thisPlugin = this;
		mainFile = this.getFile();
		//check updates
		Updater updater = new Updater(this, 359922, mainFile, UpdateType.DEFAULT, false);
		UpdateResult result = updater.getResult();
		switch (result) {
			case SUCCESS: Bukkit.getConsoleSender().sendMessage(nameLite+ChatColor.GREEN+"Update completed!"+ChatColor.GOLD+" Please, restart/reload the server to take effect."); break;
			case NO_UPDATE: Bukkit.getConsoleSender().sendMessage(nameLite+ChatColor.GREEN+"Up to date!"); break;
			case FAIL_DBO: Bukkit.getConsoleSender().sendMessage(nameLite+ChatColor.RED+"Uh? Is 'dev.bukkit.com' offline?"); break;
			case UPDATE_AVAILABLE: Bukkit.getConsoleSender().sendMessage(nameLite+ChatColor.RED+"What? The developer used an incorrect type."+ChatColor.WHITE+" ("+result+")"); break;
			default: Bukkit.getConsoleSender().sendMessage(nameLite+ChatColor.RED+"There seems to be a problem with the updating system... "+ChatColor.WHITE+result); break;
		}
		//display plugin name
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"      ___   ");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"  |  |      "+name+ChatColor.DARK_GREEN+"v. "+ChatColor.GREEN+version+ChatColor.YELLOW+" ~ "+ChatColor.AQUA+"JkKU");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"  |  |  _   "+ChatColor.DARK_GRAY+"Thanks for using my plugin.");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"  |  |___|  ");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"            ");
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		//config.yml
		Bukkit.getConsoleSender().sendMessage(nameLite+"Adding 'config.yml'...");
		File config = new File(this.getDataFolder(), "config.yml");
		pathConfig = config.getPath();
		if(!config.exists()) {
			Bukkit.getConsoleSender().sendMessage(nameLite+"Creating 'config.yml'...");
			copy(getClass().getResourceAsStream("/config.yml"), getDataFolder()+"/config.yml");
		}
		//messages.yml
		Bukkit.getConsoleSender().sendMessage(nameLite+"Adding 'messages.yml'...");
		messagesFile = new File(this.getDataFolder(), "messages.yml");
		if(!messagesFile.exists()) {
			Bukkit.getConsoleSender().sendMessage(nameLite+"Creating 'messages.yml'...");
			copy(getClass().getResourceAsStream("/messages.yml"), getDataFolder()+"/messages.yml");
		}
		//teleports.yml
		Bukkit.getConsoleSender().sendMessage(nameLite+"Adding 'teleports.yml'...");
		teleportsFile = new File(this.getDataFolder(), "teleports.yml");
		if(!teleportsFile.exists()) {
			Bukkit.getConsoleSender().sendMessage(nameLite+"Creating 'teleports.yml'...");
			copy(getClass().getResourceAsStream("/teleports.yml"), getDataFolder()+"/teleports.yml");
		}
		//commands
		this.getCommand("m").setExecutor(new Commands(this));
		this.getCommand("ig-admin").setExecutor(new Commands(this));
		//permission messages
		String perm = getConfig().getString("Permission-message");
		this.getCommand("ig-admin").setPermissionMessage(name+ChatColor.WHITE+ChatColor.translateAlternateColorCodes('&', perm));
	}
	
	public FileConfiguration getMessages() {
		if(messages == null) {
			reloadMessages();
		}
		return messages;
	}
	
	public void reloadMessages() {
		if(messages == null) {
			messagesFile = new File(getDataFolder(), "messages.yml");
		}
		messages = YamlConfiguration.loadConfiguration(messagesFile);
		Reader defConfigStream;
		try {
			defConfigStream = new InputStreamReader(this.getResource("messages.yml"), "UTF8");
			if(defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				messages.setDefaults(defConfig);
			}
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getTeleports() {
		if(teleports == null) {
			reloadMessages();
		}
		return teleports;
	}
	
	public void reloadTeleports() {
		if(teleports == null) {
			teleportsFile = new File(getDataFolder(), "teleports.yml");
		}
		teleports = YamlConfiguration.loadConfiguration(teleportsFile);
		Reader defConfigStream;
		try {
			defConfigStream = new InputStreamReader(this.getResource("teleports.yml"), "UTF8");
			if(defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				teleports.setDefaults(defConfig);
			}
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	//From: https://stackoverflow.com/questions/10308221/how-to-copy-file-inside-jar-to-outside-the-jar/44077426#44077426
	public static boolean copy(InputStream source , String destination) {
		try {
			Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return true;
    }
}
