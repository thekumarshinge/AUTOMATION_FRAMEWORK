package com.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DataProviderClass 
{
	public static Workbook book;
	public static Sheet sheet;
	
	public static Object[][] GetSheetdata(String SheetLocation, String sheetName) throws Exception, IOException
	{
		FileInputStream file = null;		
		try
		{
			file = new FileInputStream(SheetLocation);
		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			book = WorkbookFactory.create(file);
		}
		catch(Exception Io)
		{
			Io.printStackTrace();
		}
		
		sheet = book.getSheet(sheetName);		
		Object [][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		
		for(int i = 0; i<sheet.getLastRowNum(); i++)
		{
			for(int j =0; j<sheet.getRow(0).getLastCellNum(); j++)
			{
				data[i][j] = sheet.getRow(i+1).getCell(j).toString();
			}
			
		}
		return data;
		
	}
}