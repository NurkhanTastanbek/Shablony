import java.util.*;

interface IMediator {
    void sendMessage(String message, IUser sender, String channelName);
    void sendPrivateMessage(String message, IUser sender, String receiverName);
    void addUser(IUser user, String channelName);
    void removeUser(IUser user, String channelName);
    void blockUser(String userName);
    void broadcast(String message);
}

interface IUser {
    String getName();
    void receive(String message, String from);
    void send(String message, String channelName);
    void sendPrivate(String message, String receiverName);
}

class ChatMediator implements IMediator {
    private Map<String, List<IUser>> channels = new HashMap<>();
    private Set<String> blockedUsers = new HashSet<>();

    @Override
    public void addUser(IUser user, String channelName) {
        channels.putIfAbsent(channelName, new ArrayList<>());
        if (!channels.get(channelName).contains(user)) {
            channels.get(channelName).add(user);
            sendMessage("System: " + user.getName() + " joined the channel.", null, channelName);
        }
    }

    @Override
    public void removeUser(IUser user, String channelName) {
        if (channels.containsKey(channelName) && channels.get(channelName).contains(user)) {
            channels.get(channelName).remove(user);
            sendMessage("System: " + user.getName() + " left the channel.", null, channelName);
        }
    }

    @Override
    public void sendMessage(String message, IUser sender, String channelName) {
        if (sender != null && blockedUsers.contains(sender.getName())) {
            System.out.println("System Error: User " + sender.getName() + " is blocked and cannot send messages.");
            return;
        }

        if (!channels.containsKey(channelName)) {
            System.out.println("System Error: Channel " + channelName + " does not exist.");
            return;
        }

        List<IUser> usersInChannel = channels.get(channelName);
        if (sender != null && !usersInChannel.contains(sender)) {
            System.out.println("System Error: " + sender.getName() + " is not in channel " + channelName);
            return;
        }

        String from = (sender == null) ? "System" : sender.getName();
        for (IUser user : usersInChannel) {
            if (user != sender) {
                user.receive("[" + channelName + "] " + message, from);
            }
        }
    }

    @Override
    public void sendPrivateMessage(String message, IUser sender, String receiverName) {
        if (blockedUsers.contains(sender.getName())) {
            System.out.println("System Error: " + sender.getName() + " is blocked.");
            return;
        }

        IUser receiver = null;
        for (List<IUser> users : channels.values()) {
            for (IUser u : users) {
                if (u.getName().equalsIgnoreCase(receiverName)) {
                    receiver = u;
                    break;
                }
            }
        }

        if (receiver != null) {
            receiver.receive("[Private] " + message, sender.getName());
        } else {
            System.out.println("System Error: User " + receiverName + " not found.");
        }
    }

    @Override
    public void blockUser(String userName) {
        blockedUsers.add(userName);
        System.out.println("System: User " + userName + " has been blocked by Admin.");
    }

    @Override
    public void broadcast(String message) {
        Set<IUser> allUsers = new HashSet<>();
        for (List<IUser> list : channels.values()) {
            allUsers.addAll(list);
        }
        for (IUser user : allUsers) {
            user.receive("[Global] " + message, "Admin");
        }
    }
}

class User implements IUser {
    protected IMediator mediator;
    protected String name;

    public User(IMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    @Override
    public String getName() { return name; }

    @Override
    public void send(String message, String channelName) {
        mediator.sendMessage(message, this, channelName);
    }

    @Override
    public void sendPrivate(String message, String receiverName) {
        mediator.sendPrivateMessage(message, this, receiverName);
    }

    @Override
    public void receive(String message, String from) {
        System.out.println(name + " received from " + from + ": " + message);
    }
}

public class Main {
    public static void main(String[] args) {
        IMediator chat = new ChatMediator();

        IUser alice = new User(chat, "Alice");
        IUser bob = new User(chat, "Bob");
        IUser charlie = new User(chat, "Charlie");

        chat.addUser(alice, "General");
        chat.addUser(bob, "General");
        chat.addUser(charlie, "Dev");

        System.out.println("--- Channel Communication ---");
        alice.send("Hello everyone in General!", "General");
        charlie.send("Hello Devs!", "Dev");

        System.out.println("\n--- Private Messaging ---");
        bob.sendPrivate("Hey Alice, how are you?", "Alice");

        System.out.println("\n--- Cross-channel sending (Error handling) ---");
        alice.send("I'm trying to talk to Devs!", "Dev");

        System.out.println("\n--- Admin Blocking ---");
        chat.blockUser("Alice");
        alice.send("Can you hear me?", "General");

        System.out.println("\n--- Notifications ---");
        chat.removeUser(bob, "General");

        System.out.println("\n--- Global Broadcast ---");
        chat.broadcast("Server will restart in 5 minutes.");
    }
}
