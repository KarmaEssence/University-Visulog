package up.visulog.gitrawdata;

import utils.Author;
import utils.MyDate;

/**
 * @version 1.0
 */
public class MergeBuilder {
    private final String id;
    private Author author;
    private MyDate date;
    private String description;
    private String mergedFrom;

    /** 
     * basic constructor of a MergeBuilder
     * @param id unique for each MergeBuilder
     */
    public MergeBuilder(String id) {
        this.id = id;
    }

    /** 
     * allows to set the property 'author'
     * @param author a String
     * @return the MergeBuilder once the property is modified
     */
    public MergeBuilder setAuthor(String author) {
        this.author = new Author(author);
        return this;
    }

    /** 
     * allows to set the property 'date'
     * @param date a 'MyDate' type object
     * @return the MergeBuilder once the property is modified
     */
    public MergeBuilder setDate(MyDate date) {
        this.date = date;
        return this;
    }

    /** 
     * allows to set the property 'description'
     * @param description a String
     * @return the MergeBuilder once the property is modified
     */
    public MergeBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /** 
     * allows to set the property 'mergedFrom'
     * @param mergedFrom the branch where the MergeBuilder was merged from
     * @return the MergeBuilder once the property is modified
     */
    public MergeBuilder setMergedFrom(String mergedFrom) {
        this.mergedFrom = mergedFrom;
        return this;
    }

    /** 
     * calls the constructor of Merge.java to create a new Merge with a MergeBuilder
     * @return Merge the merge newly created
     */
    public Merge createMerge() {
        return new Merge(id, author, date, description, mergedFrom);
    }

    /** 
     * @return String an error message to the user
     */
    public String fieldIgnored() {
	   return "Some field of the merge was ignored";
    }
    
}
