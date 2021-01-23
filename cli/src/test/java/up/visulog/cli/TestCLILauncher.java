package up.visulog.cli;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @version 1.0
 */
public class TestCLILauncher {
    /*
    TODO: one can also add integration tests here:
    - run the whole program with some valid options and look whether the output has a valid format
    - run the whole program with bad command and see whether something that looks like help is printed
     */

    /*
    à faire: on peut ajouter des tests d'integration ici:
    -faire tourner le programme avec des options et regarder si la sortie est valide au format
    -faire tourner le programme avec des mauvaises commandes et regarder si quelque chose ressemble à de l'aide apparait
     */
    @Test
    /**
     * we run the program and we see if everything is right
     */
    public void testArgumentParser() {
        var config1 = CLILauncher.makeConfigFromCommandLineArgs(new String[]{".", "--addPlugin=countEverything"});
        assertTrue(config1.isPresent());
        var config2 = CLILauncher.makeConfigFromCommandLineArgs(new String[] {
                "--nonExistingOption"
        });
        assertFalse(config2.isPresent());
    }
}
