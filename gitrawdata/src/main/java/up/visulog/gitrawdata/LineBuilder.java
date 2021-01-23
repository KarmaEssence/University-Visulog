package up.visulog.gitrawdata;

import utils.Author;
import utils.MyDate;

/**
 * @version 1.0
 */
public class LineBuilder {
    private String id;
    private Author author;
    private MyDate date;
    private String description;
    private String mergedFrom;
    private int lineChanges;

    /**
     * constructor of a LineBuilder
     * @param id unique for each LineBuilder
     */
    public LineBuilder(String id) {
        this.id = id;
    }

    /** 
     * allows to set the property 'author'
     * @param author a String
     * @return the LineBuilder once the property is modified
     */
    public LineBuilder setAuthor(String author) {
        this.author = new Author(author);
        return this;
    }

    /** 
     * allows to set the property 'date'
     * @param date a 'MyDate' type object
     * @return the LineBuilder once the property is modified
     */
    public LineBuilder setDate(MyDate date) {
        this.date = date;
        return this;
    }

    /** 
     * allows to set the property 'description'
     * @param description a String
     * @return the LineBuilder once the property is modified
     */
    public LineBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /** 
     * allows to set the property 'mergedFrom'
     * @param mergedFrom the branch where the Line was merged from
     * @return the LineBuilder once the property is modified
     */
    public LineBuilder setMergedFrom(String mergedFrom) {
        this.mergedFrom = mergedFrom;
        return this;
    }

    /** 
     * allows to set the property 'lineChanges'
     * @param lineChanges how much lines were changed
     * @return the LineBuilder once the property is modified
     */
    public LineBuilder setLineChanged(int lineChanges) {
        this.lineChanges = lineChanges;
        return this;
    }

    /** 
     * calls the constructor of Line.java to create a new Line with a LineBuilder
     * @return Line the line newly created
     */
    public Line createLine() {
        return new Line(id, author, date, description, mergedFrom, lineChanges);
    }

    /** 
     * @return String an error message to the user
     */
    public String fieldIgnored() {
	   return "Some field of the line was ignored";
    }
    
}
