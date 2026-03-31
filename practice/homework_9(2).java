import java.util.*;

abstract class FileSystemComponent {
    protected String name;

    public FileSystemComponent(String name) {
        this.name = name;
    }

    public abstract void display(String indent);
    public abstract long getSize();
    public String getName() { return name; }
}

class File extends FileSystemComponent {
    private long size;

    public File(String name, long size) {
        super(name);
        this.size = size;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- File: " + name + " (" + size + " bytes)");
    }

    @Override
    public long getSize() {
        return size;
    }
}

class Directory extends FileSystemComponent {
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    public void add(FileSystemComponent component) {
        if (!components.contains(component)) {
            components.add(component);
        } else {
            System.out.println("Warning: Component '" + component.getName() + "' already exists in '" + name + "'.");
        }
    }

    public void remove(FileSystemComponent component) {
        if (components.contains(component)) {
            components.remove(component);
        } else {
            System.out.println("Error: Component '" + component.getName() + "' not found in '" + name + "'.");
        }
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "+ Directory: " + name);
        for (FileSystemComponent component : components) {
            component.display(indent + "  ");
        }
    }

    @Override
    public long getSize() {
        long totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
}

public class Main {
    public static void main(String[] args) {
        Directory root = new Directory("Root");
        Directory documents = new Directory("Documents");
        Directory pictures = new Directory("Pictures");
        Directory work = new Directory("Work");

        File report = new File("Report.pdf", 1500);
        File notes = new File("Notes.txt", 500);
        File vacation = new File("Vacation.jpg", 3000);
        File logo = new File("Logo.png", 2000);

        root.add(documents);
        root.add(pictures);

        documents.add(report);
        documents.add(notes);
        documents.add(work);
        
        work.add(new File("Project.docx", 5000));

        pictures.add(vacation);
        pictures.add(logo);

        System.out.println("--- File System Structure ---");
        root.display("");

        System.out.println("\n--- Size Calculations ---");
        System.out.println("Total size of '" + root.getName() + "': " + root.getSize() + " bytes");
        System.out.println("Total size of '" + documents.getName() + "': " + documents.getSize() + " bytes");

        System.out.println("\n--- Testing Constraints ---");
        documents.add(report); // Duplicate check
        root.remove(new File("NonExistent.exe", 0)); // Existence check
    }
}
