package com.rvsappiumautomation.runner;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.rvsappiumautomation.actions.UtilityActionHelper;
import com.rvsappiumautomation.base.AutomationBase;
import com.rvsappiumautomation.exception.ExceptionHandler;
import com.rvsappiumautomation.utils.AutomationMail;

/**
 * This is the runner class of our framework
 * @author rahul.raman
 *
 */
public class TestRunner extends AutomationBase {
	// TestCase test;

	@BeforeSuite
	public void launchApp() throws IOException, ExceptionHandler {
		appLaunch_SetUp_InDevice();
	}

	@BeforeSuite
	public void loadHTMLConfigFile() throws IOException {
		createHTMLReportTemplate();
	}

	@BeforeMethod
	public void currentRunningTestName(Method method) {
		collectCurrentTestCaseName(method);
	}

	@AfterMethod
	public void collectingTestResult(ITestResult result, Method method) throws Exception {
		getTestResult(result);
	}

	@AfterTest
	public void collectingDeviceLog() throws IOException {
		mobiledriver.quit();
		UtilityActionHelper.getDeviceLog(mobiledriver);
	}

	@AfterSuite
	public void endReportGeneration() throws AddressException, MessagingException, IOException {
		// stopAppiumServer();
		endReporting();
		AutomationMail.sendMailReport();
	}
}
