package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import utils.MyDate;

import java.util.*;
import java.util.List;

/** 
 * @version 1.0
 */
public class CountCommitsPerMonthPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;
    private static ArrayList<Commit>[] monthlyCommits;

    /**
     * the constructor of a 'CountCommitsPerMonthPlugin' type object
     * @param generalConfiguration a 'Configuration' type object
     */
    public CountCommitsPerMonthPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    /**
     * we fill a new variable with the List in parameter
     * @param gitLog a List of Commit(s)
     * @return result not the same 'result' as the property of CountCommitsPerMonthPlugin
     */
    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        List<Commit> commits = new ArrayList<>(List.copyOf(gitLog));
        commits.sort(Commit::isSooner);
        int nb = commits.get(0).date.countMonthsBetween(commits.get(commits.size() - 1).date);
        monthlyCommits = new ArrayList[nb];
        int index = 0;
        for (int i = 0; i < nb; i++) {
            monthlyCommits[i] = new ArrayList<>();
            while (index < commits.size()) {
                Commit com = commits.get(index);
                int dist = com.date.countMonthsBetween(commits.get(0).date);
                if (dist == i + 1) {
                    monthlyCommits[i].add(com);
                    index++;
                } else {
                    break;
                }
            }
        }

        MyDate.Month[] months = MyDate.Month.values();
        for (int i = 0; i < nb; i++) {
            int n = monthlyCommits[i].size();
            MyDate.Month m = months[(i + monthlyCommits[0].get(0).date.month.ordinal()) % 12];
            int year = monthlyCommits[0].get(0).date.year + i / 12;
            result.commitsPerMonth.put(new Pair(year, m), n);
        }
        return result;
    }

    @Override
    /**
     * allows to run processLog 
     */
    public void run() {
        try {
            result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
        } catch (Exception e) {
            System.out.println("\n**ERROR**");
            System.out.println("The selected directory has either corrupted or incorrect git data");
            System.exit(0);
        }
    }

    @Override
    /**
     * a getter of 'result'
     * @return result
     */
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    static class Pair implements Comparable {
        int year;
        MyDate.Month month;

        /**
         * a constructor to build a 'Pair' type object
         * @param year an int
         * @param month a 'Month' object
         */
        public Pair(int year, MyDate.Month month) {
            this.year = year;
            this.month = month;
        }

        @Override
        /**
         * the usual toString() method
         * @return String
         */
        public String toString() {
            return year + ", " + month;
        }

        @Override
        /** 
         * comparing two Pairs through comparing the property of each
         * @param o an Object, which is also a Pair
         * @return int showing which Pair was created earlier
         */
        public int compareTo(Object o) {
            Pair pair = (Pair) o;
            if (this.year < pair.year)
                return -1;
            if (this.year > pair.year)
                return 1;
            return Integer.compare(this.month.ordinal(), pair.month.ordinal());
        }
    }

    static class Result implements AnalyzerPlugin.Result {
        private final Map<Pair, Integer> commitsPerMonth = new HashMap<>();

        /**
         * a getter 
         * @return commitsPerMonth the property 
         */
        Map<Pair, Integer> getCommitsPerMonth() {
            return commitsPerMonth;
        }

        @Override
        /** 
         * calls toString()
         * @return String the text description of commitsPerMonth
         */
        public String getResultAsString() {
            return commitsPerMonth.toString();
        }

        @Override
        /**
         * creates a StringBuilder called 'json' and fills it with a loop
         * @return json.toString() we return a String description of 'json'
         */
        public String getResultAsJSON() {
            StringBuilder json = new StringBuilder("monthData='[");
            int size = commitsPerMonth.entrySet().size();
            Map<Pair, Integer> treeMap = new TreeMap<>(commitsPerMonth);
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
