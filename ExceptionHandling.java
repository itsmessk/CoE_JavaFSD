import java.util.Scanner;

class ExceptionHandling {
    public static void processInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter a number: ");
            double number = Double.parseDouble(scanner.nextLine());
            System.out.println("Reciprocal: " + (1 / number));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (ArithmeticException e) {
            System.out.println("Division by zero is not allowed.");
        }
    }
}