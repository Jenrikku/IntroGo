package jkku.mc.introgo.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import jkku.mc.introgo.Go;
import net.md_5.bungee.api.ChatColor;

public class MainC implements TabExecutor {
	private Go go;
	private FileConfiguration config;
	private String sufix;
	private String version;
	
	public MainC(Go go) {
		this.go = go;
		config = go.getConfig();
		sufix = go.sufix;
		version = go.version;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> result = new ArrayList<String>();
		switch(args.length) {
		case 1:
			result.add("setspawn");
			result.add("info");
			result.add("reload");
		}
		return result;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch(args.length) {
		default:
			sender.sendMessage(sufix+ChatColor.BLUE+"Admin command");
			return false;
		case 1:
			switch(args[0]) {
			case "setspawn":
				if(sender instanceof Player) {
					final Location l = ((Player) sender).getLocation();
					
					config.set("initialSpawn.enabled", true);
					config.set("initialSpawn.world", l.getWorld().getName());
					config.set("initialSpawn.x", l.getX());
					config.set("initialSpawn.y", l.getY());
					config.set("initialSpawn.z", l.getZ());
					config.set("initialSpawn.yaw", l.getYaw());
					config.set("initialSpawn.pitch", l.getPitch());
					
					sender.sendMessage(sufix+"Initial spawn set for all players.");
				} else {
					sender.sendMessage(sufix+"Please, log in as a player.");
				}
			break;
			case "info":
				sender.sendMessage(ChatColor.GREEN+sufix+ChatColor.DARK_GREEN+"v"+ChatColor.BOLD+version);
			break;
			case "reload":
				go.reloadConfig();
				sender.sendMessage(sufix+"\'config.yml\' reload successfully!");
			break;
			default:
				sender.sendMessage(sufix+ChatColor.BLUE+"Admin command");
				return false;
			}
		break;
		}
		return true;
	}

}
