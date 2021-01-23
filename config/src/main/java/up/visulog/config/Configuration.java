package up.visulog.config;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 */
public class Configuration {

    private final Path gitPath;
    private final Map<String, PluginConfig> plugins;

    /**
     * a constructor to build 'Configuration' type objects
     * @param gitPath a 'Path' type object 
     * @param plugins a Map which associates a String with a PluginConfig
     */
    public Configuration(Path gitPath, Map<String, PluginConfig> plugins) {
        this.gitPath = gitPath;
        this.plugins = Map.copyOf(plugins);
    }
    
    @Override
    /**
     * the usual toString() method
     * @return String a text which introduces pluginMap
     */
    public String toString(){
        String pluginMap="";
        for (Map.Entry<String, PluginConfig> entry : plugins.entrySet()) {
            pluginMap+=entry.getKey() + " - " + entry.getValue()+"\n";
        }
        return "\n\nGit path: " +gitPath+"\n\nPlugins: \n"+pluginMap+"\n";
    }

    /** 
     * getter of gitPath property
     * @return gitPath the Path of the Configuration
     */
    public Path getGitPath() {
        return gitPath;
    }

    /**
     * getter of plugins property
     * @return plugins the Map of the Configuration
     */
    public Map<String, PluginConfig> getPluginConfigs() {
        return plugins;
    }
}
