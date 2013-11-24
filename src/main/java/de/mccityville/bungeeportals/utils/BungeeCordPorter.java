package de.mccityville.bungeeportals.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

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
	
	public void changeServer(Player player, String serverName) {
		synchronized (pending) {
			if (pending.contains(player)) {
				player.sendMessage(Constants.WARNING_PREFIX + "Please wait until your current teleport is complete.");
				return;
			}
			
			pending.add(player);
		}
		
		player.sendMessage(Constants.INFO_PREFIX + "Taking you to " + serverName + ".");
		parent.getLogger().info(player.getName() + " will be teleported to " + serverName + ".");
		
		try {
			FutureTask<Void> task = new FutureTask<Void>(new PortThread(player, serverName));
			task.run();
			task.get(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			parent.getLogger().log(Level.SEVERE, e.getMessage(), e);
			player.sendMessage(Constants.ERROR_PREFIX + "Connector interrupted. See console for further information.");
		} catch (ExecutionException e) {
			parent.getLogger().log(Level.SEVERE, e.getMessage(), e);
			player.sendMessage(Constants.ERROR_PREFIX + "Connector throws an exception. See console for further information.");
		} catch (TimeoutException e) {
			parent.getLogger().warning("Time out while taking " + player.getName() + " to " + serverName + "!");
			player.sendMessage(Constants.WARNING_PREFIX + "BungeeCord took to long to teleport you.");
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
