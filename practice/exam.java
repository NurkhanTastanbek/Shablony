interface Logger {
    void logInfo(String message);
    void logError(String message);
}
class OldFileLogger implements Logger {
    public void logInfo(String message) {
        System.out.println("Файлға жазылуда (INFO): " + message);
    }
    public void logError(String message) {
        System.err.println("Файлға жазылуда (ERROR): " + message);
    }
}
class CloudService {
    public void sendToCloud(String msg, String level) {
        System.out.println("Облакаға жіберілді [" + level + "]: " + msg);
    }
}
class CloudLoggerAdapter implements Logger {
    private CloudService cloudService;

    public CloudLoggerAdapter(CloudService service) {
        this.cloudService = service;
    }

    @Override
    public void logInfo(String message) {
        cloudService.sendToCloud(message, "INFORMATION");
    }

    @Override
    public void logError(String message) {
        cloudService.sendToCloud(message, "FATAL_ERROR");
    }
}
public class Main {
    public static void main(String[] args) {
        Logger logger = new CloudLoggerAdapter(new CloudService())
        logger.logInfo("Жүйе сәтті іске қосылды");
        logger.logError("Байланыс үзілді");
    }
}
