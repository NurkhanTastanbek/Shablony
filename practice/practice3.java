import java.util.ArrayList;
import java.util.List;

class Order {
    private List<String> items = new ArrayList<>();
    private double totalAmount;

    public void addItem(String item, double price) {
        items.add(item);
        totalAmount += price;
    }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double amount) { this.totalAmount = amount; }
}

interface IPayment {
    void processPayment(double amount);
}

class CreditCardPayment implements IPayment {
    public void processPayment(double amount) { System.out.println("Payment via Credit Card: " + amount); }
}

class PayPalPayment implements IPayment {
    public void processPayment(double amount) { System.out.println("Payment via PayPal: " + amount); }
}

class BankTransferPayment implements IPayment {
    public void processPayment(double amount) { System.out.println("Payment via Bank Transfer: " + amount); }
}

interface IDelivery {
    void deliverOrder(Order order);
}

class CourierDelivery implements IDelivery {
    public void deliverOrder(Order order) { System.out.println("Delivering by courier..."); }
}

class PostDelivery implements IDelivery {
    public void deliverOrder(Order order) { System.out.println("Delivering by post..."); }
}

class PickUpPointDelivery implements IDelivery {
    public void deliverOrder(Order order) { System.out.println("Pick up from point..."); }
}

interface INotification {
    void sendNotification(String message);
}

class EmailNotification implements INotification {
    public void sendNotification(String message) { System.out.println("Email: " + message); }
}

class SmsNotification implements INotification {
    public void sendNotification(String message) { System.out.println("SMS: " + message); }
}

interface IDiscountStrategy {
    double applyDiscount(double amount);
}

class FestiveDiscount implements IDiscountStrategy {
    public double applyDiscount(double amount) { return amount * 0.9; }
}

class NoDiscount implements IDiscountStrategy {
    public double applyDiscount(double amount) { return amount; }
}

class OrderManager {
    private final IPayment paymentProcessor;
    private final IDelivery deliveryService;
    private final INotification notificationService;

    public OrderManager(IPayment payment, IDelivery delivery, INotification notification) {
        this.paymentProcessor = payment;
        this.deliveryService = delivery;
        this.notificationService = notification;
    }

    public void process(Order order) {
        paymentProcessor.processPayment(order.getTotalAmount());
        deliveryService.deliverOrder(order);
        notificationService.sendNotification("Order status: Success");
    }
}

public class Main {
    public static void main(String[] args) {
        Order order = new Order();
        order.addItem("Laptop", 500000.0);
        order.addItem("Mouse", 15000.0);

        IDiscountStrategy discount = new FestiveDiscount();
        order.setTotalAmount(discount.applyDiscount(order.getTotalAmount()));

        IPayment payment = new PayPalPayment();
        IDelivery delivery = new CourierDelivery();
        INotification notification = new SmsNotification();

        OrderManager manager = new OrderManager(payment, delivery, notification);
        manager.process(order);
    }
}
