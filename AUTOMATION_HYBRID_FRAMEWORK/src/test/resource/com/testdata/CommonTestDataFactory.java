package com.testdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;

import com.util.DataProviderClass;
import com.util.FileReadWrite;

public class CommonTestDataFactory {

	/*
	 * Declared & initialized the LOCAL_PROJECT_PATH
	 */
	private static final String LOCAL_PROJECT_PATH = System.getProperty("user.dir");

	/*
	 * Declared & initialized the TEST_DATA_EXCEL_FILE & TEST_DATA_EXCEL_SHEET
	 */
	public static final String TEST_DATA_EXCEL_FILE = LOCAL_PROJECT_PATH
			+ "/src/test/resource/com/testdata/TEST_DATA.xlsx";
	
	public static final String TEST_DATA_EXCEL_SHEET = "URL_AND_USER_CREDENTIALS";

	public static final String url(@Optional("1") int url_index) throws Exception   {
		return FileReadWrite.GetValueFromSheet(TEST_DATA_EXCEL_FILE, TEST_DATA_EXCEL_SHEET, url_index, 1);
	}

	public static final String username(@Optional("1") int username_index) throws Exception {
		return FileReadWrite.GetValueFromSheet(TEST_DATA_EXCEL_FILE, TEST_DATA_EXCEL_SHEET, username_index, 2);
	}

	public static final String password(@Optional("1") int password_index) throws Exception {
		return FileReadWrite.GetValueFromSheet(TEST_DATA_EXCEL_FILE, TEST_DATA_EXCEL_SHEET, password_index, 3);
	}

	/*
	 * ArrayList
	 */
	public static final ArrayList<String> ADMINISTARTION = new ArrayList<>(Arrays.asList("a"));
	
	// <=================================_DATA_PROVIDER_METHODS_=================================>
	/*
	 * Data Provider Methods :: By excel sheet
	 */
	@DataProvider(name = "usrname")
	public static Object[][] UserTestData() throws IOException, Exception {
		return DataProviderClass.GetSheetdata(TEST_DATA_EXCEL_FILE, TEST_DATA_EXCEL_SHEET);
	}
		
	//By Arraylist
	@DataProvider(name = "Basic")
	public static Object[][] BasicLookupTypeData() throws IOException, Exception {
		return new Object[][] {{"M"}, {"L"}, {"R"}};
	}
	
	
	
	}