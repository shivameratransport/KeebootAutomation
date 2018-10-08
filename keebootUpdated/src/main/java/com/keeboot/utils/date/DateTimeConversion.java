package com.keeboot.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeConversion {

    public static String convert(String date, String fromFormat, String toFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(fromFormat, Locale.ENGLISH);
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return convert(parsedDate, toFormat);
    }

    public static String convert(Date date, String toFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(toFormat, Locale.ENGLISH);
        return dateFormat.format(date);
    }

    /**
     *
     * @param daysOut
     *            - Number of days from current date to get date for
     * @param format
     *            - Desired format of date. Samples are "MM/dd/yyyy", "MMMM dd, yyyy", and "dd-MM-yy"
     * @return String future date in desired format
     */
    public static String getDaysOut(String daysOut, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, Integer.parseInt(daysOut));
        String convertedDate = dateFormat.format(cal.getTime());
        return convertedDate;
    }

    /**
     *
     * @param format
     *            ex: 'MM/dd/yyyy', 'yyyy-MM-dd'
     * @return Formatted date as String
     */
    public String formatDate(String format) {
        return formatDate(format, 0);
    }

    /**
     *
     * @param format
     *            ex: 'MM/dd/yyyy', 'yyyy-MM-dd'
     * @param daysOut
     *            as any Integer
     * @return Formatted date as String with adjusted amount of days
     */
    public String formatDate(String format, String daysOut) {
        return formatDate(format, Integer.parseInt(daysOut));
    }

    /**
     *
     * @param format
     *            ex: 'MM/dd/yyyy', 'yyyy-MM-dd'
     * @param daysOut
     *            as any Integer
     * @return Formatted date as String with adjusted amount of days
     */
    public String formatDate(String format, int daysOut) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, daysOut);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Get the number of days between start & end dates
     *
     * @param format
     *            ex: 'MM/dd/yyyy', 'yyyy-MM-dd'
     * @param startDate
     *            ex: 'MM/dd/yyyy', 'yyyy-MM-dd'
     * @param endDate
     *            ex: 'MM/dd/yyyy', 'yyyy-MM-dd'
     * @return days as int
     */
    public static String getDays(String startDate, String endDate, String format) {
        String diffDays = null;
        try {
            Date periodStartDate = new SimpleDateFormat(format).parse(startDate);
            Date periodEndDate = new SimpleDateFormat(format).parse(endDate);
            long diffTime = periodEndDate.getTime() - periodStartDate.getTime();
            diffDays = String.valueOf((int) (diffTime / (1000 * 60 * 60 * 24)));
        } catch (Exception e) {
            e.printStackTrace();
            // TestReporter.assertFail("Date format mismatch");
        }
        return diffDays;
    }
}
