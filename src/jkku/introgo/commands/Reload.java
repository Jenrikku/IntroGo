package jkku.introgo.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import jkku.introgo.Go;

public class Reload implements CommandExecutor{
	private Go plugin;
	
	public Reload(Go plugin){
		this.plugin= plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			plugin.reloadMessages();
			plugin.reloadConfig();
			Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.GREEN+"Reloaded!");
		} else {
			Player player = (Player) sender;
			plugin.reloadMessages();
			plugin.reloadConfig();
			player.sendMessage(plugin.name+ChatColor.GREEN+"Reloaded!");
			Bukkit.getConsoleSender().sendMessage(plugin.name+ChatColor.YELLOW+"The player "+ChatColor.BOLD+player+ChatColor.YELLOW+" has reloaded the plugin.");
		}
		return true;
	}
}
