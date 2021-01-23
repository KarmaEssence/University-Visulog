package up.visulog.config;

import java.util.*;

/**
 * @version 1.0
 */
public class Help{
	private static LinkedList<String> listcommande;

	/**
	 * the constructor of a 'Help' type object with an empty LinkedList
	 */
	public Help(){
		listcommande = new LinkedList<String>();
	}

	/**
	 * this method adds a new String to the LinkedList
	 * @param s the String to be added to listcommande
	 */
	public static void add(String s){
		listcommande.add(s);
	}

	/**
	 * this method prints as much spaces as we request
	 * @param n the number of spaces required
	 */
	public static void printSpace(int n){
		for(int i=0;i<n;i++){
			System.out.print(" ");
		}
	}

	/**
	 * this method prints as much hyppens as we request
	 * @param n the number of hyppens required
	 */
	public static void printHyppen(int n){
		for(int i=0;i<n;i++){
			System.out.print("-");
		}
	}

	public static void displayHelp(){
		remplirList();
		printHyppen(22);
		System.out.print("command available");
		printHyppen(21);
		System.out.println();
		while(!listcommande.isEmpty()){
			printSpace(2);
			System.out.println(listcommande.pop());
		}
		printHyppen(60);
	}

	/**
	 * this method calls add(s) eight times to fill listcommande with eight command LinkedList
	 */
	public static void remplirList(){
		add("--addPlugin=countEverything");
		add("--addPlugin=countEverything --overwrite");
		add("--addPlugin=countEverything --no-auto-open");
		add("--loadConfigFile=<Path of json file>");
		add("--justSaveConfigFile=<Directory and name or just name>");
		add("--createOutputDir=[<Directory name>]");
		add("--deleteOutputDir=<Directory name>");
		add("--help");
	}

}
