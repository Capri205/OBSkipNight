package net.obmc.obskipnight;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.obmc.obskipnight.commands.SkipNightCommand;
import net.obmc.obskipnight.config.PluginConfig;

public class OBSkipNight extends JavaPlugin {

    static Logger log = LoggerFactory.getLogger(OBSkipNight.class);

	public static OBSkipNight instance;

	private static final String PLUGINNAME = "OBSkipNight";
	private static String pluginprefix = "[" + PLUGINNAME + "]";
	private static String logmsgprefix = pluginprefix + " » ";

	private OBSkipNightBootstrap bootstrap;
    private PluginConfig config;


    public OBSkipNight() {
    	instance = this;
    }

    @Override
    public void onEnable() {
    	this.bootstrap = new OBSkipNightBootstrap();
		bootstrap.enable();
		log.info(getLogMsgPrefix() + "Plugin Version {} activated!", this.getPluginMeta().getVersion());

    	config = new PluginConfig(instance);

        registerCommand("skipnight", new SkipNightCommand(instance, config));

		log.info(getLogMsgPrefix() + "Plugin Version {} activated!", this.getPluginMeta().getVersion());
    }
    
    // save config disable the plugin
    @Override
	public void onDisable() {
		if (bootstrap != null) {
			bootstrap.disable();
        }
    }

    public static OBSkipNight getInstance() {
    	return instance;
    }

	// consistent messaging
	public static String getPluginName() {
		return PLUGINNAME;
	}
	public static String getPluginPrefix() {
		return pluginprefix;
	}
	public String getLogMsgPrefix() {
		return logmsgprefix;
	}
}