
import java.sql.*;
import java.util.Scanner;

public class Admin {
    public static void adminSection(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (authenticateAdmin(username, password)) {
            System.out.println("Admin login successful!");
            adminMenu(scanner);
        } else {
            System.out.println("Invalid credentials. Login failed.");
        }
    }

    private static boolean authenticateAdmin(String username, String password) {
        boolean status = false;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM admin WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            status = rs.next();
        } catch (SQLException e) {
            System.out.println("Database error during admin authentication.");
            e.printStackTrace();
        }
        return status;
    }

    private static void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1) Add Accountant");
            System.out.println("2) View Accountants");
            System.out.println("3) Edit Accountant");
            System.out.println("4) Delete Accountant");
            System.out.println("5) Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addAccountant(scanner);
                    break;
                case 2:
                    viewAccountants();
                    break;
                case 3:
                    editAccountant(scanner);
                    break;
                case 4:
                    deleteAccountant(scanner);
                    break;
                case 5:
                    System.out.println("Admin logged out.");
                    return;
                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
    }

    private static void addAccountant(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter email: ");
        String email = scanner.next();
        System.out.print("Enter phone: ");
        String phone = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO accountant (name, email, phone, password) VALUES (?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, password);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Accountant added successfully!");
            } else {
                System.out.println("Failed to add accountant.");
            }
        } catch (SQLException e) {
            System.out.println("Database error during adding accountant.");
            e.printStackTrace();
        }
    }

    private static void viewAccountants() {
        try (Connection con = DBUtil.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM accountant");

            System.out.println("\n--- List of Accountants ---");
            System.out.printf("%-5s %-20s %-25s %-15s\n", "ID", "Name", "Email", "Phone");
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-25s %-15s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"));
            }
        } catch (SQLException e) {
            System.out.println("Database error during viewing accountants.");
            e.printStackTrace();
        }
    }

    private static void editAccountant(Scanner scanner) {
        System.out.print("Enter Accountant ID to edit: ");
        int id = scanner.nextInt();

        System.out.print("Enter new name: ");
        String name = scanner.next();
        System.out.print("Enter new email: ");
        String email = scanner.next();
        System.out.print("Enter new phone: ");
        String phone = scanner.next();
        System.out.print("Enter new password: ");
        String password = scanner.next();

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE accountant SET name=?, email=?, phone=?, password=? WHERE id=?");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, password);
            ps.setInt(5, id);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Accountant updated successfully!");
            } else {
                System.out.println("Failed to update accountant. Please check the ID.");
            }
        } catch (SQLException e) {
            System.out.println("Database error during editing accountant.");
            e.printStackTrace();
        }
    }

    private static void deleteAccountant(Scanner scanner) {
        System.out.print("Enter Accountant ID to delete: ");
        int id = scanner.nextInt();

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM accountant WHERE id=?");
            ps.setInt(1, id);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Accountant deleted successfully!");
            } else {
                System.out.println("Failed to delete accountant. Please check the ID.");
            }
        } catch (SQLException e) {
            System.out.println("Database error during deleting accountant.");
            e.printStackTrace();
        }
    }
}