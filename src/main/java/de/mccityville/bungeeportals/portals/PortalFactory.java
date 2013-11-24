package de.mccityville.bungeeportals.portals;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class PortalFactory {
	
	private static final WorldEditPlugin we = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	
	private final PortalManager pm;
	
	public PortalFactory(PortalManager pm) {
		this.pm = pm;
	}
	
	public void createPortalFromSelection(Player player, String target) {
		Selection selection = we.getSelection(player);
		
		if (selection == null)
			throw new PortalCreationException("No selection found.");
		
		World world = selection.getWorld();
		Location min = selection.getMinimumPoint();
		Location max = selection.getMaximumPoint();
		
		Vector minVec = min.toVector();
		minVec.setX((int) minVec.getX());
		minVec.setY((int) minVec.getY());
		minVec.setZ((int) minVec.getZ());
		
		Vector maxVec = max.toVector();
		maxVec.setX((int) maxVec.getX() + 1);
		maxVec.setY((int) maxVec.getY() + 1);
		maxVec.setZ((int) maxVec.getZ() + 1);
		
		pm.addPortal(new Portal(minVec, maxVec, world, target));
	}
	
	public PortalManager getManager() {
		return pm;
	}
}
