import java.util.*;

interface IPaymentProcessor {
    void processPayment(double amount);
}

class PayPalPaymentProcessor implements IPaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment: $" + amount);
    }
}

class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Stripe transaction completed: $" + totalAmount);
    }
}

class SquarePaymentService {
    public void executeCharge(double value, String currency) {
        System.out.println("Square charge executed: " + value + " " + currency);
    }
}

class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;

    public StripePaymentAdapter(StripePaymentService stripeService) {
        this.stripeService = stripeService;
    }

    @Override
    public void processPayment(double amount) {
        stripeService.makeTransaction(amount);
    }
}

class SquarePaymentAdapter implements IPaymentProcessor {
    private SquarePaymentService squareService;

    public SquarePaymentAdapter(SquarePaymentService squareService) {
        this.squareService = squareService;
    }

    @Override
    public void processPayment(double amount) {
        squareService.executeCharge(amount, "USD");
    }
}

public class Main {
    public static void main(String[] args) {
        List<IPaymentProcessor> processors = new ArrayList<>();

        processors.add(new PayPalPaymentProcessor());
        processors.add(new StripePaymentAdapter(new StripePaymentService()));
        processors.add(new SquarePaymentAdapter(new SquarePaymentService()));

        double orderAmount = 150.0;

        System.out.println("--- Payment Processing System ---");
        for (IPaymentProcessor processor : processors) {
            processor.processPayment(orderAmount);
        }
    }
}
