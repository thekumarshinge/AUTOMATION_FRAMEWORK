package com.base;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.google.common.collect.ImmutableList;
import com.util.CommonUserActions;
import com.util.TakeScreenshot;

public class Driver {

	public static ThreadLocal<ExtentTest> TL_extenttest = new ThreadLocal<>();
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extentreport;
	public static ExtentTest extenttests;
	public static File Folder;

	// Creating objects of ThreadLocal class for WebDriver
	public ThreadLocal<WebDriver> TL_driver = new ThreadLocal<>();

	// Set method for Thread Local for WebDriver
	public synchronized void SetDriver(WebDriver driver) {
		this.TL_driver.set(driver);
	}

	// Get Method for Thread Local of WebDriver
	public synchronized WebDriver GetDriver() {
		return this.TL_driver.get();
	}

	public static String ReportName;

	public static String GenerateReportName(String appname) {
		File Folder = new File("report");
		Folder.mkdir();
		String timestamp = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ss").format(new Date());
		ReportName = System.getProperty("user.dir") + "/report/" + appname + "_" + timestamp + ".html";
		return ReportName;
	}

	@BeforeSuite
	@Parameters({ "appname", "role", "testername", "os", "browser" })
	public void SetupReport(@Optional("App") String appname, @Optional("UnKnown") String role,
			@Optional("<--- Your Name --->") String testername, @Optional("WINDOWS 10") String os,
			@Optional("Chrome") String browser) throws Exception {

		Reporter.log("[INFO] [" + CommonUserActions.GetCurrentDateTime() + "] :: STARTED REPORT CREATION !!!", true);

		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(GenerateReportName(role + "_" + appname))
				.viewConfigurer().viewOrder().as(new ViewName[] { ViewName.TEST, ViewName.CATEGORY, ViewName.AUTHOR,
						ViewName.DEVICE, ViewName.EXCEPTION, ViewName.LOG, ViewName.DASHBOARD })
				.apply();
		sparkReporter.config().setEncoding("utf-8");
		sparkReporter.config().setDocumentTitle("QA :: MTS Test Environment");
		sparkReporter.config().setReportName(appname + " Test Automation Report");
		sparkReporter.config().setTheme(Theme.STANDARD);
		// sparkReporter.config().getResourceCDN();
		sparkReporter.config().enableOfflineMode(false);
		sparkReporter.config().setProtocol(Protocol.HTTPS);
		sparkReporter.config().isTimelineEnabled();

		extentreport = new ExtentReports();

		extentreport.setSystemInfo("Tester Name", testername);
		extentreport.setSystemInfo("HostName", "LocalHost");
		extentreport.setSystemInfo("OS", os);
		extentreport.setSystemInfo("Browser", browser);

		// System.out.println("Report Path : " + ReportName);

		extentreport.attachReporter(sparkReporter);
	}

	@Parameters("browser")
	@BeforeClass
	public void DriverOpen(@Optional("Chrome") String browser) throws Exception {

		Folder = new File("download-files");
		Folder.mkdir();
		switch (browser) {
		case "Chrome":
			
			//WebDriverManager.chromedriver().setup();

			ChromeOptions chromeOptions = new ChromeOptions();
			// Basic Chrome Options
			// Maximize the browser window
			chromeOptions.addArguments("--start-maximized");
			// Set zooming level of browser window
			chromeOptions.addArguments("--force-device-scale-factor=0.8");

			// Disable the "Chrome is being controlled by automation software" message
			chromeOptions.addArguments("--disable-infobars");
			// Disable browser extensions
			chromeOptions.addArguments("--disable-extensions");
			// Disable browser popups
			chromeOptions.addArguments("--profile.default_content_settings.popups=0");
			// Accept insecure certificates
			chromeOptions.setAcceptInsecureCerts(true);
			chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
			// Automatically dismiss any JavaScript alerts
			chromeOptions.setCapability("unhandledPromptBehavior", "dismiss");

			// Disable the save password prompt using preferences
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			// Set Absolute path of Default Download Directory
			prefs.put("download.default_directory", Folder.getAbsolutePath());
			chromeOptions.setExperimentalOption("prefs", prefs);

			// Mandatory Desired Capabilities
			DesiredCapabilities Cap = new DesiredCapabilities();
			Cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

			chromeOptions.merge(Cap);

			SetDriver(new ChromeDriver(chromeOptions));
			break;

		case "Headless_Chrome":
			//WebDriverManager.chromedriver().setup();

			ChromeOptions headlessOptions = new ChromeOptions();
			// Basic Chrome Options
			headlessOptions.addArguments("--headless=new");
			// Maximize the browser window
			headlessOptions.addArguments("--start-maximized");
			// Set zooming level of browser window
			// headlessOptions.addArguments("--force-device-scale-factor=0.45");

			// Disable the "Chrome is being controlled by automation software" message
			headlessOptions.addArguments("--disable-infobars");
			// Disable browser extensions
			headlessOptions.addArguments("--disable-extensions");
			// Disable browser popups
			headlessOptions.addArguments("--profile.default_content_settings.popups=0");
			// Accept insecure certificates
			headlessOptions.setAcceptInsecureCerts(true);
			headlessOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
			// Automatically dismiss any JavaScript alerts
			headlessOptions.setCapability("unhandledPromptBehavior", "dismiss");

			// Disable the save password prompt using preferences
			Map<String, Object> headlessprefs = new HashMap<>();
			headlessprefs.put("credentials_enable_service", false);
			headlessprefs.put("profile.password_manager_enabled", false);
			// Set Absolute path of Default Download Directory
			headlessprefs.put("download.default_directory", Folder.getAbsolutePath());
			headlessOptions.setExperimentalOption("prefs", headlessprefs);

			// Mandatory Desired Capabilities
			DesiredCapabilities headlessCap = new DesiredCapabilities();
			headlessCap.setCapability(ChromeOptions.CAPABILITY, headlessOptions);

			headlessOptions.merge(headlessCap);

			SetDriver(new ChromeDriver(headlessOptions));
			break;

		case "Edge":
		//	WebDriverManager.edgedriver().setup();

//			System.setProperty(
//		            "webdriver.chrome.driver",
//		            "C:\\Users\\ADMIN\\Documents\\chromedriver.exe");

			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("-inprivate");
			edgeOptions.addArguments("--start-maximized");
			edgeOptions.addArguments("--disable-infobars");
			edgeOptions.addArguments("--force-device-scale-factor=0.8");
			// edgeOptions.addArguments("--disable-features=msHubApps");
			edgeOptions.setExperimentalOption("excludeSwitches", ImmutableList.of("disable-popup-blocking"));
			edgeOptions.setExperimentalOption("useAutomationExtension", false);
			edgeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

			Map<String, Object> edgePrefs = new HashMap<String, Object>();
			edgePrefs.put("enableSidebar", false);
			edgePrefs.put("credentials_enable_service", false);
			edgePrefs.put("profile.password_manager_enabled", false);
			edgePrefs.put("profile.default_content_settings.popups", 0);
			edgePrefs.put("download.default_directory", Folder.getAbsolutePath());
			edgePrefs.put("profile.default_content_setting_values.notifications", 2);
			edgePrefs.put("profile.content_settings.pattern_pairs.*,*.multiple-automatic-downloads", 1);

			edgeOptions.setExperimentalOption("prefs", edgePrefs);

			DesiredCapabilities edgeCapabilities = new DesiredCapabilities();
			edgeCapabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			edgeCapabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
			edgeCapabilities.setCapability("browser", "Edge");
			edgeCapabilities.setCapability("os", "Windows");
			edgeCapabilities.setCapability("os_version", "10");
			edgeCapabilities.setCapability("edgeOptions", edgeOptions);

			edgeOptions.merge(edgeCapabilities);

			SetDriver(new EdgeDriver(edgeOptions));
			break;
		}

		if(browser.equals("Headless_Chrome")) {
			// set the window to the dimension we want, for example 1440 X 900 / 1920 x 1080
						GetDriver().manage().window().setSize(new Dimension(2560, 1440));
		}
		
		GetDriver().manage().window().maximize();
		GetDriver().manage().deleteAllCookies();
		GetDriver().manage().timeouts().pageLoadTimeout(Duration.ofMinutes(3));
		GetDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		File listOfFiles[] = Folder.listFiles();
		if (listOfFiles.length > 0) {
			for (File file : Folder.listFiles()) {
				file.delete();
			}
		}
		Reporter.log("[INFO] [" + CommonUserActions.GetCurrentDateTime() + "] :: '" + browser
				+ "' BROWSER IS LAUNCHED !!!", true);
	}

	// To Open the application URL
	public void CallApplicationURL(String URLName) {

		GetDriver().get(URLName);
		Reporter.log("[INFO] [" + CommonUserActions.GetCurrentDateTime() + "] :: '" + URLName + "' URL HIT !!!",
				true);
	}

	// Check status of test method and set final status to test method
	@AfterMethod
	public void SetStatus(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			TL_extenttest.get().log(Status.FAIL,
					MarkupHelper.createLabel("FAILED TEST CASE IS ::  " + result.getName(), ExtentColor.RED));
			TL_extenttest.get().log(Status.FAIL, MediaEntityBuilder
					.createScreenCaptureFromBase64String(TakeScreenshot.CaptureFullPagescreenshotBase64(GetDriver()))
					.build());
			TL_extenttest.get().fail(new RuntimeException(result.getThrowable()));
			Reporter.log("[TEST METHOD] ["+CommonUserActions.GetCurrentDateTime()+"] :: COMPLETED EXECUTION :: " + result.getName(), true);
		} else if (result.getStatus() == ITestResult.SKIP) {
			TL_extenttest.get().log(Status.SKIP,
					MarkupHelper.createLabel("SKIPPED TEST CASE IS ::  " + result.getName(), ExtentColor.BLUE));
			TL_extenttest.get().fail(new RuntimeException(result.getThrowable()));
			Reporter.log("[TEST METHOD] ["+CommonUserActions.GetCurrentDateTime()+"] :: COMPLETED EXECUTION :: " + result.getName(), true);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			TL_extenttest.get().log(Status.PASS,
					MarkupHelper.createLabel("SUCCESS TEST CASE IS ::  " + result.getName(), ExtentColor.GREEN));
			Reporter.log("[TEST METHOD] ["+CommonUserActions.GetCurrentDateTime()+"] :: COMPLETED EXECUTION :: " + result.getName(), true);
		}
	}

	@AfterClass
	public void TearDown() {
		GetDriver().quit();
		Reporter.log("[INFO] [" + CommonUserActions.GetCurrentDateTime() + "] :: BROWSER IS CLOSED !!!", true);
	}

	@AfterSuite
	public void FlushReport() throws IOException {
		extentreport.flush();
		Desktop.getDesktop().browse(new File(ReportName).toURI());
		Reporter.log("[INFO] [" + CommonUserActions.GetCurrentDateTime() + "] :: TEST REPORT IS READY !!!", true);
	}
}