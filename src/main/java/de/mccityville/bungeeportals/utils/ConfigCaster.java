package de.mccityville.bungeeportals.utils;

import java.util.Map;

public final class ConfigCaster {
	
	private final Map<String, Object> data;
	
	public ConfigCaster(Map<String, Object> data) {
		this.data = data;
	}
	
	public <T> T required(String key, Class<T> target) {
		return cast(key, target, null, true);
	}
	
	public <T> T cast(String key, Class<T> target, T def, boolean required) {
		Object o = data.get(key);
		
		if (required && o == null && def != null)
			throw new RuntimeException("Invalid config. No node with key " + key);
		else if (o == null && def != null)
			o = def;
		
		return target.cast(o);
	}
}
