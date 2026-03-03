import java.util.*;

interface ICommand {
    void execute();
    void undo();
}

class Light {
    public void on() { System.out.println("Light is ON"); }
    public void off() { System.out.println("Light is OFF"); }
}

class Door {
    public void open() { System.out.println("Door is OPEN"); }
    public void close() { System.out.println("Door is CLOSED"); }
}

class Thermostat {
    private int temperature = 22;
    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("Thermostat temperature set to " + temperature + "°C");
    }
    public int getTemperature() { return temperature; }
}

class SecuritySystem {
    public void arm() { System.out.println("Security System ARMED"); }
    public void disarm() { System.out.println("Security System DISARMED"); }
}

class LightOnCommand implements ICommand {
    private Light light;
    public LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.on(); }
    public void undo() { light.off(); }
}

class LightOffCommand implements ICommand {
    private Light light;
    public LightOffCommand(Light light) { this.light = light; }
    public void execute() { light.off(); }
    public void undo() { light.on(); }
}

class DoorOpenCommand implements ICommand {
    private Door door;
    public DoorOpenCommand(Door door) { this.door = door; }
    public void execute() { door.open(); }
    public void undo() { door.close(); }
}

class DoorCloseCommand implements ICommand {
    private Door door;
    public DoorCloseCommand(Door door) { this.door = door; }
    public void execute() { door.close(); }
    public void undo() { door.open(); }
}

class TempChangeCommand implements ICommand {
    private Thermostat thermostat;
    private int prevTemperature;
    private int newTemperature;

    public TempChangeCommand(Thermostat thermostat, int newTemp) {
        this.thermostat = thermostat;
        this.newTemperature = newTemp;
    }

    public void execute() {
        prevTemperature = thermostat.getTemperature();
        thermostat.setTemperature(newTemperature);
    }

    public void undo() {
        thermostat.setTemperature(prevTemperature);
    }
}

class SecurityArmCommand implements ICommand {
    private SecuritySystem security;
    public SecurityArmCommand(SecuritySystem s) { this.security = s; }
    public void execute() { security.arm(); }
    public void undo() { security.disarm(); }
}

class RemoteInvoker {
    private Stack<ICommand> history = new Stack<>();

    public void executeCommand(ICommand command) {
        command.execute();
        history.push(command);
    }

    public void undoLastCommand() {
        if (history.isEmpty()) {
            System.out.println("Error: No commands to undo.");
            return;
        }
        ICommand lastCommand = history.pop();
        System.out.print("Undoing: ");
        lastCommand.undo();
    }
}

public class Main {
    public static void main(String[] args) {
        RemoteInvoker remote = new RemoteInvoker();

        Light livingRoomLight = new Light();
        Door frontDoor = new Door();
        Thermostat thermostat = new Thermostat();
        SecuritySystem security = new SecuritySystem();

        System.out.println("--- Executing Commands ---");
        remote.executeCommand(new LightOnCommand(livingRoomLight));
        remote.executeCommand(new DoorOpenCommand(frontDoor));
        remote.executeCommand(new TempChangeCommand(thermostat, 25));
        remote.executeCommand(new SecurityArmCommand(security));

        System.out.println("\n--- Testing Undo History ---");
        remote.undoLastCommand(); // Undo Security
        remote.undoLastCommand(); // Undo Temp (back to 22)
        remote.undoLastCommand(); // Undo Door
        remote.undoLastCommand(); // Undo Light

        System.out.println("\n--- Testing Error Handling ---");
        remote.undoLastCommand(); // Attempt to undo when empty

        System.out.println("\n--- Complex State Test ---");
        remote.executeCommand(new TempChangeCommand(thermostat, 30));
        remote.executeCommand(new TempChangeCommand(thermostat, 18));
        remote.undoLastCommand(); // Should go back to 30
    }
}
