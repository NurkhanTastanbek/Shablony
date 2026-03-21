import java.util.*;

abstract class Beverage {
    protected String description = "Unknown Beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}

abstract class BeverageDecorator extends Beverage {
    protected Beverage beverage;

    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public abstract String getDescription();
}

class Espresso extends Beverage {
    public Espresso() {
        description = "Espresso";
    }

    @Override
    public double cost() {
        return 1.99;
    }
}

class Tea extends Beverage {
    public Tea() {
        description = "Tea";
    }

    @Override
    public double cost() {
        return 1.50;
    }
}

class Latte extends Beverage {
    public Latte() {
        description = "Latte";
    }

    @Override
    public double cost() {
        return 2.49;
    }
}

class Mocha extends Beverage {
    public Mocha() {
        description = "Mocha";
    }

    @Override
    public double cost() {
        return 2.99;
    }
}

class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.50;
    }
}

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Sugar";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.20;
    }
}

class WhippedCream extends BeverageDecorator {
    public WhippedCream(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whipped Cream";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.70;
    }
}

class Syrup extends BeverageDecorator {
    public Syrup(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Vanilla Syrup";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.40;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Order 1: Simple Espresso ---");
        Beverage order1 = new Espresso();
        printOrder(order1);

        System.out.println("\n--- Order 2: Tea with Milk and Sugar ---");
        Beverage order2 = new Tea();
        order2 = new Milk(order2);
        order2 = new Sugar(order2);
        printOrder(order2);

        System.out.println("\n--- Order 3: Double Mocha with Everything ---");
        Beverage order3 = new Mocha();
        order3 = new Milk(order3);
        order3 = new Syrup(order3);
        order3 = new WhippedCream(order3);
        printOrder(order3);

        System.out.println("\n--- Order 4: Latte with extra Syrup ---");
        Beverage order4 = new Latte();
        order4 = new Syrup(order4);
        order4 = new Syrup(order4); // Can add multiple times
        printOrder(order4);
    }

    private static void printOrder(Beverage beverage) {
        System.out.println("Description: " + beverage.getDescription());
        System.out.printf("Total Cost: $%.2f%n", beverage.cost());
    }
}
