interface Printer {
    void print(String content);
}

interface Scanner {
    void scan(String content);
}

interface Fax {
    void fax(String content);
}


class AllInOnePrinter implements Printer, Scanner, Fax {
    @Override
    public void print(String content) {
        System.out.println("Басып шығару: " + content);
    }

    @Override
    public void scan(String content) {
        System.out.println("Сканерлеу: " + content);
    }

    @Override
    public void fax(String content) {
        System.out.println("Факс жіберу: " + content);
    }
}

class BasicPrinter implements Printer {
    @Override
    public void print(String content) {
        System.out.println("Қарапайым басып шығару: " + content);
    }
}

class Photocopier implements Printer, Scanner {
    @Override
    public void print(String content) {
        System.out.println("Көшірмені басып шығару: " + content);
    }

    @Override
    public void scan(String content) {
        System.out.println("Құжатты сканерлеу...");
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Көпфункционалды құрылғы жұмысы ===");
        AllInOnePrinter mfp = new AllInOnePrinter();
        mfp.print("Есеп беру");
        mfp.scan("Сурет");
        mfp.fax("Келісімшарт");

        System.out.println("\n=== Қарапайым принтер жұмысы ===");
        BasicPrinter basic = new BasicPrinter();
        basic.print("Хат");
        

        System.out.println("\n=== Көшірме жасау құрылғысы ===");
        Photocopier copier = new Photocopier();
        copier.scan("Паспорт");
        copier.print("Паспорт көшірмесі");
    }
}
