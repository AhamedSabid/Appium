package com.rvsappiumautomation.testcases;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.rvsappiumautomation.exception.ExceptionHandler;
import com.rvsappiumautomation.runner.TestRunner;
import com.rvsappiumautomation.utils.AutomationConstants;

/**
 * Test cases for the second class should handle here
 * @author rahul.raman
 *
 */
public class SecondPage extends TestRunner {
	
	@Test
	public void testCase004() {
		testCreatedUser("FirstAuthor");
	throw new SkipException("Skipping – This is not ready for testing ");
	}

	@Test
	public void testCase005() throws ExceptionHandler {
		testCreatedUser("SecondAuthor");
		try
		{
		System.out.println("For checking the pass test case");
		Assert.assertTrue(true);
		} catch (Exception e) {
			throw new ExceptionHandler(AutomationConstants.EXCEPTION_MESSAGE + getExceptionMessage() + "\n"
					+ AutomationConstants.CAUSETEXT + e.getMessage());
					}
	}
	
	@Test
	public void testCase006() throws ExceptionHandler {
		testCreatedUser("FirstAuthor");
		try {
		System.out.println("<=====Intentionally failing for testing purpose====>");
		Assert.assertTrue(false);
		} catch (Exception e) {
			throw new ExceptionHandler(AutomationConstants.EXCEPTION_MESSAGE + getExceptionMessage() + "\n"
					+ AutomationConstants.CAUSETEXT + e.getMessage());
					}
		}
	}	
		

