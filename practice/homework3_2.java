abstract class Employee {
    private String name;
    private double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public String getName() { return name; }
    public double getBaseSalary() { return baseSalary; }

    public abstract double calculateSalary();
}

class PermanentEmployee extends Employee {
    public PermanentEmployee(String name, double baseSalary) {
        super(name, baseSalary);
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() * 1.2; // 20% бонус
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(String name, double baseSalary) {
        super(name, baseSalary);
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() * 1.1; // 10% бонус
    }
}

class InternEmployee extends Employee {
    public InternEmployee(String name, double baseSalary) {
        super(name, baseSalary);
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() * 0.8; 
    }
}

class SalaryCalculator {
    public double calculate(Employee employee) {
        // Полиморфизм арқылы тиісті кластың есептеуін шақырады
        return employee.calculateSalary();
    }
}
public class Main {
    public static void main(String[] args) {
        SalaryCalculator calculator = new SalaryCalculator();

        Employee emp1 = new PermanentEmployee("Әліби", 500000);
        Employee emp2 = new ContractEmployee("Берік", 400000);
        Employee emp3 = new InternEmployee("Айжан", 200000);

        printSalary(emp1, calculator);
        printSalary(emp2, calculator);
        printSalary(emp3, calculator);

    }

    public static void printSalary(Employee emp, SalaryCalculator calc) {
        System.out.println(emp.getName() + " жалақысы: " + calc.calculate(emp) + " тг");
    }
}
