package up.visulog.analyzer;

import java.util.List;

public class AnalyzerResult {
    /**
     * @version 1.0
     */

    /**
     * the getter of subResults
     * @return List<AnalyzerPlugin.Result> a list of getSubResults, it's the getter of the property
     */
    public List<AnalyzerPlugin.Result> getSubResults() {
        return subResults;
    }

    private final List<AnalyzerPlugin.Result> subResults;

    /** 
     * a constructor for AnalyzerResult
     */
    public AnalyzerResult(List<AnalyzerPlugin.Result> subResults) {
        this.subResults = subResults;
    }

    @Override
    /**
     * the usual toString() method
     * @return String it's a description of the AnalyzerResult
     */
    public String toString() {
        return subResults.stream().map(AnalyzerPlugin.Result::getResultAsString).reduce("", (acc, cur) -> acc + "\n" + cur);
    }
    /**
     * works the same way as toString() but returns JSON code
     * @return String we convert the AnalyzerResult to JSON 
     */
    public String toJSON() {
        return subResults.stream().map(AnalyzerPlugin.Result::getResultAsJSON).reduce("", (acc, cur) -> acc + cur);
    }
}
