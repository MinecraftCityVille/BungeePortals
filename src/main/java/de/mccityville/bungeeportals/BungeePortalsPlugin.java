package de.mccityville.bungeeportals;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import de.mccityville.bungeeportals.commands.BpCommand;
import de.mccityville.bungeeportals.portals.Portal;
import de.mccityville.bungeeportals.portals.PortalListener;
import de.mccityville.bungeeportals.portals.PortalManager;
import de.mccityville.bungeeportals.utils.BungeeCordPorter;

public class BungeePortalsPlugin extends JavaPlugin {
	
	private final PortalManager portalManager = new PortalManager();
	private BungeeCordPorter porter;
	private BungeePortalConfiguration configuration;
	
	@Override
	public void onEnable() {
		prepareConfiguration();
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, BungeeCordPorter.BUNGEECORD_CHANNEL);
		porter = new BungeeCordPorter(this);
		
		getCommand("bp").setExecutor(new BpCommand(this));
		
		Bukkit.getPluginManager().registerEvents(new PortalListener(portalManager, porter), this);
	}
	
	public BungeeCordPorter getPorter() {
		return porter;
	}
	
	public PortalManager getPortalManager() {
		return portalManager;
	}
	
	public void savePortals() {
		configuration.clearPortals();
		configuration.addPortals(portalManager.getPortals());
		getConfig().set("bungeeportal-configuration", configuration);
		saveConfig();
	}
	
	private void prepareConfiguration() {
		ConfigurationSerialization.registerClass(Portal.class);
		ConfigurationSerialization.registerClass(BungeePortalConfiguration.class);
		
		configuration = (BungeePortalConfiguration) getConfig().get("bungeeportal-configuration");
		if (configuration == null) {
			configuration = new BungeePortalConfiguration();
			getConfig().set("bungeeportal-configuration", configuration);
			saveConfig();
			getLogger().info("New default configuration generated.");
		}
		
		portalManager.addPortals(configuration.getPortals());
	}
}
