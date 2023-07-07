package com.rvsappiumautomation.actions;

import com.rvsappiumautomation.base.AutomationBase;

public class SendKeyActionHelper extends AutomationBase {
	static UtilityActionHelper utilActHelp = new UtilityActionHelper();

	/**
	 * Function to send value to a field once after clearing the existing text in
	 * that field
	 * 
	 * @author rahul.raman
	 * @param ElementDetails
	 * @param InputValue
	 * @throws Exception
	 */
	public void clearAndSetValue(String ElementDetails, String InputValue) throws Exception {
		try {
			Thread.sleep(1000);
			if (utilActHelp.getElementValue(mobiledriver, ElementDetails).isDisplayed()) {
				utilActHelp.getElementValue(mobiledriver, ElementDetails).clear();
				utilActHelp.getElementValue(mobiledriver, ElementDetails).sendKeys(InputValue);
				Thread.sleep(1000);
			} else {
				System.out.println("Required text entering field is not displayed in the page");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Function to send text to an input field
	 * 
	 * @author rahul.raman
	 * @param ElementDetails
	 * @param InputValue
	 * @throws Exception
	 */
	public void setValue(String ElementDetails, String InputValue) throws Exception {
		try {
			Thread.sleep(1000);
			if (utilActHelp.getElementValue(mobiledriver, ElementDetails).isDisplayed()) {
				utilActHelp.getElementValue(mobiledriver, ElementDetails).sendKeys(InputValue);
				Thread.sleep(1000);
			} else {
				System.out.println("Required text entering field is not displayed in the page");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
