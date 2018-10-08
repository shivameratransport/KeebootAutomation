package com.keeboot.utils.dataProviders;

import static com.keeboot.utils.TestReporter.logTrace;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.keeboot.AutomationException;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.exception.DataProviderInputFileNotFound;
import com.keeboot.utils.io.FileLoader;

public class CSVDataProvider {
    private static String delimiter = ",";

    /**
     * This gets the test data from a csv file. It returns all the data
     * as a 2d array
     *
     * @param filePath
     *            the file path of the CSV file
     * @return 2d array of test data
     */
    public static Object[][] getData(String filePath) {
        logTrace("Entering CSVDataProvider#getData");
        String line = "";
        String[][] dataArray = null;
        List<String> csvRowList = new ArrayList<String>();
        String[] rowSplit;
        int columnCount = 0;
        int rowCount = 0;

        // Get the file location from the project main/resources folder
        if (!(filePath.contains(":") || filePath.startsWith("/"))) {
            URL file = CSVDataProvider.class.getResource(filePath);
            if (file == null) {
                throw new DataProviderInputFileNotFound("No file was found on path [ " + filePath + " ]");
            }
            filePath = file.getPath();
        }

        // in case file path has a %20 for a whitespace, replace with actual
        // whitespace
        filePath = filePath.replace("%20", " ");

        logTrace("File path of CSV to open [ " + filePath + " ]");
        // open the CSV and add each line into a list
        try (BufferedReader bufferedReader = FileLoader.openTextFileFromProject(filePath)) {
            logTrace("Successfully loaded FileReader object into BufferedReader");

            logTrace("Read in file and load each line into a List");
            while ((line = bufferedReader.readLine()) != null) {
                csvRowList.add(line);
            }
            logTrace("Successfully read in [ " + csvRowList.size() + " ] lines from file");
        } catch (IOException e) {
            throw new AutomationException("Failed to read in CSV file", e);
        }

        // Remove first line of headers
        csvRowList.remove(0);

        logTrace("Determining column count based on delimiter [ " + delimiter + " ]");
        columnCount = csvRowList.get(0).split(delimiter).length;
        logTrace("Found [ " + (columnCount + 1) + " ] columns");

        logTrace("Determining row count");
        rowCount = csvRowList.size();
        logTrace("Found [ " + (rowCount + 1) + " ] rows");

        logTrace("Attempting to create an array based on rows and columns. Array to built [" + (rowCount + 1) + "][" + (columnCount) + "]");
        dataArray = new String[rowCount][columnCount];

        // transform the list into 2d array
        // start at row 1 since, first row 0 is column headings
        logTrace("Transferring data to Array");
        for (int rowNum = 0; rowNum <= rowCount - 1; rowNum++) {

            // take the row which is a string, and split it
            rowSplit = csvRowList.get(rowNum).split(delimiter);

            for (int colNum = 0; colNum < columnCount; colNum++) {
                try {
                    dataArray[rowNum][colNum] = rowSplit[colNum];
                } catch (ArrayIndexOutOfBoundsException e) {
                    dataArray[rowNum][colNum] = "";
                }
            }
        }

        logTrace("Exiting CSVDataProvider#getData");
        return dataArray;
    }

    public static Object[][] getData(String filePath, String delimiterValue) {
        logTrace("Overriding default delimiter of [ , ] to be [ " + delimiter + " ]");
        delimiter = delimiterValue;
        return getData(filePath);
    }

    /**
     * This gets MLife user data from a csv file. It returns all the data as an array.
     *
     * @param userID
     *            the MLife userID of the MLife user
     * @return array of test data
     */
    public static String[] getMLifeUser(String userID) {
        logTrace("Entering CSVDataProvider#getMLifeUser");
        String filePath = "/datasheets/common/MLifeUsers.csv";
        String line = "";
        List<String> csvRowList = new ArrayList<String>();
        String[] rowSplit;
        String[] mlifeUser = null;
        int columnCount = 0;
        int rowCount = 0;

        // Get the file location from the project main/resources folder
        if (!(filePath.contains(":") || filePath.startsWith("/"))) {
            URL file = CSVDataProvider.class.getResource(filePath);
            if (file == null) {
                throw new DataProviderInputFileNotFound("No file was found on path [ " + filePath + " ]");
            }
            filePath = file.getPath();
        }

        // in case file path has a %20 for a whitespace, replace with actual
        // whitespace
        filePath = filePath.replace("%20", " ");

        logTrace("File path of CSV to open [ " + filePath + " ]");
        // open the CSV and add each line into a list
        try (BufferedReader bufferedReader = FileLoader.openTextFileFromProject(filePath)) {
            logTrace("Successfully loaded FileReader object into BufferedReader");

            logTrace("Read in file and load each line into a List");
            while ((line = bufferedReader.readLine()) != null) {
                csvRowList.add(line);
            }
            logTrace("Successfully read in [ " + csvRowList.size() + " ] lines from file");
        } catch (IOException e) {
            throw new AutomationException("Failed to read in CSV file", e);
        }

        // Remove first line of headers
        csvRowList.remove(0);

        logTrace("Determining column count based on delimiter [ " + delimiter + " ]");
        columnCount = csvRowList.get(0).split(delimiter).length;
        logTrace("Found [ " + (columnCount + 1) + " ] columns");

        logTrace("Determining row count");
        rowCount = csvRowList.size();
        logTrace("Found [ " + (rowCount + 1) + " ] rows");

        // Loop through the rows and search for the MLife userID.
        logTrace("Searching MLife users for [ " + userID + " ].");
        for (int rowNum = 0; rowNum <= rowCount - 1; rowNum++) {

            // Find the row that is the MLife user, set it for return.
            rowSplit = csvRowList.get(rowNum).split(delimiter);
            if (rowSplit[0].equalsIgnoreCase(userID)) {
                mlifeUser = rowSplit;
            }
        }

        logTrace("Exiting CSVDataProvider#getMLifeUser");

        // return the row of MLife user data;
        return mlifeUser;
    }

    /**
     * This gets Random MLife Email ID and Mlife Number from a csv file. It returns the data as an array.
     *
     * @return Array containing Email and Mlife Number
     */
    public static String[] getRandomMLifeUser() {
        logTrace("Entering CSVDataProvider#getMLifeUser");
        String filePath = "/datasheets/common/MLifeUsers.csv";
        String line = "";
        List<String> csvRowList = new ArrayList<String>();
        String[] mlifeUser = new String[5];

        // Get the file location from the project main/resources folder
        if (!(filePath.contains(":") || filePath.startsWith("/"))) {
            URL file = CSVDataProvider.class.getResource(filePath);
            if (file == null) {
                throw new DataProviderInputFileNotFound("No file was found on path [ " + filePath + " ]");
            }
            filePath = file.getPath();
        }

        // in case file path has a %20 for a whitespace, replace with actual
        // whitespace
        filePath = filePath.replace("%20", " ");

        logTrace("File path of CSV to open [ " + filePath + " ]");
        // open the CSV and add each line into a list
        try (BufferedReader bufferedReader = FileLoader.openTextFileFromProject(filePath)) {
            logTrace("Successfully loaded FileReader object into BufferedReader");

            logTrace("Read in file and load each line into a List");
            while ((line = bufferedReader.readLine()) != null) {
                csvRowList.add(line);
            }
            logTrace("Successfully read in [ " + csvRowList.size() + " ] lines from file");
            bufferedReader.close();
        } catch (IOException e) {
            throw new AutomationException("Failed to read in CSV file", e);
        }

        // Remove first line of headers
        csvRowList.remove(0);
        // Generate a random number between 17 and last Row Number value
        int minRow = 17; // Sapphire users start from row number 17
        int rowNum = Randomness.randomNumberBetween(minRow, csvRowList.size());
        // Split the Row to get the EMail ID
        mlifeUser[0] = csvRowList.get(rowNum).split(delimiter)[0];
        // Split the Row to get the Mlife Number
        mlifeUser[1] = csvRowList.get(rowNum).split(delimiter)[4];
        // Split the Row to get the encoded Password
        mlifeUser[2] = csvRowList.get(rowNum).split(delimiter)[1];
        // Split the Row to get the First Name
        mlifeUser[3] = csvRowList.get(rowNum).split(delimiter)[2];
        // Split the Row to get the Last Name
        mlifeUser[4] = csvRowList.get(rowNum).split(delimiter)[3];
        // return the user data;
        return mlifeUser;
    }
}
