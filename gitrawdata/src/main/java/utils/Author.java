package utils;

import java.util.Objects;

/**
 * @version 1.0
 */
public class Author {
    String name, mail;

    /** 
     * the constructor of an Author
     * @param str a String containing the name and the mail adress
     */
    public Author(String str) {
//        if(str.lastIndexOf("<") == -1)
        String[] author = str.split("<");
        if (author.length > 1) {
            this.name = author[0].substring(0, author[0].length() - 1);
            this.mail = author[1].substring(0, author[1].length() - 1);
        } else
            this.name = str;
    }

    /**
     * getter of the mail
     * @return mail the property 'mail'
     */
    public String getMail() {
        return mail;
    }

    /**
     * getter of the name
     * @return name the property 'name'
     */
    public String getName() {
        return name;
    }

    @Override
    /**
     * text description of an Author
     * @return String 
     */
    public String toString() {
        return name + ((mail != null) ? (" <" + mail + ">") : "");
    }

    @Override
    /**
     * checks if two Author type objects are equal
     * @param o a second object we'll compare to the first parameter
     * @return boolean true if both objects are equal, false if not
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name) &&
                Objects.equals(mail, author.mail);
    }

    @Override
    /**
     * allows to hash the properties of an Author
     * @param name the name of the Author
     * @param mail the mail adress of the Author
     * @return the value once hashing has been done
     */
    public int hashCode() {
        return Objects.hash(name, mail);
    }
}
