import java.io.*;
import java.util.List;

public class LibraryManager extends LibrarySystem {
    private final String booksFile = "books.dat";
    private final String usersFile = "users.dat";
    
    public LibraryManager() {
        super();
        loadData();
    }
    
    @Override
    public synchronized void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    @Override
    public synchronized void addUser(User user) {
        users.add(user);
        saveUsers();
    }
    
    @Override
    public void borrowBook(String ISBN, String userID) 
            throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException {
        synchronized(this) {
            Book book = findBookByISBN(ISBN);
            if (book == null) {
                throw new BookNotFoundException("Book with ISBN " + ISBN + " not found.");
            }
            User user = findUserByID(userID);
            if (user == null) {
                throw new UserNotFoundException("User with ID " + userID + " not found.");
            }
            if (book.isBorrowed()) {
                System.out.println("Book is already borrowed.");
                return;
            }
            user.borrowBook(book);
            book.setBorrowed(true);
            System.out.println(user.getName() + " borrowed " + book.getTitle());
            saveData();
        }
    }

    @Override
    public void returnBook(String ISBN, String userID) 
            throws BookNotFoundException, UserNotFoundException {
        synchronized(this) {
            Book book = findBookByISBN(ISBN);
            if (book == null) {
                throw new BookNotFoundException("Book with ISBN " + ISBN + " not found.");
            }
            User user = findUserByID(userID);
            if (user == null) {
                throw new UserNotFoundException("User with ID " + userID + " not found.");
            }
            if (!book.isBorrowed()) {
                System.out.println("Book is not currently borrowed.");
                return;
            }
            user.returnBook(book);
            book.setBorrowed(false);
            System.out.println(user.getName() + " returned " + book.getTitle());
            saveData();
        }
    }

    @Override
    public void reserveBook(String ISBN, String userID) 
            throws BookNotFoundException, UserNotFoundException {
        synchronized(this) {
            Book book = findBookByISBN(ISBN);
            if (book == null) {
                throw new BookNotFoundException("Book with ISBN " + ISBN + " not found.");
            }
            User user = findUserByID(userID);
            if (user == null) {
                throw new UserNotFoundException("User with ID " + userID + " not found.");
            }
            System.out.println(user.getName() + " has reserved " + book.getTitle());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(booksFile))) {
            books = (List<Book>) ois.readObject();
            System.out.println("Books loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Books file not found. Starting with empty book list.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
            users = (List<User>) ois.readObject();
            System.out.println("Users loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found. Starting with empty user list.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void saveData() {
        saveBooks();
        saveUsers();
    }
    
    private void saveBooks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(booksFile))) {
            oos.writeObject(books);
            System.out.println("Books saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(users);
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void displayLibraryState() {
        System.out.println("\n--- Current Library State ---");
        System.out.println("Books:");
        for (Book book : books) {
            System.out.println(book);
        }
        
        System.out.println("\nUsers:");
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("----------------------------\n");
    }
}