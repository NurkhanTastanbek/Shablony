using System;
using System.IO;
using System.Linq;

// --- DRY: Ортақ конфигурация ---
public static class AppConfig
{
    public const string ConnectionString = "Server=myServer;Database=myDb;User Id=myUser;Password=myPass;";
}

// --- DRY: Параметрленген логика ---
public class Logger
{
    public void Log(string message, string level = "INFO")
    {
        Console.WriteLine($"{level.ToUpper()}: {message}");
    }
}

public class DatabaseService
{
    public void Connect()
    {
        string connectionString = AppConfig.ConnectionString;
        // Қосылу логикасы осында
    }
}

public class LoggingService
{
    private readonly Logger _logger = new Logger();

    public void LogToDb(string message)
    {
        string connectionString = AppConfig.ConnectionString;
        _logger.Log(message, "DB_LOG");
    }
}

// --- KISS: Қарапайым және тиімді әдістер ---
public class Calculator
{
    public int Divide(int a, int b)
    {
        // Exception-ды күтпей-ақ, алдын ала тексеру (KISS)
        return b == 0 ? 0 : a / b;
    }

    public void ProcessNumbers(int[] numbers)
    {
        // Guard Clause: артық іштей салынуды болдырмау
        if (numbers == null || numbers.Length == 0) return;

        foreach (var number in numbers)
        {
            if (number > 0) Console.WriteLine(number);
        }
    }

    public void PrintPositiveNumbers(int[] numbers)
    {
        // Артық LINQ-сыз қарапайым цикл (KISS)
        if (numbers == null) return;

        foreach (var n in numbers)
        {
            if (n > 0) Console.WriteLine(n);
        }
    }
}

// --- KISS: Артық функциялардан тазартылған кластар ---
public class FileReader
{
    public string ReadFile(string filePath)
    {
        // Пайдаланушыны буфер өлшемімен мазаламаймыз
        return File.Exists(filePath) ? File.ReadAllText(filePath) : string.Empty;
    }
}

public class ReportGenerator
{
    public void GenerateReport(string format)
    {
        // Әр форматқа жеке әдіс жазбаймыз (YAGNI принципі - "Сен оны пайдаланбайсың")
        Console.WriteLine($"{format.ToUpper()} форматында есеп дайындалуда...");
    }
}
