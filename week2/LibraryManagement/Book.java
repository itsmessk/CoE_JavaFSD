import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private String ISBN;
    private boolean isBorrowed;
    
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
 
    public String getAuthor() {
        return author;
    }
 
    public void setAuthor(String author) {
        this.author = author;
    }
 
    public String getISBN() {
        return ISBN;
    }
 
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    
    public boolean isBorrowed() {
        return isBorrowed;
    }
    
    public synchronized void setBorrowed(boolean borrowed) {
        this.isBorrowed = borrowed;
    }

    @Override
    public String toString() {
        return String.format("Book[Title='%s', Author='%s', ISBN='%s', Borrowed=%s]", 
                title, author, ISBN, isBorrowed);
    }
}