package up.visulog.cli;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @version 1.0
 */
public class TestCLILauncher {

    /**
     * we run the program and we see if everything is right
     */
    @Test
    public void testArgumentParser() {
        var config1 = CLILauncher.makeConfigFromCommandLineArgs(new String[]{"..", "--addPlugin=countEverything"});
        assertTrue(config1.isPresent());
        var config2 = CLILauncher.makeConfigFromCommandLineArgs(new String[] {
                "--nonExistingOption"
        });
        assertFalse(config2.isPresent());
    }
}
