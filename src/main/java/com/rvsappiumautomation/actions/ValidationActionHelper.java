package com.rvsappiumautomation.actions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.rvsappiumautomation.data.PropertiesDataHandler;
import com.rvsappiumautomation.utils.AutomationConstants;

public class ValidationActionHelper {
	static UtilityActionHelper utilActHelp = new UtilityActionHelper();

	/**
	 * This method checks the virtual keyboard is active or not, currently it works
	 * only for Android platform
	 * 
	 * @author kiran.mg
	 * @return isKeyboardPresent
	 * @param driver
	 * @throws Exception
	 */
	public boolean isKeyboardPresent(WebDriver driver) throws Exception {
		boolean isKeyboardPresent = false;
		String DeviceUDID = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "udid");
		try {
			if (driver != null) {
				if (PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "PlatformName")
						.equalsIgnoreCase("android")) {
					String checkkeyboard = "adb -s " + DeviceUDID + " shell dumpsys input_method | grep mInputShown";
					Process pb = Runtime.getRuntime().exec(checkkeyboard);

					BufferedReader in = new BufferedReader(new InputStreamReader(pb.getInputStream()));
					String temp = null;
					List<String> line = new ArrayList<String>();
					while ((temp = in.readLine()) != null) {
						line.add(temp);
					}
					if (line.get(0).contains("mInputShown=true") == true) {
						isKeyboardPresent = true;
					} else {
						isKeyboardPresent = false;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
		return isKeyboardPresent;
	}

	/**
	 * 
	 * This method returns WiFi status - Applicable for Mobile Native Android
	 * Applications
	 * 
	 * @author kiran.mg
	 * @param driver
	 * @return isWiFiAvailable
	 * @throws Exception
	 */
	public boolean isWiFiAvailable(WebDriver driver) throws Exception {
		boolean isWiFiAvailable = false;
		String DeviceUDID = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "udid");
		try {
			if (driver != null) {
				if (PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "PlatformName")
						.equalsIgnoreCase("android")) {
					Process togetWiFiStatus = Runtime.getRuntime()
							.exec("adb -s " + DeviceUDID + " shell dumpsys wifi | grep Wi-Fi");
					togetWiFiStatus.waitFor();
					BufferedReader outputreader = new BufferedReader(
							new InputStreamReader(togetWiFiStatus.getInputStream()));
					String singleLine;
					while ((singleLine = outputreader.readLine()) != null) {
						if (singleLine.contains("Wi-Fi is enabled")) {
							isWiFiAvailable = true;
							break;
						} else if (singleLine.contains("Wi-Fi is disabled")) {
							isWiFiAvailable = false;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
		return isWiFiAvailable;
	}

	/**
	 * 
	 * This method is to check whether the element is displayed or not
	 * 
	 * @author kiran.mg
	 * @param driver
	 * @param elementName
	 * @return elementDisplayed
	 * @throws Exception
	 */
	public boolean isElementDisplayed(WebDriver driver, String elementName) throws Exception {
		boolean elementDisplayed = false;
		try {
			if (driver != null) {
				WebElement element = utilActHelp.getElementValue(driver, elementName);
				if (element.isDisplayed()) {
					elementDisplayed = true;
				} else {
					elementDisplayed = false;
				}

			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return elementDisplayed;
	}

	/**
	 * Function to check whether the element is selected in Android and iOS
	 *
	 * @author cyril.kjohn
	 * @param driver
	 * @param elementName
	 * @return elementSelected (boolean)
	 * @throws Exception
	 */

	public boolean isElementSelected(WebDriver driver, String elementName) throws Exception {
		boolean elementSelected = false;

		try {
			if (driver != null) {
				WebElement element = utilActHelp.getElementValue(driver, elementName);
				if (element.isSelected()) {
					elementSelected = true;
				} else {
					elementSelected = false;
				}
			} else {
				throw new Exception();
			}

		} catch (Exception e) {

			throw new Exception(e.getMessage());
		}
		return elementSelected;
	}

	/**
	 * Function to check whether an element is present using its locators details.
	 * 
	 * @author babitha.baby
	 * @param driver
	 * @param elementName
	 * @throws Exception
	 */

	public boolean IsElementPresent(WebDriver driver, String elementName) throws Exception

	{
		boolean elementPresent = false;
		try {

			if (driver != null) {
				WebElement element = utilActHelp.getElementValue(driver, elementName);
				if (element != null) {
					elementPresent = true;
				} else {
					elementPresent = false;
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return elementPresent;
	}

	/**
	 * 
	 * This method is to check whether the element is displayed or not
	 * 
	 * @author athira.ts
	 * @param driver
	 * @param elementName
	 * @return checkBoxEnabled
	 * @throws Exception
	 */
	public boolean isCheckBoxEnabled(WebDriver driver, String elementName) throws Exception {
		boolean checkBoxEnabled = false;
		try {
			if (driver != null) {
				WebElement element = utilActHelp.getElementValue(driver, elementName);
				if (element.isEnabled()) {
					checkBoxEnabled = true;
				} else {
					checkBoxEnabled = false;
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return checkBoxEnabled;
	}
}
