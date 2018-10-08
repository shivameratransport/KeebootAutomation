package com.keeboot.utils.listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.testng.log4testng.Logger;

public class GenerateHTMLReportSumm {

    private static final Logger L = Logger.getLogger(GenerateHTMLReportSumm.class);

    // ~ Instance fields ------------------------------------------------------

    private PrintWriter out;
    private int row;
    private Integer testIndex;

    // ~ Methods --------------------------------------------------------------

    /** Creates summary for one page url */
    public void generateHtmlReport(String testname, HashMap<String, Integer> violations) {
        initHtmlReport();
        startHtml(out, testname);
        generateViolationSummReport(testname, violations);
        endHtml(out);
        out.flush();
        out.close();
    }

    /** Initiates HTML Report */
    public PrintWriter initHtmlReport() {
        try {
            out = createWriter("./target/");
            return out;
        } catch (IOException e) {
            L.error("output file", e);
            return null;
        }
    }

    protected PrintWriter createWriter(String outdir) throws IOException {

        new File(outdir).mkdirs();
        return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "CustomAxeAccessibilitySummReport.html"))));

    }

    public void generateViolationSummReport(String testname, HashMap<String, Integer> violations) {
        tableStart("testOverview", null);
        startSummaryRow(testname);
        out.print("<tr>");
        // tableColumnStart("Sr#");
        tableColumnStart("Page / Screen / Link", 300);
        tableColumnStart("# Violations", 75);
        out.println("</tr>");

        int qtyViolationsSum = 0;
        testIndex = 1;
        for (Map.Entry<String, Integer> m : violations.entrySet()) {
            // summaryCell(testIndex, Integer.MAX_VALUE);
            startSummaryRow(m.getKey());
            summaryCell(m.getValue(), Integer.MAX_VALUE);
            qtyViolationsSum = qtyViolationsSum + m.getValue();

            out.println("</tr>");
            testIndex++;
        }
        if (testIndex > 2)

        {
            out.println("<tr style=\"background-color:Gray; color: white; class=\"total\"><td><b>Total</b></td>");
            // summaryCell(" ", true);
            summaryCell(qtyViolationsSum, Integer.MAX_VALUE);
            // out.println("<td colspan=\"3\">&nbsp;</td></tr>");
        }
        out.println("</table>");
    }

    private void summaryCell(String v, boolean isgood) {
        out.print("<td class=\"numi" + (isgood ? "" : "_attn") + "\">" + v
                + "</td>");
    }

    private void startSummaryRow(String label) {
        row += 1;
        // out.print("<tr"
        // + (row % 2 == 0 ? " class=\"stripe\"" : "")
        // + "><td style=\"text-align:left;padding-right:2em\"><a \"#t"
        // + testIndex + "\"><b>" + label + "</b></a>" + "</td>");
        out.print("<tr"
                + (row % 2 == 0 ? " class=\"stripe\"" : "")
                + "><td "
                + testIndex + "\"><b>" + label + "</b></a>" + "</td>");
        // [Govind]: Commented below as don't need hyperlink on Test Group Name Label. If needed then uncomment below and comment above.

        /*
         * out.print("<tr"
         * + (row % 2 == 0 ? " class=\"stripe\"" : "")
         * + "><td style=\"text-align:left;padding-right:2em\"><a href=\"#t"
         * + testIndex + "\"><b>" + label + "</b></a>" + "</td>");
         */
    }

    private void summaryCell(int v, int maxexpected) {
        summaryCell(String.valueOf(v), v <= maxexpected);
    }

    private void tableStart(String cssclass, String id) {
        out.println("<table cellspacing=\"0\" cellpadding=\"0\""
                + (cssclass != null ? " class=\"" + cssclass + "\""
                        : " style=\"padding-bottom:2em\"")
                + (id != null ? " id=\"" + id + "\"" : "") + ">");
        row = 0;
    }

    private void tableColumnStart(String label, int cw) {
        out.print("<th col width=" + cw + " style=\"background-color:Gray; color: white;\">" + label + "</th>");
    }

    private void titleRow(String label, int cq) {
        titleRow(label, cq, null);
    }

    private void titleRow(String label, int cq, String id) {
        out.print("<tr");
        if (id != null) {
            out.print(" id=\"" + id + "\"");
        }
        out.println("><th colspan=\"" + cq + "\">" + label + "</th></tr>");
        row = 0;
    }

    /** Starts HTML stream */
    /** Starts HTML stream */
    protected void startHtml(PrintWriter out, String testname) {
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("<head>");
        out.println("<title>Accessibility Test Report</title>");
        out.println("<style type=\"text/css\">");
        out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
        out.println("td,th {border:1px solid #009;padding:.25em .5em}");
        out.println(".result th {vertical-align:bottom}");
        out.println(".param th {padding-left:1em;padding-right:1em}");
        out.println(".param td {padding-left:.5em;padding-right:2em}");
        out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
        out.println(".numi,.numi_attn {text-align:right}");
        out.println(".total td {font-weight:bold}");
        out.println(".passedodd td {background-color: #0A0}");
        out.println(".passedeven td {background-color: #3F3}");
        out.println(".skippedodd td {background-color: #CCC}");
        out.println(".skippedodd td {background-color: #DDD}");
        out.println(".failedodd td,.numi_attn {background-color: Tomato; color: white}");
        out.println(".failedeven td,.stripe .numi_attn {background-color: Orange; color: white}");
        out.println(".totalodd td {background-color: #0A0}");
        out.println(".totaleven td {background-color: #3F3}");
        out.println(".stacktrace {white-space:pre;font-family:monospace}");
        out.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
    }

    /** Finishes HTML stream */
    protected void endHtml(PrintWriter out) {
        out.println("<h3 style=\"background-color:DodgerBlue; color: white\"> This is summary. For details refer attachment. </h3>");
        out.println("</body></html>");
    }

}