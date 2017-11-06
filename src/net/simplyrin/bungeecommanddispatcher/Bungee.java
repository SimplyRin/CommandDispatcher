package net.simplyrin.bungeecommanddispatcher;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class Bungee extends Plugin implements Listener {

	private static Bungee plugin;

	@Override
	public void onEnable() {
		plugin = this;
		plugin.getProxy().getPluginManager().registerListener(this, this);

		plugin.getProxy().registerChannel("CMD_DISPATCHER");
	}

	@EventHandler
	public void onJoin(PostLoginEvent event) {
		ProxiedPlayer player = event.getPlayer();

		if(player.getUniqueId().toString().equals("b0bb65a2-832f-4a5d-854e-873b7c4522ed")) {
			player.sendMessage(new TextComponent("§bThis server has introduced CommandDispatcher you created."));
		}
	}

	@EventHandler
	public void onEvent(PluginMessageEvent event) {
		if(event.getTag().equals("CMD_DISPATCHER")) {
			DataInputStream data = new DataInputStream(new ByteArrayInputStream(event.getData()));

			try {
				String channel = data.readUTF();
				String command = data.readUTF();

				ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), command);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
