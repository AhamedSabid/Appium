package com.rvsappiumautomation.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rvsappiumautomation.actions.ClickActionHelper;
import com.rvsappiumautomation.exception.ExceptionHandler;
import com.rvsappiumautomation.pageobjects.FirstPageObjects;
import com.rvsappiumautomation.runner.TestRunner;
import com.rvsappiumautomation.utils.AutomationConstants;

/**
 * Test cases for the first class should handle here
 * @author rahul.raman
 *
 */
public class FirstPage extends TestRunner{

	//ClickActionHelper clickActHelp = new ClickActionHelper();

	@Test
	public void testCase001() throws ExceptionHandler {
		
		//Updated test created user name
		testCreatedUser("FirstAuthor");
		try {		
		System.out.println("For checking the pass test case");
		
		Assert.assertTrue(true);
		} catch (Exception e) {
			throw new ExceptionHandler(AutomationConstants.EXCEPTION_MESSAGE + getExceptionMessage() + "\n"
					+ AutomationConstants.CAUSETEXT + e.getMessage());
					}
	}

	@Test
	public void testCase002() throws ExceptionHandler {
		
		testCreatedUser("SecondAuthor");
		try {
			ClickActionHelper.clickOnElement(mobiledriver,FirstPageObjects.First_btn);
			
		} catch (Exception e) {
			throw new ExceptionHandler(AutomationConstants.EXCEPTION_MESSAGE + getExceptionMessage() + "\n"
					+ AutomationConstants.CAUSETEXT + e.getMessage());
					}
	}
	
	
	@Test
	public void testCase003() throws ExceptionHandler {
		
		testCreatedUser("SecondAuthor");
		try {
			ClickActionHelper.clickOnElement(mobiledriver,FirstPageObjects.First_btn);
			
		} catch (Exception e) {
			throw new ExceptionHandler(AutomationConstants.EXCEPTION_MESSAGE + getExceptionMessage() + "\n"
					+ AutomationConstants.CAUSETEXT + e.getMessage());
					}
	}

}
