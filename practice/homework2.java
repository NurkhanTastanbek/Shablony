import java.util.Arrays;

// --- 1. DRY: Ортақ конфигурация ---
class AppConfig {
    // Конфигурацияны бір жерде сақтау - болашақта өзгерту оңай
    public static final String CONNECTION_STRING = "Server=myServer;Database=myDb;User Id=myUser;Password=myPass;";
}

// --- 2. DRY: Параметрленген әдіс ---
class Logger {
    // Үш бөлек әдістің орнына бір әмбебап әдіс (DRY)
    public void log(String message, String level) {
        System.out.println(level.toUpperCase() + ": " + message);
    }
}

class DatabaseService {
    public void connect() {
        String conn = AppConfig.CONNECTION_STRING;
        System.out.println("Базаға қосылуда: " + conn);
    }
}

class LoggingService {
    public void log(String message) {
        String conn = AppConfig.CONNECTION_STRING;
        System.out.println("Лог базаға жазылды: " + message);
    }
}

// --- 3. KISS: Қарапайымдылық және "Түзу" код ---
class NumberProcessor {
    // Guard Clauses қолдану: іштей салынудан (if ішінде if) құтылу
    public void processNumbers(int[] numbers) {
        // Егер массив бос болса, бірден тоқтатамыз
        if (numbers == null || numbers.length == 0) return;

        for (int number : numbers) {
            if (number > 0) {
                System.out.println("Өңделуде: " + number);
            }
        }
    }

    // Артық Stream/LINQ-сыз қарапайым цикл қолдану (KISS)
    public void printPositiveNumbers(int[] numbers) {
        if (numbers == null) return;

        for (int n : numbers) {
            if (n > 0) {
                System.out.println(n);
            }
        }
    }

    // Exception орнына қарапайым 'if' арқылы тексеру
    public int divide(int a, int b) {
        return (b == 0) ? 0 : a / b;
    }
}

// --- 4. YAGNI: Артық функцияларды алып тастау ---
class FileReader {
    public String readFile(String filePath) {
        // Пайдаланушыға керек емес буфер өлшемі сияқты баптауларды алып тастадық
        return "Файл оқылды: " + filePath;
    }
}

class ReportGenerator {
    // Әр форматқа жеке әдіс жазбай, параметрмен басқару
    public void generateReport(String format) {
        System.out.println(format.toUpperCase() + " форматында есеп жасалуда...");
    }
}

// --- Негізгі іске қосу класы ---
public class Main {
    public static void main(String[] args) {
        // DRY тексеру
        DatabaseService db = new DatabaseService();
        db.connect();

        Logger logger = new Logger();
        logger.log("Жүйе іске қосылды", "info");
        logger.log("Критикалық қате", "error");

        // KISS тексеру
        NumberProcessor processor = new NumberProcessor();
        int[] data = { -1, 5, 0, 12 };
        System.out.println("\nСандарды өңдеу:");
        processor.processNumbers(data);

        // Бөлу (қатесіз)
        System.out.println("\nБөлу (10/0): " + processor.divide(10, 0));

        // YAGNI тексеру
        ReportGenerator report = new ReportGenerator();
        report.generateReport("pdf");
    }
}
