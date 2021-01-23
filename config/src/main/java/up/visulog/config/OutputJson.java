package up.visulog.config;

import up.visulog.config.OutputDir;
import up.visulog.config.OutputFile;

import java.io.File;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * @version 1.0
 */
 public class OutputJson extends OutputFile{
	private static String directoryPath;
	private JSONObject obj;
	private JSONArray list;

	/**
	 * the 1st constructor for OutputJson
	 * @param name the name of the File
	 * @param path the directoryPath of the File
	 */
	public OutputJson(String name,String path){
		super(name,path);
		obj = new JSONObject();
		list = new JSONArray();
	}

	/**
	 * the 2nd constructor for OutputJson
	 * @param name the name of the File
	 */
	public OutputJson(String name){
		super(name);
		obj = new JSONObject();
        list = new JSONArray();
	}

	/**
	 * a setter of directoryPath
	 * @param path the new directoryPath of the File
	 */
	public static void setDirectoryPath(String path){
		directoryPath = path;
	}

	/**
	 * checks if the File is a JSON file by ckecking the end of fileName
	 * @return true if the File is a JSON file, false if not
	 */
	public boolean isJsonFile(){
		for(int i=0;i<fileName.getName().length();i++){
			if(fileName.getName().charAt(i) == '.'){
				return fileName.getName().endsWith("json");
			}
		}
		return false;
	}

	/**
	 * this method considers the command line arguments and if they include the three plugins below we put the instrucion into the JSONArray
	 * @param args the command line arguments
	 */
	public void addList(String[] args){
		if(args.length>0){
            for(String s:args){
            	if(!s.contains("--justSaveConfigFile") && 
            	   !s.contains("--loadConfigFile")&& 
            	   !s.contains("--deleteOutputDir")){
                	list.put(s);
            	}
            }
            obj.put("Parameter",list);
        }
	}

	/**
	 * this method calls outputEdit of the mother-class
	 */
	public void saveFile(){
		super.outputEdit(obj.toString());
	}

	/**
	 * this method reads a JSON file and returns an array 
	 * @return String[] 
	 * @throws Exception
	 */
	public String[] readJsonFile() throws Exception {
		if(!isJsonFile())return new String[0];
		FileReader file = new FileReader(fileName);
		BufferedReader reader = new BufferedReader(file);
		String json = reader.readLine();
		reader.close();
		return stringToArray(json);
	}

	/** 
	 * this method finds how much parameter(s) there is(are)
	 * @param parameter a String containing all paramters 
	 * @return c the number of parameters there are
	 */
	private int numberOfParameters(String parameter){
		boolean verif = false;
		int c = 1;
		for(int i=0;i<parameter.length();i++){
			if(parameter.charAt(i) == '[') verif = true;
			if(verif && parameter.charAt(i) == ','){
				c++;
			}

		}
		return c;
	}

	/** 
	 * converts a String to an Array of Strings by reading the String
	 * @param parameter
	 * @return res
	 */
	public String[] stringToArray(String parameter){
		String[] res = new String[numberOfParameters(parameter)];
		int c=0;
		boolean verif = false;
		String tmp = "";
		for (int i=0;i<parameter.length();i++) {
			if(parameter.charAt(i) == ']') verif = false;
			if(verif){
				if(parameter.charAt(i)!= '"' && parameter.charAt(i)!=','){
					res[c] += parameter.charAt(i);
				}
				
			}
			if (parameter.charAt(i) == ',') {
				c++;
				res[c] = "";
			}
			if(parameter.charAt(i) == '['){
				res[c] = "";
				verif = true;
			} 
		}
		return res;
	}
}