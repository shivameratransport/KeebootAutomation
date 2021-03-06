package com.keeboot.utils.io;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.keeboot.AutomationException;

public class ExcelDocumentReader {
    private Sheet excelWSheet;
    private Workbook excelWBook;
    private Cell cell;
    private String filepath;
    private int startRow = 1;

    public ExcelDocumentReader(String filepath) {
        this.filepath = filepath;
    }

    public ExcelDocumentReader() {
    }

    /**
     * This gets the test data from excel workbook by the sheet specified. It returns all the data
     * as a 2d array
     *
     * @param sheetName
     *            the excel sheet
     * @return 2d array of test data
     */

    public Object[][] readData(String sheetName) {
        return (readData(sheetName, -1));
    }

    public Object[][] readData(String filepath, String sheetName) {
        return (readData(filepath, sheetName, -1));
    }

    public Object[][] readData(String sheetName, int rowToRead, int startRow) {
        this.startRow = startRow;
        return readData(filepath, sheetName, rowToRead);
    }

    public Object[][] readData(String sheetName, int rowToRead) {
        return readData(filepath, sheetName, rowToRead);
    }

    public Object[][] readData(String filepath, String sheetName, int rowToRead) {

        String[][] tabArray = null;
        int totalRows = 1;

        try {

            // Access the required test data sheet
            if (filepath.toUpperCase().indexOf(".XLSX") > 0) {
                excelWBook = new XSSFWorkbook(FileLoader.getAbosutePathForResource(filepath)); // XLSX
            } else {
                FileInputStream excelFile = new FileInputStream(FileLoader.getAbosutePathForResource(filepath));
                excelWBook = new HSSFWorkbook(excelFile); // XLS
            }

            if (StringUtils.isNumeric(sheetName)) {
                excelWSheet = excelWBook.getSheetAt(Integer.valueOf(sheetName));
            } else {
                excelWSheet = excelWBook.getSheet(sheetName);
            }

            int startCol = 0;
            int offsetRows = 0;
            int ci, cj;

            if (startRow > 1) {
                totalRows = excelWSheet.getLastRowNum();
                offsetRows = totalRows - startRow;
            } else if (rowToRead == -1 && startRow == 0) {
                totalRows = excelWSheet.getLastRowNum() + 1;
            } else if (rowToRead == -1) {
                totalRows = excelWSheet.getLastRowNum();
            } else {
                startRow = rowToRead;
                totalRows = startRow;
            }

            // you can write a function as well to get Column count

            int totalCols = excelWSheet.getRow(startRow).getLastCellNum();

            tabArray = new String[totalRows - offsetRows][totalCols];
            if (offsetRows != 0) {
                offsetRows--;
            } else if (startRow == 0) {
                offsetRows = 1;
            }
            ci = 0;
            for (int i = startRow; i <= totalRows - offsetRows; i++, ci++) {
                cj = 0;
                for (int j = startCol; j < totalCols; j++, cj++) {
                    tabArray[ci][cj] = getCellData(i, j);
                }
            }

        } catch (FileNotFoundException e) {
            throw new AutomationException("Failed to locate Excel file");
        } catch (IOException e) {
            throw new AutomationException("Could not read Excel file");
        }
        return (tabArray);
    }

    private String getCellData(int rowNum, int colNum) {
        cell = excelWSheet.getRow(rowNum).getCell(colNum);
        if (cell == null) {
            return "";
        }
        String cellData = "";
        switch (cell.getCellType()) {
            case CELL_TYPE_NUMERIC:
                DataFormatter formatter = new DataFormatter();
                cellData = formatter.formatCellValue(cell);
                break;

            case CELL_TYPE_STRING:
                cellData = cell.getStringCellValue();
                break;

            case CELL_TYPE_FORMULA:
                cellData = String.valueOf(cell.getCellFormula());
                break;

            case CELL_TYPE_BOOLEAN:
                cellData = String.valueOf(cell.getBooleanCellValue());
                break;

            default:
                break;
        }

        return cellData;
    }
}
