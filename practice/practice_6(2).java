using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

public interface IObserver
{
    string Name { get; }
    Task UpdateAsync(string stockSymbol, decimal price);
}

public interface IStockExchange
{
    void RegisterObserver(string stockSymbol, IObserver observer);
    void RemoveObserver(string stockSymbol, IObserver observer);
    Task NotifyObserversAsync(string stockSymbol, decimal price);
}

public class StockExchange : IStockExchange
{
    private readonly Dictionary<string, List<IObserver>> _observers = new();

    public void RegisterObserver(string stockSymbol, IObserver observer)
    {
        if (!_observers.ContainsKey(stockSymbol))
            _observers[stockSymbol] = new List<IObserver>();
        _observers[stockSymbol].Add(observer);
        Console.WriteLine($"[LOG]: {observer.Name} registered for {stockSymbol}");
    }

    public void RemoveObserver(string stockSymbol, IObserver observer)
    {
        if (_observers.ContainsKey(stockSymbol))
        {
            _observers[stockSymbol].Remove(observer);
            Console.WriteLine($"[LOG]: {observer.Name} removed from {stockSymbol}");
        }
    }

    public async Task UpdatePriceAsync(string stockSymbol, decimal newPrice)
    {
        Console.WriteLine($"\n[MARKET]: {stockSymbol} -> {newPrice:C}");
        await NotifyObserversAsync(stockSymbol, newPrice);
    }

    public async Task NotifyObserversAsync(string stockSymbol, decimal price)
    {
        if (_observers.ContainsKey(stockSymbol))
        {
            var tasks = _observers[stockSymbol].Select(obs => obs.UpdateAsync(stockSymbol, price));
            await Task.WhenAll(tasks);
        }
    }
}

public class Trader : IObserver
{
    public string Name { get; }
    public Trader(string name) => Name = name;

    public Task UpdateAsync(string stockSymbol, decimal price)
    {
        Console.WriteLine($"[Trader {Name}]: Received {stockSymbol} price: {price:C}");
        return Task.CompletedTask;
    }
}

public class TradingRobot : IObserver
{
    public string Name { get; }
    private decimal _threshold;
    private bool _buyAbove;

    public TradingRobot(string name, decimal threshold, bool buyAbove)
    {
        Name = name;
        _threshold = threshold;
        _buyAbove = buyAbove;
    }

    public Task UpdateAsync(string stockSymbol, decimal price)
    {
        if (_buyAbove && price >= _threshold)
            Console.WriteLine($"[Robot {Name}]: ALERT! Price {price:C} >= {_threshold}. ACTION: BUY {stockSymbol}");
        else if (!_buyAbove && price <= _threshold)
            Console.WriteLine($"[Robot {Name}]: ALERT! Price {price:C} <= {_threshold}. ACTION: SELL {stockSymbol}");
        
        return Task.CompletedTask;
    }
}

class Program
{
    static async Task Main(string[] args)
    {
        StockExchange exchange = new StockExchange();

        Trader john = new Trader("John");
        TradingRobot bot1 = new TradingRobot("Alpha-Bot", 150.00m, true);
        TradingRobot bot2 = new TradingRobot("Omega-Bot", 100.00m, false);

        exchange.RegisterObserver("AAPL", john);
        exchange.RegisterObserver("AAPL", bot1);
        exchange.RegisterObserver("TSLA", bot2);

        await exchange.UpdatePriceAsync("AAPL", 140.00m);
        await Task.Delay(500);
        
        await exchange.UpdatePriceAsync("AAPL", 155.00m);
        await Task.Delay(500);

        await exchange.UpdatePriceAsync("TSLA", 90.00m);
        await Task.Delay(500);

        exchange.RemoveObserver("AAPL", john);
        await exchange.UpdatePriceAsync("AAPL", 160.00m);

        Console.WriteLine("\nSimulation completed.");
    }
}
