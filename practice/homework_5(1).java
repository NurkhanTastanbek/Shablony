import java.util.ArrayList;
import java.util.List;

class Report {
    private String header;
    private String content;
    private String footer;
    private String style;

    public void setHeader(String header) { this.header = header; }
    public void setContent(String content) { this.content = content; }
    public void setFooter(String footer) { this.footer = footer; }
    public void setStyle(String style) { this.style = style; }

    public void updateContent(String newContent) { this.content = newContent; }

    @Override
    public String toString() {
        return "--- Report Result ---\n" +
               (style != null ? "Style: " + style + "\n" : "") +
               "Header: " + header + "\n" +
               "Content: " + content + "\n" +
               "Footer: " + footer + "\n--------------------";
    }
}

interface IReportBuilder {
    void setHeader(String header);
    void setContent(String content);
    void setFooter(String footer);
    void setFormatting(String style);
    Report getReport();
}

class TextReportBuilder implements IReportBuilder {
    private Report report = new Report();

    public void setHeader(String header) { report.setHeader("[TEXT HEADER]: " + header); }
    public void setContent(String content) { report.setContent(content); }
    public void setFooter(String footer) { report.setFooter("[TEXT FOOTER]: " + footer); }
    public void setFormatting(String style) { report.setStyle("Plain Text (" + style + ")"); }
    public Report getReport() { return report; }
}

class HtmlReportBuilder implements IReportBuilder {
    private Report report = new Report();

    public void setHeader(String header) { report.setHeader("<h1>" + header + "</h1>"); }
    public void setContent(String content) { report.setContent("<p>" + content + "</p>"); }
    public void setFooter(String footer) { report.setFooter("<footer>" + footer + "</footer>"); }
    public void setFormatting(String style) { report.setStyle("CSS Style: " + style); }
    public Report getReport() { return report; }
}

class XmlReportBuilder implements IReportBuilder {
    private Report report = new Report();

    public void setHeader(String header) { report.setHeader("<header>" + header + "</header>"); }
    public void setContent(String content) { report.setContent("<content>" + content + "</content>"); }
    public void setFooter(String footer) { report.setFooter("<footer>" + footer + "</footer>"); }
    public void setFormatting(String style) { report.setStyle("XML Schema: " + style); }
    public Report getReport() { return report; }
}

class ReportDirector {
    public void constructReport(IReportBuilder builder, String h, String c, String f, String s) {
        builder.setHeader(h);
        builder.setContent(c);
        builder.setFooter(f);
        builder.setFormatting(s);
    }
}

public class Main {
    public static void main(String[] args) {
        ReportDirector director = new ReportDirector();

        IReportBuilder textBuilder = new TextReportBuilder();
        director.constructReport(textBuilder, "Monthly Sales", "Total: $5000", "End of Document", "Bold");
        Report textReport = textBuilder.getReport();
        System.out.println(textReport);

        IReportBuilder htmlBuilder = new HtmlReportBuilder();
        director.constructReport(htmlBuilder, "Web Analytics", "Users: 1500", "2024 (c) All Rights Reserved", "Dark Mode");
        Report htmlReport = htmlBuilder.getReport();
        System.out.println(htmlReport);

        IReportBuilder xmlBuilder = new XmlReportBuilder();
        director.constructReport(xmlBuilder, "Data Export", "Items: 45", "ID: 99823", "Standard");
        Report xmlReport = xmlBuilder.getReport();
        System.out.println(xmlReport);

        System.out.println("\nDynamic Content Update on Text Report:");
        textReport.updateContent("Updated Total: $7500");
        System.out.println(textReport);
    }
}
