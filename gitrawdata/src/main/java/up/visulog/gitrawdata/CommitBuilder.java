package up.visulog.gitrawdata;

import utils.Author;
import utils.MyDate;

/**
 * @version 1.0
 */
public class CommitBuilder {
    private String id;
    private Author author;
    private MyDate date;
    private String description;
    private String mergedFrom;

    /**
     * constructor of a CommitBuilder
     * @param id unique for each CommitBuilder
     */
    public CommitBuilder(String id) {
        this.id = id;
    }
    /** 
     * allows to set the property 'author'
     * @param author a String
     * @return the CommitBuilder once the property is modified
     */
    public CommitBuilder setAuthor(String author) {
        this.author = new Author(author);
        return this;
    }

    /** 
     * allows to set the property 'date'
     * @param date a 'MyDate' type object
     * @return the CommitBuilder once the property is modified
     */
    public CommitBuilder setDate(MyDate date) {
        this.date = date;
        return this;
    }
    /** 
     * allows to set the property 'description'
     * @param description a String
     * @return the CommitBuilder once the property is modified
     */
    public CommitBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /** 
     * allows to set the property 'mergedFrom'
     * @param mergedFrom the branch where the Commit was merged from
     * @return the CommitBuilder once the property is modified
     */
    public CommitBuilder setMergedFrom(String mergedFrom) {
        this.mergedFrom = mergedFrom;
        return this;
    }

    /** 
     * calls the constructor of Commit.java to create a new Commit with a CommitBuilder
     * @return Commit the commit newly created
     */
    public Commit createCommit() {
        return new Commit(id, author, date, description, mergedFrom);
    }

    /** 
     * @return String an error message to the user
     */
    public String fieldIgnored() {
	   return "Some field of the commit was ignored";
    }
    
}
