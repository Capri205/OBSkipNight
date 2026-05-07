package net.obmc.obskipnight.listeners;

import org.bukkit.event.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.obmc.obskipnight.OBSkipNight;
import net.obmc.obskipnight.config.PluginConfig;

public class PlayerListener implements Listener {

	static Logger log = LoggerFactory.getLogger(OBSkipNight.class);
	
	private final OBSkipNight plugin;
	private final PluginConfig config;
	
	public PlayerListener(PluginConfig config, OBSkipNight plugin) {

		this.plugin = plugin;
		this.config = config;
	}
}
