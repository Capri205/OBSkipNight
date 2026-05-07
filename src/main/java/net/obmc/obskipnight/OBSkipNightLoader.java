package net.obmc.obskipnight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;

public class OBSkipNightLoader implements PluginLoader {

    static Logger log = LoggerFactory.getLogger(OBSkipNight.class);

    private OBSkipNight plugin = OBSkipNight.getInstance();

    public OBSkipNightLoader() {
        this.plugin = OBSkipNight.getInstance();
    }

    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
        //throw new UnsupportedOperationException("Unimplemented method 'classloader'");
    }

    public void enable() {
    }

    public void loadConfiguration() {
    }

    public void loadClasses() {
    }

    public void loadDependencies() {
    }
}