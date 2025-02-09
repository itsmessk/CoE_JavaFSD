import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class InventoryManager {
    private Map<String, Product> products;
    private PriorityBlockingQueue<Order> orderQueue;
    private ExecutorService executorService;

    public InventoryManager() {
        products = new ConcurrentHashMap<>();
        orderQueue = new PriorityBlockingQueue<>(10, new OrderComparator());
        executorService = Executors.newFixedThreadPool(5);
    }

    public void addProduct(Product product) throws InvalidLocationException {
        if (product.getLocation() == null) {
            throw new InvalidLocationException("Invalid location for product " + product.getProductID());
        }
        products.put(product.getProductID(), product);
        log("Added product: " + product);
    }

    public void updateProductQuantity(String productID, int quantity) {
        Product product = products.get(productID);
        if (product != null) {
            synchronized (product) {
                product.setQuantity(quantity);
            }
            log("Updated quantity for product " + productID + " to " + quantity);
        } else {
            log("Product " + productID + " not found for update.");
        }
    }

    public void enqueueOrder(Order order) {
        orderQueue.add(order);
        log("Enqueued order: " + order.getOrderID() + " with priority " + order.getPriority());
    }

    public void processOrders() {
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            if (order != null) {
                executorService.submit(() -> {
                    try {
                        processOrder(order);
                    } catch (OutOfStockException e) {
                        log("Order " + order.getOrderID() + " failed: " + e.getMessage());
                    }
                });
            }
        }
    }

    private void processOrder(Order order) throws OutOfStockException {
        log("Processing order: " + order.getOrderID());
        for (String productID : order.getProductIDs()) {
            Product product = products.get(productID);
            if (product != null) {
                synchronized (product) {
                    if (product.getQuantity() > 0) {
                        product.setQuantity(product.getQuantity() - 1);
                        log("Product " + productID + " picked from " + product.getLocation());
                    } else {
                        throw new OutOfStockException("Product " + productID + " is out of stock.");
                    }
                }
            } else {
                log("Product " + productID + " not found in inventory.");
            }
        }
        log("Order " + order.getOrderID() + " processed successfully.");
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public void saveInventoryToFile(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(products);
            log("Inventory saved to file: " + filename);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void loadInventoryFromFile(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            products = (Map<String, Product>) in.readObject();
            log("Inventory loaded from file: " + filename);
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
    }

    public void displayInventory() {
        log("Current Inventory State:");
        products.values().forEach(System.out::println);
    }

    private void log(String message) {
        System.out.println("[LOG] " + message);
    }
}