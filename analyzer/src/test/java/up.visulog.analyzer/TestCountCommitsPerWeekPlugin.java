package up.visulog.analyzer;

import org.junit.Test;
import up.visulog.gitrawdata.Commit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * @version 1.0
 */
public class TestCountCommitsPerWeekPlugin {
    @Test
    /**
     * we parse a fictive Commit and we check if everything is right
     */
    public void checkCommitSum() throws IOException, URISyntaxException {
        var expected = "Commit{id='6304c1acdc1cbdeb8315528781896abc72a021b8', date='Tue Sep 1 12:30:53 2020 +0200', author='Aldric Degorre <adegorre@irif.fr>', description='More gradle configuration (with subprojects)'}"; // un exemple de commit
        int entries = 7;
        var uri = getClass().getClassLoader().getResource("git.log").toURI();
        try (var reader = Files.newBufferedReader(Paths.get(uri))) {
            var log = Commit.parseLog(reader);
//            System.out.println(log);
            var res = CountCommitsPerWeekPlugin.processLog(log);
            log.sort(Commit::isSooner);

            int nb = (int) Math.ceil((double) log.get(0).date.countDaysBetween(log.get(log.size() - 1).date) / 7);
//            for (var item : res.getCommitsPerWeek().entrySet()) {
//                System.out.println(item);
//            }
            assertEquals(nb, res.getCommitsPerWeek().size());
            var sum = res.getCommitsPerWeek().values()
                    .stream().reduce(0, Integer::sum);
            assertEquals(entries, sum.longValue());
        }
    }
}
