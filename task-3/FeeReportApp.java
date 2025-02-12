

import java.util.Scanner;

public class FeeReportApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("****** Fee Report Software ******");

        while (true) {
            System.out.println("\n1) Admin Login");
            System.out.println("2) Accountant Login");
            System.out.println("3) Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Admin.adminSection(scanner);
                    break;
                case 2:
                    Accountant.accountantSection(scanner);
                    break;
                case 3:
                    System.out.println("Thank you for using Fee Report Software!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
    }
}