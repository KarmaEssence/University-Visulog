package up.visulog.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @version 1.0
 */
public class OutputDir{

    private static String folder;

    /**
     * gives an access to the git path
     * @returns s the git path  
     */
    public static String getVisulogPath(){
        String s = System.getProperty("user.dir");
        s = s.substring(0,s.length()-4);
        return s;
    }

    /**
     * gives an access to the git directory
     * @return s the complete path, including the directory
     */
    public static String getDirectoryPath(String name){
        String s = System.getProperty("user.dir");
        s = s.substring(0,s.length()-4);
        return s+"/"+name;
    }

    /**
     * a getter for folder
     * @return folder the folder of the OutputDir
     */
    public static String getFolder(){
        return folder;
    }

    /**
     * a setter for folder
     * @param nameFolder the new name of the folder
     */
    public static void setFolder(String nameFolder){
        folder = nameFolder;
    }

    /**
     * checks the content of the expression
     * @param s the expression
     * @return false if s starts with '/' or contains a symbol non alphanumerical
     */ 
    public static boolean avoidExpression(String path){
        if(path.length()== 0 || path.charAt(0)==47){
            System.out.println("Wrong folder name");
            return false;
        }
        for(int i=0;i<path.length();i++){
            if((path.charAt(i)>32 && path.charAt(i)<45)
            || (path.charAt(i)>45 && path.charAt(i)<47)
            || (path.charAt(i)>57 && path.charAt(i)<65)
            || (path.charAt(i)>90 && path.charAt(i)<97)
            || (path.charAt(i)>122 && path.charAt(i)<127)){
                System.out.println("Wrong folder name");
                return false;
            }
        }
        return true;
    }

    /**
     * checks if the expression contains the forbidden command
     * @param s the expression
     * @return true if s contains --deleteOutputDir, false if not
     */
    public static boolean avoidIllegalCommand(String s){
        return s.contains("--deleteOutputDir");
    }

    /**
     * transform a String into an Array
     * @param arg the directory 
     * @return res the directory as an Array
     */
    public static String[] argDirectory(String arg){
        String s = "";
        int c = 1;
        for (int i=0;i<arg.length();i++) {
            if(arg.charAt(i) == '/') c++;
        }
        String[] res = new String[c];
        c=0;
        for (int i=0;i<arg.length();i++) {
            if(arg.charAt(i) == '/'){
                res[c] = s;
                c++;
                s = "";
            }else{
                s+=arg.charAt(i);

            }
            res[c] = s;
        }
        return res;
    }

    /**
     * allows to fill a var with getVisulogPath(), then the program creates a File and finally it creates a folder
     * @param name the name of the File
     */
    public static void outputCreateDirectory(String name){
        if(avoidExpression(name)){
            String path = getVisulogPath();
            File file = new File(path+"/"+name);
            if(!file.exists()){
                if (file.mkdir()) {
                    System.out.println("-".repeat(21)+" Sucessfull: Create dir "+"-".repeat(21)+"\n");
                    System.out.println("Folder created: " + name +"\n");
                    System.out.println("Path of folder: " + file.getPath() +"\n");
                    System.out.println("-".repeat(57)+"\n");
                    
                }else{
                    System.out.println("-".repeat(21)+" Failed: Create dir "+"-".repeat(21)+"\n");
                    System.out.println("Folder not created: " + name +"\n");
                    System.out.println("-".repeat(57)+"\n");
                }
                
            }else{
        
                System.out.println("-".repeat(21)+" Sucessfull: Create dir "+"-".repeat(21)+"\n");
                System.out.println("Folder already exists."+"\n");
                System.out.println("Path of folder: " + file.getPath() +"\n");
                System.out.println("-".repeat(66)+"\n");
            }
        }    
    } 

    /**
     * allows to delete a directory 
     * @param name the name of the File
     */
    public static void outputDeleteDirectory(String name){
        if(avoidExpression(name)){
            String path = getVisulogPath();
            File d = new File(path+"/"+name);
            if(d.exists()){
            File[] file = d.listFiles();
            String tmp = "";
            for(int i=0;i<file.length;i++){
                if(file[i] != null){
                    tmp = file[i].getName();
                    if(file[i].isDirectory()){
                        outputDeleteDirectory(name+"/"+file[i].getName());
                    }else{
                        if (file[i].delete()) {
                            System.out.println("File deleted: " + tmp);
                        }else{
                            System.out.println("File is not deleted: " + tmp);                                }     
                        } 
                    }
                }
                tmp = d.getName();
                if (d.delete()) {
                System.out.println("Folder deleted: " + tmp);
                }   
            }else{
                System.out.println("Wrong folder name");
            }
        }
    } 

    /**
     * analyzing the command line arguments
     * @param args the command line arguments
     * @param name 
     * @return String[] we fill it with null or --createOutputDir plugin
     */
    public static String[] ParameterFromArgs(String[] args,String name){
        String[] tmp;
        for(int i=0;i<args.length;i++){
            tmp = args[i].split("=");
            if(tmp[0].equals(name)){
                if(tmp.length == 1 && name.equals("--createOutputDir")){
                    tmp = new String[2];
                    tmp[0] = "--createOutputDir";
                    tmp[1] = "OutputDir";
                }
                return tmp;
            }
        }
        return new String[2];
    }

    /**
     * checks if the config is config chosen by the user is correct 
     * @param args the command line arguments
     * @param name 
     * @return String[] depends on the situation
     */
    private static String[] configIsCorrect(String[] args,String name){
        if(avoidIllegalCommand(name))return new String[0];
        String[] res = ParameterFromArgs(args,name);
        if(res.length != 2)return new String[0];
        String[] argDir = argDirectory(res[1]);
        if(argDir.length == 0) return new String[0];
        return argDir;
    }

    /**
     * checks if the directory isn't empty 
     * @param argDir an array of strings
     * @return false if we find tmp = argDir[at a row] empty, true if not
     */
    private static boolean argDirisNotEmpty(String[] argDir){
        for(String tmp:argDir){
            if(tmp.isEmpty()){
                System.out.println("Wrong folder name");
                return false;
            }
        }
        return true;
    }

    /**
     * this methods creates a directory
     * @param argDir String[]
     * @param name String
     * @return string the path 
     */
    private static String createDir(String[] argDir,String name){
        String tmp = (name.equals("--justSaveConfigFile"))? "ConfigFile":"";
        String path = (argDir.length!=1)?"":tmp;
        if(!argDirisNotEmpty(argDir))return "";
        if(argDir.length == 1 && name.equals("--justSaveConfigFile")){
             outputCreateDirectory(path);
        }else{
            int less = (name.equals("--justSaveConfigFile"))?1:0;
            for (int i=0;i<argDir.length-less;i++) {
                if(avoidExpression(argDir[i])){
                    if(i==0) path = argDir[i];
                    else path = path + "/" + argDir[i];
                    outputCreateDirectory(path);
                }
            } 
        }
        return path;
    }

    /**
     * considers the plugin and finds the path of the visulog File
     * @param argDir
     * @param name the plugin
     * @param path the name of the Path 
     * @return String[] containing the Path of the File
     */
    private static String[] getPathFile(String[] argDir,String name,String path){
        if(name.equals("--justSaveConfigFile")){
            String [] pathfile = new String[2];
            pathfile[0] = getVisulogPath() + "/" + path;
            pathfile[1] = argDir[argDir.length-1];
            return pathfile;
        }else if(name.equals("--createOutputDir")){
            String [] pathfile = new String[1];
            pathfile[0] = getVisulogPath() + "/" + path;
            return pathfile;
        }else{
            return new String[0];
        }
    }

    /**
     * creates a directory and returns the path of this directory
     * @param args the command line arguments
     * @param name the name of the file
     * @return pathfile String[]
     */
    public static String[] configurationDir(String[] args,String name){
        String[] argDir = configIsCorrect(args,name);
        String path = createDir(argDir,name);
        String[] pathfile = getPathFile(argDir,name,path);
        return pathfile;
    }

    /**
     * finds the path of the configuration directory 
     * @param args the command line arguments
     * @param the name of the file
     * @return path String
     */
    public static String getConfigurationDir(String[] args,String name){
        String[] argDir = configIsCorrect(args,name);
        String path = getVisulogPath() + "/";
        for (int i=0;i<argDir.length;i++){
            if(i==0) path += argDir[i];
            else path = path + "/" + argDir[i];
        }
        return path; 
    }

    /**
     * allows to save a File into the system by copying
     * @param location1 
     * @param location2
     */
    public static void saveFileInDir(String location1, String location2){
        var paths1 = FileSystems.getDefault().getPath(location1);
        var paths2 = FileSystems.getDefault().getPath(location2);
        try{
            Files.copy(paths1,paths2,StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            e.printStackTrace();
        }
        

    }

    /**
     * calls saveFileInDir(..) for the Visulog files such as HTML page, CSS sheet and Canvasjs files
     */
    public static void saveResultInDir(){
        String visulogPath = getVisulogPath();
        saveFileInDir(visulogPath+"/index.html",folder+"/index.html");
        saveFileInDir(visulogPath+"/style.css",folder+"/style.css");
        saveFileInDir(visulogPath+"/data.js",folder+"/data.js");
        
        File core = new File(folder+"/core");
        File canvasjs = new File(folder+"/core/canvasjs");
        if(!core.exists()){
            core.mkdirs();
            saveFileInDir(visulogPath+"/core/jquery-1.11.1.min.js",folder+"/core/jquery-1.11.1.min.js");
        }
        if(!canvasjs.exists()){
            saveFileInDir(visulogPath+"/core/canvasjs",folder+"/core/canvasjs");
            saveFileInDir(visulogPath+"/core/canvasjs/canvasjs.min.js",folder+"/core/canvasjs/canvasjs.min.js");
            saveFileInDir(visulogPath+"/core/canvasjs/canvasjs.react.js",folder+"/core/canvasjs/canvasjs.react.js");
            saveFileInDir(visulogPath+"/core/canvasjs/jquery.canvasjs.min.js",folder+"/core/canvasjs/jquery.canvasjs.min.js");

        }
        
    }

}