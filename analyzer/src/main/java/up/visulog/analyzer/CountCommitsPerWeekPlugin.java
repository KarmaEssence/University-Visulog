package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.*;

/**
 * @version 1.0
 */
public class CountCommitsPerWeekPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;
    private static ArrayList<Commit>[] weeklyCommits;

    /**
     * basic constructor which builds a 'CountCommitsPerWeekPlugin'type Object
     * @param generalConfiguration a 'Configuration' type object
     */
    public CountCommitsPerWeekPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    /**
     * we fill a new variable with the List in parameter
     * @param gitLog a List of Commit(s)
     * @return result not the same 'result' as the property of CountCommitsPerWeekPlugin
     */
    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        List<Commit> commits = new ArrayList<>(List.copyOf(gitLog));
        commits.sort(Commit::isSooner);
        int daysBetween = commits.get(0).date.countDaysBetween(commits.get(commits.size() - 1).date);
        int nb = (int) Math.ceil((double) daysBetween / 7);
        weeklyCommits = new ArrayList[nb];
        int index = 0;
        for (int i = 0; i < nb; i++) {
            weeklyCommits[i] = new ArrayList<>();
            while (index < commits.size()) {
                Commit com = commits.get(index);
                int dist = com.date.countDaysBetween(commits.get(0).date);
                if (dist <= (i + 1) * 7) {
                    weeklyCommits[i].add(com);
                    index++;
                } else {
                    break;
                }
            }
        }
        int firstYear = weeklyCommits[0].get(0).date.year, firstMonth = weeklyCommits[0].get(0).date.month.ordinal();
        for (int i = 0; i < nb; i++) {
            Commit com = null;
            if (!weeklyCommits[i].isEmpty()) com = weeklyCommits[i].get(0);
            int n = weeklyCommits[i].size();
            result.commitsPerWeek.put(new Pair(com == null ? firstYear + (i + firstMonth * 4) / 52 : com.date.year, i + 1), n);
        }
        return result;
    }

    @Override
    /**
     * allows to run processLog
     */
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
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

    static class Pair implements Comparable {
        int year;
        int week;
        /** 
         * a basic constructor to build a pair 
         * @param year the year 
         * @param week the week
         */
        public Pair(int year, int week) {
            this.year = year;
            this.week = week;
        }

        @Override
        /**
         * the usual toString() method
         * @return String a text description of a Pair
         */
        public String toString() {
            return year + ", week " + week;
        }

        @Override
        /**
         * compares two Pairs with theirs properties
         * @param o an object, which is also a Pair
         * @return int showing which Pair was created first
         */
        public int compareTo(Object o) {
            Pair pair = (Pair) o;
            if (this.year < pair.year)
                return -1;
            if (this.year > pair.year)
                return 1;
            return Integer.compare(this.week, pair.week);
        }
    }

    static class Result implements AnalyzerPlugin.Result {
        private final Map<Pair, Integer> commitsPerWeek = new HashMap<>();

        /** 
         * a getter of 'commitsPerWeek' property
         * @return commitsPerWeek the property of a 'Result'
         */
        Map<Pair, Integer> getCommitsPerWeek() {
            return commitsPerWeek;
        }

        @Override
        /**
         * the usual toString() method applied to a 'Result'
         * @return the text descripton of 'commitsPerWeek'
         */
        public String getResultAsString() {
            return commitsPerWeek.toString();
        }

        /**
         * with a StringBuilder we fill the variable 'html' with HTML code
         * @return String HTML code 
         */
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits per author: <ul>");
            Map<Pair, Integer> treeMap = new TreeMap<>(commitsPerWeek);
            for (var item : treeMap.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }

        @Override
        /**
         * works the same way as getResultAsHtmlDiv but with JSON 
         * @return String JSON code 
         */
        public String getResultAsJSON() {
            StringBuilder json = new StringBuilder("weekData='[");
            int size = commitsPerWeek.entrySet().size();
            Map<Pair, Integer> treeMap = new TreeMap<>(commitsPerWeek);
            int index = 1;
            for (var item : treeMap.entrySet()) {
                String pName = item.getKey().toString();
                if (index != size) {
                    json.append("{\"x\":" + index + ", \"y\":").append(item.getValue()).append(", \"label\":\"").append(pName).append("\"},");
                } else {
                    json.append("{\"x\":" + index + ", \"y\":").append(item.getValue()).append(", \"label\":\"").append(pName).append("\"}");
                }
                index++;
            }
            json.append("]';");
            return json.toString();
        }
    }
}
