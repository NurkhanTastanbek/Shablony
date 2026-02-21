import java.util.ArrayList;
import java.util.List;

interface ICloneable<T> {
    T clone();
}

class Product implements ICloneable<Product> {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void setPrice(double price) { this.price = price; }

    @Override
    public Product clone() {
        return new Product(this.name, this.price);
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

class Discount implements ICloneable<Discount> {
    private String description;
    private double amount;

    public Discount(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public Discount clone() {
        return new Discount(this.description, this.amount);
    }

    @Override
    public String toString() {
        return description + ": -$" + amount;
    }
}

class Order implements ICloneable<Order> {
    private List<Product> products = new ArrayList<>();
    private Discount discount;
    private double deliveryCost;
    private String paymentMethod;

    public Order() {}

    public void addProduct(Product p) { products.add(p); }
    public void setDiscount(Discount d) { this.discount = d; }
    public void setDeliveryCost(double cost) { this.deliveryCost = cost; }
    public void setPaymentMethod(String method) { this.paymentMethod = method; }

    @Override
    public Order clone() {
        Order newOrder = new Order();
        for (Product p : this.products) {
            newOrder.addProduct(p.clone());
        }
        if (this.discount != null) {
            newOrder.setDiscount(this.discount.clone());
        }
        newOrder.setDeliveryCost(this.deliveryCost);
        newOrder.setPaymentMethod(this.paymentMethod);
        return newOrder;
    }

    public List<Product> getProducts() { return products; }

    @Override
    public String toString() {
        return "Order [Products=" + products + 
               ", Discount=" + discount + 
               ", Delivery=$" + deliveryCost + 
               ", Payment=" + paymentMethod + "]";
    }
}

public class Main {
    public static void main(String[] args) {
        Order templateOrder = new Order();
        templateOrder.addProduct(new Product("Smartphone", 800));
        templateOrder.addProduct(new Product("Case", 20));
        templateOrder.setDeliveryCost(15.0);
        templateOrder.setPaymentMethod("Credit Card");
        templateOrder.setDiscount(new Discount("Welcome Bonus", 50));

        System.out.println("Original: " + templateOrder);

        Order newOrder = templateOrder.clone();
        newOrder.setPaymentMethod("PayPal");
        newOrder.getProducts().get(0).setPrice(750);

        System.out.println("Cloned & Modified: " + newOrder);
        System.out.println("Original after clone: " + templateOrder);
    }
}
