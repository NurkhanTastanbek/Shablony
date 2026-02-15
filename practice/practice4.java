import java.util.Scanner;

interface Document {
    void open();
}

class Report implements Document {
    public void open() { System.out.println("Report document opened."); }
}

class Resume implements Document {
    public void open() { System.out.println("Resume document opened."); }
}

class Letter implements Document {
    public void open() { System.out.println("Letter document opened."); }
}

class Invoice implements Document {
    public void open() { System.out.println("Invoice document opened."); }
}

abstract class DocumentCreator {
    public abstract Document createDocument();

    public void openDocument() {
        Document doc = createDocument();
        doc.open();
    }
}

class ReportCreator extends DocumentCreator {
    public Document createDocument() { return new Report(); }
}

class ResumeCreator extends DocumentCreator {
    public Document createDocument() { return new Resume(); }
}

class LetterCreator extends DocumentCreator {
    public Document createDocument() { return new Letter(); }
}

class InvoiceCreator extends DocumentCreator {
    public Document createDocument() { return new Invoice(); }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DocumentCreator creator = null;

        System.out.println("Enter document type (report, resume, letter, invoice):");
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "report":
                creator = new ReportCreator();
                break;
            case "resume":
                creator = new ResumeCreator();
                break;
            case "letter":
                creator = new LetterCreator();
                break;
            case "invoice":
                creator = new InvoiceCreator();
                break;
            default:
                System.out.println("Invalid type.");
        }

        if (creator != null) {
            creator.openDocument();
        }

        scanner.close();
    }
}
