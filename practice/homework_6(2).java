import java.util.ArrayList;
import java.util.List;
interface IObserver {
    void update(String currency, double rate);
}

interface ISubject {
    void registerObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObservers();
}

class CurrencyExchange implements ISubject {
    private List<IObserver> observers = new ArrayList<>();
    private String currency;
    private double rate;

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
        System.out.println("[Жүйе]: Жаңа жазылушы қосылды.");
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
        System.out.println("[Жүйе]: Жазылушы өшірілді.");
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(currency, rate);
        }
    }
    public void setRate(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
        System.out.println("\n[БИРЖА]: " + currency + " курсы өзгерді: " + rate + " KZT");
        notifyObservers();
    }
}


class Bank implements IObserver {
    @Override
    public void update(String currency, double rate) {
        System.out.println("[БАНК]: Ресми бағам жаңартылды: " + currency + " = " + rate);
    }
}

class Trader implements IObserver {
    private String name;

    public Trader(String name) {
        this.name = name;
    }

    @Override
    public void update(String currency, double rate) {
        if (rate < 450.0) {
            System.out.println("[ТРЕЙДЕР " + name + "]: Баға төмен! Сатып алуды бастаймын.");
        } else {
            System.out.println("[ТРЕЙДЕР " + name + "]: Баға жоғары, әлі де күтемін.");
        }
    }
}

class MobileApp implements IObserver {
    @Override
    public void update(String currency, double rate) {
        System.out.println("[APP]: Пуш-хабарлама: " + currency + " жаңа бағасы - " + rate);
    }
}


public class Main {
    public static void main(String[] args) {
        CurrencyExchange exchange = new CurrencyExchange();

        IObserver nationalBank = new Bank();
        IObserver traderArman = new Trader("Арман");
        IObserver halykApp = new MobileApp();

        exchange.registerObserver(nationalBank);
        exchange.registerObserver(traderArman);
        exchange.registerObserver(halykApp);

        exchange.setRate("USD", 445.5);

        exchange.setRate("USD", 455.0);
        exchange.removeObserver(traderArman);
        exchange.setRate("USD", 462.3);
    }
}
