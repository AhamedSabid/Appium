package com.rvsappiumautomation.reporting;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.rvsappiumautomation.actions.UtilityActionHelper;
import com.rvsappiumautomation.data.PropertiesDataHandler;
import com.rvsappiumautomation.utils.AutomationConstants;

/**
 * Class to handle the extent report
 * 
 * @author rahul.raman
 *
 */
public class AutomationReport {
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extentReport;
	public static ExtentTest extentTest;

	public void createHTMLReportTemplate() throws IOException {
		String platformName = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "PlatformName");
		// In before suite we are creating HTML report template, adding basic
		// information to it and load the extent-config.xml file

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
		Date date = new Date();
		String filePathdate = dateFormat.format(date).toString();
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/AutomationReport/"
				+ platformName + "_" + filePathdate + "_AppiumExtentReport.html");
		// Create an object of Extent Reports
		extentReport = new ExtentReports();
		extentReport.attachReporter(htmlReporter);
		extentReport.setSystemInfo("Machine Name", System.getProperty("os.name"));
		extentReport.setSystemInfo("Environment", platformName);
		extentReport.setSystemInfo("User Name", System.getProperty("user.name"));
		extentReport.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		extentReport.setSystemInfo("Java Version", System.getProperty("java.version"));
		htmlReporter.config().setDocumentTitle("Appium Automation");
		// Name of the report
		htmlReporter.config().setReportName("AutoBots Appium Execution Report");
		// Dark Theme
		htmlReporter.config().setTheme(Theme.STANDARD);
	}

	public void collectCurrentTestCaseName(Method method) {
		// In before method we are collecting the current running test case name
		String className = this.getClass().getSimpleName();
		extentTest = extentReport.createTest(className + "-" + method.getName());
	}


	public void getTestResult(ITestResult result) throws Exception {
		String methodName = result.getName();
		ExtentTest node = extentTest.createNode(methodName);
		if (result.getStatus() == ITestResult.FAILURE) {
			// MarkupHelper is used to display the output in different colors
			extentTest.log(Status.FAIL,
					MarkupHelper.createLabel(methodName + " - Test Case Failed", ExtentColor.RED));
			extentTest.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
			// To capture screenshot path and store the path of the screenshot in the string
			// "screenshotPath"
			// We do pass the path captured by this method in to the extent reports using
			// "extentTest.addScreenCapture" method.
			String screenshotPath = UtilityActionHelper.screenshotCapture(result.getName());
			// To add it in the extent report
			extentTest
					.fail("Test Case Failed Snapshot is below " + extentTest.addScreenCaptureFromPath(screenshotPath));
			
			node.fail(methodName + "Test Step Failed");
			
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
			node.skip(methodName + "Test Step Skipped");
			
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
			node.pass(methodName + "Test Step Passed");				
		}
	}

	public void endReporting() {
		// In after suite stopping the object of ExtentReports and ExtentTest
		// extentReport.endTest(extentTest);
		extentReport.flush();
	}
	
	

	/**
	 * Function to update the test case created user name
	 * 
	 * @author rahul.raman
	 * @param developerName
	 */
	public void testCreatedUser(String developerName) {
		extentTest.assignAuthor(developerName);
	}
}
