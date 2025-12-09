import java.util.*;

// -------------------- User (Base class for Supplier and Customer) --------------------
class User {
    // Attributes from diagram
    int userId;
    String name;
    String email;
    String password;
}

// -------------------- Supplier --------------------
// Inheritance: Supplier IS-A User
// Aggregation: Supplier HAS-A List<Product> (products created outside and added)
class Supplier extends User {
    String storeName;
    List<Product> products = new ArrayList<>(); // Aggregation (weak HAS-A)

    void register(String name, String email, String password) {}
    boolean login(String email, String password) { return false; }
    void logout() {}

    Product addProduct(Product product) { return product; }
    boolean updateProduct(Product product) { return false; }
    boolean removeProduct(String productId) { return false; }

    void updateProfile(String name, String phone, String address) {}
    boolean viewProfile() { return false; }
}

// -------------------- Product --------------------
// Association: Product linked to Supplier (uses, no ownership)
// Composition: Product HAS-A Images — lifetime tied to Product
// To avoid using List for composition, we model images as an array (1..* in diagram, simplified here)
class Product {
    int productId;            // note: diagram typo "productid", using productId consistently
    String name;
    String description;
    float price;
    int stock;

    Image[] images;           // Composition (strong HAS-A, lifetime tied)
    Supplier supplier;        // Association

    boolean reduceStock(int qty) { return false; }
    boolean increaseStock(int qty) { return false; }
    boolean isAvailable() { return false; }
}

// -------------------- Image --------------------
// Association: Image linked back to Product
class Image {
    int imageId;
    String url;
    String altText;
    Product product; // Association
}

// -------------------- Customer --------------------
// Inheritance: Customer IS-A User
// Composition: Customer HAS-A Cart (owned, same lifetime)
// Aggregation: Customer HAS-A List<Address>, List<Order>, List<Review>
class Customer extends User {
    Cart cart = new Cart();               // Composition (strong HAS-A)
    List<Address> addresses = new ArrayList<>(); // Aggregation (weak HAS-A)
    List<Order> orders = new ArrayList<>();      // Aggregation (weak HAS-A)
    List<Review> reviews = new ArrayList<>();    // Aggregation (weak HAS-A)

    // Methods from diagram (two placeOrder signatures)
    boolean placeOrder() { return false; }
    boolean cancelOrder() { return false; }

    Product addToCart(Product product, int qty) { return product; }
    Order placeOrder(Address shipping) { return new Order(); }
    List<Order> getOrderHistory() { return orders; }
}

// -------------------- Cart --------------------
// Composition: Cart belongs to Customer (lifetime tied)
// Aggregation: Cart HAS-A List<Product> (products created externally)
class Cart {
    int cartId;
    List<Product> items = new ArrayList<>(); // Aggregation (weak HAS-A)

    boolean addItem(Product product, int qty) { return false; }
    boolean removeItem(Product product) { return false; }
    double getTotal() { return 0.0; }
}

// -------------------- Address --------------------
// Association: Address linked to Customer
class Address {
    int addressId;
    String city;
    int postalCode;
    long phone;
    Customer customer; // Association
}

// -------------------- Order --------------------
// Aggregation: Order HAS-A OrderItem[] (parts created outside; we avoid List for composition per your request)
// Association: Order uses Address, Payment, Customer
class Order {
    int orderId;
    OrderItem[] items;        // Aggregation (weak HAS-A; diagram shows List<OrderItem>)
    Address shippingAddress;  // Association
    double totalAmount;

    Payment payment;          // Association (0..1)
    Customer customer;        // Association

    boolean placeOrder() { return false; }
    boolean cancelOrder() { return false; }
}

// -------------------- OrderItem --------------------
// Association: OrderItem linked to Product and Order
class OrderItem {
    Product product; // Association
    int quantity;
    float unitPrice;
    Order order;     // Association

    double calculateLineTotal() { return 0.0; }
}

// -------------------- Review --------------------
// Association: Review linked to Product and Customer
class Review {
    int reviewId;
    Product product;   // Association
    Customer customer; // Association
    int rating;
    String comment;

    void addReview(String text) {}
    boolean editReview(String text) { return false; }
    boolean deleteReview(String text) { return false; }
}

// -------------------- Payment Interface --------------------
// Interface as in diagram (methods only; attributes implied via getters in implementations if needed)
interface Payment {
    boolean processPayment(double amount);
    boolean refundPayment(double amount);
}

// -------------------- CreditCardPayment --------------------
// Implements Payment, has own attributes
// Association: optional reference to Payment if needed by diagram (it shows "payment: Payment" on subclasses)
class CreditCardPayment implements Payment {
    int cardNumber;
    String cardHolder;
    int cvv;
    String expiryDate;

    Payment payment; // Association to Payment (per diagram field)

    @Override public boolean processPayment(double amount) { return false; }
    @Override public boolean refundPayment(double amount) { return false; }
}

// -------------------- BankTransferPayment --------------------
// Implements Payment, has own attributes
// Association: reference to Payment (per diagram field)
class BankTransferPayment implements Payment {
    int accountNumber;
    String bankName;
    String accountHolderName;

    Payment payment; // Association to Payment (per diagram field)

    @Override public boolean processPayment(double amount) { return false; }
    @Override public boolean refundPayment(double amount) { return false; }
}

// -------------------- Main Menu (prints selected method/class) --------------------
public class MainMenu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Online Clothing Store Demo ---");
            System.out.println("1. Call Supplier.addProduct()");
            System.out.println("2. Call Customer.placeOrder()");
            System.out.println("3. Call Order.cancelOrder()");
            System.out.println("4. Call Cart.addItem()");
            System.out.println("5. Call Review.addReview()");
            System.out.println("0. Exit");
            System.out.print("Select: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("You have selected addProduct() of Supplier class");
                    break;
                case 2:
                    System.out.println("You have selected placeOrder() of Customer class");
                    break;
                case 3:
                    System.out.println("You have selected cancelOrder() of Order class");
                    break;
                case 4:
                    System.out.println("You have selected addItem() of Cart class");
                    break;
                case 5:
                    System.out.println("You have selected addReview() of Review class");
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (choice != 0);
        sc.close();
    }
}
