package up.visulog.cli;

import java.io.File;
import java.nio.file.Path;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * @version 1.0
 */
public class DirectorySelect { 
    
    /**
     * calls getPathOfDirectory()
     * @return String the git directory chosen by the user
     */
    public static String getGitDirectory(){
        JFrame frame = new JFrame("frame");        
        return getPathOfDirectory();
    }

    /**
     * allows to get the path of a (git) directory
     * @return String the path 
     */
    private static String getPathOfDirectory(){
        try{
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select a Git Project Directory");
            chooser.setCurrentDirectory(new java.io.File(".."));
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                boolean goodDirectory = isValidGitDirectory(path);
                while(!goodDirectory){

                    JOptionPane.showMessageDialog(chooser,
                    "\n**ERROR**\nThe directory '"+path+"' does NOT have a '.git' sub directory",
                    "Incorrect Directory Selection",
                    JOptionPane.ERROR_MESSAGE);
        
                    path = getPathOfDirectory();
                    goodDirectory = isValidGitDirectory(path);
                }
                return path;
            }
            System.exit(0);
        } catch(Exception e){
            System.out.println("**ERROR** An error occurred when selecting a file.");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * checks if a directory is a git directory
     * @param path the directory
     * @return true if path is a git Directory
     */
    private static boolean isValidGitDirectory(String path) {
        File dirExists = new File(path);
        if (!dirExists.exists()){
            System.out.println("\n**ERROR**");
            System.out.println("Sorry, but that directory does not exist");
        } else{
            File dirGitExists = new File(path+"/.git");
            if (dirGitExists.exists()){
                return true;
            }
        }
        return false;
    }

    /**
     * calls isValidGitDirectory()
     * @return boolean look at isValidGitDirectory() description
     */
    public static boolean analyzeThePathOfArg(String path){
        if(isValidGitDirectory(path)){
            return true;
        }
        return false;
    }

}
