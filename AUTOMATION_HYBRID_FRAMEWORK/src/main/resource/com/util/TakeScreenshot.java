package com.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class TakeScreenshot {
	public static String Screenshot_Path = System.getProperty("user.dir") + "\\Screenshot\\";

	/*
	 * This method needs to call at the end of every Test Script so that it can take
	 * the screenshot.
	 */
	public static void capturescreenshot(WebDriver driver, String screenshotname) throws Exception {
		try {
			Thread.sleep(1000);
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String timestamp = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ss").format(new Date());
			FileUtils.copyFile(src, new File(Screenshot_Path + screenshotname + "" + timestamp + ".png"));
		} catch (Exception e) {
			// System.out.print("Exception while taking screenshot" + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * To take screenshot in Base64
	 */
	public static String CapturescreenshotBase64(WebDriver driver) throws Exception {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	}

	/*
	 * In this we will able capture entire screenshot web page from top to bottom
	 * and convert screenshot in Base64 format
	 */
	public static String CaptureFullPagescreenshotBase64(WebDriver driver) throws Exception {
		// Capture full webpage screenshot using Ashsot
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver);

		// Convert the screenshot to a BufferedImage
		BufferedImage bufferedImage = screenshot.getImage();

		// Convert the BufferedImage to a byte array
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", baos);
		} catch (IOException e) {
			System.out.println("TakeScreenshot.CaptureFullPagescreenshotBase64()");
		}
		byte[] imageBytes = baos.toByteArray();

		// Convert the byte array to a Base64 encoded string
		String base64Screenshot = Base64.getEncoder().encodeToString(imageBytes);

		return base64Screenshot;
	}

	/*
	 * In this we will able crop screenshot if captured screenshot is contains more
	 * than screen view and convert screenshot in Base64 format
	 */
	public static String CaptureCropedscreenshotBase64(WebDriver driver) throws Exception {
		String base64Screenshot = null;
		try {
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			// Load the captured screenshot as a BufferedImage
			BufferedImage fullImage = ImageIO.read(screenshotFile);

			// Get the current screen dimensions
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int screenWidth = (int) screenSize.getWidth();
			int screenHeight = (int) screenSize.getHeight();

//			System.err.println("screenWidth : " + screenWidth);
//			System.err.println("screenHeight : " + screenHeight);

			if (screenWidth > fullImage.getWidth()) {
				screenWidth = fullImage.getWidth();
			} else if (screenHeight > fullImage.getHeight()) {
				screenHeight = fullImage.getHeight();
			}

//			System.err.println("Revised screenWidth : " + screenWidth);
//			System.err.println("Revised screenHeight : " + screenHeight);

			// Crop the image based on the screen dimensions
			BufferedImage croppedImage = fullImage.getSubimage(0, 0, screenWidth, screenHeight);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(croppedImage, "png", baos);
			} catch (IOException e) {
				// System.err.println(e);
				e.printStackTrace();
			}
			byte[] imageBytes = baos.toByteArray();

			// Convert the byte array to a Base64 encoded string
			base64Screenshot = Base64.getEncoder().encodeToString(imageBytes);

		} catch (IOException | RasterFormatException e) {
			// System.err.println(e);
			e.printStackTrace();
		}

		return base64Screenshot;
	}

	/*
	 * In this we will able to capture screenshot to outside browser. (note.: Where
	 * ever you want to use this screenshot method. keep that browser session window
	 * maximize and on front.)
	 */
	public static String CaptureEntireScreenscreenshotBase64(WebDriver driver) throws Exception {
		String base64Image = null;
		try {
			// Create a Robot instance
			Robot robot = new Robot();

			// Capture the screen
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenImage = robot.createScreenCapture(screenRect);

			// Convert the image to base64
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(screenImage, "png", baos);
			byte[] imageBytes = baos.toByteArray();
			base64Image = Base64.getEncoder().encodeToString(imageBytes);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return base64Image;
	}

	/*
	 * In this we will able crop taken screenshot if captured screenshot is contains
	 * more than screen view and convert screenshot in Base64 format
	 */
	public static String CaptureCropedFullScreenscreenshotBase64(WebDriver driver) throws Exception {
		String base64Screenshot = null;
		try {

			// Capture full webpage screenshot using Ashot
			Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
					.takeScreenshot(driver);

			// Convert the screenshot to a BufferedImage
			BufferedImage fullImage = screenshot.getImage();

			// Get the current screen dimensions
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int screenWidth = (int) screenSize.getWidth();
			// int screenHeight = (int) screenSize.getHeight();

			int WebpageHeight = screenshot.getImage().getHeight();

//			System.err.println("screenWidth : " + screenWidth);
//			System.err.println("screenHeight : " + screenHeight);
//			System.err.println("WebpageHeight : " + WebpageHeight);

			if (screenWidth > fullImage.getWidth()) {
				screenWidth = fullImage.getWidth();
			}

//			else if (screenHeight > fullImage.getHeight()) {
//				screenHeight = fullImage.getHeight();
//			}

//			System.err.println("Revised screenWidth : " + screenWidth);
//			System.err.println("Revised screenHeight : " + screenHeight);
//			System.err.println("Revised WebpageHeight : " + WebpageHeight);

			// Crop the image based on the screen dimensions
			BufferedImage croppedImage = fullImage.getSubimage(0, 0, screenWidth, WebpageHeight);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(croppedImage, "png", baos);
			} catch (IOException e) {
				// System.err.println(e);
				e.printStackTrace();
			}
			byte[] imageBytes = baos.toByteArray();

			// Convert the byte array to a Base64 encoded string
			base64Screenshot = Base64.getEncoder().encodeToString(imageBytes);

		} catch (RasterFormatException e) {
			// System.err.println(e);
			e.printStackTrace();
		}

		return base64Screenshot;
	}
}
