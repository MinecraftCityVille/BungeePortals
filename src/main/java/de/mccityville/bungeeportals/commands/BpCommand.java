package de.mccityville.bungeeportals.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mccityville.bungeeportals.BungeePortalsPlugin;
import de.mccityville.bungeeportals.Constants;
import de.mccityville.bungeeportals.portals.PortalCreationException;
import de.mccityville.bungeeportals.portals.PortalFactory;

public class BpCommand implements CommandExecutor {
	
	private final BungeePortalsPlugin plugin;
	private final PortalFactory pf;
	
	public BpCommand(BungeePortalsPlugin plugin) {
		this.plugin = plugin;
		
		pf = new PortalFactory(plugin.getPortalManager());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 2 && "create".equalsIgnoreCase(args[0])) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Constants.ERROR_PREFIX + "You must execute this command as a player.");
				return true;
			}
			
			if (!sender.hasPermission("bungeeportals.admin.create")) {
				sender.sendMessage(Constants.ERROR_PREFIX + "No permission to execute this command.");
				return true;
			}
			
			boolean success = true;
			
			try {
				pf.createPortalFromSelection((Player) sender, args[1]);
			} catch (PortalCreationException e) {
				sender.sendMessage(Constants.ERROR_PREFIX + "Error occured: " + e.getMessage());
				success = false;
			}
			
			if (success) {
				plugin.savePortals();
				sender.sendMessage(Constants.INFO_PREFIX + "Portal to " + args[1] + " created!");
			}
			
			return true;
		}
		
		return false;
	}
}
