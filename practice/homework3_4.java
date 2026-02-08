import java.util.ArrayList;
import java.util.List;

interface MessageSender {
    void send(String message);
}

class EmailSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("Email sent: " + message);
    }
}

class SmsSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("SMS sent: " + message);
    }
}
class TelegramSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("Telegram message sent: " + message);
    }
}

// Енді ол нақты кластарға емес, MessageSender интерфейсіне тәуелді
class NotificationService {
    private final List<MessageSender> senders;

    public NotificationService(List<MessageSender> senders) {
        this.senders = senders;
    }

    public void sendNotification(String message) {

        for (MessageSender sender : senders) {
            sender.send(message);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        List<MessageSender> mySenders = new ArrayList<>();
        mySenders.add(new EmailSender());
        mySenders.add(new SmsSender());
        mySenders.add(new TelegramSender());

        NotificationService notificationService = new NotificationService(mySenders);
        notificationService.sendNotification("Сәлем! Бұл DIP принципіне негізделген хабарлама.");
    }
}
