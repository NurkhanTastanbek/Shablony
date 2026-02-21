import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

enum LogLevel {
    INFO(1), WARNING(2), ERROR(3);
    private final int priority;
    LogLevel(int priority) { this.priority = priority; }
    public int getPriority() { return priority; }
}

class Logger {
    private static volatile Logger instance;
    private LogLevel currentLevel = LogLevel.INFO;
    private String logFilePath = "app.log";
    private long maxFileSize = 5000; // Ротация үшін (байт)
    private static final Object lock = new Object();

    private Logger() {
        loadConfiguration("config.txt");
    }

    public static Logger getInstance() {
        Logger result = instance;
        if (result == null) {
            synchronized (lock) {
                result = instance;
                if (result == null) {
                    instance = result = new Logger();
                }
            }
        }
        return result;
    }

    private void loadConfiguration(String configPath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(configPath));
            for (String line : lines) {
                if (line.startsWith("level=")) setLogLevel(LogLevel.valueOf(line.split("=")[1]));
                if (line.startsWith("path=")) logFilePath = line.split("=")[1];
            }
        } catch (Exception e) {
            System.out.println("Config not found, using defaults.");
        }
    }

    public void setLogLevel(LogLevel level) {
        this.currentLevel = level;
    }

    public synchronized void log(String message, LogLevel level) {
        if (level.getPriority() < currentLevel.getPriority()) return;

        checkRotation();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logEntry = String.format("[%s] %s: %s", timestamp, level, message);

        try (PrintWriter out = new PrintWriter(new FileWriter(logFilePath, true))) {
            out.println(logEntry);
            System.out.println("Console: " + logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkRotation() {
        File file = new File(logFilePath);
        if (file.exists() && file.length() > maxFileSize) {
            String newName = "app_" + System.currentTimeMillis() + ".log";
            file.renameTo(new File(newName));
            System.out.println("Log rotated: " + newName);
        }
    }
}

class LogReader {
    public void readLogs(String path, LogLevel filter) {
        System.out.println("\n--- Reading Logs (Filter: " + filter + ") ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(filter.toString())) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("config.txt"))) {
            writer.println("level=INFO");
            writer.println("path=app.log");
        } catch (IOException e) {}

        Logger logger = Logger.getInstance();


        Runnable logTask = () -> {
            for (int i = 0; i < 3; i++) {
                logger.log("Message from thread " + Thread.currentThread().getId(), LogLevel.INFO);
                logger.log("Warning from thread " + Thread.currentThread().getId(), LogLevel.WARNING);
                logger.log("Error from thread " + Thread.currentThread().getId(), LogLevel.ERROR);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        };

        Thread t1 = new Thread(logTask);
        Thread t2 = new Thread(logTask);
        Thread t3 = new Thread(logTask);

        t1.start(); t2.start(); t3.start();

        try {
            t1.join(); t2.join(); t3.join();


            logger.setLogLevel(LogLevel.ERROR);
            logger.log("This info will not be logged", LogLevel.INFO);
            logger.log("This error will be logged", LogLevel.ERROR);


            LogReader reader = new LogReader();
            reader.readLogs("app.log", LogLevel.ERROR);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
