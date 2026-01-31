import java.util.ArrayList;
import java.util.List;
class User {
    private String name;
    private String email;
    private String role;

    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Геттерлер мен сеттерлер
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
class UserManager {
    private List<User> users = new ArrayList<>();

    private User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        if (user != null && findUserByEmail(user.getEmail()) == null) {
            users.add(user);
            System.out.println("Жүйеге қосылды: " + user.getName());
        } else {
            System.out.println("Қате: Пайдаланушы бос немесе email бұрыннан тіркелген.");
        }
    }

    public void removeUser(String email) {
        User user = findUserByEmail(email);
        if (user != null) {
            users.remove(user);
            System.out.println("Пайдаланушы өшірілді: " + email);
        }
    }
    public void updateUser(String email, String newName, String newRole) {
        User user = findUserByEmail(email); // DRY қолдану
        if (user != null) {
            user.setName(newName);
            user.setRole(newRole);
            System.out.println("Ақпарат жаңартылды: " + email);
        }
    }

    public void printUsers() {
        System.out.println("\n--- Пайдаланушылар тізімі ---");
        for (User u : users) {
            System.out.println(u.getName() + " | " + u.getEmail() + " | " + u.getRole());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        UserManager manager = new UserManager();

        manager.addUser(new User("Аман", "aman@example.kz", "Admin"));
        manager.addUser(new User("Жансая", "zhansaya@example.kz", "User"));

        manager.printUsers();
        manager.updateUser("aman@example.kz", "Аманжол", "SuperAdmin");
        manager.removeUser("zhansaya@example.kz");
        manager.printUsers();
    }
}
