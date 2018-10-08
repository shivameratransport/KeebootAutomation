package com.keeboot.utils.dataProviders;

import java.net.URL;

import com.keeboot.utils.exception.DataProviderInputFileNotFound;
import com.keeboot.utils.io.ExcelDocumentReader;

public class ExcelDataProvider {
    private String filePath;
    private String sheetName;
    private int row;

    public ExcelDataProvider(String filePath, String sheetName) {
        this(filePath, sheetName, -1);
    }

    public ExcelDataProvider(String filePath, String sheetName, int rowToRead) {
        URL file = getClass().getResource(filePath);
        if (file == null) {
            throw new DataProviderInputFileNotFound("Failed to find a file in path [ " + filePath + " ]");
        }

        this.filePath = file.getPath();
        this.sheetName = sheetName;
        this.row = rowToRead;
    }

    public Object[][] getTestData() {
        return new ExcelDocumentReader(this.filePath).readData(this.sheetName, this.row);
    }
}
