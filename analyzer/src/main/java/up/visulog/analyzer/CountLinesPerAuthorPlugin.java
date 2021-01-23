package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Line;
import utils.Author;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * version @1.0
 */
public class CountLinesPerAuthorPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    /**
     * a basic constructor to build a 'CountLinesPerAuthorPlugin' type object
     * @param generalConfiguration a 'Configuration' type object
     */
    public CountLinesPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    /**
     * * we fill a new variable with the List in parameter
     * @param gitLog a List of Commit(s)
     * @return result not the same 'result' as the property of CountCommitsPerWeekPlugin
     */
    static Result processLog(List<Line> gitLog) {
        var result = new Result();
        for (var lines : gitLog) {
            var numlineChanges = lines.lineChanges;
            var nb = result.linesPerAuthor.getOrDefault(lines.author, 0);
            result.linesPerAuthor.put(lines.author, nb + numlineChanges);
        }
        return result;
    }

    @Override
    /**
     * allows to run processLog
     */
    public void run() {
        try{
            result = processLog(Line.parseLogFromCommand(configuration.getGitPath()));
        } catch(Exception e){
            System.out.println("\n**ERROR**");
            System.out.println("An error occurred when counting the number of line changes");
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
        private final Map<Author, Integer> linesPerAuthor = new HashMap<>();

        /** 
         * a getter of 'linesPerAuthor' property
         * @return linesPerAuthor the property of a 'Result'
         */
        Map<Author, Integer> getLinesPerAuthor() {
            return linesPerAuthor;
        }

        @Override
        /**
         * the usual toString() method applied to a 'Result'
         * @return the text descripton of 'linesPerAuthor'
         */
        public String getResultAsString() {
            return linesPerAuthor.toString();
        }

        @Override
        /**
         * creates a StringBuilder called 'json' and fills it with a loop
         * @return json.toString() we return a String description of 'json'
         */
        public String getResultAsJSON() {
            StringBuilder json = new StringBuilder("lineData='[");
            int size = linesPerAuthor.entrySet().size();
            int index = 1;
            for (var item : linesPerAuthor.entrySet()) {
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
