import java.util.ArrayList;
import java.util.List;

public class HelloWorld {
    public static void main(String[] args) {
        Customer customer = new Customer("Priya Sharma", "priya@example.com");
        PremiumCustomer premiumCustomer = new PremiumCustomer("Rahul Verma", "rahul@example.com");

        Order regularOrder = new Order(101, customer);
        regularOrder.addItem(new Product("Laptop", 1200.00), 1);
        regularOrder.addItem(new Product("Mouse", 35.00), 2);
        regularOrder.confirm();

        Order premiumOrder = new Order(102, premiumCustomer);
        premiumOrder.addItem(new Product("Keyboard", 85.00), 1);
        premiumOrder.confirm();

        NotificationService emailService = new EmailNotificationService();
        emailService.send("Order #" + regularOrder.getOrderId() + " confirmed for " + customer.getName());

        PaymentService paymentService = new CardPaymentService();
        paymentService.process(regularOrder.getFinalAmount());

        System.out.println("=== Customer Profile ===");
        System.out.println(customer);

        System.out.println("\n=== Order Summary ===");
        System.out.println(regularOrder.getSummary());
        System.out.println(premiumOrder.getSummary());

        System.out.println("\n=== Admin Access Demo ===");
        AdminUser admin = new AdminUser("Aisha Khan", "aisha@example.com", "ADMIN");
        admin.givePermission();
    }
}

class User {
    private final String name;
    private final String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void displayProfile() {
        System.out.println("Name: " + name + " | Email: " + email);
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "'}";
    }
}

class Customer extends User {
    private boolean active;

    public Customer(String name, String email) {
        super(name, email);
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public double getDiscountPercent() {
        return 0;
    }
}

class PremiumCustomer extends Customer {
    public PremiumCustomer(String name, String email) {
        super(name, email);
    }

    @Override
    public double getDiscountPercent() {
        return 10;
    }
}

class AdminUser extends User {
    private final String accessLevel;

    public AdminUser(String name, String email, String accessLevel) {
        super(name, email);
        this.accessLevel = accessLevel;
    }

    public void givePermission() {
        System.out.println(getName() + " has " + accessLevel + " access to the application.");
    }
}

class Product {
    private final String name;
    private final double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getLineTotal() {
        return product.getPrice() * quantity;
    }
}

class Order {
    private final int orderId;
    private final Customer customer;
    private final List<OrderItem> items;
    private String status;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = "CREATED";
    }

    public void addItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be more than zero.");
        }
        items.add(new OrderItem(product, quantity));
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getLineTotal();
        }
        return total;
    }

    public double getDiscountAmount() {
        return calculateTotal() * customer.getDiscountPercent() / 100;
    }

    public double getFinalAmount() {
        return calculateTotal() - getDiscountAmount();
    }

    public void confirm() {
        this.status = "CONFIRMED";
    }

    public int getOrderId() {
        return orderId;
    }

    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Order #").append(orderId)
               .append(" for ").append(customer.getName())
               .append(" | Status: ").append(status)
               .append("\nItems:\n");

        for (OrderItem item : items) {
            summary.append("- ")
                   .append(item.getProduct().getName())
                   .append(" x")
                   .append(item.getQuantity())
                   .append(" = $")
                   .append(String.format("%.2f", item.getLineTotal()))
                   .append("\n");
        }

        summary.append("Subtotal: $")
               .append(String.format("%.2f", calculateTotal()))
               .append("\nDiscount: $")
               .append(String.format("%.2f", getDiscountAmount()))
               .append("\nFinal Total: $")
               .append(String.format("%.2f", getFinalAmount()));

        return summary.toString();
    }
}

interface NotificationService {
    void send(String message);
}

class EmailNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("[Email] " + message);
    }
}

abstract class PaymentService {
    public final void process(double amount) {
        validateAmount(amount);
        performPayment(amount);
    }

    protected abstract void performPayment(double amount);

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }
}

class CardPaymentService extends PaymentService {
    @Override
    protected void performPayment(double amount) {
        System.out.println("Processing card payment of $" + String.format("%.2f", amount));
    }
}

