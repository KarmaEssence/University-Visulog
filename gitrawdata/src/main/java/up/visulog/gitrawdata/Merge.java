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
public class Merge{
    
    public final String id;
    public final MyDate date;
    public final Author author;
    public final String description;
    public final String mergedFrom;

    /**
     * basic constructor to create merges 
     * @param id a String which is unique for each merge
     * @param Author who created the merge
     * @param date when the merge was created
     * @param description a text description of the merge
     * @param mergedFrom the branch where the merge comes from 
     */
    public Merge(String id, Author author, MyDate date, String description, String mergedFrom) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.description = description;
        this.mergedFrom = mergedFrom;
    }

    /**
     * allows to call parseLog with a specified ProcessBuilder
     * @param gitPath a Path 
     * @return List<Merge> look at parseLog for explanations
     */
    public static List<Merge> parseLogFromCommand(Path gitPath) {
        ProcessBuilder builder = new ProcessBuilder("git", "--git-dir="+gitPath+"/.git", "log", "--merges").directory(gitPath.toFile());
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
     * this method fills a list of Merges and returns it
     * @param reader a BufferedReader
     * @return result an ArrayList of Merges
     */
    public static List<Merge> parseLog(BufferedReader reader) {
        var result = new ArrayList<Merge>();
        Optional<Merge> merge = parseMerge(reader);
        while (merge.isPresent()) {
            result.add(merge.get());
            merge = parseMerge(reader);
        }
        return result;
    }

    /**
     * Parses a log item and outputs a commit object. Exceptions will be thrown in case the input does not have the proper format.
     * @param input a BufferedReader
     * @return Optional<Merge>
     */
    public static Optional<Merge> parseMerge(BufferedReader input) {
        try {
            var line = input.readLine();
            if (line == null) return Optional.empty(); // if no line can be read, we are done reading the buffer
            var idChunks = line.split(" ");
            if (!idChunks[0].equals("commit")) parseError();
            var builder = new MergeBuilder(idChunks[1]);

            line = input.readLine();
            while (!line.isEmpty()) {
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
                line = input.readLine(); //prepare next iteration
                if (line == null) parseError(); // end of stream is not supposed to happen now (commit data incomplete)
            }

            // now read the commit message per se
            var description = input
                    .lines() // get a stream of lines to work with
                    .takeWhile(currentLine -> !currentLine.isEmpty()) // take all lines until the first empty one (commits are separated by empty lines). Remark: commit messages are indented with spaces, so any blank line in the message contains at least a couple of spaces.
                    .map(String::trim) // remove indentation
                    .reduce("", (accumulator, currentLine) -> accumulator + currentLine); // concatenate everything
            builder.setDescription(description);
            return Optional.of(builder.createMerge());
        } catch (IOException e) {
            parseError();
        }
        return Optional.empty(); // this is supposed to be unreachable, as parseError should never return
    }

    /** Helper function for generating parsing exceptions
     * @throws RuntimeException if format is Wrong
     */
    private static void parseError() {
        throw new RuntimeException("Wrong commit format.");
    }

    @Override
    /**
     * text description of a Merge
     * @return String showing all caracteristics of a Merge
     */
    public String toString() {
        return "Merge{" +
                "id='" + id + '\'' +
                (mergedFrom != null ? ("mergedFrom...='" + mergedFrom + '\'') : "") + //TODO: find out if this is the only optional field --done
                (date != null ? (", date='" + date + '\'') : "") +
                (author != null ? (", author='" + author + '\'') : "") +
                ", description='" + description + '\'' +
                '}';
    }
}
