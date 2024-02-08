package org.swaglabs.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadExcelFile {

    private static String excelFilePath;
    private static FileInputStream file;
    static Workbook book;
    static Sheet sheet;

    public ReadExcelFile(String testDatailePath) {
        this.excelFilePath = testDatailePath;
    }

    public Object[][] getTestData(String sheetName) {

        Object[][] inputData = new Object[0][];

        try {
            file = new FileInputStream(excelFilePath);
            book = WorkbookFactory.create(file);
            sheet = book.getSheet(sheetName);
            DataFormatter dataFormatter = new DataFormatter();

            int numRows = sheet.getPhysicalNumberOfRows()-1;
            int numCols = sheet.getRow(0).getPhysicalNumberOfCells();

            System.out.println("numRows"+numRows);
            System.out.println("numCols"+numCols);


            inputData = new Object[numRows][numCols];

            for (int i = 0; i < numRows; i++) {
                Row row = sheet.getRow(i+1);
                for (int j = 0; j < numCols; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if (cell.getCellType() == CellType.BLANK) {
                        inputData[i][j] = "";
                    } else {
                        inputData[i][j] = cell.toString();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Excel file not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File input output has issue");
            e.printStackTrace();
        }
        return inputData;
    }
}
