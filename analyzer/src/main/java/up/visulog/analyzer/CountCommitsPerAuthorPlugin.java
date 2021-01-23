package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import utils.Author;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

     /**
     * @version 1.0
     */
public class CountCommitsPerAuthorPlugin implements AnalyzerPlugin {
    
    private final Configuration configuration;
    private Result result;

    /**
     * @param generalConfiguration a Configuration type object
     */
    public CountCommitsPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    /**
     * we fill result.commitsPerAuthor with a list of commits
     * @param gitLog a List of Commit(s)
     * @return result a Result type object
     */
    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            var nb = result.commitsPerAuthor.getOrDefault(commit.author, 0);
            result.commitsPerAuthor.put(commit.author, nb + 1);
        }
        return result;
    }

    @Override
    /** 
     * allows to run processLog and to fill the property
     */
    public void run() {
        try{
            result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
        } catch(Exception e){
            System.out.println("\n**ERROR**");
            System.out.println("An error occurred when counting the number of commits");
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    /** 
     * @return result the property "result", this method is a getCommitsPerAuthor
     */
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    
    static class Result implements AnalyzerPlugin.Result {
        private final Map<Author, Integer> commitsPerAuthor = new HashMap<>();

        /**
         * @return commitsPerAuthor the property of a "Result" type object
         */
        Map<Author, Integer> getCommitsPerAuthor() {
            return commitsPerAuthor;
        }

        @Override
        /**
         * the usual toString() method
         * @return a String description of 'commitsPerAuthor' property
         */
        public String getResultAsString() {
            return commitsPerAuthor.toString();
        }

        @Override
        /**
         * creates a StringBuilder called 'json' and fills it with a loop
         * @return json.toString() we return a String description of 'json'
         */
        public String getResultAsJSON() {
            StringBuilder json = new StringBuilder("commitData='[");
            int size = commitsPerAuthor.entrySet().size();
            int index = 1;
            for (var item : commitsPerAuthor.entrySet()) {
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
