package com.util;

import java.io.File;

import org.testng.Assert;

import com.base.Driver;

public class VerifyAndDeleteFile extends Driver {

	public static String VerifyFileFromFolder() throws Exception {
		String FileName = null;
		File listOfFiles[] = Folder.listFiles();
		System.err.println("No. of Files: " + listOfFiles.length);
		Assert.assertTrue(listOfFiles.length > 0, "FAILED: There is no file");
		for (File file : listOfFiles) {
			Assert.assertTrue(file.length() >= 0, "FAILED: File size must be more than zero");
			FileName = file.getName();
		}

		return FileName;
	}

	public static boolean VerifyPresenceOfFileInFolder() throws Exception {

		File listOfFiles[] = Folder.listFiles();
		boolean PresenceOfFile = listOfFiles.length > 0;

		return PresenceOfFile;
	}

	public static void DeleteFileFromFolder(String filename) throws Exception {
		for (File file : Folder.listFiles()) {
			String FileName = file.getName();
			if (FileName.equals(filename)) {
				file.delete();
				break;
			}
			continue;
		}
	}
}