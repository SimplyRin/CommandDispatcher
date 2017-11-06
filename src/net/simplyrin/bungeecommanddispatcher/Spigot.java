package net.simplyrin.bungeecommanddispatcher;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Spigot extends JavaPlugin implements Listener {

	private static Spigot plugin;

	@Override
	public void onEnable() {
		plugin = this;
		plugin.getServer().getPluginManager().registerEvents(this, this);
		plugin.getCommand("b-command").setExecutor(this);
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "CMD_DISPATCHER");
	}

	@Override
	public void onDisable() {
		plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
		plugin = null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("cmddispatcher.command")) {
			sender.sendMessage(getPrefix() + "§cYou don't have access to this command!");
			return true;
		}

		if(args.length > 0) {
			String message = "";
			for(int i = 0; i < args.length; ++i) {
				message = message + args[i] + " ";
			}
			sendMessage(message);
			return true;
		}

		sender.sendMessage(getPrefix() + "§cUsage: /" + cmd.getName() + " <command>");
		return true;
	}

	public static String getPrefix() {
		return "§7[§cCmdDispatcher§7] §r";
	}

	private static void sendMessage(String message) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);

		try {
			out.writeUTF("CMD_DISPATCHER");
			out.writeUTF(message.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		Bukkit.getServer().sendPluginMessage(plugin, "CMD_DISPATCHER", b.toByteArray());
	}

}
