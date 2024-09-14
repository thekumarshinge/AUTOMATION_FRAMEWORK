package com.test;

import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.base.Driver;
import com.page.LoginPage;
import com.testdata.CommonTestDataFactory;
import com.testutil.GetTestClassAndTestMethodName;
import com.util.TakeScreenshot;

@Listeners(com.util.TestNGListeners.class)
public class HomePageTestClass extends Driver {

	@Parameters("role")
	@Test(priority = 1, enabled = true, alwaysRun = true, description = "Login Into APP")
	public void APP_001(@Optional("User") String role) throws Exception {

		extenttests = extentreport
				.createTest(GetTestClassAndTestMethodName.getCurrentClassName() + " :: "
						+ GetTestClassAndTestMethodName.getCurrentMethodName(), "LogIn Into App.")
				.assignCategory("App");
		TL_extenttest.set(extenttests);

		LoginPage lp = new LoginPage(GetDriver());

		lp.LaunchURL(CommonTestDataFactory.url(1));

		TL_extenttest.get().log(Status.PASS, "<b>'<i>APP</i>'</b> Login Screen is displayed.", MediaEntityBuilder
				.createScreenCaptureFromBase64String(TakeScreenshot.CapturescreenshotBase64(GetDriver())).build());

		lp.LoginToApp(CommonTestDataFactory.username(1), CommonTestDataFactory.password(1));
	}

	@Parameters("role")
	@Test(priority = 2, enabled = true, dependsOnMethods = {"APP_001"}, description = "Verify Home Screen")
	public void APP_002(@Optional("Admin") String role) throws Exception {
		extenttests = extentreport
				.createTest(
						GetTestClassAndTestMethodName.getCurrentClassName() + " :: "
								+ GetTestClassAndTestMethodName.getCurrentMethodName(),
						"User should be able to view home screen. <b>(For '" + role + "' Role)</b>.")
				.assignCategory("home");
		TL_extenttest.set(extenttests);

		
		
		
	}



}