import java.util.ArrayList;
import java.util.List;

// 1. Книга (Book) класы
class Book {
    private String title;
    private String author;
    private String isbn;
    private int copies;

    public Book(String title, String author, String isbn, int copies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.copies = copies;
    }

    // Геттерлер мен Сеттерлер
    public String getTitle() { return title; }
    public String getIsbn() { return isbn; }
    public int getCopies() { return copies; }
    public void setCopies(int copies) { this.copies = copies; }

    @Override
    public String toString() {
        return "'" + title + "' (" + author + "), ISBN: " + isbn + ", Дана: " + copies;
    }
}

// 1. Читатель (Reader) класы
class Reader {
    private String name;
    private int readerId;

    public Reader(String name, int readerId) {
        this.name = name;
        this.readerId = readerId;
    }

    public String getName() { return name; }
    public int getReaderId() { return readerId; }

    @Override
    public String toString() {
        return "Оқырман: " + name + " (ID: " + readerId + ")";
    }
}

// 1. Библиотека (Library) класы
class Library {
    private List<Book> books = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();

    // Книги басқару әдістері
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Кітап қосылды: " + book.getTitle());
    }

    public void removeBook(String isbn) {
        books.removeIf(b -> b.getIsbn().equals(isbn));
        System.out.println("ISBN " + isbn + " кітабы өшірілді.");
    }

    // Читатели басқару әдістері
    public void registerReader(Reader reader) {
        readers.add(reader);
        System.out.println("Оқырман тіркелді: " + reader.getName());
    }

    // Выдача и возврат книг
    public void issueBook(String isbn, Reader reader) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book.getCopies() > 0) {
                    book.setCopies(book.getCopies() - 1);
                    System.out.println(reader.getName() + " оқырманға '" + book.getTitle() + "' берілді.");
                } else {
                    System.out.println("Кешіріңіз, '" + book.getTitle() + "' кітабы таусылды.");
                }
                return;
            }
        }
        System.out.println("Кітап табылмады.");
    }

    public void returnBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                book.setCopies(book.getCopies() + 1);
                System.out.println("Кітап қайтарылды: " + book.getTitle());
                return;
            }
        }
    }

    public void showStatus() {
        System.out.println("\n--- Кітапхана қоры ---");
        books.forEach(System.out::println);
    }
}


class LibraryApp {
    public static void main(String[] args) {
        Library myLibrary = new Library();

        // Кітаптар мен оқырмандарды құру
        Book book1 = new Book("Абай жолы", "М. Әуезов", "101-ABC", 3);
        Book book2 = new Book("Қан мен тер", "Ә. Нұрпейісов", "102-XYZ", 1);
        Reader reader1 = new Reader("Арман", 1);
        Reader reader2 = new Reader("Әлия", 2);

        // 1. Кітаптарды қосу және өшіру функционалдығы
        myLibrary.addBook(book1);
        myLibrary.addBook(book2);

        // 2. Тіркеу және кітап беру процесін тексеру
        myLibrary.registerReader(reader1);
        myLibrary.registerReader(reader2);

        myLibrary.showStatus();

        System.out.println("\n--- Процестер ---");
        myLibrary.issueBook("101-ABC", reader1); // Сәтті беру
        myLibrary.issueBook("102-XYZ", reader2); // Соңғы дананы беру
        myLibrary.issueBook("102-XYZ", reader1); // Кітап таусылғанын тексеру

        myLibrary.returnBook("102-XYZ"); // Қайтару
        myLibrary.showStatus();
    }
}
