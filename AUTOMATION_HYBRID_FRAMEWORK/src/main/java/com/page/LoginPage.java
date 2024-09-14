package com.page;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.base.Driver;
import com.locator.WebElementLocatorFactory;
import com.util.CommonUserActions;

public class LoginPage extends Driver{
	private WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	/*
	 * Author :: 
	 */
	
	/*
	 * LaunchURL() : This method is perform launching URL. Here user should be
	 * provide One argument i.e., URL For example :: https://www.google.com OR
	 * www.google.com
	 */
	public void LaunchURL(String url) throws InterruptedException {
		//CommonUserActions.JAVAScriptZoomInOut(driver, 80);
		driver.get(url);
		Thread.sleep(5000);
	}

	/*
	 * LoginTonApp() : Perform the login activity by sending Username &
	 * Password in dedicated input fields. Here user should have pass two arguments
	 * i.e, Username & Password.
	 */
	public void LoginToApp(String UserId, String Password) throws Exception {
		CommonUserActions.EnterInputInTextField(driver, WebElementLocatorFactory.LOGINPAGE_USERID, UserId);
		CommonUserActions.EnterInputInTextField(driver, WebElementLocatorFactory.LOGINPAGE_PASSWORD, Password);
		Thread.sleep(2000);
		CommonUserActions.ActionMoveAndClick(driver, WebElementLocatorFactory.LOGINPAGE_SIGNIN, 5000);
		Reporter.log("[INFO] [" + CommonUserActions.GetCurrentDateTime() + "] :: LOGIN TO MTS !!!", true);
	}

	/*
	 * Create the ArrayList in String type ArrayList reference name is "SESSION_ID"
	 * This ArrayList will used in below mentioned methods :: MoveToParentWindow(),
	 * GetCurrentSession(), MoveToChildWindow(), ClearSession()
	 */
	private ArrayList<String> SESSION_ID = new ArrayList<String>();

	/*
	 * MoveToParentWindow() : This method will capture Session_Id of first launched
	 * browser window and It store in ArrayList<String> SESSION_ID.
	 */
	public void MoveToParentWindow() throws Exception {
		CommonUserActions.SwitchBackToParentWindow(driver);
		SESSION_ID.add(CommonUserActions.winHandleBefore);
	}

	/*
	 * GetCurrentSession() : This method will capture Session_Id's of new launched
	 * browser window or tab. (Which are launched after performing any click on
	 * webpage) and It store in ArrayList<String> SESSION_ID This method will return
	 * the Session_Id
	 */
	public String GetCurrentSession() throws Exception {
		String CurrentSession = null;
		String ParentID = SESSION_ID.get(0);
		for (String winHandle : driver.getWindowHandles()) {
			if (!ParentID.contains(winHandle)) {
				CurrentSession = winHandle;
			}
		}
		return CurrentSession;
	}

	/*
	 * MoveToChildWindow() : This method is perform switch between Parent and new
	 * child window or tab
	 */
	public void MoveToChildWindow() throws Exception {
		for (String winHandle : driver.getWindowHandles()) {
			if (!SESSION_ID.contains(winHandle)) {
				SESSION_ID.add(winHandle);
			}
		}
		CommonUserActions.SwitchToChildWindow(driver, SESSION_ID);
	}

	/*
	 * ClearSession() : This method will Close the current child window and Remove the
	 * current Session_Id from SESSION_ID ArrayList.
	 */
	public void ClearSession() throws Exception {
		int i = SESSION_ID.size() - 1;

		driver.close();

		Thread.sleep(1000);

		System.out.println("Removed Session ID:: " + SESSION_ID.get(i));

		SESSION_ID.remove(i);
		
		Reporter.log("[INFO] [" + CommonUserActions.GetCurrentDateTime() + "] :: SESSION IS REMOVED !!!", true);
	}
	
	/*
	 * ScrolltoTop() This method directly scroll page to top of webpage
	 */
	public static void ScrolltoTop(WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		Thread.sleep(2000);
	}

	/*
	 * ScrolltoBottom() This method directly scroll page to bottom of webpage
	 */
	public static void ScrolltoBottom(WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		Thread.sleep(2000);
	}
}