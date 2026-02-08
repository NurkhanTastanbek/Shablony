class Order {
    private String productName;
    private int quantity;
    private double price;

    public Order(String productName, int quantity, double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}

class PriceCalculator {
    public double calculateTotalPrice(Order order) {
        // Логика: Саны * Бағасы * 10% жеңілдік
        return order.getQuantity() * order.getPrice() * 0.9;
    }
}


class PaymentProcessor {
    public void processPayment(String paymentDetails, double amount) {
        System.out.println("Төлем өңделді: " + amount + " тг. Тәсілі: " + paymentDetails);
    }
}


class NotificationService {
    public void sendConfirmationEmail(String email) {
        System.out.println("Email-ге растау хаты жіберілді: " + email);
    }
}


public class Main {
    public static void main(String[] args) {
        Order myOrder = new Order("iPhone 15", 1, 450000.0);

        
        PriceCalculator calculator = new PriceCalculator();
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        NotificationService notificationService = new NotificationService();

        double finalPrice = calculator.calculateTotalPrice(myOrder);

        paymentProcessor.processPayment("Kaspi Gold", finalPrice);

        notificationService.sendConfirmationEmail("customer@mail.kz");

        System.out.println("\nТапсырыс сәтті аяқталды!");
    }
}
