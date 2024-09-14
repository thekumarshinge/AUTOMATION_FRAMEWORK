package com.testutil;

import java.util.Arrays;
import java.util.List;

public class GetTestClassAndTestMethodName {

	public static String getCurrentMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
	
	public static String getCurrentClassName() {
		String Classname = Thread.currentThread().getStackTrace()[2].getClassName();
	
		// Split the sentence into words
		String[] wordsArray = Classname.replace(".", " ").split(" ");

		// Convert array to a list
		List<String> wordsList = Arrays.asList(wordsArray);
		
		return  wordsList.get(wordsList.size()-1).trim();
	}
}
