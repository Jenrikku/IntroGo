package jkku.introgo;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

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
	public String version = pdffile.getVersion();
	public String name = ChatColor.GOLD+"["+ChatColor.GREEN+"Intro"+ChatColor.YELLOW+"Go"+ChatColor.GOLD+"] ";
	public String nameLite = "[IntroGo] ";
	
	public String pathConfig;
	private FileConfiguration messages = null;
	private File messagesFile = null;
	
	public void onEnable() {
		thisPlugin = this;
		mainFile = this.getFile();
		Updater updater = new Updater(this, 359922, mainFile, UpdateType.DEFAULT, false);
		UpdateResult result = updater.getResult();
		switch (result) {
			case SUCCESS: Bukkit.getConsoleSender().sendMessage(nameLite+ChatColor.GREEN+"Update completed!"+ChatColor.GOLD+" Please, restart/reload the server to take effect."); break;
			case NO_UPDATE: Bukkit.getConsoleSender().sendMessage(nameLite+ChatColor.GREEN+"Up to date!"); break;
			case FAIL_DBO: Bukkit.getConsoleSender().sendMessage(nameLite+ChatColor.RED+"Uh? Is 'dev.bukkit.com' offline?"); break;
			case UPDATE_AVAILABLE: Bukkit.getConsoleSender().sendMessage(nameLite+ChatColor.RED+"What? The developer used an incorrect type."+ChatColor.WHITE+" ("+result+")"); break;
			default: Bukkit.getConsoleSender().sendMessage(nameLite+ChatColor.RED+"There seems to be a problem with the updating system... "+ChatColor.WHITE+result); break;
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"      ___   ");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"  |  |      "+name+ChatColor.DARK_GREEN+"v. "+ChatColor.GREEN+version+ChatColor.GOLD+" ~ "+ChatColor.AQUA+"JkKU");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"  |  |  _   "+ChatColor.DARK_GRAY+"Thanks for using my plugin.");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"  |  |___|  ");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"            ");
		//config.yml
		Bukkit.getConsoleSender().sendMessage(nameLite+"Adding 'config.yml'...");
		File config = new File(this.getDataFolder(), "config.yml");
		pathConfig = config.getPath();
		if(!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
		//messages.yml
		Bukkit.getConsoleSender().sendMessage(nameLite+"Adding 'messages.yml'...");
		messagesFile = new File(this.getDataFolder(), "messages.yml");
		if(!messagesFile.exists()) {
			this.getMessages().options().copyDefaults(true);
			try {
				messages.save(messagesFile);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		//FileConfiguration messages = getMessages();
		//List<String> mCommands = messages.getStringList("Messages.commands");
		//for(int i=0;i<mCommands.size();i++){
		//	String command = mCommands.get(i);
		//	this.getCommand(command).setExecutor(new Messages(this));
		//}
		this.getCommand("m").setExecutor(new Commands(this));
		this.getCommand("ig-admin").setExecutor(new Commands(this));
		this.getCommand("ig-admin").setPermissionMessage(name+ChatColor.RED+"Sorry, you can't do that.");
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
}
