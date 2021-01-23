package up.visulog.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @version 1.0
 */
public class OutputHtml extends OutputFile{
	private static String directoryPath;

	/**
	 * constructor of an 'OutputHtml' type object
	 * @param name the name of the HTML page 
	 */
	public OutputHtml(String name){
		super(name,directoryPath);
	}

	/** 
	 * a setter of directoryPath
	 * @param path the new value of directoryPath
	 */
	public static void setDirectoryPath(String path){
		directoryPath = path;
	}

	/** 
	 * checks if directoryPath is not null or empty
	 * @returns true of directoryPath isn't null and isn't empty
	 */
	public static boolean dirPathExist(){
		return directoryPath!= null && directoryPath.length()>0;
	}
}