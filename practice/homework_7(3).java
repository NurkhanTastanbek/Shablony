import java.util.*;

interface IMediator {
    void sendMessage(String message, User sender);
    void sendPrivateMessage(String message, User sender, String receiverName);
    void addUser(User user);
    void removeUser(User user);
}

abstract class User {
    protected IMediator mediator;
    protected String name;

    public User(IMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void send(String message);
    public abstract void sendPrivate(String message, String receiverName);
    public abstract void receive(String message, String from);
}

class ChatRoom implements IMediator {
    private List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        users.add(user);
        broadcastSystemMessage(user.getName() + " joined the chat.");
    }

    @Override
    public void removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            broadcastSystemMessage(user.getName() + " left the chat.");
        }
    }

    @Override
    public void sendMessage(String message, User sender) {
        if (!users.contains(sender)) {
            System.out.println("Error: User " + sender.getName() + " is not in the chat room.");
            return;
        }
        for (User user : users) {
            if (user != sender) {
                user.receive(message, sender.getName());
            }
        }
    }

    @Override
    public void sendPrivateMessage(String message, User sender, String receiverName) {
        if (!users.contains(sender)) {
            System.out.println("Error: User " + sender.getName() + " is not in the chat room.");
            return;
        }
        boolean found = false;
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(receiverName)) {
                user.receive("[Private] " + message, sender.getName());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Error: User " + receiverName + " not found.");
        }
    }

    private void broadcastSystemMessage(String message) {
        System.out.println("[SYSTEM]: " + message);
    }
}

class ChatUser extends User {
    public ChatUser(IMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void send(String message) {
        System.out.println(this.name + " sends: " + message);
        mediator.sendMessage(message, this);
    }

    @Override
    public void sendPrivate(String message, String receiverName) {
        System.out.println(this.name + " sends private to " + receiverName + ": " + message);
        mediator.sendPrivateMessage(message, this, receiverName);
    }

    @Override
    public void receive(String message, String from) {
        System.out.println(this.name + " received from " + from + ": " + message);
    }
}

public class Main {
    public static void main(String[] args) {
        ChatRoom chat = new ChatRoom();

        User user1 = new ChatUser(chat, "Alice");
        User user2 = new ChatUser(chat, "Bob");
        User user3 = new ChatUser(chat, "Charlie");

        chat.addUser(user1);
        chat.addUser(user2);
        chat.addUser(user3);

        System.out.println("--- Group Message Test ---");
        user1.send("Hi everyone!");

        System.out.println("\n--- Private Message Test ---");
        user2.sendPrivate("Hey Charlie, do you have a minute?", "Charlie");

        System.out.println("\n--- Leave Chat Test ---");
        chat.removeUser(user2);
        user3.send("Where did Bob go?");

        System.out.println("\n--- Error Handling Test ---");
        User impostor = new ChatUser(chat, "Impostor");
        impostor.send("I'm not in the list!");
    }
}
