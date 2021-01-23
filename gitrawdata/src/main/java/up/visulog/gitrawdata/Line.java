package up.visulog.gitrawdata;

import utils.Author;
import utils.MyDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @version 1.0
 */
public class Line{

    public final String id;
    public final MyDate date;
    public final Author author;
    public final String description;
    public final String mergedFrom;
    public final int lineChanges;

    /**
     * basic constructor to create Lines 
     * @param id a String which is unique for each Line
     * @param Author who created the Line
     * @param date when the Line was created
     * @param description a text description of the Line
     * @param mergedFrom the branch where the Line comes from 
     * @param lineChanges how much lines were changed
     */
    public Line(String id, Author author, MyDate date, String description, String mergedFrom, int lineChanges) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.description = description;
        this.mergedFrom = mergedFrom;
        this.lineChanges = lineChanges;
    }
    
    /**
     * allows to call parseLog with a specified ProcessBuilder
     * @param gitPath a Path 
     * @return List<Line> look at parseLog for explanations
     */
    public static List<Line> parseLogFromCommand(Path gitPath) {
        ProcessBuilder builder = new ProcessBuilder("git", "--git-dir="+gitPath+"/.git", "log", "--shortstat", "--no-merges", "--pretty=format:commit %H%nAuthor: %aN <%aE>%nDate: %ad").directory(gitPath.toFile());
        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException("Error running \"git log\".", e);
        }
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return parseLog(reader);
    }
    /**
     * this method fills a list of Commits and returns it
     * @param reader a BufferedReader
     * @return result an ArrayList of Commits
     */
    public static List<Line> parseLog(BufferedReader reader) {
        var result = new ArrayList<Line>();
        Optional<Line> lines = parseLine(reader);
        while (lines.isPresent()) {
            result.add(lines.get());
            lines = parseLine(reader);
        }
        return result;
    }

    /**
     * Parses a log item and outputs a line object. Exceptions will be thrown in case the input does not have the proper format.
     * @param input a BufferedReader
     * @return Optional<Line> 
     */
    public static Optional<Line> parseLine(BufferedReader input) {
        try {
            boolean stopReading = false;
            var line = input.readLine();
            if (stopReading||line==null) return Optional.empty(); // if no line can be read, we are done reading the buffer
            var idChunks = line.split(" ");
            if (!idChunks[0].equals("commit")) {
                parseError();
            }
            var builder = new LineBuilder(idChunks[1]);

            line = input.readLine();
            while (!line.isEmpty()) {
                try{
                    var colonPos = line.indexOf(":");                    
                    var fieldName = line.substring(0, colonPos);
                    var fieldContent = line.substring(colonPos + 1).trim();
                    switch (fieldName) {
                        case "Author":
                            builder.setAuthor(fieldContent.replace("'", "\\'"));
                            break;
                        case "Merge":
                            builder.setMergedFrom(fieldContent.replace("'", "\\'"));
                            break;
                        case "Date":
                            MyDate date = new MyDate(fieldContent.replace("'", "\\'"));
                            builder.setDate(date);
                            break;
                        default:
			    System.out.println(builder.fieldIgnored());
			    
                    }
                } catch(Exception e1Exception){
                    String [] fileEdits = line.split(", ");
                    int insertions = Integer.parseInt(fileEdits[1].split(" ")[0]);
                    int deletions=0;
                    try {
                        deletions = Integer.parseInt(fileEdits[2].split(" ")[0]);
                    } catch (Exception e2Exception){
                        deletions= 0;
                    }
                    stopReading = true;
                    builder.setLineChanged(insertions+deletions);
                    break;
                }
                line = input.readLine(); //prepare next iteration
                if (line == null) break; 
            }

            // now read the commit message per se
            var description = input
                    .lines() // get a stream of lines to work with
                    .takeWhile(currentLine -> !currentLine.isEmpty()) // take all lines until the first empty one (commits are separated by empty lines). Remark: commit messages are indented with spaces, so any blank line in the message contains at least a couple of spaces.
                    .map(String::trim) // remove indentation
                    .reduce("", (accumulator, currentLine) -> accumulator + currentLine); // concatenate everything
            builder.setDescription(description);
            return Optional.of(builder.createLine());
        } catch (IOException e) {
            parseError();
        }
        return Optional.empty(); // this is supposed to be unreachable, as parseError should never return
    }

    /** Helper function for generating parsing exceptions. 
     * @throws RuntimeException if format is Wrong
     */
    private static void parseError() {
        throw new RuntimeException("Wrong commit format.");
    }

    @Override
    /**
     * text description of a commit
     * @return String a sentence introducing all the caracteristics of a Line
     */
    public String toString() {
        return "LineChanges{" +
                "id='" + id + '\'' +
                (mergedFrom != null ? ("mergedFrom...='" + mergedFrom + '\'') : "") + //TODO: find out if this is the only optional field --done
                (date != null ? (", date='" + date + '\'') : "") +
                (author != null ? (", author='" + author + '\'') : "") +
                ", description='" + description + '\'' +
                '}';
    }
}
