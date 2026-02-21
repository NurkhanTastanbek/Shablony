import java.util.HashMap;
import java.util.Map;
import java.io.*;

class ConfigurationManager {
    private static volatile ConfigurationManager instance;
    private Map<String, String> settings;
    private static final Object lock = new Object();

    private ConfigurationManager() {
        settings = new HashMap<>();
        settings.put("appName", "SingletonApp");
    }

    public static ConfigurationManager getInstance() {
        ConfigurationManager result = instance;
        if (result == null) {
            synchronized (lock) {
                result = instance;
                if (result == null) {
                    instance = result = new ConfigurationManager();
                }
            }
        }
        return result;
    }

    public String getSetting(String key) {
        if (!settings.containsKey(key)) {
            throw new RuntimeException("Error: Setting not found: " + key);
        }
        return settings.get(key);
    }

    public void setSetting(String key, String value) {
        settings.put(key, value);
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : settings.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    settings.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void loadFromDatabase() {
        System.out.println("Connecting to Database...");
        settings.put("db_status", "connected");
    }
}

public class Main {
    public static void main(String[] args) {
        Runnable task = () -> {
            ConfigurationManager config = ConfigurationManager.getInstance();
            System.out.println("Thread " + Thread.currentThread().getId() + " - Hash: " + config.hashCode());
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join(); t2.join(); t3.join();

            ConfigurationManager appConfig = ConfigurationManager.getInstance();
            appConfig.setSetting("version", "1.0.1");
            appConfig.saveToFile("settings.txt");
            
            appConfig.loadFromFile("settings.txt");
            appConfig.loadFromDatabase();

            System.out.println("Final Version: " + appConfig.getSetting("version"));
            System.out.println("DB Status: " + appConfig.getSetting("db_status"));
            
            appConfig.getSetting("wrong_key");

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
