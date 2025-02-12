
import java.sql.*;
import java.util.Scanner;

public class Accountant {
    public static void accountantSection(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (authenticateAccountant(email, password)) {
            System.out.println("Accountant login successful!");
            accountantMenu(scanner);
        } else {
            System.out.println("Invalid credentials. Login failed.");
        }
    }

    private static boolean authenticateAccountant(String email, String password) {
        boolean status = false;
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM accountant WHERE email=? AND password=?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            status = rs.next();
        } catch (SQLException e) {
            System.out.println("Database error during accountant authentication.");
            e.printStackTrace();
        }
        return status;
    }

    private static void accountantMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Accountant Menu ---");
            System.out.println("1) Add Student");
            System.out.println("2) View Students");
            System.out.println("3) Edit Student");
            System.out.println("4) Delete Student");
            System.out.println("5) Check Due Fees");
            System.out.println("6) Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    editStudent(scanner);
                    break;
                case 4:
                    deleteStudent(scanner);
                    break;
                case 5:
                    viewDueFees();
                    break;
                case 6:
                    System.out.println("Accountant logged out.");
                    return;
                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter email: ");
        String email = scanner.next();
        System.out.print("Enter course: ");
        String course = scanner.next();
        System.out.print("Enter fee: ");
        double fee = scanner.nextDouble();
        System.out.print("Enter amount paid: ");
        double paid = scanner.nextDouble();
        double due = fee - paid;
        scanner.nextLine(); // Consume newline
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.next();

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO student (name, email, course, fee, paid, due, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, course);
            ps.setDouble(4, fee);
            ps.setDouble(5, paid);
            ps.setDouble(6, due);
            ps.setString(7, address);
            ps.setString(8, phone);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Student added successfully!");
            } else {
                System.out.println("Failed to add student.");
            }
        } catch (SQLException e) {
            System.out.println("Database error during adding student.");
            e.printStackTrace();
        }
    }

    private static void viewStudents() {
        try (Connection con = DBUtil.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");

            System.out.println("\n--- List of Students ---");
            System.out.printf("%-5s %-15s %-20s %-10s %-8s %-8s %-8s %-25s %-15s\n",
                    "ID", "Name", "Email", "Course", "Fee", "Paid", "Due", "Address", "Phone");
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-20s %-10s %-8.2f %-8.2f %-8.2f %-25s %-15s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getDouble("fee"),
                        rs.getDouble("paid"),
                        rs.getDouble("due"),
                        rs.getString("address"),
                        rs.getString("phone"));
            }
        } catch (SQLException e) {
            System.out.println("Database error during viewing students.");
            e.printStackTrace();
        }
    }

    private static void editStudent(Scanner scanner) {
        System.out.print("Enter Student ID to edit: ");
        int id = scanner.nextInt();

        System.out.print("Enter new name: ");
        String name = scanner.next();
        System.out.print("Enter new email: ");
        String email = scanner.next();
        System.out.print("Enter new course: ");
        String course = scanner.next();
        System.out.print("Enter new fee: ");
        double fee = scanner.nextDouble();
        System.out.print("Enter new amount paid: ");
        double paid = scanner.nextDouble();
        double due = fee - paid;
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new address: ");
        String address = scanner.nextLine();
        System.out.print("Enter new phone: ");
        String phone = scanner.next();

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE student SET name=?, email=?, course=?, fee=?, paid=?, due=?, address=?, phone=? WHERE id=?");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, course);
            ps.setDouble(4, fee);
            ps.setDouble(5, paid);
            ps.setDouble(6, due);
            ps.setString(7, address);
            ps.setString(8, phone);
            ps.setInt(9, id);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Failed to update student. Please check the ID.");
            }
        } catch (SQLException e) {
            System.out.println("Database error during editing student.");
            e.printStackTrace();
        }
    }

    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter Student ID to delete: ");
        int id = scanner.nextInt();

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM student WHERE id=?");
            ps.setInt(1, id);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student. Please check the ID.");
            }
        } catch (SQLException e) {
            System.out.println("Database error during deleting student.");
            e.printStackTrace();
        }
    }

    private static void viewDueFees() {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM student WHERE due > 0");
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Students with Due Fees ---");
            System.out.printf("%-5s %-15s %-20s %-10s %-8s %-8s %-8s %-25s %-15s\n",
                    "ID", "Name", "Email", "Course", "Fee", "Paid", "Due", "Address", "Phone");
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-20s %-10s %-8.2f %-8.2f %-8.2f %-25s %-15s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getDouble("fee"),
                        rs.getDouble("paid"),
                        rs.getDouble("due"),
                        rs.getString("address"),
                        rs.getString("phone"));
            }
        } catch (SQLException e) {
            System.out.println("Database error during viewing due fees.");
            e.printStackTrace();
        }
    }
}