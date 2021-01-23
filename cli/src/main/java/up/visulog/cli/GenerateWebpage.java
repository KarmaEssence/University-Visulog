package up.visulog.cli;

import java.io.File;
import java.io.IOException;

/**
 * @version 1.0
 */
class GenerateWebpage {

	/**
	 * we read the table 'exists' with a loop and we print an error message if a case is 'false' for CanvasJS files and HTML files
	 */
    public static void generateFiles(){
		boolean[] exists = doCanvasJSFilesExist();
		for (boolean x : exists){
			if (!x){
				System.out.println("\n**ERROR**\nThe CanvasJS folder is missing or a file within it is missing");
				System.out.println("\nIMPORTANT: Rename the downloaded unzipped folder to 'canvasjs' in the 'core' directory or it will not work");
				System.out.println("Download from: https://canvasjs.com/download-html5-charting-graphing-library/?f=chart (Project was written on v3.2 later versions may cause issues)\n");
				return;
			}
		}
		MakeWebFiles.makeFiles();
		exists = doHTMLFilesExist();
		for (int i=0; i<exists.length; i++){
			if (!exists[i]){
				if (i==0){
					System.out.println("\n**ERROR** An error has occured, the file 'index.html' is missing\n");
					return;
				} else {
					System.out.println("\n**ERROR** An error has occured, the file 'style.css' is missing\n");
					return;
				}
			}
		}
	}
	
	/**
	 * this method checks index.html and style.css 
	 * @return existanceList existanceList[0] shows if index.html exists AND is a File, existanceList[1] does the same with style.css
	 */
	private static boolean[] doHTMLFilesExist(){
		boolean[] existanceList = new boolean[2];
            
		File indexHTML_File = new File("../index.html");
		existanceList[0] = indexHTML_File.exists() && indexHTML_File.isFile();
	
		File styleCSS_File = new File("../style.css");
		existanceList[1] = styleCSS_File.exists() && styleCSS_File.isFile();

		return existanceList;
	}
	
	/**
	 * this method checks the files : canvasjs, canvasjs.min.js, canvasjs.react.js, ...
	 * @return existanceList existanceList[0] shows if canvasjs exists AND is a directory, existanceList[1] shows if canvasjs.min.js exists AND is a file, ...
	 */
	private static boolean[] doCanvasJSFilesExist(){
		boolean[] existanceList = new boolean[5];
	
		File canvasJS_Dir = new File("../core/canvasjs");
		existanceList[0] = canvasJS_Dir.exists() && canvasJS_Dir.isDirectory();

		File canvasJS_File1 = new File("../core/canvasjs/canvasjs.min.js");
		existanceList[1] = canvasJS_File1.exists() && canvasJS_File1.isFile();

		File canvasJS_File2 = new File("../core/canvasjs/canvasjs.react.js");
		existanceList[2] = canvasJS_File2.exists() && canvasJS_File2.isFile();

		File canvasJS_File3 = new File("../core/canvasjs/jquery.canvasjs.min.js");
		existanceList[3] = canvasJS_File3.exists() && canvasJS_File3.isFile();	

		File canvasJS_File4 = new File("../core/jquery-1.11.1.min.js");
		existanceList[4] = canvasJS_File3.exists() && canvasJS_File4.isFile();		

		return existanceList;
	}

	
    
}
