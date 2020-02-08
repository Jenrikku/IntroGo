package jkku.introgo.resource;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import jkku.introgo.Go;
import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;
import net.gravitydevelopment.updater.Updater.UpdateType;

public class Commands implements TabExecutor {
	private Go plugin;
	
	public Commands(Go plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration messages = plugin.getMessages();
		FileConfiguration teleports = plugin.getMessages();
		FileConfiguration config = plugin.getConfig();
		List<String> mNames = messages.getStringList("Messages.names");
		switch(command.getName()) {
		case "m":
			if(args.length > 0 && args.length < 2 && mNames.contains(args[0]) && messages.contains("Messages.messages."+args[0])) {
				List<String> mtoSend = messages.getStringList("Messages.messages."+args[0]);
				for(int i=0;i<mtoSend.size();i++) {
					if(sender instanceof Player) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', mtoSend.get(i)));
					} else {
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', mtoSend.get(i)));
					}
				}
			} else {
				if(sender instanceof Player) {
					sender.sendMessage("Please, use only 1 argument that exists. (/m <something>)");
					if(sender.hasPermission("ig.admin")) {
						sender.sendMessage(plugin.name+ChatColor.YELLOW+"You have admin permissions, please check the messages.yml");
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"Please, use only 1 argument that exists. (m <something>)");
					Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"Check the 'messages.yml'");
				}
			}
		break;
		case "tpt":
			if(args.length > 0 && args.length < 2 && teleports.getStringList("Teleports.names").contains(args[1])) {
				if(sender instanceof Player) {
					((Player) sender).teleport(new Location(
							plugin.getServer().getWorld(teleports.getString("Teleports.teleports."+args[1]+".world")),
							Double.valueOf(teleports.getString("Teleports.teleports."+args[1]+".x")),
							Double.valueOf(teleports.getString("Teleports.teleports."+args[1]+".y")),
							Double.valueOf(teleports.getString("Teleports.teleports."+args[1]+".z")),
							Float.valueOf(teleports.getString("Teleports.teleports."+args[1]+".yaw")),
							Float.valueOf(teleports.getString("Teleports.teleports."+args[1]+".pitch"))));
					if(config.getBoolean("Teleport-message-allowed")) { 
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Teleport-message").replace("<name>", args[0])));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"Please, use only 1 argument that exists. (tpt <something>)");
				}
			} else {
				if(sender instanceof Player) {
					sender.sendMessage("Please, use only 1 argument that exists. (/tpt <something>)");
					if(sender.hasPermission("ig.admin")) {
						sender.sendMessage(plugin.name+ChatColor.YELLOW+"You have admin permissions, please check the teleports.yml");
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"Please, use only 1 argument that exists. (tpt <something>)");
					Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"Check the 'teleports.yml'");
				}
			}
		break;
		case "ig-admin":
			switch(args.length) {
			case 1:
				switch(args[0]) {
				case "reload":
					if(sender instanceof Player) {
						plugin.reloadMessages();
						plugin.reloadConfig();
						sender.sendMessage(plugin.name+ChatColor.GREEN+"Reloaded!");
					} else {
						plugin.reloadMessages();
						plugin.reloadConfig();
						Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.GREEN+"Reloaded!");
					}
				break;
				case "list":
					if(sender instanceof Player) {
						for(int i=0;i<mNames.size();i++) {
							sender.sendMessage(plugin.name+ChatColor.YELLOW+"/m "+mNames.get(i));
						}
					} else {
						for(int i=0;i<mNames.size();i++) {
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"/m "+mNames.get(i));
						}
					}
					
				break;
				case "update":
					Updater updater = new Updater(plugin.thisPlugin, 359922, plugin.mainFile, UpdateType.NO_DOWNLOAD, false);
					UpdateResult result = updater.getResult();
					if(sender instanceof Player) {
						sender.sendMessage(plugin.name+ChatColor.DARK_GREEN+"v. "+ChatColor.GREEN+plugin.version+ChatColor.YELLOW+" ~ "+ChatColor.AQUA+"JkKU");
						sender.sendMessage("Checking for updates...");
						switch(result) {
						case UPDATE_AVAILABLE:
							sender.sendMessage(ChatColor.GREEN+"[!]"+ChatColor.DARK_GREEN+" There's a new version available, if you want to download it now, please use '/ig-admin update now' or restart the server.");
						break;
						case NO_UPDATE:
							sender.sendMessage(ChatColor.DARK_GREEN+"You're using the latest version.");
						break;
						default:
							sender.sendMessage(ChatColor.RED+"There seems to be a problem with the updating system... "+ChatColor.WHITE+result);
						break;
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
				case "editpt":
					if(sender instanceof Player) {
						sender.sendMessage(plugin.name+ChatColor.YELLOW+"/ig-admin editpt <create / delete / restore> <name>");
					} else {
						Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"ig-admin editpt <create / delete / restore> <name>");
					}
				break;
				default:
					if(sender instanceof Player) {
						sender.sendMessage(plugin.name+ChatColor.YELLOW+"This admin command doesn't exists.");
					} else {
						Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"This admin command doesn't exists.");
					}
				break;
				}
			case 2:
				if(args[0] == "editpt") {
					if(sender instanceof Player) {
						sender.sendMessage(plugin.name+ChatColor.YELLOW+"/ig-admin editpt <create / delete / restore> <name>");
					} else {
						Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"ig-admin editpt <create / delete / restore> <name>");
					}
				}
				if(args[0] == "update" && args[1] == "now") {
					if(sender instanceof Player) {
						sender.sendMessage(plugin.name+ChatColor.YELLOW+"Downloading...");
						Updater updateNow = new Updater(plugin.thisPlugin, 359922, plugin.mainFile, UpdateType.DEFAULT, false);
						UpdateResult resultNow = updateNow.getResult();
						switch(resultNow) {
						case SUCCESS:
							sender.sendMessage(ChatColor.DARK_GREEN+"The plugin has been updated, reload the server to take effect.");
						break;
						case NO_UPDATE:
							sender.sendMessage(ChatColor.DARK_GREEN+"You're using the latest version.");
						break;
						default:
							sender.sendMessage(ChatColor.RED+"There seems to be a problem with the updating system... "+ChatColor.WHITE+resultNow);
						break;
						}
					} else {
						Updater updateNow = new Updater(plugin.thisPlugin, 359922, plugin.mainFile, UpdateType.DEFAULT, false);
						UpdateResult resultNow = updateNow.getResult();
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
				}
				if(args[0] == "reload" || args[0] == "list") {
					if(sender instanceof Player) {
						sender.sendMessage(plugin.name+ChatColor.YELLOW+"Please, use only 1 argument.");
					} else {
						Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"Please, use only 1 argument.");
					}
				}
			break;
			case 3:
				if(args[0] == "editpt") {
					if(args[1] == "create" && !teleports.getStringList("Teleports.names").contains(args[2])) {
						if(sender instanceof Player) {
							teleports.getStringList("Teleports.names").add(args[2]);
							teleports.set("Teleports.teleports."+args[2]+".world", ((Player) sender).getLocation().getWorld().getName());
							teleports.set("Teleports.teleports."+args[2]+".x", ((Player) sender).getLocation().getX());
							teleports.set("Teleports.teleports."+args[2]+".y", ((Player) sender).getLocation().getY());
							teleports.set("Teleports.teleports."+args[2]+".z", ((Player) sender).getLocation().getZ());
							teleports.set("Teleports.teleports."+args[2]+".pitch", ((Player) sender).getLocation().getPitch());
							teleports.set("Teleports.teleports."+args[2]+".yaw", ((Player) sender).getLocation().getYaw());
							plugin.saveConfig();
							sender.sendMessage(plugin.name+ChatColor.GREEN+"You have created the '"+args[2]+"' teleport.");
						} else {
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.RED+"You need to be a player to do that.");
						}
					} else if(args[1] == "create" && teleports.getStringList("Teleports.names").contains(args[2])) {
						if(sender instanceof Player) {
							sender.sendMessage(plugin.name+ChatColor.RED+"This teleport already exists. Nothing was created.");
						} else {
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.RED+"This teleport already exists. Nothing was created.");
						}
					}
					if(args[1] == "delete" && teleports.getStringList("Teleports.names").contains(args[2])) {
						teleports.getStringList("Teleports.names").set(teleports.getStringList("Teleports.names").indexOf(args[2]), null);
						plugin.saveConfig();	
						if(sender instanceof Player) {
							sender.sendMessage(plugin.name+ChatColor.YELLOW+"You have deleted the '"+args[2]+"' teleport.");
							sender.sendMessage(ChatColor.RED+"This only deletes the command from the game, but the teleport stills in 'teleports.yml'");
						} else {
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"You have deleted the '"+args[2]+"' teleport.");
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"This only deletes the command from the game, but the teleport stills in 'teleports.yml'");
						}
					} else if(args[1] == "delete" && !teleports.getStringList("Teleports.names").contains(args[2])) {
						if(sender instanceof Player) {
							sender.sendMessage(plugin.name+ChatColor.RED+"This teleport doesn't exist.");
						} else {
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.RED+"This teleport doesn't exist.");
						}
					}
					if(args[1] == "restore" && teleports.getString("Teleports.teleports."+args[2]+".x") != null) {
						teleports.getStringList("Teleports.names").add(args[2]);
						if(sender instanceof Player) {
							sender.sendMessage(plugin.name+ChatColor.GREEN+"Success!");
						} else {
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.GREEN+"Success!");
						}
					} else {
						if(sender instanceof Player) {
							sender.sendMessage(plugin.name+ChatColor.RED+"There isn't any teleport to restore.");
						} else {
							Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.RED+"There isn't any teleport to restore.");
						}
					}
					if(sender instanceof Player) {
						sender.sendMessage(plugin.name+ChatColor.YELLOW+"/ig-admin editpt <create / delete / restore> <name>");
					} else {
						Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"ig-admin editpt <create / delete / restore> <name>");
					}
				}
			break;
			default: 
				if(sender instanceof Player) {
					sender.sendMessage(plugin.name+ChatColor.YELLOW+"Too many arguments!");
				} else {
					Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"Too many arguments!");
				}
			break;
			}
		break;
		default: break;
		}
		return true;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		FileConfiguration teleports = plugin.getMessages();
		switch(command.getName()) {
		case "m":
			FileConfiguration messages = plugin.getMessages();
			return messages.getStringList("Messages.names");
		case "tpt":
			return teleports.getStringList("Teleports.names");
		case "ig-admin":
			List<String> adminList = new ArrayList<>();
			switch(args.length) {
			case 0:
				adminList.add("reload");
				adminList.add("list");
				adminList.add("update");
				adminList.add("editpt");
				return adminList;
			case 1:
				if(args[0] == "update") {
					adminList.add("now");
				}
				if(args[0] == "editpt") {
					adminList.add("create");
					adminList.add("delete");
					adminList.add("restore");
				}
				return adminList;
			case 2:
				if(args[1] == "delete") {
					return teleports.getStringList("Teleports.names");
				} else {
					break;
				}
			default:
				return null;
			}
		default:
			return null;
		}
	}
}