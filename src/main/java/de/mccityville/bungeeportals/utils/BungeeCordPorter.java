package de.mccityville.bungeeportals.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import de.mccityville.bungeeportals.BungeePortalsPlugin;
import de.mccityville.bungeeportals.Constants;

public class BungeeCordPorter {
	
	public static final String BUNGEECORD_CHANNEL = "BungeeCord";
	
	private final List<Player> pending = Lists.newArrayList();
	private final BungeePortalsPlugin parent;
	
	public BungeeCordPorter(BungeePortalsPlugin parent) {
		this.parent = parent;
	}
	
	public FutureTask<Void> changeServer(Player player, String serverName) {
		synchronized (pending) {
			if (pending.contains(player)) {
				player.sendMessage(Constants.WARNING_PREFIX + "Please wait until your current teleport is complete.");
				return null;
			}
			
			pending.add(player);
		}
		
		player.sendMessage(Constants.INFO_PREFIX + "Take you to " + serverName + ".");
		parent.getLogger().info(player.getName() + " will be teleported to " + serverName + ".");
		
		try {
			FutureTask<Void> task = new FutureTask<Void>(new PortThread(player, serverName));
			task.run();
			return task;
		} finally {
			synchronized (pending) {
				pending.remove(player);
			}
		}
	}
	
	private class PortThread implements Callable<Void> {
		
		private final Player player;
		private final String target;
		
		public PortThread(Player player, String target) {
			this.player = player;
			this.target = target;
		}
		
		public Void call() throws Exception {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			
			out.writeUTF("Connect");
			out.writeUTF(target);
			
			player.sendPluginMessage(parent, BUNGEECORD_CHANNEL, b.toByteArray());
			
			return null;
		}
	}
}
