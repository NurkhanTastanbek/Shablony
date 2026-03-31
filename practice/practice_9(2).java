import java.util.*;

abstract class OrganizationComponent {
    protected String name;

    public OrganizationComponent(String name) {
        this.name = name;
    }

    public abstract void display(String indent);
    public abstract double getBudget();
    public abstract int getEmployeeCount();
    public abstract void getAllEmployees(List<Employee> employeeList);
    public abstract Employee findEmployee(String name);
    
    public String getName() {
        return name;
    }
}

class Employee extends OrganizationComponent {
    private String position;
    private double salary;

    public Employee(String name, String position, double salary) {
        super(name);
        this.position = position;
        this.salary = salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- Employee: " + name + " (" + position + "), Salary: $" + salary);
    }

    @Override
    public double getBudget() {
        return salary;
    }

    @Override
    public int getEmployeeCount() {
        return 1;
    }

    @Override
    public void getAllEmployees(List<Employee> employeeList) {
        employeeList.add(this);
    }

    @Override
    public Employee findEmployee(String name) {
        if (this.name.equalsIgnoreCase(name)) {
            return this;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Position: " + position + ", Salary: $" + salary;
    }
}

class Contractor extends OrganizationComponent {
    private double fixedFee;

    public Contractor(String name, double fixedFee) {
        super(name);
        this.fixedFee = fixedFee;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- Contractor: " + name + " (Fixed Fee: $" + fixedFee + ")");
    }

    @Override
    public double getBudget() {
        return 0; // Контракторы не включаются в бюджет отдела по условию
    }

    @Override
    public int getEmployeeCount() {
        return 1;
    }

    @Override
    public void getAllEmployees(List<Employee> employeeList) {
        // Контракторы не являются штатными сотрудниками Employee
    }

    @Override
    public Employee findEmployee(String name) {
        return null; 
    }
}

class Department extends OrganizationComponent {
    private List<OrganizationComponent> components = new ArrayList<>();

    public Department(String name) {
        super(name);
    }

    public void add(OrganizationComponent component) {
        components.add(component);
    }

    public void remove(OrganizationComponent component) {
        components.remove(component);
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "+ Department: " + name);
        for (OrganizationComponent component : components) {
            component.display(indent + "  ");
        }
    }

    @Override
    public double getBudget() {
        double totalBudget = 0;
        for (OrganizationComponent component : components) {
            totalBudget += component.getBudget();
        }
        return totalBudget;
    }

    @Override
    public int getEmployeeCount() {
        int count = 0;
        for (OrganizationComponent component : components) {
            count += component.getEmployeeCount();
        }
        return count;
    }

    @Override
    public void getAllEmployees(List<Employee> employeeList) {
        for (OrganizationComponent component : components) {
            component.getAllEmployees(employeeList);
        }
    }

    @Override
    public Employee findEmployee(String name) {
        for (OrganizationComponent component : components) {
            Employee found = component.findEmployee(name);
            if (found != null) return found;
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        Department headOffice = new Department("Head Office");
        
        Department itDept = new Department("IT Department");
        Employee dev1 = new Employee("Alice", "Senior Developer", 5000);
        Employee dev2 = new Employee("Bob", "Junior Developer", 3000);
        Contractor externalTester = new Contractor("John Doe", 1500);
        
        itDept.add(dev1);
        itDept.add(dev2);
        itDept.add(externalTester);

        Department hrDept = new Department("HR Department");
        Employee hr1 = new Employee("Jane", "HR Manager", 4000);
        hrDept.add(hr1);

        headOffice.add(itDept);
        headOffice.add(hrDept);

        System.out.println("--- Corporate Structure ---");
        headOffice.display("");

        System.out.println("\n--- Statistics ---");
        System.out.println("Total Budget (Salaries): $" + headOffice.getBudget());
        System.out.println("Total Staff Count: " + headOffice.getEmployeeCount());

        System.out.println("\n--- Search and Modify ---");
        Employee found = headOffice.findEmployee("Alice");
        if (found != null) {
            System.out.println("Found: " + found);
            System.out.println("Increasing salary for Alice...");
            found.setSalary(5500);
        }

        System.out.println("New IT Budget: $" + itDept.getBudget());

        System.out.println("\n--- List All Staff Members (IT + Subordinates) ---");
        List<Employee> allStaff = new ArrayList<>();
        itDept.getAllEmployees(allStaff);
        for (Employee e : allStaff) {
            System.out.println(e);
        }
    }
}
