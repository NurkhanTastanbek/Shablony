import java.util.*;
import java.io.*;

class ReportStyle {
    public String backgroundColor;
    public String fontColor;
    public int fontSize;

    public ReportStyle(String backgroundColor, String fontColor, int fontSize) {
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
        this.fontSize = fontSize;
    }
}

class Report {
    public String header;
    public String content;
    public String footer;
    public Map<String, String> sections = new LinkedHashMap<>();
    public ReportStyle style;

    public void export(String format) {
        System.out.println("\nExporting report to " + format.toUpperCase() + "...");
        System.out.println("Background: " + style.backgroundColor + ", Font Size: " + style.fontSize);
        System.out.println(header);
        sections.forEach((name, body) -> System.out.println("Section [" + name + "]: " + body));
        System.out.println("Content: " + content);
        System.out.println(footer);
    }
}

interface IReportBuilder {
    void setHeader(String header);
    void setContent(String content);
    void setFooter(String footer);
    void addSection(String sectionName, String sectionContent);
    void setStyle(ReportStyle style);
    Report getReport();
}

class TextReportBuilder implements IReportBuilder {
    private Report report = new Report();

    public void setHeader(String header) { report.header = "TEXT HEADER: " + header; }
    public void setContent(String content) { report.content = content; }
    public void setFooter(String footer) { report.footer = "TEXT FOOTER: " + footer; }
    public void addSection(String name, String body) { report.sections.put(name, body); }
    public void setStyle(ReportStyle style) { report.style = style; }
    public Report getReport() { return report; }
}

class HtmlReportBuilder implements IReportBuilder {
    private Report report = new Report();

    public void setHeader(String header) { report.header = "<h1>" + header + "</h1>"; }
    public void setContent(String content) { report.content = "<p>" + content + "</p>"; }
    public void setFooter(String footer) { report.footer = "<footer>" + footer + "</footer>"; }
    public void addSection(String name, String body) { report.sections.put("<h2>" + name + "</h2>", "<div>" + body + "</div>"); }
    public void setStyle(ReportStyle style) { report.style = style; }
    public Report getReport() { return report; }
}

class JsonReportBuilder implements IReportBuilder {
    private Report report = new Report();

    public void setHeader(String header) { report.header = "\"header\": \"" + header + "\""; }
    public void setContent(String content) { report.content = "\"content\": \"" + content + "\""; }
    public void setFooter(String footer) { report.footer = "\"footer\": \"" + footer + "\""; }
    public void addSection(String name, String body) { report.sections.put(name, body); }
    public void setStyle(ReportStyle style) { report.style = style; }
    public Report getReport() { 
        report.header = "{\n  " + report.header;
        report.footer = "  " + report.footer + "\n}";
        return report; 
    }
}

class ReportDirector {
    public void constructReport(IReportBuilder builder, ReportStyle style) {
        builder.setStyle(style);
        builder.setHeader("Annual Business Report");
        builder.addSection("Introduction", "This is the start of the document.");
        builder.addSection("Market Analysis", "The market is growing rapidly.");
        builder.setContent("Main analysis data is provided here.");
        builder.setFooter("Confidential - 2026");
    }
}

public class Main {
    public static void main(String[] args) {
        ReportDirector director = new ReportDirector();
        ReportStyle modernStyle = new ReportStyle("White", "Black", 12);

        IReportBuilder textBuilder = new TextReportBuilder();
        director.constructReport(textBuilder, modernStyle);
        Report textReport = textBuilder.getReport();
        textReport.export("text");

        IReportBuilder htmlBuilder = new HtmlReportBuilder();
        director.constructReport(htmlBuilder, new ReportStyle("Blue", "White", 14));
        Report htmlReport = htmlBuilder.getReport();
        htmlReport.export("html");

        IReportBuilder jsonBuilder = new JsonReportBuilder();
        director.constructReport(jsonBuilder, modernStyle);
        Report jsonReport = jsonBuilder.getReport();
        jsonReport.export("json");
    }
}
