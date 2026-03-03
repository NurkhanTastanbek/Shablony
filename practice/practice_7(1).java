import java.util.*;

interface ICommand {
    void execute();
    void undo();
}

class Light {
    private String location;
    public Light(String location) { this.location = location; }
    public void on() { System.out.println(location + " light is ON"); }
    public void off() { System.out.println(location + " light is OFF"); }
}

class AirConditioner {
    public void on() { System.out.println("AC is ON"); }
    public void off() { System.out.println("AC is OFF"); }
}

class SmartCurtains {
    public void open() { System.out.println("Curtains are OPEN"); }
    public void close() { System.out.println("Curtains are CLOSED"); }
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

class CurtainsOpenCommand implements ICommand {
    private SmartCurtains curtains;
    public CurtainsOpenCommand(SmartCurtains curtains) { this.curtains = curtains; }
    public void execute() { curtains.open(); }
    public void undo() { curtains.close(); }
}

class CurtainsCloseCommand implements ICommand {
    private SmartCurtains curtains;
    public CurtainsCloseCommand(SmartCurtains curtains) { this.curtains = curtains; }
    public void execute() { curtains.close(); }
    public void undo() { curtains.open(); }
}

class NoCommand implements ICommand {
    public void execute() {}
    public void undo() {}
}

class MacroCommand implements ICommand {
    private List<ICommand> commands;
    public MacroCommand(List<ICommand> commands) { this.commands = commands; }
    public void execute() {
        System.out.println("Executing Macro...");
        for (ICommand cmd : commands) cmd.execute();
    }
    public void undo() {
        System.out.println("Undoing Macro...");
        for (int i = commands.size() - 1; i >= 0; i--) commands.get(i).undo();
    }
}

class RemoteControl {
    private ICommand[] onCommands;
    private ICommand[] offCommands;
    private Stack<ICommand> undoStack = new Stack<>();
    private Stack<ICommand> redoStack = new Stack<>();

    public RemoteControl(int slots) {
        onCommands = new ICommand[slots];
        offCommands = new ICommand[slots];
        ICommand noCommand = new NoCommand();
        for (int i = 0; i < slots; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }

    public void setCommand(int slot, ICommand on, ICommand off) {
        onCommands[slot] = on;
        offCommands[slot] = off;
    }

    public void pressOn(int slot) {
        onCommands[slot].execute();
        undoStack.push(onCommands[slot]);
        redoStack.clear();
    }

    public void pressOff(int slot) {
        offCommands[slot].execute();
        undoStack.push(offCommands[slot]);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            ICommand cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            ICommand cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl(5);
        Light livingRoomLight = new Light("Living Room");
        SmartCurtains curtains = new SmartCurtains();

        remote.setCommand(0, new LightOnCommand(livingRoomLight), new LightOffCommand(livingRoomLight));
        remote.setCommand(1, new CurtainsOpenCommand(curtains), new CurtainsCloseCommand(curtains));

        List<ICommand> partyOn = Arrays.asList(new LightOnCommand(livingRoomLight), new CurtainsOpenCommand(curtains));
        List<ICommand> partyOff = Arrays.asList(new LightOffCommand(livingRoomLight), new CurtainsCloseCommand(curtains));
        remote.setCommand(2, new MacroCommand(partyOn), new MacroCommand(partyOff));

        remote.pressOn(0);
        remote.pressOn(1);
        remote.undo();
        remote.redo();
        
        System.out.println("--- Macro Test ---");
        remote.pressOn(2);
        remote.undo();

        System.out.println("--- Empty Slot Test ---");
        remote.pressOn(4);
    }
}
