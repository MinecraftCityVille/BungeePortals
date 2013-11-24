package de.mccityville.bungeeportals.portals;

import java.util.Collection;
import java.util.List;

import org.bukkit.World;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class PortalManager {
	
	private final List<Portal> portals = Lists.newArrayList();
	
	public ImmutableList<Portal> getPortals() {
		ImmutableList.Builder<Portal> b = ImmutableList.builder();
		
		if (portals.size() > 0)
			b.addAll(portals);
		
		return b.build();
	}
	
	public ImmutableList<Portal> getPortals(World world) {
		ImmutableList.Builder<Portal> b = ImmutableList.builder();
		
		synchronized (this) {
			for (Portal portal : portals)
				if (world.equals(portal.getWorld()))
					b.add(portal);
		}
		
		return b.build();
	}
	
	public synchronized void addPortal(Portal portal) {
		portals.add(portal);
	}
	
	public synchronized void addPortals(Collection<Portal> portals) {
		this.portals.addAll(portals);
	}
	
	public synchronized void removePortal(Portal portal) {
		portals.remove(portal);
	}
}
