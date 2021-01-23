package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Merge;
import utils.Author;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 */
public class CountMergesPerAuthorPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    /**
     * a basic constructor to build a 'CountMergesPerAuthorPlugin' type object
     * @param generalConfiguration a 'Configuration' type object
     */
    public CountMergesPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    /**
     * we fill a new variable with the List in parameter
     * @param gitLog a List of Commit(s)
     * @return result not the same 'result' as the property of CountCommitsPerWeekPlugin
     */
    static Result processLog(List<Merge> gitLog) {
        var result = new Result();
        for (var merge : gitLog) {
            var nb = result.mergesPerAuthor.getOrDefault(merge.author, 0);
            result.mergesPerAuthor.put(merge.author, nb + 1);
        }
        return result;
    }

    @Override
    /**
     * allows to run processLog
     */
    public void run() {
        try{
            result = processLog(Merge.parseLogFromCommand(configuration.getGitPath()));
        } catch(Exception e){
            System.out.println("\n**ERROR**");
            System.out.println("An error occurred when counting the number of merges");
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    /**
     * a getter of 'result'
     * @return result the property
     */
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    static class Result implements AnalyzerPlugin.Result {
        private final Map<Author, Integer> mergesPerAuthor = new HashMap<>();

        /** 
         * a getter of 'MergesPerAuthor' property
         * @return mergesPerAuthor the property of a 'Result'
         */
        Map<Author, Integer> getMergesPerAuthor() {
            return mergesPerAuthor;
        }

        @Override
        /**
         * the usual toString() method applied to a 'Result'
         * @return the text descripton of 'mergesPerAuthor'
         */
        public String getResultAsString() {
            return mergesPerAuthor.toString();
        }

        @Override
        /**
         * creates a StringBuilder called 'json' and fills it with a loop
         * @return json.toString() we return a String description of 'json'
         */
        public String getResultAsJSON() {
            StringBuilder json = new StringBuilder("mergeData='[");
            int size = mergesPerAuthor.entrySet().size();
            int index = 1;
            for (var item : mergesPerAuthor.entrySet()) {
                String pName = item.getKey().getName();
                if (index != size){
                    json.append("{\"x\":"+index+", \"y\":").append(item.getValue()).append(", \"label\":\"").append(pName).append("\"},");
                } else {
                    json.append("{\"x\":"+index+", \"y\":").append(item.getValue()).append(", \"label\":\"").append(pName).append("\"}");
                }
                index++;
            }
            json.append("]';");
            return json.toString();
        }
    }
}
