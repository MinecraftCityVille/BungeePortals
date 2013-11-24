package de.mccityville.bungeeportals.portals;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import de.mccityville.bungeeportals.utils.BungeeCordPorter;

public class PortalListener implements Listener {
	
	private final PortalManager pm;
	private final BungeeCordPorter porter;
	
	public PortalListener(PortalManager pm, BungeeCordPorter porter) {
		this.pm = pm;
		this.porter = porter;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Location from = e.getFrom(), to = e.getTo();
		
		if ((from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ()) && from.getWorld().equals(to.getWorld())) {
			Vector toVec = to.toVector();
			for (Portal portal : pm.getPortals(to.getWorld())) {
				if (portal.getMinPos().getX() <= toVec.getX() && toVec.getX() <= portal.getMaxPos().getX() &&
						portal.getMinPos().getY() <= toVec.getY() && toVec.getY() <= portal.getMaxPos().getY() &&
						portal.getMinPos().getZ() <= toVec.getZ() && toVec.getZ() <= portal.getMaxPos().getZ()) {
					porter.changeServer(e.getPlayer(), portal.getTarget());
				}
			}
		}
	}
}
