package jkku.introgo.templates;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import jkku.introgo.Go;

public class Messages implements CommandExecutor {
	private Go plugin;
	
	public Messages(Go plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration messages = plugin.getMessages();
		List<String> mCommands = messages.getStringList("Messages.commands");
		List<String> mMessages = messages.getStringList("Messages.messages");
		int mCommand = (int) mCommands.indexOf(command.toString());
		String toSend = (String) mMessages.get(mCommand);
		if(!(sender instanceof Player)){
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', toSend));
		} else {
			Player player = (Player) sender;
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', toSend));
		}
		return true;
	}
}