package com.keeboot.utils.listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.IInvokedMethod;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.internal.Utils;
import org.testng.log4testng.Logger;

public class GenerateHTMLReport1 {

    private static final Logger L = Logger.getLogger(GenerateHTMLReport.class);

    // ~ Instance fields ------------------------------------------------------

    private PrintWriter out;
    private int row;
    private Integer testIndex;
    private int methodIndex;
    private Scanner scanner;

    // ~ Methods --------------------------------------------------------------

    /** Creates summary for one page url 
     * @throws JSONException */
    public void generateHtmlReport(String testname, JSONArray violations, String url) throws JSONException {
        initHtmlReport();
        startHtml(out, testname);
        generateViolationDetailReport(testname, violations, url);
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
        /*
         * File eReport=new File(outdir, "emailable-report.html" );
         * System.out.println(eReport.toPath());
         * System.out.println("eReport.exists()  "+eReport.exists());
         * if(eReport.exists()){
         * eReport.delete();
         * System.out.println("Deleted");
         * }
         */
        return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "CustomAxeAccessibilityReport.html"))));

    }

    /** Starts and defines columns result summary table */
    private void startResultSummaryTable(String style) {
        tableStart(style, "summary");
        out.println("<tr><th>Class</th>"
                + "<th>Method</th><th>Exception & screenshot</th><th>Start Time </th><th>Time<br/>(hh:mm:ss)</th></tr>");
        row = 0;
    }

    protected void generateExceptionReport(Throwable exception,
            ITestNGMethod method) {
        out.print("<div class=\"stacktrace\">");
        out.print(Utils.stackTrace(exception, true)[0]);
        out.println("</div>");
    }

    public void generateViolationDetailReport(String testname, JSONArray violations, String url) throws JSONException {
        titlePageUrl(url, 10);

        out.print("<tr>");
        tableColumnStart("Sr #");
        tableColumnStart("Violation");
        tableColumnStart("Description");
        tableColumnStart("Impact");
        tableColumnStart("Help URL");
        tableColumnStart("Tags");
        tableColumnStart("Solution");
        out.println("</tr>");

        testIndex = 1;

        for (int i = 0; i < violations.length(); i++) {

            JSONObject rec = violations.getJSONObject(i);

            String help = rec.getString("help");
            String helpUrl = rec.getString("helpUrl");
            String impact = rec.getString("impact");
            JSONArray tags = rec.getJSONArray("tags");
            String description = rec.getString("description");
            JSONArray nodes = (JSONArray) rec.get("nodes");

            String message = null;
            List<String> fullMessage = new ArrayList<String>();
            JSONObject firstSport = nodes.getJSONObject(0);
            JSONArray anyArray = firstSport.getJSONArray("any");
            for (int k = 0; k < anyArray.length(); k++) {
                JSONObject mess = anyArray.getJSONObject(k);
                message = mess.getString("message");
                fullMessage.add(message);
            }
            JSONArray mtarget;
            List<String> finalloc = new ArrayList<String>();
            // getting target
            for (int m = 0; m < nodes.length(); m++) {
                JSONObject t = nodes.getJSONObject(m);
                mtarget = t.getJSONArray("target");
                finalloc.add(mtarget.toString());
            }

            out.print("<tr>");

            summaryCell((i + 1), Integer.MAX_VALUE);
            summaryCellString(help);
            summaryCellString(description);
            summaryCellString(impact);
            // summaryCell(helpUrl, true);
            out.print("<td style=\"text-align:left;padding-right:2em\"><a href=\"" + helpUrl + "\"><b> Help Link </b></a>" + "</td>");
            summaryCellString(tags.toString());
            summaryCellString(fullMessage.toString());

            out.println("</tr>");
            testIndex++;
        }
        out.println("</table>");
    }

    @SuppressWarnings("unused")
    private void summaryCell(String[] val) {
        StringBuffer b = new StringBuffer();
        for (String v : val) {
            b.append(v + " ");
        }
        summaryCell(b.toString(), true);
    }

    public void generateViolationDetailReport2(String testname, JSONArray violations, String url) throws JSONException {
        titlePageUrl(url, 10);

        out.print("<tr>");
        out.print("<th style=\"background-color:Gray; color: white\">Page / Screen / Url</th>");
        out.print("<th colspan=\"10\" style=\"background-color:Gray; color: white\">Violation Details</th>");
        out.println("</tr>");

        testIndex = 1;

        for (int i = 0; i < violations.length(); i++) {

            JSONObject rec = violations.getJSONObject(i);

            String help = rec.getString("help");
            String helpUrl = rec.getString("helpUrl");
            String impact = rec.getString("impact");
            JSONArray tags = rec.getJSONArray("tags");
            String description = rec.getString("description");
            JSONArray nodes = (JSONArray) rec.get("nodes");

            String message = null;
            List<String> fullMessage = new ArrayList<String>();
            JSONObject firstSport = nodes.getJSONObject(0);
            JSONArray anyArray = firstSport.getJSONArray("any");
            for (int k = 0; k < anyArray.length(); k++) {
                JSONObject mess = anyArray.getJSONObject(k);
                message = mess.getString("message");
                fullMessage.add(message);
            }
            JSONArray mtarget;
            List<String> finalloc = new ArrayList<String>();
            // getting target
            for (int m = 0; m < nodes.length(); m++) {
                JSONObject t = nodes.getJSONObject(m);
                mtarget = t.getJSONArray("target");
                finalloc.add(mtarget.toString());
            }

            out.print("<tr>");
            summaryCell((i + 1), Integer.MAX_VALUE);
            summaryCellString(url);

            out.print("<table>");
            out.print("<tr>");
            summaryCellString("Violation");
            summaryCellString(help);
            out.print("</tr>");
            out.print("<tr>");
            summaryCellString("Description");
            summaryCellString(description);
            out.print("</tr>");
            out.print("<tr>");
            summaryCellString("Impact");
            summaryCellString(impact);
            out.print("</tr>");

            out.print("<tr>");
            summaryCellString("Help Link");
            out.print("<td style=\"text-align:left;padding-right:2em\"><a href=\"" + helpUrl + "\"><b>" + helpUrl + "</b></a>" + "</td>");
            out.print("</tr>");

            out.print("<tr>");
            summaryCellString("Tags");
            summaryCellString(tags.toString());
            out.print("</tr>");

            out.print("<tr>");
            summaryCellString("Solution");
            summaryCellString(fullMessage.toString());
            out.print("</tr>");

            testIndex++;
        }
        out.println("</tr>");
        out.println("</table>");
    }

    private void summaryCell(String v, boolean isgood) {
        out.print("<td class=\"numi" + (isgood ? "" : "_attn") + "\">" + v
                + "</td>");
    }

    private void summaryCellString(String v) {
        out.print("<td style=\"text-align:left;padding-right:2em\">" + v
                + "</td>");
    }

    private void startSummaryRow(String label) {
        row += 1;

        out.print("<tr"
                + (row % 2 == 0 ? " class=\"stripe\"" : "")
                + "><td style=\"text-align:left;padding-right:2em\"><a href=\"#t"
                + testIndex + "\"><b>" + label + "</b></a>" + "</td>");

    }

    private void titlePageUrl(String url, int cq) {
        row += 1;

        out.println("><th colspan=\"" + cq + "\"><a href=\""
                + url + "\"><b>" + url + "</b></a></th></tr>");
        row = 0;
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

    private void tableColumnStart(String label) {
        out.print("<th style=\"background-color:Gray; color: white\">" + label + "</th>");
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
    public void startHtml(PrintWriter out, String testname) {
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("<head>");
        out.println("<title>MGM Automated Test Execution Report</title>");
        out.println("<style type=\"text/css\">");
        out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
        out.println("td,th {border:1px solid #009;padding:.25em .5em}");
        out.println(".result th {vertical-align:bottom}");
        out.println(".param th {padding-left:1em;padding-right:1em}");
        out.println(".param td {padding-left:.5em;padding-right:2em}");
        out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
        out.println(".numi,.numi_attn {text-align:right}");
        out.println(".total td {font-weight:bold; background-color:Gray; color: white}");
        out.println(".passedodd td {background-color: #0A0}");
        out.println(".passedeven td {background-color: #3F3}");
        out.println(".skippedodd td {background-color: #CCC}");
        out.println(".skippedeven td {background-color: #DDD}");
        out.println(".failedodd td,.numi_attn {background-color: rgb(255, 255, 255)}");
        out.println(".failedeven td,.stripe .numi_attn {background-color: rgb(240, 240, 240)}");
        // out.println(".failedodd td,.numi_attn {background-color: Tomato;color: white}");
        // out.println(".failedeven td,.stripe .numi_attn {background-color: Orange;color:white}");
        out.println(".stacktrace {white-space:pre;font-family:monospace}");
        out.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        tableStart("testOverview", null);
        titleRow(testname, 10);
    }

    /** Finishes HTML stream */
    public void endHtml(PrintWriter out) {
        out.println("<center> Custom Report with Screenshots. </center>");
        out.println("</body></html>");
    }

    // ~ Inner Classes --------------------------------------------------------
    /** Arranges methods by classname and method name */
    private class TestSorter implements Comparator<IInvokedMethod> {
        // ~ Methods
        // -------------------------------------------------------------

        /** Arranges methods by classname and method name */
        @Override
        public int compare(IInvokedMethod o1, IInvokedMethod o2) {
            // System.out.println("Comparing " + ((ITestNGMethod) o1).getMethodName() + " " + o1.getDate() + " and " + ((ITestNGMethod) o2).getMethodName() + "
            // " + o2.getDate());
            // return (int) (o1.getDate() - o2.getDate());
            // System.out.println("First method class name "+o1.getTestMethod().getTestClass().getName());
            // System.out.println("second method class name "+o2.getTestMethod().getTestClass().getName());
            int r = o1.getTestMethod().getTestClass().getName().compareTo(o2.getTestMethod().getTestClass().getName());
            // System.out.println("class name compare "+ r);
            if (r == 0) {
                // System.out.println("First method name "+o1.getTestMethod());
                // System.out.println("second method name "+o2.getTestMethod());
               // r = o1.getTestMethod().compareTo(o2.getTestMethod());

            }
            return r;
        }

    }

    private class TestMethodSorter implements Comparator<ITestNGMethod> {
        @Override
        public int compare(ITestNGMethod o1, ITestNGMethod o2) {
            // return (int) (o1.getDate() - o2.getDate());
            // System.out.println("First method class name "+o1.getTestClass().getName());
            // System.out.println("second method class name "+o2.getTestClass().getName());
            int r = o1.getTestClass().getName().compareTo(o2.getTestClass().getName());
            // System.out.println("class name compare "+ r);
            if (r == 0) {
                // System.out.println("First method name "+o1.getMethodName());
                // System.out.println("second method name "+o2.getMethodName());
                r = o1.getMethodName().compareTo(o2.getMethodName());
            }
            return r;
        }
    }

    private class TestResultsSorter implements Comparator<ITestResult> {
        @Override
        public int compare(ITestResult o1, ITestResult o2) {
            // return (int) (o1.getMethod().getDate() - o2.getMethod().getDate());
            // System.out.println("First method class name "+o1.getTestClass().getName());
            // System.out.println("second method class name "+o2.getTestClass().getName());
            int result = o1.getTestClass().getName().compareTo(o2.getTestClass().getName());
            if (result == 0) {
                // System.out.println("First method name "+o1.getMethod().getMethodName());
                // System.out.println("second method name "+o2.getMethod().getMethodName());
                result = o1.getMethod().getMethodName().compareTo(o2.getMethod().getMethodName());
            }
            return result;
        }
    }

}