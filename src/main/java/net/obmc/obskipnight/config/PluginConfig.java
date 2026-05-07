package net.obmc.obskipnight.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.obmc.obskipnight.OBSkipNight;

public class PluginConfig {

    static Logger log = LoggerFactory.getLogger(OBSkipNight.class);

    protected final OBSkipNight plugin;

    private final FileConfiguration config;

    public int timer;
    public boolean ownerOnly;
    public boolean doBroadcast;

    public PluginConfig(OBSkipNight plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();

        this.timer = config.getInt("timer", 5);
        this.ownerOnly = config.getBoolean("ownerOnly", false);
        this.doBroadcast = config.getBoolean("doBroadcast", true);
    }

    public int getTimer() {
        return this.timer;
    }
    public void setTimer(int timer) {
        this.timer = timer;
        config.set("timer", timer);
        save();
    }

    public boolean getOwnerOnly() {
        return this.ownerOnly;
    }
    public void setOwnerOnly(boolean ownerOnly) {
        this.ownerOnly =  ownerOnly;
        config.set("owneronly", ownerOnly);
        save();
    }

    public boolean getDoBroadcast() {
        return this.doBroadcast;
    }
    public void setDoBroadcast(boolean doBroadcast) {
        this.doBroadcast = doBroadcast;
        config.set("broadcast", doBroadcast);
        save();
    }

    public void save() {
        plugin.saveConfig();
    }
}