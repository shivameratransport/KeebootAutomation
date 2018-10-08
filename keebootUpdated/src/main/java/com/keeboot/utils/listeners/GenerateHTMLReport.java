package com.keeboot.utils.listeners;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GenerateHTMLReport {

    FileWriter out;
    BufferedWriter writer;
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
    Date date = new Date();

    public FileWriter initHTMLReport() {
        try {
            // out = new FileWriter("./AllResults/AxeResults/" +
            // dateFormat.format(date) + "_Report.html");
            out = new FileWriter("./target/CustomAxeAccessibilityReport.html");

            return out;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void startHTML(FileWriter out, String testname) throws IOException {
        System.out.println("FunctionName : 'CreateHtmlReport'");
        writer = new BufferedWriter(out);

        // HTML Header
        writer.write("<html> <head>	<title>" + testname + "</title></head>");
        // Report Title
        writer.write(
                "<body style='Font-Family: sherif'><table width=100%><th><font color=BLUE size=5 align='center'><h4><br>Accessiblity Automated Test Result"
                        + "</font></th></table>" + "</h4>");
        writer.write(
                "<table border=0  cellpadding=0 cellspacing=0 width=1186 style='border-collapse: collapse;table-layout:fixed;width:889pt'>"
                        + " <col width=31 style='mso-width-source:userset;mso-width-alt:1070;width:23pt'> "
                        + "<col width=412 style='mso-width-source:userset;mso-width-alt:14382;width:309pt'>"
                        + " <col width=116 style='mso-width-source:userset;mso-width-alt:4049;width:87pt'>"
                        + " <col width=627 style='mso-width-source:userset;mso-width-alt:21876;width:470pt'> "
                        + "<tr height=25 style='height:18.5pt'>  <td colspan=4 height=25 align='center' class=xl75 width=1186 style='height:18.5pt; width:889pt'><font color=BLUE size=4><h4>TestSuite : "
                        + testname + "</h4></td></tr>");

        // writer.write("<table border=1 cellpadding='0' cellspacing='0'
        // style='border-collapse:collapse/;table-layout:auto;width:889pt'>");
        // writer.write("<tr height=19 style='height:14.5pt'>"
        // + "<th height=19 class=xl67 width=31
        // style='height:14.5pt;width:23pt'>Sr #</th>");
        // writer.write("<th class=xl68 width=412
        // style='border-left:none;width:309pt'>Page / Screen / URL</th>"
        // + " <th colspan=2 class=xl73 style='border-left:none'>Violation
        // Details</th>"
        // + " </tr> <tr height=39
        // style='mso-height-source:userset;height:29.25pt'>");

    }

    public void generateViolationSummReport(HashMap<String, Integer> hmSummary) throws IOException {
        // TODO Auto-generated method stub

        writer.write(
                "<table border=1 cellpadding='0' cellspacing='0' style='border-collapse:collapse/;table-layout:auto;width:800pt'>"
                        + "<th class=xl73 style='border-left:none;width:80pt;background-color:#008080'>Violation Sr.</th>"
                        + "<th class=xl73 style='border-left:none;width:620pt;background-color:#008080'> Page / URL / Screen </th>"
                        + "<th class=xl73 style='border-left:none;width:100pt;background-color:#008080'> Number of Violations </th></tr>");

        int qtyViolationsSum = 0;
        int testIndex = 0;
        for (Map.Entry<String, Integer> m : hmSummary.entrySet()) {
            String url = m.getKey();
            int violationCount = m.getValue();
            writer.write("<tr><td class=xl73 align='center' style='border-left:none;width:80pt;font-size:20px'>"
                    + (testIndex + 1) + "</td>");
            writer.write("<td class=xl73 style='border-left:none;width:620pt;font-size:20px'><a href=#" + url + " > "
                    + url + " </a></td>");
            writer.write("<td class=xl73 align='center' style='border-left:none;width:100pt;font-size:20px'> "
                    + violationCount + " </td></tr>");

            qtyViolationsSum = qtyViolationsSum + m.getValue();
            testIndex++;
        }
        writer.write(
                "<tr><td colspan=2 class=xl73 align='center' style='border-left:none;width:620pt;font-size:20px;background-color:#00CC99'> Total Number of Violations </td>");
        writer.write("<td class=xl73 style='border-left:none;width:100pt;font-size:20px;background-color:#00CC99'> "
                + qtyViolationsSum + " </td></tr>");

    }

    public void generateDetailViolationReport(String testname, JSONArray violations, String url) throws IOException, JSONException {

        writer.write(
                "<table border=1 cellpadding='0' cellspacing='0' style='border-collapse:collapse/;table-layout:auto;width:1000pt'>");
        /*
         * writer.write("<tr height=19 style='height:14.5pt'>" +
         * "<th height=19 class=xl67 width=31 style='height:14.5pt;width:23pt;background-color:#00CC99'>Sr #</th>"
         * );
         */
        writer.write(
                "<th colspan=4 class=xl73 style='border-left:none;width:1000pt;background-color:#008080'>Violation Details - Number of Violations in the page : "
                        + violations.length() + "</th></tr>");

        for (int i = 0; i < violations.length(); i++) {

            JSONObject rec = violations.getJSONObject(i);
            String issue = rec.getString("help").replace("<", "(").replace(">", ")");
            String helpUrl = rec.getString("helpUrl");
            String impact = rec.getString("impact");
            JSONArray tags = rec.getJSONArray("tags");
            String description = rec.getString("description").replace("<", "(").replace(">", ")");
            JSONArray nodes = rec.getJSONArray("nodes");

            // int rowspan = nodes.length() + 6;

            /*
             * writer.
             * write("<tr height=39 style='mso-height-source:userset;height:29.25pt'> <td rowspan="
             * + rowspan +
             * " height=234 class=xl72 width=31 style='height:175.5pt;  border-top:none;border-left:none;width:23pt'> "
             * + (i + 1) + " </td>");
             */
            writer.write("<tr height=39 style='mso-height-source:userset;height:29.25pt'>");
            writer.write(
                    "<td class=xl69 align='center' width=116 style='border-top:none;border-left:none;width:93pt;background-color:#00CC99'><b> Issue "
                            + (i + 1) + ":</b></td> ");
            writer.write(
                    "<td colspan=3 class=xl69 width=627 style='border-top:none;border-left:none;width:907pt;background-color:#00CC99'>"
                            + issue + "</td> </tr>");
            writer.write("<tr height=39 style='mso-height-source:userset;height:29.25pt'>"
                    + " <td height=39 class=xl69 align='center' width=116 style='height:29.25pt;border-top:none;  border-left:none;width:93pt'><b> Description:</b></td>");
            writer.write(
                    "<td colspan=3 class=xl69 width=627 style='border-top:none;border-left:none;word-wrap:break-word;width:907pt'>"
                            + description + "</td> </tr>");
            writer.write("<tr height=39 style='mso-height-source:userset;height:29.25pt'>"
                    + " <td height=39 align='center' class=xl69 width=116 style='height:29.25pt;border-top:none;  border-left:none;width:93pt'><b> Impact:</b></td>");
            writer.write(
                    "<td colspan=3 class=xl66 width=627 style='border-top:none;border-left:none;width:907pt;;color:red;'>"
                            + impact + "</td></tr>");
            writer.write("<tr height=39 style='mso-height-source:userset;height:29.25pt'> "
                    + " <td height=39 align='center' class=xl69 width=116 style='height:29.25pt;border-top:none;  border-left:none;width:93pt'><b> Help Link:</b></td>");
            writer.write(
                    " <td colspan=3 class=xl70 width=627 style='border-top:none;border-left:none; word-wrap:break-word;width:907pt'><a  href="
                            + helpUrl + "  target='_parent'><span style='font-weight:700'>" + helpUrl
                            + "</span></a></td>    </tr>");
            writer.write(" <tr height=39 style='mso-height-source:userset;height:29.25pt'>"
                    + "  <td height=39 align='center' class=xl69 width=116 style='height:29.25pt;border-top:none;  border-left:none;width:93pt'><b> Tags:</b></td>");
            writer.write(
                    "<td colspan=3 class=xl69 width=627 style='border-top:none;border-left:none; word-wrap:break-word;width:907pt'>"
                            + tags + "</td> </tr> ");
            writer.write("<tr height=39 style='mso-height-source:userset;height:29.25pt'> "
                    + "<th class=xl69 width=627 style='border-top:none;border-left:none;word-wrap:break-word;width:93pt'> Node Sr# </th>");
            writer.write(
                    "<th class=xl69 width=627 style='border-top:none;border-left:none;word-wrap:break-word;width:250pt'> Source </th>");
            writer.write(
                    "<th class=xl69 width=627 style='border-top:none;border-left:none;word-wrap:break-word;width:250pt'> Location </th>");
            writer.write(
                    "<th class=xl69 width=627 style='border-top:none;border-left:none;word-wrap:break-word;width:407pt'> Soutions </th> </tr>");
            for (int j = 0; j < nodes.length(); j++) {
                StringBuilder solutions = new StringBuilder();
                String source = nodes.getJSONObject(j).getString("html").replace("<", "").replace(">", "").replace("&",
                        " &");
                String location = nodes.getJSONObject(j).getJSONArray("target").getString(0).replace(".", " .");
                writer.write("<tr height=39 style='mso-height-source:userset;height:29.25pt'>"
                        + "<th class=xl69 width=627 style='border-top:none;border-left:none;word-wrap:break-word;width:93pt'>"
                        + (j + 1) + "</th>");
                writer.write(
                        "<td class=xl69 width=627 style='border-top:none;border-left:none;word-wrap:break-word;width:250pt'>"
                                + source + "</td>");
                writer.write(
                        "<td class=xl69 width=627 style='border-top:none;border-left:none;word-wrap:break-word;width:250pt'>"
                                + location + "</td>");
                JSONArray anyArray = nodes.getJSONObject(j).getJSONArray("any");
                for (int k = 0; k < anyArray.length(); k++) {
                    String message = anyArray.getJSONObject(k).getString("message");
                    solutions.append("<br> " + (k + 1) + ": " + message);
                }
                writer.write(
                        "		 <td class=xl69 width=627 style='border-top:none;border-left:none;word-wrap:break-word;width:407pt'>"
                                + solutions + "</td></tr>");
                solutions = null;

            }
        }

        writer.write("<p  style='font-size:15px;'>Best Viewable in Chrome</p>");
        writer.write("<p id=" + url + " style='font-size:20px;'>Page URL: " + url + "</p>");

    }

    public void endHTML() throws IOException {
        // TODO Auto-generated method stub
        writer.flush();
        writer.close();
    }

    public void htmlReport(String testname, JSONArray violations, String url) throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        Date date = new Date();

        System.out.println("FunctionName : 'CreateHtmlReport'");
        // Creating object to write in HTML report file
        FileWriter out = new FileWriter(
                "./AllResults/AxeResults/" + dateFormat.format(date) + "_" + testname + ".html");
        BufferedWriter writer = new BufferedWriter(out);

        // HTML Header
        writer.write("<html> <head>	<title>" + testname + "</title></head>");
        // Report Title
        writer.write(
                "<body style='Font-Family: sherif'><table width=100%><th><font color=BLUE size=5 align='center'><h4><br>Accessiblity Automated Test Result"
                        + "</font></th></table>" + "</h4>");
        writer.write(
                "<table border=0  cellpadding=0 cellspacing=0 width=1186 style='border-collapse: collapse;table-layout:fixed;width:889pt'>"
                        + " <col width=31 style='mso-width-source:userset;mso-width-alt:1070;width:23pt'> "
                        + "<col width=412 style='mso-width-source:userset;mso-width-alt:14382;width:309pt'>"
                        + " <col width=116 style='mso-width-source:userset;mso-width-alt:4049;width:87pt'>"
                        + " <col width=627 style='mso-width-source:userset;mso-width-alt:21876;width:470pt'> "
                        + "<tr height=25 style='height:18.5pt'>  <td colspan=4 height=25 align='center' class=xl75 width=1186 style='height:18.5pt; width:889pt'><font color=BLUE size=4><h4>TestSuite : "
                        + testname + "</h4></td></tr>");

        int violationCount = 0;

        writer.write(
                "<table border=1 cellpadding='0' cellspacing='0' style='border-collapse:collapse/;table-layout:auto;width:889pt'>");
        writer.write("<tr height=19 style='height:14.5pt'>"
                + "<th height=19 class=xl67 width=31 style='height:14.5pt;width:23pt'>Sr #</th>");
        writer.write("<th class=xl68 width=412 style='border-left:none;width:309pt'>Page / Screen /  URL</th>"
                + "  <th colspan=2 class=xl73 style='border-left:none'>Violation Details</th>"
                + " </tr> <tr height=39 style='mso-height-source:userset;height:29.25pt'>");

        for (int i = 0; i < violations.length(); i++) {
            violationCount++;
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

            writer.write(
                    "<td rowspan=6  height=234 class=xl72 width=31 style='height:175.5pt;  border-top:none;border-left:none;width:23pt'>"
                            + (i + 1) + "</td>");
            writer.write(
                    "<td rowspan=6 class=xl74 width=412 style='border-top:none;border-left:none;word-wrap:break-word;width:309pt'>"
                            + "<a  href=" + url + " target='_parent'>" + url + "</a></td>");
            writer.write("<td class=xl69 align='center' width=116 style='border-top:none;border-left:none;width:87pt'>"
                    + " Violation </td>");
            writer.write("<td class=xl69 width=627 style='border-top:none;border-left:none;width:470pt'>" + help
                    + "</td> </tr>");
            writer.write("<tr height=39 style='mso-height-source:userset;height:29.25pt'>"
                    + " <td height=39 class=xl69 align='center' width=116 style='height:29.25pt;border-top:none;  border-left:none;width:87pt'>"
                    + "Description</td>");
            writer.write(
                    "<td class=xl69 width=627 style='border-top:none;border-left:none;word-wrap:break-word;width:470pt'>"
                            + description + "</td></tr>");
            writer.write("<tr height=39 style='mso-height-source:userset;height:29.25pt'>"
                    + " <td height=39 align='center' class=xl69 width=116 style='height:29.25pt;border-top:none;  "
                    + "border-left:none;width:87pt'>Impact</td>");
            writer.write("  <td class=xl66 width=627 style='border-top:none;border-left:none;width:470pt;;color:red;'>"
                    + impact + "</td> </tr>");
            writer.write("<tr height=39 style='mso-height-source:userset;height:29.25pt'> "
                    + " <td height=39 align='center' class=xl69 width=116 style='height:29.25pt;border-top:none; "
                    + " border-left:none;width:87pt'>Help Link</td>");
            writer.write(
                    " <td class=xl70 width=627 style='border-top:none;border-left:none; word-wrap:break-word;width:470pt'>"
                            + "<a  href=" + helpUrl + "  target='_parent'><span style='font-weight:700'>" + helpUrl
                            + "</span></a></td></tr>");
            writer.write(" <tr height=39 style='mso-height-source:userset;height:29.25pt'>"
                    + "  <td height=39 align='center' class=xl69 width=116 style='height:29.25pt;border-top:none;  border-left:none;width:87pt'>"
                    + "Tags</td> ");
            writer.write(
                    "<td class=xl69 width=627 style='border-top:none;border-left:none; word-wrap:break-word;width:470pt'>"
                            + tags + "</td> </tr> ");
            writer.write("<tr height=39 style='mso-height-source:userset;height:29.25pt'>  "
                    + "<td height=39 align='center' class=xl69 width=116 style='height:29.25pt;border-top:none;  border-left:none;width:87pt'>"
                    + "Solution</td> ");
            writer.write(
                    "<td class=xl69 width=627 style='border-top:none;border-left:none;word-wrap:break-word;width:470pt'>"
                            + message + "</td> </tr>");
        }
        writer.write("<p  style='font-size:30px;'>Total violation Count: " + violationCount + "</p>");

        writer.flush();
        writer.close();

    }

}