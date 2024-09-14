package com.util;

import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestNGListeners implements ITestListener, IInvokedMethodListener, ISuiteListener {

	/*
	 * This belongs to ISuiteListener and will execute before the Suite start
	 */
	@Override
	public void onStart(ISuite result) {

		Reporter.log("[SUITE] ["+CommonUserActions.GetCurrentDateTime()+"] :: ABOUT TO BEGIN EXECUTING :: " + result.getName(), true);

	}

	/*
	 * This belongs to ITestListener and will execute before starting of Test
	 * set/batch
	 */
	@Override
	public void onStart(ITestContext result) {

		Reporter.log("[TEST] ["+CommonUserActions.GetCurrentDateTime()+"] :: ABOUT TO BEGIN EXECUTING :: " + result.getName(), true);

	}

	/*
	 * This belongs to ITestListener and will execute before starting of Test METHOD
	 */
	@Override
	public void onTestStart(ITestResult result) {
		Reporter.log("[TEST METHOD] ["+CommonUserActions.GetCurrentDateTime()+"] :: ABOUT TO BEGIN EXECUTING :: " + result.getName(), true);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		printTestResults(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		printTestResults(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		printTestResults(result);
	}

	/*
	 * This is the method which will be executed in case of test pass or fail This
	 * will provide the information on the test
	 */
	private void printTestResults(ITestResult result) {

		Reporter.log("[INFO] ["+CommonUserActions.GetCurrentDateTime()+"] :: Test Method resides in " + result.getTestClass().getName(), true);
		Reporter.log("[INFO] ["+CommonUserActions.GetCurrentDateTime()+"] :: Test Method : " + result.getName(), true);

		if (result.getParameters().length != 0) {

			String params = "";

			for (Object parameter : result.getParameters()) {

				if(result.getParameters().length > 1) {
					params+= ", ";
				}
				
				params += parameter.toString();
			}

			Reporter.log("[INFO] ["+CommonUserActions.GetCurrentDateTime()+"] :: Test Method had the following parameters : " + params, true);

		}

		String status = null;

		switch (result.getStatus()) {

		case ITestResult.SUCCESS:

			status = "PASSED";

			break;

		case ITestResult.FAILURE:

			status = "FAILED";

			break;

		case ITestResult.SKIP:

			status = "SKIPPED";

		}

		Reporter.log("[INFO] ["+CommonUserActions.GetCurrentDateTime()+"] :: Test Status : " + status, true);
	}

	/*
	 * This belongs to ITestListener and will execute, once the Test is finished
	 */
	@Override
	public void onFinish(ITestContext result) {
		Reporter.log("[TEST] ["+CommonUserActions.GetCurrentDateTime()+"] :: COMPLETED EXECUTION :: " + result.getName(), true);
	}

	/*
	 * This belongs to ISuiteListener and will execute, once the Suite is finished
	 */
	@Override
	public void onFinish(ISuite result) {

		Reporter.log("[SUITE] COMPLETED EXECUTION :: " + result.getName(), true);

	}

	/*
	 * This belongs to IInvokedMethodListener and will execute before every method
	 * including @Before @After @Test
	 */
	/*
	 * @Override public void beforeInvocation(IInvokedMethod result, ITestResult
	 * arg1) { Reporter.log(" [ALL] ABOUT TO BEGIN EXECUTING :: " +
	 * returnMethodName(result.getTestMethod()), true); }
	 */

	/*
	 * This belongs to IInvokedMethodListener and will execute after every method
	 * including @Before @After @Test
	 */
	/*
	 * @Override public void afterInvocation(IInvokedMethod result, ITestResult
	 * arg1) { Reporter.log(" [ALL] COMPLETED EXECUTION :: " +
	 * returnMethodName(result.getTestMethod()), true); }
	 * 
	 */

	/*
	 * This will return method names to the calling function private String
	 */
	
//	  private String returnMethodName(ITestNGMethod method) { return
//	  method.getRealClass().getSimpleName() + "." + method.getMethodName();
//	  }
	 
}