import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String name;
    private String userID;
    private List<Book> borrowedBooks;
    private static final int MAX_BORROWED_BOOKS = 5;
    
    public User(String name, String userID) {
        this.name = name;
        this.userID = userID;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getUserID() {
        return userID;
    }
 
    public void setUserID(String userID) {
        this.userID = userID;
    }
 
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    
    public synchronized void borrowBook(Book book) throws MaxBooksAllowedException {
        if (borrowedBooks.size() >= MAX_BORROWED_BOOKS) {
            throw new MaxBooksAllowedException("User has reached the maximum number of borrowed books.");
        }
        borrowedBooks.add(book);
    }
    
    public synchronized void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("User[Name='%s', UserID='%s', BorrowedBooks=", name, userID));
        for (Book book : borrowedBooks) {
            sb.append(book.getISBN()).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}