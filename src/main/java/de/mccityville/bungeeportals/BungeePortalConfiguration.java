package de.mccityville.bungeeportals;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.mccityville.bungeeportals.portals.Portal;

public class BungeePortalConfiguration implements ConfigurationSerializable {
	
	private static final String CONFIG_PORTALS = "portals";
	
	private final List<Portal> portals = Lists.newArrayList();
	
	public void addPortal(Portal portal) {
		Validate.notNull(portal);
		
		synchronized (portals) {
			portals.add(portal);
		}
	}
	
	public void addPortals(Collection<Portal> portals) {
		Validate.notEmpty(portals);
		
		synchronized (portals) {
			this.portals.addAll(portals);
		}
	}
	
	public void removePortal(Portal portal) {
		Validate.notNull(portal);
		
		synchronized (portals) {
			portals.remove(portal);
		}
	}
	
	public ImmutableList<Portal> getPortals() {
		ImmutableList.Builder<Portal> b = ImmutableList.builder();
		
		if (portals.size() > 0)
			b.addAll(portals);
		
		return b.build();
	}
	
	public void clearPortals() {
		synchronized (portals) {
			portals.clear();
		}
	}
	
	public Map<String, Object> serialize() {
		Map<String, Object> data = Maps.newHashMap();
		
		data.put(CONFIG_PORTALS, portals);
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public static BungeePortalConfiguration deserialize(Map<String, Object> data) {
		BungeePortalConfiguration config = new BungeePortalConfiguration();
		
		Object portals = data.get(CONFIG_PORTALS);
		if (portals != null)
			config.portals.addAll((List<Portal>) portals);
		
		return config;
	}
}
