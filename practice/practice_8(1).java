import java.util.*;

interface IReport {
    String generate();
}

class SalesReport implements IReport {
    @Override
    public String generate() {
        return "Sales Data: [Item A: $100, Item B: $200, Item C: $50]";
    }
}

class UserReport implements IReport {
    @Override
    public String generate() {
        return "User Data: [User1: Active, User2: Inactive, User3: Active]";
    }
}

abstract class ReportDecorator implements IReport {
    protected IReport report;

    public ReportDecorator(IReport report) {
        this.report = report;
    }

    @Override
    public String generate() {
        return report.generate();
    }
}

class DateFilterDecorator extends ReportDecorator {
    private String dateRange;

    public DateFilterDecorator(IReport report, String dateRange) {
        super(report);
        this.dateRange = dateRange;
    }

    @Override
    public String generate() {
        return report.generate() + " | Filtered by Date: " + dateRange;
    }
}

class SortingDecorator extends ReportDecorator {
    private String criteria;

    public SortingDecorator(IReport report, String criteria) {
        super(report);
        this.criteria = criteria;
    }

    @Override
    public String generate() {
        return report.generate() + " | Sorted by: " + criteria;
    }
}

class AmountFilterDecorator extends ReportDecorator {
    private double minAmount;

    public AmountFilterDecorator(IReport report, double minAmount) {
        super(report);
        this.minAmount = minAmount;
    }

    @Override
    public String generate() {
        return report.generate() + " | Filtered by Min Amount: $" + minAmount;
    }
}

class CsvExportDecorator extends ReportDecorator {
    public CsvExportDecorator(IReport report) {
        super(report);
    }

    @Override
    public String generate() {
        return "CSV_START\n" + report.generate().replace(" | ", ",") + "\nCSV_END";
    }
}

class PdfExportDecorator extends ReportDecorator {
    public PdfExportDecorator(IReport report) {
        super(report);
    }

    @Override
    public String generate() {
        return "[PDF Header]\n" + report.generate() + "\n[PDF Footer: Page 1]";
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Scenario 1: Sales Report with Filtering and Sorting ---");
        IReport salesReport = new SalesReport();
        salesReport = new DateFilterDecorator(salesReport, "2023-01-01 to 2023-12-31");
        salesReport = new SortingDecorator(salesReport, "Amount");
        System.out.println(salesReport.generate());

        System.out.println("\n--- Scenario 2: User Report Exported to PDF ---");
        IReport userReport = new UserReport();
        userReport = new PdfExportDecorator(userReport);
        System.out.println(userReport.generate());

        System.out.println("\n--- Scenario 3: Complex Sales Report with Multi-level Decoration ---");
        IReport complexReport = new SalesReport();
        complexReport = new AmountFilterDecorator(complexReport, 150.0);
        complexReport = new DateFilterDecorator(complexReport, "Last 30 Days");
        complexReport = new CsvExportDecorator(complexReport);
        System.out.println(complexReport.generate());

        System.out.println("\n--- Scenario 4: Dynamic Decoration based on Input ---");
        IReport dynamicReport = createDynamicReport("sales", true, true, "pdf");
        System.out.println(dynamicReport.generate());
    }

    public static IReport createDynamicReport(String type, boolean filter, boolean sort, String exportFormat) {
        IReport report = type.equalsIgnoreCase("sales") ? new SalesReport() : new UserReport();

        if (filter) {
            report = new DateFilterDecorator(report, "Current Month");
        }
        if (sort) {
            report = new SortingDecorator(report, "Date");
        }
        
        if (exportFormat.equalsIgnoreCase("csv")) {
            report = new CsvExportDecorator(report);
        } else if (exportFormat.equalsIgnoreCase("pdf")) {
            report = new PdfExportDecorator(report);
        }

        return report;
    }
}
