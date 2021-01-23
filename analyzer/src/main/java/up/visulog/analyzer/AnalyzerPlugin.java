package up.visulog.analyzer;

import java.lang.module.Configuration;

public interface AnalyzerPlugin {
    /**
     * @version 1.0
     */
    interface Result {
        String getResultAsString();

        String getResultAsJSON();

    }

    /**
     * run this analyzer plugin
     */
    void run();

    /**
     *
     * @return the result of this analysis. Runs the analysis first if not already done.
     */
    Result getResult();
}
