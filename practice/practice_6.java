import java.util.*;

interface ICostCalculationStrategy {
    double calculate(double distance, int passengers, boolean isBusinessClass, boolean hasExtraBaggage);
}

class FlightStrategy implements ICostCalculationStrategy {
    @Override
    public double calculate(double distance, int passengers, boolean isBusinessClass, boolean hasExtraBaggage) {
        double basePrice = distance * 0.5; // Әуе жолы қымбатырақ
        if (isBusinessClass) basePrice *= 2.5;
        if (hasExtraBaggage) basePrice += 50 * passengers;
        return basePrice * passengers;
    }
}

class TrainStrategy implements ICostCalculationStrategy {
    @Override
    public double calculate(double distance, int passengers, boolean isBusinessClass, boolean hasExtraBaggage) {
        double basePrice = distance * 0.15;
        if (isBusinessClass) basePrice *= 1.8;
        if (hasExtraBaggage) basePrice += 10 * passengers;
        return basePrice * passengers;
    }
}

class BusStrategy implements ICostCalculationStrategy {
    @Override
    public double calculate(double distance, int passengers, boolean isBusinessClass, boolean hasExtraBaggage) {
        double basePrice = distance * 0.08;
        if (isBusinessClass) throw new IllegalArgumentException("Bus does not have Business Class!");
        if (hasExtraBaggage) basePrice += 5 * passengers;
        return basePrice * passengers;
    }
}

class TravelBookingContext {
    private ICostCalculationStrategy strategy;

    public void setStrategy(ICostCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateTravelCost(double distance, int passengers, String discountType, boolean isBusiness, boolean baggage) {
        if (strategy == null) {
            throw new IllegalStateException("Transport type not selected!");
        }
        if (distance <= 0 || passengers <= 0) {
            throw new IllegalArgumentException("Invalid distance or passenger count!");
        }

        double total = strategy.calculate(distance, passengers, isBusiness, baggage);

        if (discountType.equalsIgnoreCase("CHILD")) total *= 0.5;
        else if (discountType.equalsIgnoreCase("SENIOR")) total *= 0.7;

        return total;
    }
}

public class Main {
    public static void main(String[] args) {
        TravelBookingContext context = new TravelBookingContext();
        Scanner scanner = new Scanner(System.stdin);

        try {
            System.out.println("Select Transport: 1-Flight, 2-Train, 3-Bus");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> context.setStrategy(new FlightStrategy());
                case 2 -> context.setStrategy(new TrainStrategy());
                case 3 -> context.setStrategy(new BusStrategy());
                default -> throw new Exception("Invalid transport type!");
            }

            System.out.print("Enter distance (km): ");
            double dist = scanner.nextDouble();
            System.out.print("Number of passengers: ");
            int pass = scanner.nextInt();
            System.out.print("Business Class? (true/false): ");
            boolean isBusiness = scanner.nextBoolean();
            System.out.print("Extra baggage? (true/false): ");
            boolean baggage = scanner.nextBoolean();
            System.out.print("Discount (NONE, CHILD, SENIOR): ");
            String discount = scanner.next();

            double finalPrice = context.calculateTravelCost(dist, pass, discount, isBusiness, baggage);
            System.out.printf("\nTotal Trip Cost: %.2f USD\n", finalPrice);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
