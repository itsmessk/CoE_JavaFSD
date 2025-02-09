import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();

        try {
            inventoryManager.loadInventoryFromFile("inventory.dat");
        } catch (Exception e) {
            System.out.println("No existing inventory found. Initializing new inventory.");
        }

        try {
            inventoryManager.addProduct(new Product("P001", "Widget", 10, new Location(1, 1, 1)));
            inventoryManager.addProduct(new Product("P002", "Gadget", 5, new Location(1, 1, 2)));
            inventoryManager.addProduct(new Product("P003", "Thingamajig", 20, new Location(2, 1, 1)));
        } catch (InvalidLocationException e) {
            System.out.println(e.getMessage());
        }

        Order order1 = new Order("O001", Arrays.asList("P001", "P002"), Order.Priority.EXPEDITED);
        Order order2 = new Order("O002", Arrays.asList("P001", "P003"), Order.Priority.STANDARD);
        Order order3 = new Order("O003", Arrays.asList("P002"), Order.Priority.EXPEDITED);
        Order order4 = new Order("O004", Arrays.asList("P001", "P003", "P002"), Order.Priority.STANDARD);

        inventoryManager.enqueueOrder(order1);
        inventoryManager.enqueueOrder(order2);
        inventoryManager.enqueueOrder(order3);
        inventoryManager.enqueueOrder(order4);

        inventoryManager.processOrders();

        inventoryManager.shutdown();

        inventoryManager.displayInventory();

        inventoryManager.saveInventoryToFile("inventory.dat");
    }
}