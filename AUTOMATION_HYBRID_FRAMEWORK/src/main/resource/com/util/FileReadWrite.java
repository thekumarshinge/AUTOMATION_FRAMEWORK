package com.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.base.Driver;


public class FileReadWrite extends Driver {
	
	// To Read the Test data from the particular Excel Sheet.
	public static String GetValueFromSheet(String SheetLocation, String SubSheetName, int Row, int Col)
			throws Exception {
		FileInputStream FileName = new FileInputStream(SheetLocation);
		XSSFWorkbook workbookread = new XSSFWorkbook(FileName);
		XSSFSheet sheetread = workbookread.getSheet(SubSheetName);
		Cell cell = sheetread.getRow(Row).getCell(Col);
		String CellData = cell.getStringCellValue();
		workbookread.close();
		return CellData;
	}

	// To Read the Test numeric data from the particular Excel Sheet.
	public static double GetNumericValueFromSheet(String SheetLocation, String SubSheetName, int Row, int Col)
			throws Exception {
		FileInputStream FileName = new FileInputStream(SheetLocation);
		XSSFWorkbook workbookread = new XSSFWorkbook(FileName);
		XSSFSheet sheetread = workbookread.getSheet(SubSheetName);
		Cell cell = sheetread.getRow(Row).getCell(Col);
		double CellData = cell.getNumericCellValue();
		workbookread.close();
		return CellData;
	}
	
	// To Write the Test Result in the particular Excel Sheet.
	public static void SetValueInSheet(String SheetLocation, String SubSheetName, int Row, int Col, String TestResult)
			throws Exception {
		FileInputStream FileName = new FileInputStream(SheetLocation);
		XSSFWorkbook workbookwirte = new XSSFWorkbook(FileName);
		XSSFSheet sheetwrite = workbookwirte.getSheet(SubSheetName);
		sheetwrite.getRow(Row).createCell(Col).setCellValue(TestResult);
		FileOutputStream fileOut = new FileOutputStream(SheetLocation);
		workbookwirte.write(fileOut);
		fileOut.close();
		workbookwirte.close();
	}

	public static int GetRowcount(String SheetLocation, String SheetName) throws IOException {
		
		FileInputStream FileName = new FileInputStream(SheetLocation);
		XSSFWorkbook workbookread = new XSSFWorkbook(FileName);
		XSSFSheet sheetwrite = workbookread.getSheet(SheetName);
		int rowCount = sheetwrite.getLastRowNum() + 1;
		workbookread.close();
		return rowCount;
	}
	
	public static int GetColumncount(String SheetLocation, String SheetName, int DefaultRow) throws IOException {
		
		FileInputStream FileName = new FileInputStream(SheetLocation);
		XSSFWorkbook workbookread = new XSSFWorkbook(FileName);
		XSSFSheet sheetwrite = workbookread.getSheet(SheetName);
		XSSFRow row = sheetwrite.getRow(DefaultRow);
		int colCount = row.getLastCellNum();
		workbookread.close();
		return colCount;
	}
	
	public static String GetCellIndexByValue(String SheetLocation, String SheetName, String ExpectedText) throws Exception{
		
		FileInputStream FileName = new FileInputStream(SheetLocation);
		XSSFWorkbook workbookread = new XSSFWorkbook(FileName);
		XSSFSheet sheetwrite = workbookread.getSheet(SheetName);
		DataFormatter formatter = new DataFormatter();
		CellReference cellRef = null;
		for (Row row : sheetwrite) {
		    for (Cell cell : row) {
		        
		    	cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

		        // get the text that appears in the cell by getting the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
		        String text = formatter.formatCellValue(cell);

		        // is it an exact match?
		        if (ExpectedText.equals(text)) {
		           System.out.println("Text matched at " + cellRef.formatAsString());
		           workbookread.close();
		           return cellRef.formatAsString();
		        } 
		    }
		}
		workbookread.close();
		return null;
	}
	
}