package com.rvsappiumautomation.actions;

import org.openqa.selenium.Point;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.rvsappiumautomation.base.AutomationBase;

public class ClickActionHelper extends AutomationBase {
	static UtilityActionHelper utilActHelp = new UtilityActionHelper();

	/**
	 * Function to click on a Element using its locators details.
	 * 
	 * @author kiran.mg
	 * @param driver
	 * @param elementName
	 * @throws Exception
	 */
	public static void clickOnElement(WebDriver driver, String elementName) throws Exception {

		try {
			if (driver != null) {

				WebElement Element = utilActHelp.getElementValue(driver, elementName);
				Element.click();
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Function to perform double-click on a Element using its locators details.
	 * 
	 * @author babitha.baby
	 * @param driver
	 * @param elementName
	 * @throws Exception
	 */

	public void doubleClick(WebDriver driver, String elementName) throws Exception {

		try {
			Actions action = new Actions(driver);
			WebElement Element = utilActHelp.getElementValue(driver, elementName);

			// action.moveTo(Element);
			action.doubleClick(Element);
			action.perform();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Function to click on an element when only its location details (x-y
	 * coordinates) are available
	 *
	 * @author cyril.kjohn
	 * @param driver
	 * @param X-Coordinate
	 *            value
	 * @param Y-Coordinate
	 *            value
	 * @return
	 * @throws Exception
	 */

	public void clickByLocation(WebDriver driver, int xcoordinate, int ycoordinate) throws Exception {

		try {

			Actions Act = new Actions(driver);
			Act.moveByOffset(xcoordinate, ycoordinate).click().build().perform();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Function to find element by its x-y coordinates and click on that location
	 *
	 * @author cyril.kjohn
	 * @param driver
	 * @param elementname
	 * @return
	 * @throws Exception
	 */
	public void clickByExistingElementLocation(WebDriver driver, String elementName) throws Exception {

		try {

			int xc, yc = 0;
			WebElement Element = utilActHelp.getElementValue(driver, elementName);
			Point point = Element.getLocation();
			xc = point.getX();
			yc = point.getY();

			Actions Act = new Actions(driver);
			Act.moveByOffset(xc, yc).click().build().perform();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Function to perform Click And Hold on a Element using its locators details.
	 * 
	 * @author athira.ts
	 * @param driver
	 * @param elementName
	 * @throws Exception
	 */
	public void ClickAndHold(WebDriver driver, String elementName) throws Exception {

		try {

			Actions action = new Actions(driver);
			WebElement Element = utilActHelp.getElementValue(driver, elementName);
			action.clickAndHold(Element).build().perform();
			action.moveToElement(Element).release();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Function to perform click On an Element Using JS on a Element using its
	 * locators details.
	 * 
	 * @author athira.ts
	 * @param driver
	 * @param elementName
	 * @throws Exception
	 */
	public void clickOnElementUsingJS(WebDriver driver, String elementName) throws Exception {
		try {

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			WebElement Element = utilActHelp.getElementValue(driver, elementName);
			jse.executeScript("arguments[0].click();", Element);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Function to multi click on an Element multiple times using its locators
	 * details.
	 * 
	 * @author babitha.baby
	 * @param driver
	 * @param elementName
	 * @throws Exception
	 */
	public void MultiClick(WebDriver driver, String elementName) throws Exception {

		try {
			Actions action = new Actions(driver);
			WebElement Element = utilActHelp.getElementValue(driver, elementName);

			for (int i = 1; i <= 4; i++) {
				action.click(Element);
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
