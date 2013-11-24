package de.mccityville.bungeeportals.portals;

import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.Vector;

import com.google.common.collect.Maps;

import de.mccityville.bungeeportals.utils.ConfigCaster;

public class Portal implements ConfigurationSerializable {
	
	private static final String CONFIG_POS1 = "pos1";
	private static final String CONFIG_POS2 = "pos2";
	private static final String CONFIG_WORLD = "world";
	private static final String CONFIG_TARGET = "target";
	
	private final Vector minPos, maxPos;
	private final World world;
	private final String target;
	
	public Portal(Vector pos1, Vector pos2, World world, String target) {
		Validate.notNull(pos1);
		Validate.notNull(pos2);
		Validate.notNull(world);
		Validate.notEmpty(target);
		
		this.world = world;
		this.target = target;
		minPos = new Vector();
		maxPos = new Vector();
		
		if (pos1.getX() > pos2.getX()) {
			minPos.setX(pos2.getX());
			maxPos.setX(pos1.getX());
		} else {
			minPos.setX(pos1.getX());
			maxPos.setX(pos2.getX());
		}
		
		if (pos1.getY() > pos2.getY()) {
			minPos.setY(pos2.getY());
			maxPos.setY(pos1.getY());
		} else {
			minPos.setY(pos1.getY());
			maxPos.setY(pos2.getY());
		}
		
		if (pos1.getZ() > pos2.getZ()) {
			minPos.setZ(pos2.getZ());
			maxPos.setZ(pos1.getZ());
		} else {
			minPos.setZ(pos1.getZ());
			maxPos.setZ(pos2.getZ());
		}
	}
	
	public Vector getMinPos() {
		return minPos.clone();
	}
	
	public Vector getMaxPos() {
		return maxPos.clone();
	}
	
	public World getWorld() {
		return world;
	}
	
	public String getTarget() {
		return target;
	}
	
	public Map<String, Object> serialize() {
		Map<String, Object> data = Maps.newHashMap();
		
		data.put(CONFIG_POS1, minPos);
		data.put(CONFIG_POS2, maxPos);
		data.put(CONFIG_TARGET, target);
		data.put(CONFIG_WORLD, world.getName());
		
		return data;
	}
	
	public static Portal deserialize(Map<String, Object> data) {
		ConfigCaster caster = new ConfigCaster(data);
		
		return new Portal(
				caster.required(CONFIG_POS1, Vector.class),
				caster.required(CONFIG_POS2, Vector.class),
				Bukkit.getWorld(caster.required(CONFIG_WORLD, String.class)),
				caster.required(CONFIG_TARGET, String.class)
		);
	}
}
