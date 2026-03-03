import java.util.Scanner;

abstract class Beverage {

    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
        System.out.println("--- Beverage is ready ---\n");
    }

    private void boilWater() {
        System.out.println("Boiling water...");
    }

    private void pourInCup() {
        System.out.println("Pouring into cup...");
    }

    protected abstract void brew();

    protected abstract void addCondiments();

    protected boolean customerWantsCondiments() {
        return true;
    }

    protected boolean getUserInput(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(message + " (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("y")) return true;
            if (answer.equals("n")) return false;
            System.out.println("Error: Invalid input. Please enter 'y' or 'n'.");
        }
    }
}

class Tea extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Steeping the tea...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding lemon...");
    }
}

class Coffee extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Dripping coffee through filter...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding sugar and milk...");
    }

    @Override
    protected boolean customerWantsCondiments() {
        return getUserInput("Would you like to add sugar and milk to your coffee?");
    }
}

class HotChocolate extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Mixing cocoa powder with hot water...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding marshmallows...");
    }

    @Override
    protected boolean customerWantsCondiments() {
        return getUserInput("Would you like to add marshmallows to your hot chocolate?");
    }
}

class CustomSequenceBeverage extends Beverage {
    @Override
    public final void brew() {
        System.out.println("Special Brew: Adding ingredients before water...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding special spices...");
    }
    
    // Демонстрация изменения логики через переопределение части шаблона
    public void prepareSpecial() {
        System.out.println("Starting custom sequence...");
        addCondiments();
        brew();
        System.out.println("Custom sequence finished.\n");
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Making Tea ---");
        Beverage tea = new Tea();
        tea.prepareRecipe();

        System.out.println("--- Making Coffee ---");
        Beverage coffee = new Coffee();
        coffee.prepareRecipe();

        System.out.println("--- Making Hot Chocolate ---");
        Beverage chocolate = new HotChocolate();
        chocolate.prepareRecipe();

        System.out.println("--- Making Custom Beverage ---");
        CustomSequenceBeverage custom = new CustomSequenceBeverage();
        custom.prepareSpecial();
    }
}
