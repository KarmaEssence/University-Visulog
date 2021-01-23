package up.visulog.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * @version 1.0
 */
public abstract class OutputFile{
	protected String directoryPath;
	protected File fileName;

	/**
	 * the 1st constructor of an OutputFile
	 * @param name the name of the File
	 * @param path the directoryPath of the File
	 */
	public OutputFile(String name,String path){
		directoryPath = path;
		fileName = createOutputFile(name);
	}

	/**
	 * the 2nd constructor of OutputFile
	 * @param name the name of the File
	 */
	public OutputFile(String name){
		fileName = new File(name);
	}

	/**
	 * a getter for directoryPath
	 * @return directoryPath
	 */
	public String getDirectoryPath(){
		return directoryPath;
	}

	/**
	 * a getter for fileName
	 * @return fileName
	 */
	public File getFileName(){
		return fileName;
	}
	
	/**
	 * tries to create a File, throws an exception if unable to do it. Sends message to the user according to the situation
	 * @param name the name of the new File
	 * @return file 
	 */
	private File createOutputFile(String name){
		String path = directoryPath + "/";
		try{
			File file = new File(path+name);
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			}else{
				System.out.println("File already exists.");
			}
			return file;
		}catch(IOException e){
			System.out.println("An error occured.");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * tries to write a text into a File with a FileWriter
	 * @param text the content to add
	 */
	private void editOutputFile(String text){
		try{
			FileWriter wf = new FileWriter(fileName.getAbsolutePath());
			wf.write(text);
			if(fileName.getName().contains("json"))wf.flush();
			wf.close();
		}catch(IOException e){
			System.out.println("Error: Text are not saved");
			e.printStackTrace();
		}
	}

	/**
	 * allows to call editOutputFile if the File is existing 
	 * @param text the text to add into the File
	 */
	public void outputEdit(String text){
		if(directoryPath.length()>0 && fileName!=null){
				editOutputFile(text);
		}
	}

}