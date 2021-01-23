package up.visulog.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 */
public class GenerateJSON {

    /**
     * creates a File and calls makeFile(..)
     * @param data String 
     * @param typeToCount String
     */
    public static void makeJSON(String data, String typeToCount){
        boolean createFile = false;
        File dataFile = new File("../data.js");
        if (!dataFile.exists()){
            createFile = true;
        }
        makeFile(createFile, data, typeToCount);
    }

    /**
     * we progressively fill the var 'content' with the BufferedReader, we modify 'data' ans we call writeFile
     * @param createFile true if the File didn't exist, false if it was existing 
     * @param data a String 
     * @param typeToCount useful for updateData method
     */
    private static void makeFile(boolean createFile, String data, String typeToCount){
        String content="";
        try {
            File dataFile = new File("../data.js");
            BufferedReader br = new BufferedReader(new FileReader(dataFile)); 
            while ((content = br.readLine()) != null) {
                content += content; 
            }
        } catch (IOException e1){
            try {
                FileWriter writer = new FileWriter("../data.js");
                writer.write("");
                writer.close();
            } catch (IOException e2) {
                System.out.println("**ERROR** An error occurred.");
                e2.printStackTrace();
            }
        }
        data = updateData(content, data, typeToCount);
        writeFile(data, "data.js", createFile);
    }

    /**
     * we fill a String with another String
     * @param content 
     * @param data 
     * @param typeToCount 
     * @return newData a variable filled with the loop on splitData
     */
    private static String updateData(String content, String data, String typeToCount){
        if (content==null){
            return data;
        }
        List<String> splitData = new ArrayList<String>(Arrays.asList(content.split(";")));
        String newData="";
        for (String e : splitData){
            if (e.contains(typeToCount)){
                e=data.substring(0, data.length()-1);
            }
            newData+=e+";";
        }
        return newData;
    }

    /**
     * allows to modify the content of a given File 
     * @param content the content which will be added 
     * @param fileName the name of the File which will be modified
     * @param createFile boolean
     */
    private static void writeFile(String content, String fileName, boolean createFile){
        try {
            FileWriter writer = new FileWriter("../"+fileName);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.out.println("**ERROR** An error occurred.");
            e.printStackTrace();
        }
    }
    
}
