package up.visulog.cli;

import up.visulog.analyzer.Analyzer;
import up.visulog.cli.GenerateJSON;
import up.visulog.config.Configuration;
import up.visulog.config.OutputDir;
import up.visulog.config.OutputFile;
import up.visulog.config.OutputJson;
import up.visulog.config.OutputHtml;
import up.visulog.config.PluginConfig;
import up.visulog.analyzer.*;
import up.visulog.config.Help;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Optional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.Map;

/**
 * @version 1.0
 */
public class CLILauncher {
    private static boolean createHTML = false;
    private static boolean gitFolderExist = false;

    /**
     * the whole Visulog program starts here and it considers plugins wanted by the user
     */
    public static void main(String[] args) {
        var config = makeConfigFromCommandLineArgs(args);
        if (config.isPresent()) {
            var analyzer = new Analyzer(config.get());
            var results = analyzer.computeResults();
            ArrayList<String> instructionOrder = new ArrayList<String>();
            for (Map.Entry<String, PluginConfig> entry : config.get().getPluginConfigs().entrySet()) {
                String instruction = entry.getKey();
                if (instruction.equals("overwrite")) {
                    instructionOrder.add(0, instruction);
                } else {
                    instructionOrder.add(instruction);
                }
            }
            System.out.println("");
            Boolean dontOpen = false;
            for (String instruction : instructionOrder) {
                switch (instruction) {
                    case "countCommits":
                        GenerateJSON.makeJSON(results.toJSON(), "commitData");
                        break;

                    case "countMerges":
                        GenerateJSON.makeJSON(results.toJSON(), "mergeData");
                        break;

                    case "countLineChanges":
                        GenerateJSON.makeJSON(results.toJSON(), "lineData");
                        break;
                    case "countCommitsPerMonth":
                        GenerateJSON.makeJSON(results.toJSON(), "monthData");
                        break;
                    case "countCommitsPerWeek":
                        GenerateJSON.makeJSON(results.toJSON(), "weekData");
                        break;

                    case "overwrite":
                        System.out.println("-".repeat(21) + " Overwrite Log " + "-".repeat(21) + "\n");
                        deleteFile("../index.html");
                        deleteFile("../style.css");
                        System.out.println("-".repeat(57) + "\n");
                        break;

                    case "no-auto-open":
                        dontOpen = true;
                        break;

                    default:
                        break;
                }
            }
            if (createHTML || gitFolderExist) {
                GenerateWebpage.generateFiles();
            }
            if (!dontOpen && (createHTML || gitFolderExist)) {
                openBrowser();
            }

            if (OutputDir.getFolder() != null) {
                OutputDir.saveResultInDir();
            }
        } else {
            Help h = new Help();
            h.displayHelp();
        }
    }

    /**
     * allows to open index.html in a specified browser
     */
    private static void openBrowser() {
        Runtime runCMD = Runtime.getRuntime();
        String url = (new File("../index.html")).getAbsolutePath();
        String[] browserList = {"epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links", "lynx"};
        StringBuffer cmd = new StringBuffer();
        for (int i = 0; i < browserList.length; i++)
            if (i == 0)
                cmd.append(String.format("%s \"%s\"", browserList[i], url));
            else
                cmd.append(String.format(" || %s \"%s\"", browserList[i], url));
        try {
            runCMD.exec(new String[]{"sh", "-c", cmd.toString()});
        } catch (IOException failedAutoOpen) {
            failedAutoOpen.printStackTrace();
            System.out.println("\n**ERROR** \nFailed to auto-open 'index.html' in your default browser");
        }
    }

    /**
     * allows to delete a File
     * @param fileName the name of the File we have to delete
     */
    private static void deleteFile(String fileName) {
        File fileToDelete = new File(fileName);
        if (fileToDelete.delete()) {
            System.out.println("Deleted the old version of: '" + fileToDelete.getName() + "'\n");
        } else {
            System.out.println("**ERROR** Failed to delete: '" + fileToDelete.getName() + "'\n");
        }
    }

    /**
     * allows to create a config with the command lines printed by the user
     * @param args the command line arguments printed into the shell
     * @return Optional<Configuration>
     */
    static Optional<Configuration> makeConfigFromCommandLineArgs(String[] args) {
        var plugins = new HashMap<String, PluginConfig>();
        var gitPath = FileSystems.getDefault().getPath(".");
        for (var arg : args) {
            if (arg.startsWith("--")) {
                String[] parts = arg.split("=");
                String pName = parts[0];
                String pValue = (parts.length == 2) ? parts[1] : "";
                switch (pName) {
                    case "--addPlugin":
                        if (pValue.equals("countEverything")) {
                            plugins.put("countCommits", new PluginConfig() {
                            });
                            plugins.put("countMerges", new PluginConfig() {
                            });
                            plugins.put("countLineChanges", new PluginConfig() {
                            });
                            plugins.put("countCommitsPerMonth", new PluginConfig() {
                            });
                            plugins.put("countCommitsPerWeek", new PluginConfig() {
                            });

                            String[] res = argDirectory(pValue);
                            if (!gitFolderExist) {
                                createHTML = true;
                            }
                        } else {
                            System.out.println("\n**ERROR**");
                            System.out.println("An unknown value has been assigned to the argument: --addPlugin\n");
                            return Optional.empty();
                        }
                        break;

                    case "--loadConfigFile":
                        // Load options from a file
                        if ((args.length == 2 && args[1].contains("--loadConfigFile"))
                                || (args.length == 1)) {
                            if ((pValue.contains(".json") && !pValue.substring(0, pValue.length() - 5).contains(".")) ||
                                    (!pValue.contains(".json") && !pValue.substring(0, pValue.length()).contains("."))) {
                                String loadPath = OutputDir.getConfigurationDir(args, "--loadConfigFile");
                                OutputJson json = new OutputJson(loadPath);
                                try {
                                    String[] parameter = json.readJsonFile();
                                    if (parameter.length > 0) {
                                        return makeConfigFromCommandLineArgs(parameter);
                                    } else {
                                        System.out.println("Your file is not found");
                                    }

                                } catch (Exception e) {
                                    System.out.println("An occured error");
                                }

                            } else {
                                System.out.println("Your file is not found");
                            }
                        } else {
                            return Optional.empty();
                        }
                        break;

                    case "--justSaveConfigFile":
                        //Save command line options to a file instead of running the analysis
                        if ((pValue.contains(".json") && !pValue.substring(0, pValue.length() - 5).contains("."))
                                || (!pValue.contains(".json") && !pValue.substring(0, pValue.length()).contains("."))) {
                            String[] filePath = OutputDir.configurationDir(args, "--justSaveConfigFile");
                            if (filePath.length > 0) {
                                if (!filePath[1].endsWith(".json")) filePath[1] = filePath[1] + ".json";
                                if (filePath[1].startsWith(".json")) filePath[1] = "config" + ".json";
                                OutputJson jsonFile = new OutputJson(filePath[1], filePath[0]);
                                jsonFile.addList(args);
                                jsonFile.saveFile();
                            } else {
                                System.out.println("An occcured error");
                            }
                        } else {
                            System.out.println("Wrong file name...");
                        }
                        break;

                    case "--createOutputDir":
                        String[] createPath = OutputDir.configurationDir(args, "--createOutputDir");
                        if (!createPath[0].endsWith("visulog/")) {
                            OutputDir.setFolder(createPath[0]);
                            OutputDir.saveResultInDir();
                            OutputDir.setFolder(createPath[0]);
                        }
                        break;

                    case "--deleteOutputDir":
                        if (OutputHtml.dirPathExist()) OutputHtml.setDirectoryPath("");
                        OutputDir.outputDeleteDirectory(pValue);
                        OutputDir.setFolder(null);
                        break;

                    case "--help":
                        Help h = new Help();
                        h.displayHelp();
                        break;

                    case "--overwrite":
                        plugins.put("overwrite", new PluginConfig() {
                        });
                        String[] res = argDirectory(pValue);
                        break;

                    case "--no-auto-open":
                        plugins.put("no-auto-open", new PluginConfig() {
                        });
                        res = argDirectory(pValue);
                        break;

                    default:
                    System.out.println("\n**ERROR**");
                    System.out.println("Unrecognised command: "+arg+"\n");
                    return Optional.empty();
                }
            }else if(java.util.Arrays.asList(args).indexOf(arg) == 0 && 
                !gitFolderExist){
                arg = gitPathFolder(arg);
                if(DirectorySelect.analyzeThePathOfArg(arg)){
                    gitPath = FileSystems.getDefault().getPath(arg);
                    File file = new File(arg);
                    gitFolderExist = true;
                    System.out.println("\nAnalysing: "+gitPath);
                }else{
                    return Optional.empty();
                }
            }else{
                System.out.println("\n**ERROR**");
                System.out.println("Unrecognised command: "+arg+"\n");
                return Optional.empty();                
            }

        }
        if (createHTML) {
            String path = DirectorySelect.getGitDirectory();
            System.out.println("\nAnalysing: "+path);
            gitPath = FileSystems.getDefault().getPath(path);
            return Optional.of(new Configuration(gitPath, plugins));
        }
        return Optional.of(new Configuration(gitPath, plugins));
    }

    /**
     * gets the path of the git path folder 
     * @param arg 
     * @return String the text description of File
     */
    private static String gitPathFolder(String arg) {
        String[] path = argDirectory(arg);
        File file = new File("");
        for (int i = 0; i < path.length; i++) {
            if (path[i].equals("..")) {
                if (i == 0) {
                    file = new File(System.getProperty("user.dir"));
                }
                file = file.getParentFile();
            } else if (path[i].equals(".")) {
                if (i == 0) {
                    file = new File(System.getProperty("user.dir"));
                }
            } else {
                if (file.toString() == null) {
                    file = new File(path[i]);
                } else {
                    file = new File(file.toString() + "/" + path[i]);
                }
            }
        }
        return file.toString();
    }

    /**
     * this method transforms a String into an array of Strings representing the directory
     * @param n String
     * @return res an Array of Strings 
     */
    private static String[] argDirectory(String n) {
        String s = "";
        int c = 1;
        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) == '/') c++;
        }
        String[] res = new String[c];
        c = 0;
        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) == '/') {
                res[c] = s;
                c++;
                s = "";
            } else {
                s += n.charAt(i);

            }
            res[c] = s;
        }
        return res;
    }

}
