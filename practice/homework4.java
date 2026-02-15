import java.util.Scanner;

interface IVehicle {
    void drive();
    void refuel();
}
class Car implements IVehicle {
    private String brand, model, fuelType;
    public Car(String brand, String model, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
    }
    public void drive() { System.out.println(brand + " " + model + " қозғалды. Отын түрі: " + fuelType); }
    public void refuel() { System.out.println(brand + " заправка жасалуда..."); }
}

class Motorcycle implements IVehicle {
    private String type;
    private int engineVolume;
    public Motorcycle(String type, int engineVolume) {
        this.type = type;
        this.engineVolume = engineVolume;
    }
    public void drive() { System.out.println(type + " мотоциклі жүріп кетті. Көлемі: " + engineVolume + "cc"); }
    public void refuel() { System.out.println("Мотоциклге отын құйылуда..."); }
}

class Truck implements IVehicle {
    private double capacity;
    private int axles;
    public Truck(double capacity, int axles) {
        this.capacity = capacity;
        this.axles = axles;
    }
    public void drive() { System.out.println("Грузовик жолға шықты. Жүк көтерілімділігі: " + capacity + " т, ось саны: " + axles); }
    public void refuel() { System.out.println("Грузовикке дизель құйылуда..."); }
}

class Bus implements IVehicle {
    private int passengerCount;
    public Bus(int passengerCount) { this.passengerCount = passengerCount; }
    public void drive() { System.out.println("Автобус бағытқа шықты. Сыйымдылығы: " + passengerCount + " адам"); }
    public void refuel() { System.out.println("Автобусқа отын құйылуда..."); }
}

abstract class VehicleFactory {
    public abstract IVehicle createVehicle();
}

class CarFactory extends VehicleFactory {
    private String brand, model, fuel;
    public CarFactory(String brand, String model, String fuel) {
        this.brand = brand; this.model = model; this.fuel = fuel;
    }
    public IVehicle createVehicle() { return new Car(brand, model, fuel); }
}

class MotorcycleFactory extends VehicleFactory {
    private String type;
    private int volume;
    public MotorcycleFactory(String type, int volume) {
        this.type = type; this.volume = volume;
    }
    public IVehicle createVehicle() { return new Motorcycle(type, volume); }
}

class TruckFactory extends VehicleFactory {
    private double cap;
    private int axles;
    public TruckFactory(double cap, int axles) {
        this.cap = cap; this.axles = axles;
    }
    public IVehicle createVehicle() { return new Truck(cap, axles); }
}

class BusFactory extends VehicleFactory {
    private int passengers;
    public BusFactory(int passengers) { this.passengers = passengers; }
    public IVehicle createVehicle() { return new Bus(passengers); }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VehicleFactory factory = null;

        System.out.println("Көлік түрін таңдаңыз (1-Car, 2-Motorcycle, 3-Truck, 4-Bus):");
        int choice = sc.nextInt();
        sc.nextLine(); 

        switch (choice) {
            case 1:
                System.out.print("Марка: "); String brand = sc.nextLine();
                System.out.print("Модель: "); String model = sc.nextLine();
                System.out.print("Отын түрі: "); String fuel = sc.nextLine();
                factory = new CarFactory(brand, model, fuel);
                break;
            case 2:
                System.out.print("Тип (Sport/Touring): "); String type = sc.nextLine();
                System.out.print("Қозғалтқыш көлемі: "); int vol = sc.nextInt();
                factory = new MotorcycleFactory(type, vol);
                break;
            case 3:
                System.out.print("Жүк көтерілімділігі (тонна): "); double cap = sc.nextDouble();
                System.out.print("Ось саны: "); int axles = sc.nextInt();
                factory = new TruckFactory(cap, axles);
                break;
            case 4:
                System.out.print("Жолаушы саны: "); int pass = sc.nextInt();
                factory = new BusFactory(pass);
                break;
            default:
                System.out.println("Қате таңдау!");
        }

        if (factory != null) {
            IVehicle vehicle = factory.createVehicle();
            System.out.println("--- Нәтиже ---");
            vehicle.drive();
            vehicle.refuel();
        }
        sc.close();
    }
}
