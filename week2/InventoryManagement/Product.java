import java.io.Serializable;

public class Product implements Serializable {
    private String productID;
    private String name;
    private int quantity;
    private Location location;

    public Product(String productID, String name, int quantity, Location location) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public String getProductID() { return productID; }
    public void setProductID(String productID) { this.productID = productID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public synchronized void setQuantity(int quantity) { this.quantity = quantity; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    @Override
    public String toString() {
        return "Product [ID=" + productID + ", Name=" + name + ", Quantity=" + quantity + ", Location=" + location + "]";
    }
}