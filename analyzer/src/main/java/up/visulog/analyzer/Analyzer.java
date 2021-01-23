package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

    /**
     * @version 1.0
     */
public class Analyzer {
   
    private final Configuration config;

    private AnalyzerResult result;

    /**
     * @param config a "Configuration" type object 
     */
    public Analyzer(Configuration config) {
        this.config = config;
    }

    /** 
     * @return AnalyzerResult
     */
    public AnalyzerResult computeResults() {
        List<AnalyzerPlugin> plugins = new ArrayList<>();
        for (var pluginConfigEntry : config.getPluginConfigs().entrySet()) {
            var pluginName = pluginConfigEntry.getKey();
            var pluginConfig = pluginConfigEntry.getValue();
            var plugin = makePlugin(pluginName, pluginConfig);
            plugin.ifPresent(plugins::add);
        }
        // run all the plugins -- exécute tous les plugins
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Callable<Object>> task = new ArrayList<Callable<Object>>();
        for (var plugin : plugins) {
            task.add(Executors.callable(plugin::run));
        }
        makeSomeOperation(executorService, task);

        // store the results together in an AnalyzerResult instance and return it
        return new AnalyzerResult(plugins.stream().map(AnalyzerPlugin::getResult).collect(Collectors.toList()));
    }

    /*Execute the list of plugin in parallel.*/
    /**
     * @param es runs tasks implementing Runnable
     * @param task a list of Callable<Object>>
     */
    private void makeSomeOperation(ExecutorService es, List<Callable<Object>> task) {
        try {
            es.invokeAll(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("An occured error");
        } finally {
            es.shutdown();
        }
    }


    /**
     * @param pluginName a String which is the name of the selected object
     * @param pluginConfig a PluginConfig type object
     * @return Optional<AnalyzerPlugin>
     */
    private Optional<AnalyzerPlugin> makePlugin(String pluginName, PluginConfig pluginConfig) {
        switch (pluginName) {
            case "countCommits":
                return Optional.of(new CountCommitsPerAuthorPlugin(config));
            case "countMerges":
                return Optional.of(new CountMergesPerAuthorPlugin(config));
            case "countLineChanges":
                return Optional.of(new CountLinesPerAuthorPlugin(config));
            case "countCommitsPerMonth":
                return Optional.of(new CountCommitsPerMonthPlugin(config));
            case "countCommitsPerWeek":
                return Optional.of(new CountCommitsPerWeekPlugin(config));

            default:
                return Optional.empty();
        }
    }

}
