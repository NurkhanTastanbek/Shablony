import java.util.ArrayList;
import java.util.List;

// Базалық класс: Vehicle
class Vehicle {
    protected String brand;
    protected String model;
    protected int year;

    public Vehicle(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public void startEngine() {
        System.out.println(brand + " " + model + ": Қозғалтқыш іске қосылды.");
    }

    public void stopEngine() {
        System.out.println(brand + " " + model + ": Қозғалтқыш тоқтатылды.");
    }

    @Override
    public String toString() {
        return year + " " + brand + " " + model;
    }
}

// Туынды класс: Car
class Car extends Vehicle {
    private int doors;
    private String transmission;

    public Car(String brand, String model, int year, int doors, String transmission) {
        super(brand, model, year);
        this.doors = doors;
        this.transmission = transmission;
    }

    @Override
    public String toString() {
        return super.toString() + " (Көлік, есіктер: " + doors + ", беріліс қорабы: " + transmission + ")";
    }
}

// Туынды класс: Motorcycle
class Motorcycle extends Vehicle {
    private String bodyType;
    private boolean hasBox;

    public Motorcycle(String brand, String model, int year, String bodyType, boolean hasBox) {
        super(brand, model, year);
        this.bodyType = bodyType;
        this.hasBox = hasBox;
    }

    @Override
    public String toString() {
        return super.toString() + " (Мотоцикл, түрі: " + bodyType + ", жәшігі: " + (hasBox ? "бар" : "жоқ") + ")";
    }
}

// Garage класы (Транспорт құралдарының тізімі бар)
class Garage {
    private String name;
    private List<Vehicle> vehicles;

    public Garage(String name) {
        this.name = name;
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        System.out.println(vehicle + " гаражға қосылды.");
    }

    public void removeVehicle(Vehicle vehicle) {
        if (vehicles.remove(vehicle)) {
            System.out.println(vehicle + " гараждан шығарылды.");
        }
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public String getName() {
        return name;
    }
}

// Fleet класы (Гараждар тізімі бар - Композиция)
class Fleet {
    private List<Garage> garages;

    public Fleet() {
        this.garages = new ArrayList<>();
    }

    public void addGarage(Garage garage) {
        garages.add(garage);
        System.out.println(garage.getName() + " автопаркке қосылды.");
    }

    public void removeGarage(Garage garage) {
        garages.remove(garage);
        System.out.println(garage.getName() + " автопарктен өшірілді.");
    }

    public void findVehicle(String modelName) {
        System.out.println("Іздеу нәтижесі '" + modelName + "':");
        for (Garage g : garages) {
            for (Vehicle v : g.getVehicles()) {
                if (v.toString().toLowerCase().contains(modelName.toLowerCase())) {
                    System.out.println("- Табылды: " + v + " [" + g.getName() + " ішінде]");
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // 1. Көліктерді жасау
        Car car1 = new Car("Toyota", "Camry", 2022, 4, "Автомат");
        Motorcycle moto1 = new Motorcycle("BMW", "S1000RR", 2023, "Спорт", false);

        // 2. Гараж жасау және көлік қосу
        Garage centralGarage = new Garage("Орталық Гараж");
        centralGarage.addVehicle(car1);
        centralGarage.addVehicle(moto1);

        // 3. Автопарк жасау және гаражды қосу
        Fleet myFleet = new Fleet();
        myFleet.addGarage(centralGarage);

        // 4. Функционалды тексеру
        System.out.println("\n--- Жұмысты тексеру ---");
        car1.startEngine();
        myFleet.findVehicle("Camry");

        // 5. Өшіруді тексеру
        System.out.println("\n--- Өшіруді тексеру ---");
        centralGarage.removeVehicle(car1);
        myFleet.removeGarage(centralGarage);
    }
}
