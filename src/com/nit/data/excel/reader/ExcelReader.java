/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nit.data.excel.reader;

/**
 *
 * @author Karthikeyan
 */
import com.nit.data.pojo.Movie;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcelReader {
    //public static final String SAMPLE_XLSX_FILE_PATH = "./sample-xlsx-file.xlsx";

    public ArrayList readingExcel(String SAMPLE_XLSX_FILE_PATH) throws IOException {
        ArrayList list = new ArrayList();
        Movie movie = new Movie();
        try {

            // Creating a Workbook from an Excel file (.xls or .xlsx)
            Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

            // Retrieving the number of sheets in the Workbook
            System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

            /*
            =============================================================
            Iterating over all the sheets in the workbook (Multiple ways)
            =============================================================
             */
            System.out.println("Retrieving Sheets using for-each loop");
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("=> " + sheet.getSheetName());
            /*
                ==================================================================
                Iterating over all the rows and columns in a Sheet (Multiple ways)
                ==================================================================
             */
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();
            // 2. Or you can use a for-each loop to iterate over the rows and columns
            System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
            for (Row row : sheet) {
                movie = new Movie();
                for (Cell cell : row) {
                    if (cell.getColumnIndex() == 0) {

                        String movieId = dataFormatter.formatCellValue(cell);
                        movie.setMovieId(movieId);
                  // System.out.print(cellValue + "\t");
                    } else if (cell.getColumnIndex() == 1) {
                        String movieName = dataFormatter.formatCellValue(cell);
                        movie.setMovieName(movieName);
                    } else if (cell.getColumnIndex() == 2) {
                        int movieRating = (int) cell.getNumericCellValue();
                        movie.setMovieRating(movieRating);
                    }
                    
                    System.out.println();
                }list.add(movie);
            }

        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
