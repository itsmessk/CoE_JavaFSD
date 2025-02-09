import java.util.ArrayList;
import java.util.List;

public abstract class LibrarySystem implements ILibrary {
    protected List<Book> books;
    protected List<User> users;
    
    public LibrarySystem() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }
    
    public abstract void addBook(Book book);
    public abstract void addUser(User user);
    
    @Override
    public Book searchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title.trim())) {
                return book;
            }
        }
        return null;
    }
    
    protected Book findBookByISBN(String ISBN) {
        for (Book book : books) {
            if (book.getISBN().equalsIgnoreCase(ISBN.trim())) {
                return book;
            }
        }
        return null;
    }
    
    protected User findUserByID(String userID) {
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID.trim())) {
                return user;
            }
        }
        return null;
    }
}