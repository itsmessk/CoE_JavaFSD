public class Main {
    public static void main(String[] args) {
        LibraryManager libManager = new LibraryManager();
        
        libManager.addBook(new Book("1984", "George Orwell", "ISBN001"));
        libManager.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "ISBN002"));
        libManager.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "ISBN003"));
        libManager.addBook(new Book("The Catcher in the Rye", "J.D. Salinger", "ISBN004"));
        libManager.addBook(new Book("Pride and Prejudice", "Jane Austen", "ISBN005"));
        
        libManager.addUser(new User("Sundar", "001"));
        libManager.addUser(new User("Karthik", "002"));
        libManager.addUser(new User("SSK", "003"));
        
        libManager.displayLibraryState();
        
        Runnable user1Task = () -> {
            try {
                libManager.borrowBook("ISBN001", "001");
                libManager.borrowBook("ISBN002", "001");
                libManager.returnBook("ISBN001", "001");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        };
        
        Runnable user2Task = () -> {
            try {
                libManager.borrowBook("ISBN003", "002");
                libManager.reserveBook("ISBN003", "002");
                libManager.borrowBook("ISBN001", "002");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        };
        
        Runnable user3Task = () -> {
            try {
                libManager.borrowBook("ISBN004", "003");
                libManager.borrowBook("ISBN005", "003");
                libManager.returnBook("ISBN004", "003");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        };
        
        Thread user1Thread = new Thread(user1Task);
        Thread user2Thread = new Thread(user2Task);
        Thread user3Thread = new Thread(user3Task);
        
        user1Thread.start();
        user2Thread.start();
        user3Thread.start();
        
        try {
            user1Thread.join();
            user2Thread.join();
            user3Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        libManager.displayLibraryState();
    }
}