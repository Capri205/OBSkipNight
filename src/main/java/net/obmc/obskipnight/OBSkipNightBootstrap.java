package net.obmc.obskipnight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;

public class OBSkipNightBootstrap implements PluginBootstrap {

    static Logger log = LoggerFactory.getLogger(OBSkipNight.class);


    public OBSkipNightBootstrap() {
    }

    // not implemented yet, but let's not stop the server loading the plugin
    @Override
    public void bootstrap(BootstrapContext bc) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void enable() {
        OBSkipNightLoader loader = new OBSkipNightLoader();
        loader.enable();
    }

    public void disable() {
    }
}
