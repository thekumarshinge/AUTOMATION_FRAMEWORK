package com.util;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author 2118943
 *
 */
public class CommonUserActions {

	public static List<WebElement> ElementList = null;

	// To Check if the element is present on the web page or not
	public static void CheckElementPresent(WebDriver driver, String WhichElement_Xpath) {
		ElementList = driver.findElements(By.xpath(WhichElement_Xpath));
		System.out.println(ElementList.size());
	}

	/*
	 * Browser Window & Tab title and Url
	 */
	// Get page title and verify.
	public static String VerifyPageTitle(WebDriver driver) throws InterruptedException {
		String PageTitle = driver.getTitle();
		return PageTitle;
	}

	// Get page URL and verify.
	public static String VerifyPageURL(WebDriver driver) throws InterruptedException {
		String PageURL = driver.getCurrentUrl();
		return PageURL;
	}

	/*
	 * RANDOM NUMBER AND TEXT GENERATOR
	 */
	// To Pick Random Integer Number From Given Range Of Number From User
	public static String GenerateRandomIntegerNumber(int Min, int Max) throws Exception {
		return (String.valueOf(ThreadLocalRandom.current().nextInt(Min, Max + 1)));
	}

	// To Pick Random Integer Number From Given Range Of Number From User
	public static String GenerateRandomDecimalNumber(int Min, int Max) throws Exception {
		return (String.valueOf(ThreadLocalRandom.current().nextDouble(Min, Max + 1)));
	}

	// To Generate Random Words From Given Length From User
	public static String GenerateRandomWord(int len) {
		String Word = "";
		for (int i = 0; i < len; i++) {
			int index = 1 + (int) (Math.random() * 26);
			char character = (char) (index + (i == 0 ? 'A' : 'a') - 1);
			Word += character;
		}
		return Word;
	}

	/*
	 * FIND ELEMENT
	 */
	// This Methods checking the respective checkbox element is checked or not.
	public static boolean CheckBoxIsChecked(WebDriver driver, String ElementXpath) throws Exception {
		boolean IsSelected = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		try {
			IsSelected = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath))).isSelected();
		} catch (Exception e) {
			IsSelected = false;
			// System.err.println("StackTrace: ");
			// e.printStackTrace();
		}
		return IsSelected;
	}

	// This Methods checking the respective Web element is displayed or not.
	public static boolean WebelementIsDisplayed(WebDriver driver, String ElementXpath) throws Exception {
		boolean IsDisplayed = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try {
			IsDisplayed = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath))).isDisplayed();
		} catch (Exception e) {
			IsDisplayed = false;
			// System.err.println("StackTrace: ");
			// e.printStackTrace();
		}
		return IsDisplayed;
	}

	// This Methods checking the respective Web element is enabled or not.
	public static boolean WebelementIsEnabled(WebDriver driver, String ElementXpath) throws Exception {
		boolean IsEnabled = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		try {
			IsEnabled = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath))).isEnabled();
		} catch (Exception e) {
			IsEnabled = false;
			// System.err.println("StackTrace: ");
			// e.printStackTrace();
		}
		return IsEnabled;
	}

	// Enter data into particular field.
	public static void EnterInputInTextField(WebDriver driver, String Field, String InputValue)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Field))).sendKeys(InputValue);
		Thread.sleep(2000);
	}

	// To Clear the value for the particular field.
	public static void ClearTextInTextField(WebDriver driver, String FieldXpath) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(FieldXpath))).clear();
		Thread.sleep(1000);
	}

	// To click on any of the web element.
	public static void ClickOnElement(WebDriver driver, String ElementXpath, int ThreadTimeAfterClick)
			throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath))).click();
		Thread.sleep(ThreadTimeAfterClick);
	}

	public static String GetText(WebDriver driver, String Field) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Field))).getText();
	}

	public static String GetAttribute(WebDriver driver, String Field, String Text) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Field))).getAttribute(Text);
	}

	// This method can be used to select the value from the dropdown by visible
	// text.
	public static void SelectDropdownValueByVisibleText(WebDriver driver, String ElementXpath,
			String DropDownValueText) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		Select select = new Select(Element);
		select.selectByVisibleText(DropDownValueText);
	}

	// This method can be used to de-select the value from the dropdown by visible
	// text.
	public static void DeSelectDropdownValueByVisibleText(WebDriver driver, String ElementXpath,
			String DropDownValueText) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		Select select = new Select(Element);
		select.deselectByVisibleText(DropDownValueText);
	}

	// This method can be used to select the value from the dropdown by index.
	public static void SelectDropdownValueByIndex(WebDriver driver, String ElementXpath, int DropDownValueIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		Select select = new Select(Element);
		select.selectByIndex(DropDownValueIndex);
	}

	// This method can be used to select the value from the dropdown by Value.
	public static void SelectDropdownValueByValue(WebDriver driver, String ElementXpath, String DropDownValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		Select select = new Select(Element);
		select.selectByValue(DropDownValue);
	}

	// To Get the select value form the dropdown.
	public static String GetDropDownSelectedValue(WebDriver driver, String DropDownExpath) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(DropDownExpath))));
		WebElement option = select.getFirstSelectedOption();
		String defaultItem = option.getText();
		return defaultItem;
	}

	// To Get the select value form the dropdown.
	public static List<WebElement> GetAllSelectedDropDownValue(WebDriver driver, String DropDownExpath) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(DropDownExpath))));
		List<WebElement> options = select.getAllSelectedOptions();
		return options;
	}

	// Action Class, this method will move to the particular Element and perform a
	// click action. (It can be used when there is ElementClickInterceptedException)
	public static void ActionMoveAndClick(WebDriver driver, String ElementXpath, int ThreadTimeAfterClick)
			throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		Actions action = new Actions(driver);
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		action.moveToElement(Element).click().build().perform();
		Thread.sleep(ThreadTimeAfterClick);
	}

	// Action Class, this method will be used to perform Drag And Drop action.
	public static void ActionDragAndDrop(WebDriver driver, String SourceXpath, String DestinationXpath,
			int ThreadTimeAfterDrop) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		Actions action = new Actions(driver);
		WebElement SourceElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SourceXpath)));
		WebElement DestinationElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(DestinationXpath)));
		action.clickAndHold(SourceElement).moveToElement(DestinationElement).release(DestinationElement).build()
				.perform();
		Thread.sleep(ThreadTimeAfterDrop);
	}

	// Action Class, this method will move to the particular Element. To test mouse
	// over.
	public static void ActionMouseOver(WebDriver driver, String ElementXpath, int ThreadTimeAfterClick)
			throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		Actions action = new Actions(driver);
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		action.moveToElement(Element).build().perform();
		Thread.sleep(ThreadTimeAfterClick);
	}

	// Action Class, this method will right click on the element.
	public static void ActionRightClick(WebDriver driver, String ElementXpath, int ThreadTimeAfterClick)
			throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		Actions action = new Actions(driver);
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		action.moveToElement(Element).contextClick().build().perform();
		Thread.sleep(ThreadTimeAfterClick);
	}

	// Action Class, this method will Horizontal scroll by selecting the element.
	public static void ActionHorizontalScroll(WebDriver driver, String ElementXpath, int ThreadTimeAfterClick)
			throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		Actions action = new Actions(driver);
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		action.moveToElement(Element).clickAndHold().moveByOffset(100, 0).release().build().perform();
		Thread.sleep(ThreadTimeAfterClick);
	}

	/*
	 * JAVASCRIPT EXECUTOR
	 */
	// JavascriptExecutor, this method will scroll to the particular element.
	public static void JAVAScriptScrollToElement(WebDriver driver, String ElementXpath) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		JavascriptExecutor JSE = (JavascriptExecutor) driver;
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		JSE.executeScript("arguments[0].scrollIntoView();", Element);
	}

	// JavascriptExecutor, this method will horizontally scroll to the till last
	// element.
	public static void JAVAScriptHorizontalScrollToElement(WebDriver driver, String ElementXpath) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		JavascriptExecutor JSE = (JavascriptExecutor) driver;
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));

		boolean isElementVisible = (boolean) JSE.executeScript("var elem = arguments[0];"
				+ "var docViewLeft = window.pageXOffset;" + "var docViewRight = docViewLeft + window.innerWidth;"
				+ "var elemLeft = elem.getBoundingClientRect().left;"
				+ "var elemRight = elem.getBoundingClientRect().right;"
				+ "return ((elemLeft >= docViewLeft) && (elemRight <= docViewRight));", Element);

		// Scroll horizontally until the last element becomes visible
		while (!isElementVisible) {
			JSE.executeScript("window.scrollBy(100,0)");
		}
	}

	// JavascriptExecutor Click, To click the element. it can be used when normal
	// click is not works.
	public static void JAVAScriptClick(WebDriver driver, String ElementXpath, int ThreadTimeAfterClick)
			throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		JavascriptExecutor JSE = (JavascriptExecutor) driver;
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		JSE.executeScript("arguments[0].click();", Element);
		Thread.sleep(ThreadTimeAfterClick);
	}

	// JavascriptExecutor SendKey, To send the value in field. it can be used when
	// normal sendkeys is not works.
	public static void JAVAScriptSendKey(WebDriver driver, String ElementXpath, String TextToSend) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		JavascriptExecutor JSE = (JavascriptExecutor) driver;
		WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		JSE.executeScript("arguments[0].value = '" + TextToSend + "';", Element);
	}

	// JavascriptExecutor Zoom. To zoom In/Out the screen.
	public static void JAVAScriptZoomInOut(WebDriver driver, int ZoomInOutPercent) {
		JavascriptExecutor JSE = (JavascriptExecutor) driver;
		JSE.executeScript("document.body.style.zoom = '" + ZoomInOutPercent + "%'");
	}

	/*
	 * iFRAME
	 */
	// Switch to any of the frame by Frame name.
	public static void SwitchToFrameByName(WebDriver driver, String FrameName) throws InterruptedException {

		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(FrameName);
	}

	// Switch to any of the frame by Frame Index.
	public static void SwitchToFrameByIndex(WebDriver driver, int FrameIndex) throws InterruptedException {
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(FrameIndex);
	}

	// Switch to default content. Come out from the switched frame.
	public static void SwitchToDefaultContent(WebDriver driver) throws InterruptedException {
		Thread.sleep(1000);
		driver.switchTo().defaultContent();
	}

	/*
	 * ALERT BOX
	 */
	// Switch to Alert and accept.
	public static void SwitchToAlertAccept(WebDriver driver, int ThreadTimeAfterSwitch) throws Exception {
		driver.switchTo().alert().accept();
		Thread.sleep(ThreadTimeAfterSwitch);
	}

	public static boolean AlertIsPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	// Switch to Alert and Dismiss.
	public static void SwitchToAlertDismiss(WebDriver driver, int ThreadTimeAfterSwitch) throws Exception {
		driver.switchTo().alert().dismiss();
		Thread.sleep(ThreadTimeAfterSwitch);
	}

	// To get the alert Text.
	public static String SwitchToAlerGetText(WebDriver driver, int ThreadTimeAfterSwitch) throws Exception {
		String AlerText = driver.switchTo().alert().getText();
		Thread.sleep(ThreadTimeAfterSwitch);
		return AlerText;
	}

	// Just Switch to Alert.
	public static void SwitchToAlert(WebDriver driver) throws InterruptedException {
		Thread.sleep(1000);
		driver.switchTo().alert();
	}

	// Switch to Alert and enter text
	public static void SwitchToAlertAcceptAndEntertext(WebDriver driver, int ThreadTimeAfterSwitch, String Text)
			throws Exception {
		driver.switchTo().alert().sendKeys(Text);
		Thread.sleep(ThreadTimeAfterSwitch);
	}

	/*
	 * SIMPLE DATE
	 */
	// To get the todays day, Only day e.g. '10'
	public static String TodaysDate = null;

	public static void GetTodaysDay() {
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// Get Current Day as a number
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		// Integer to String Conversion
		TodaysDate = Integer.toString(todayInt);
	}

	// To get the date in the proper format. e.g 'MM/dd/yyyy'
	public static void GetTodaysDate(String DateFormat) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DateFormat);
		TodaysDate = formatter.format(date);
	}

	// To get the date in the proper format. e.g 'yyyy'
	public static String GetCurrentYear() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// Get Current Day as a number
		int todayInt = calendar.get(Calendar.YEAR);
		// Integer to String Conversion
		return TodaysDate = Integer.toString(todayInt);
	}
	
	// To get the date in the proper format. e.g 'MM/dd/yyyy hh:mm:ss'
		public static String GetCurrentDateTime() {
			return new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(new Date());
		}
	

	/*
	 * ROBOT CLASS
	 */
	// To Upload the document from local driver using Robot class.
	public static void UploadDocumentByRobot(String DocumentLocation) throws Exception {
		Thread.sleep(5000);
		StringSelection ss = new StringSelection(DocumentLocation);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_V);
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(3000);
	}

	/*
	 * WINDOW HANDLING
	 */
	// Switch to Child window.
	public static String winHandleBefore = null;

	public static void SwitchToChildWindow(WebDriver driver, ArrayList<String> SessionId) throws Exception {
		Thread.sleep(10000);
		if (SessionId.size() == 1) {
			for (String winHandle : SessionId) {
				driver.switchTo().window(winHandle);
				System.out.println("<----_Parent_---->" + winHandle);
			}
		} else {
			for (String winHandle : SessionId) {
				if (!winHandleBefore.contentEquals(winHandle)) {
					driver.switchTo().window(winHandle);
					System.out.println("<----_Child_---->" + winHandle);
				}
			}
		}
	}

	// Switch to Parent window.
	/**
	 * @param driver
	 * @throws Exception
	 */
	public static void SwitchBackToParentWindow(WebDriver driver) throws Exception {
		winHandleBefore = driver.getWindowHandle();
		System.out.println("<----_Parent_---->" + winHandleBefore);
		driver.switchTo().window(winHandleBefore);
		Thread.sleep(3000);
	}

	public static void ClickEnterKey(WebDriver driver, String ElementXpath) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath))).sendKeys(Keys.ENTER);
	}

	/*
	 * Highlight WEBELEMENT USING CSS AND JAVASCRIPT CODE
	 */
	/**
	 * @param driver
	 * @param ElementXpath
	 * @param CSSValue
	 * @return
	 */
	public static String GetElementColorUsingCSS(WebDriver driver, String ElementXpath, String CSSValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ElementXpath)));
		String ColorID = element.getCssValue(CSSValue);
		String hexColor = Color.fromString(ColorID).asHex();
		return hexColor;
	}

	public static void HighlightElementUsingJAVAScript(WebDriver driver, String Field) {
		// Create an instance of JavascriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Field)));
		// Execute JavaScript code to modify the element's style
		js.executeScript("arguments[0].style.border='0.5px solid red'", element);
	}

	public static void HighlightElementUsingJAVAScript(WebDriver driver, String Field, String color, Double pixels) {
		// Create an instance of JavascriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Field)));
		// Execute JavaScript code to modify the element's style
		js.executeScript("arguments[0].style.border='" + pixels + "px solid " + color + "'", element);
	}

}