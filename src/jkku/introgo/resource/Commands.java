package jkku.introgo.resource;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import jkku.introgo.Go;
import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;
import net.gravitydevelopment.updater.Updater.UpdateType;

public class Commands implements CommandExecutor {
	private Go plugin;
	
	public Commands(Go plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration config = plugin.getConfig();
		FileConfiguration messages = plugin.getMessages();
		List<String> mCommands = messages.getStringList("Messages.commands");
		List<String> mMessages = messages.getStringList("Messages.messages");
		switch(command.getName()) {
		case "m":
			if(args.length > 0 && args.length < 2 && mCommands.contains(args[0])) {
				String mName = mMessages.get(mCommands.indexOf(args[0]));
				List<String> mtoSend = messages.getStringList("Messages.text."+mName);
				for(int i=0;i<mtoSend.size();i++) {
					if(sender instanceof Player) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', mtoSend.get(i)));
					} else {
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', mtoSend.get(i)));
					}
				}
			} else {
				if(sender instanceof Player) {
					sender.sendMessage("Please, use only 1 argument that exists. ( /m <something> )");
				} else {
					Bukkit.getConsoleSender().sendMessage(plugin.name+"Please, use only 1 argument that exists. ( m <something> )");
				}
			}
		break;
		case "ig-admin": 
			if(args.length > 0) {
				switch(args[0]) {
				case "reload":
					if(sender instanceof Player) {
						plugin.reloadMessages();
						plugin.reloadConfig();
						sender.sendMessage(plugin.name+ChatColor.GREEN+"Reloaded!");
						Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"The player "+ChatColor.BOLD+sender+ChatColor.RESET+ChatColor.YELLOW+" has reloaded the plugin.");
					} else {
						plugin.reloadMessages();
						plugin.reloadConfig();
						Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.GREEN+"Reloaded!");
					}
				break;
				case "list":
					if(sender instanceof Player) {
						for(int i=0;i<mCommands.size();i++) {
							sender.sendMessage(plugin.name+ChatColor.YELLOW+"/m "+mCommands.get(i)+" -> "+mMessages.get(i));
						}
						if(config.getBoolean("Warnings")) {
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.GOLD+"The player "+ChatColor.BOLD+sender.getName()+ChatColor.RESET+ChatColor.GOLD+" saw the commands list.");
						}
					} else {
						for(int i=0;i<mCommands.size();i++) {
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"/m "+mCommands.get(i)+" -> "+mMessages.get(i));
						}
					}
					
				break;
				case "update":
					Updater updater = new Updater(plugin.thisPlugin, 359922, plugin.mainFile, UpdateType.NO_DOWNLOAD, false);
					UpdateResult result = updater.getResult();
					if(sender instanceof Player) {
						sender.sendMessage(plugin.name+ChatColor.DARK_GREEN+"v. "+ChatColor.GREEN+plugin.version+ChatColor.GOLD+" ~ "+ChatColor.AQUA+"JkKU");
						sender.sendMessage("Checking for updates...");
						switch(result) {
						case UPDATE_AVAILABLE:
							sender.sendMessage(ChatColor.GREEN+"[!]"+ChatColor.DARK_GREEN+" There's a new version available, if you want to download it now, please use '/ig-admin updatenow' or restart the server.");
						break;
						case NO_UPDATE:
							sender.sendMessage(ChatColor.DARK_GREEN+"You're using the latest version.");
						break;
						default:
							sender.sendMessage(ChatColor.RED+"There seems to be a problem with the updating system... "+ChatColor.WHITE+result);
						break;
						}
						if(config.getBoolean("Warnings")) {
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.GOLD+"The player "+ChatColor.BOLD+sender.getName()+ChatColor.RESET+ChatColor.GOLD+" has checked for updates available.");
						}
					} else {
						Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.DARK_GREEN+"v. "+ChatColor.GREEN+plugin.version+ChatColor.GOLD+" ~ "+ChatColor.AQUA+"JkKU");
						Bukkit.getConsoleSender().sendMessage("Checking for updates...");
						switch(result) {
						case UPDATE_AVAILABLE:
							Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"[!]"+ChatColor.DARK_GREEN+" There's a new version available, if you want to download it now, please use '/ig-admin updatenow' or restart the server.");
						break;
						case NO_UPDATE:
							Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN+"You're using the latest version.");
						break;
						default:
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"There seems to be a problem with the updating system... "+ChatColor.WHITE+result);
						break;
						}
					}
				break;
				case "updatenow":
					Updater updateNow = new Updater(plugin.thisPlugin, 359922, plugin.mainFile, UpdateType.DEFAULT, false);
					UpdateResult resultNow = updateNow.getResult();
					if(sender instanceof Player) {
						sender.sendMessage(plugin.name+"Downloading...");
						switch(resultNow) {
						case SUCCESS:
							sender.sendMessage(ChatColor.DARK_GREEN+"The plugin has been updated, reload the server to take effect.");
							if(config.getBoolean("Warnings")) {
								Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.GOLD+"The player "+ChatColor.BOLD+sender.getName()+ChatColor.RESET+ChatColor.GOLD+" has updated the plugin.");
							}
						break;
						case NO_UPDATE:
							sender.sendMessage(ChatColor.DARK_GREEN+"You're using the latest version.");
						break;
						default:
							sender.sendMessage(ChatColor.RED+"There seems to be a problem with the updating system... "+ChatColor.WHITE+resultNow);
							if(config.getBoolean("Warnings")) {
								Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.GOLD+"The player "+ChatColor.BOLD+sender.getName()+ChatColor.RESET+ChatColor.GOLD+" tried to update the server but he/she gets a bad result.");
							}
						break;
						}
					} else {
						switch(resultNow) {
						case SUCCESS:
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.DARK_GREEN+"The plugin has been updated, reload the server to take effect.");
						break;
						case NO_UPDATE:
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.DARK_GREEN+"You're using the latest version.");
						break;
						default:
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.RED+"There seems to be a problem with the updating system... "+ChatColor.WHITE+resultNow);
						break;
						}
					}
				break;
				default:
					if(sender instanceof Player) {
						sender.sendMessage(plugin.name+"This admin command doesn't exists.");
					} else {
						Bukkit.getConsoleSender().sendMessage(plugin.name+"This admin command doesn't exists.");
					}
				break;
				}
			} else {
				if(sender instanceof Player) {
					sender.sendMessage(plugin.name+"Please, use only 1 argument. ( /ig-admin <something> )");
				} else {
					Bukkit.getConsoleSender().sendMessage(plugin.name+"Please, use only 1 argument. ( ig-admin <something> )");
				}
			}
		break;
		default: break;
		}
		return true;
	}
}
