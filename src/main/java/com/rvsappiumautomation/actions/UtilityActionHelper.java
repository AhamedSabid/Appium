
package com.rvsappiumautomation.actions;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.rvsappiumautomation.base.AutomationBase;
import com.rvsappiumautomation.data.PropertiesDataHandler;
import com.rvsappiumautomation.exception.ExceptionHandler;
import com.rvsappiumautomation.utils.AutomationConstants;
import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.HidesKeyboard;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class UtilityActionHelper extends AutomationBase {

	// WebDriver driver;
	// private static AppiumDriverLocalService appiumService;

	/**
	 * Generic function to handle the Android and iOS Element locators details
	 * 
	 * @author rahul.raman
	 * @param driver
	 * @param elementName
	 * @return
	 * @throws Exception
	 */
	public WebElement getElementValue(WebDriver driver, String elementName) throws Exception {
		WebElement element = null;
		String waittime = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "timeout");
		long time = Long.parseLong(waittime);
		WebDriverWait wait = new WebDriverWait(driver, time);

		String elementValue = null;
		String CurrentPlatform = (String) ((RemoteWebDriver) driver).getCapabilities().getCapability("platformName");
		if (CurrentPlatform.contains("Android") || CurrentPlatform.equalsIgnoreCase("Android")) {
			elementValue = PropertiesDataHandler.getProperty(AutomationConstants.ANDROID_OBJECTS, elementName);
		} else if (CurrentPlatform.contains("iOS")) {
			elementValue = PropertiesDataHandler.getProperty(AutomationConstants.iOS_OBJECTS, elementName);
		} else {
			System.out.println("Current Platform is =" + CurrentPlatform + ">>>>>>>>>>>>>>>>>>>>");
			System.out.println(
					"Please check the current running platform, Unable to identify objects for the current platform");
		}

		try {
			if (elementValue.startsWith(".//") || elementValue.startsWith("//") || elementValue.startsWith("(//")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementValue)));

			} else {

				if (elementValue.startsWith("AccessId")) {
					element = wait.until(ExpectedConditions.visibilityOf(
							((IOSDriver<?>) driver).findElementByAccessibilityId(elementValue.split(">>")[1])));

				}

				if (elementValue.startsWith("contains")) {
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath(("//*[contains(@value, '" + (elementValue.split(">>")[1])
									+ "') or contains(@text, '" + (elementValue.split(">>")[1])
									+ "') or contains(@name, '" + (elementValue.split(">>")[1])
									+ "') or contains(@label, '" + (elementValue.split(">>")[1]) + "')]"))));

				}

				if (elementValue.startsWith("name")) {
					Thread.sleep(3000);

					/*
					 * element = ((AndroidDriver<?>) driver).findElementByAndroidUIAutomator(
					 * "new UiSelector().text(\"" + elementValue.split(">>")[1] + "\")");
					 */
					element = wait.until(
							ExpectedConditions.visibilityOf(((AndroidDriver<?>) driver).findElementByAndroidUIAutomator(
									"new UiSelector().text(\"" + elementValue.split(">>")[1] + "\")")));

				}

				if (elementValue.startsWith("id")) {
					element = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementValue.split(">>")[1])));

				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());

		}

		return element;

	}

	/// To hide the keyboard
	/// Author: Rucha
	/// Since: 3rd-Jan-2020
	/// <returns></returns>

	public void hideKeyboard(WebDriver driver) throws Exception {

		String CurrentPlatform = (String) ((RemoteWebDriver) driver).getCapabilities().getCapability("platformName");
		if (CurrentPlatform.contains("Android") || CurrentPlatform.equalsIgnoreCase("Android")) {
			if (((HasOnScreenKeyboard) mobiledriver).isKeyboardShown()) {
				((HidesKeyboard) mobiledriver).hideKeyboard();
			} else {
				System.out.println("Keyboard not present");
			}
		} else if (CurrentPlatform.contains("iOS")) {
			try {
				((IOSDriver) mobiledriver).hideKeyboard();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println(
						"Either keyboard is not present or the hidekeyboard function having issue in this Environment");
			}
		}

		else {
			System.out.println("Current Platform is =" + CurrentPlatform + ">>>>>>>>>>>>>>>>>>>>");
			System.out.println(
					"##########********===Please check the current running platform, Unable to identify objects for the current platform===**********###########");
		}

	}

	/// To kill the app and relaunch it again for Android App
	/// Author: Rucha
	/// Since: 3rd-Jan-2020
	/// <returns></returns>

	public void killAndRelaunchAndroidApp(WebDriver driver) throws Exception {
		try {
			((AndroidDriver<?>) driver).resetApp();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	/// To kill the app and relaunch it again for iOS App
	/// Author: Rucha
	/// Since: 3rd-Jan-2020
	/// <returns></returns>

	public void killAndRelaunchiOSApp(WebDriver driver) throws Exception {
		try {
			((IOSDriver<?>) driver).resetApp();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// To Swipe down on device
	/// Author: Lallu
	/// Since: 3rd-Jan-2020
	/// <returns></returns>

	public void scrollDown() throws Exception {

		TouchAction ts = new TouchAction((PerformsTouchActions) mobiledriver);
		Thread.sleep(3000);
		Dimension size = mobiledriver.manage().window().getSize();
		int endy = (int) (size.height * 0.60);
		int starty = (int) (size.height * 0.40);
		int startx = size.width / 2;
		ts.press(PointOption.point(startx, starty)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1300)))
				.moveTo(PointOption.point(startx, endy)).release();
		ts.perform();

	}

	// To Swipe up on device
	/// Author: Lallu
	/// Since: 3rd-Jan-2020
	/// <returns></returns>

	public void scrollUp() throws Exception {

		TouchAction ts = new TouchAction((PerformsTouchActions) mobiledriver);
		Thread.sleep(3000);
		Dimension size = mobiledriver.manage().window().getSize();
		int starty = (int) (size.height * 0.60);
		int endy = (int) (size.height * 0.40);
		int startx = size.width / 2;
		ts.press(PointOption.point(startx, starty)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1300)))
				.moveTo(PointOption.point(startx, endy)).release();
		ts.perform();

	}

	/**
	 * Generic function to handle Airplane mode in Android devices
	 * 
	 * @author lallu
	 * @param status
	 * @return
	 */
	public static void setAndroidDeviceAirplaneMode(boolean status) {

		try {
			String airplaneModeStatus = "";

			if (status) {
				airplaneModeStatus = "1";
			} else {
				airplaneModeStatus = "0";
			}

			String sdkPath = System.getenv("ANDROID_HOME") + "/platform-tools/";
			Runtime.getRuntime().exec(sdkPath + "adb shell settings put global airplane_mode_on " + airplaneModeStatus);

			Thread.sleep(1000);
			Process process = Runtime.getRuntime()
					.exec(sdkPath + "adb shell am broadcast -a android.intent.action.AIRPLANE_MODE");

			process.waitFor();
			Thread.sleep(4000);

			if (status) {
				System.out.println("Android device Airplane mode status is set to ON");
			} else {
				System.out.println("Android device Airplane mode status is set to OFF");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage() + "Unable to set android device Airplane mode.");
		}

	}

	/**
	 * Function to handle the device screenshot
	 * 
	 * @author rahul.raman
	 * @param fileName
	 * @throws Exception
	 */
	public static String screenshotCapture(String fileName) throws Exception {
		String destination = null;
		try {
			File scrFile = ((TakesScreenshot) mobiledriver).getScreenshotAs(OutputType.FILE);

			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar cal = Calendar.getInstance();

			File currentDirFile = new File("Screenshots");
			String path = currentDirFile.getAbsolutePath();
			destination = path + "/" + fileName + dateFormat.format(cal.getTime()) + ".png";

			FileUtils.copyFile(scrFile, new File(destination));

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return destination;
	}

	/**
	 * Generic function to restartDevice
	 * 
	 * @author lallu
	 * @param driver
	 * @return
	 */

	public void restartDevice(WebDriver driver) throws ExceptionHandler {
		try {
			if (driver == null || driver.equals("")) {
				throw new ExceptionHandler("Driver is null");
			}
		} catch (Exception e) {
			e = new Exception(e.getMessage());
			throw new ExceptionHandler(e);
		}
		try {
			Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
			if (driver != null) {

				if (cap.getCapability("platformName").toString().equalsIgnoreCase("Android")
						|| cap.getCapability("platformName").toString().equalsIgnoreCase("LINUX")) {
					Process process = Runtime.getRuntime()
							.exec("adb -s " + cap.getCapability("udid").toString() + " reboot");
					process.waitFor();
					process.destroy();
				} else {
					Process process = Runtime.getRuntime()
							.exec("idevicediagnostics -u " + cap.getCapability("udid").toString() + " restart");
					process.waitFor();
					process.destroy();
				}
			}
		} catch (Exception lException) {
			throw new ExceptionHandler(lException);
		}

	}

	/**
	 * Function to handle the device swipe
	 * 
	 * @author muhammed.nisarpk
	 * @param driver
	 * @param element
	 * @param start
	 *            point
	 * @param element
	 *            end point
	 * @return
	 * @throws Exception
	 */
	public static void swipe(WebDriver driver, int x, int y) throws Exception {

		try {
			TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
			touchAction.tap(point(x, y)).waitAction(waitOptions(Duration.ofMillis(250))).perform();
		}

		catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Function to handle the device swipeFromLeftToRight
	 * 
	 * @author muhammed.nisarpk
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	public static void swipeFromLeftToRight(WebDriver driver) throws Exception {

		try {
			TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);

			Dimension size = driver.manage().window().getSize();

			int startx = (int) (size.width * 0.70);

			int endx = (int) (size.width * 0.30);

			int anchor = (int) (size.height / 2);

			touchAction.press(PointOption.point(startx, anchor))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1300)))

					.moveTo(PointOption.point(endx, anchor)).release().perform();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Function to handle the device swipeFromRightToLeft
	 * 
	 * @author muhammed.nisarpk
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	public static void swipeFromRightToLeft(WebDriver driver) throws Exception {

		try {
			TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);

			Dimension size = driver.manage().window().getSize();

			int startx = (int) (size.width * 0.70);

			int endx = (int) (size.width * 0.30);

			int anchor = (int) (size.height / 2);

			touchAction.press(PointOption.point(endx, anchor))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1300)))

					.moveTo(PointOption.point(startx, anchor)).release().perform();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Function to handle the device swipeFromBottomToTop
	 * 
	 * @author muhammed.nisarpk
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	public static void swipeFromBottomToTop(WebDriver driver) throws Exception {

		try {
			TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);

			Dimension size = driver.manage().window().getSize();

			int starty = (int) (size.height * 0.70);

			int endy = (int) (size.height * 0.40);

			int anchor = (int) (size.width / 2);

			touchAction.press(PointOption.point(anchor, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1300)))
					.moveTo(PointOption.point(anchor, endy)).release().perform();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Function to handle the device swipeFromTopToBottom
	 * 
	 * @author muhammed.nisarpk
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	public static void swipeFromTopToBottom(WebDriver driver) throws Exception {

		try {
			TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);

			Dimension size = driver.manage().window().getSize();

			int starty = (int) (size.height * 0.70);

			int endy = (int) (size.height * 0.40);

			int anchor = (int) (size.width / 2);

			touchAction.press(PointOption.point(anchor, endy))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1300)))
					.moveTo(PointOption.point(anchor, starty)).release().perform();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Function to clear the field
	 * 
	 * @author kiranantony
	 * @param elementName
	 * @throws Exception
	 */
	public void clearFiled(WebDriver driver, String elementName) throws Exception {
		WebElement element = getElementValue(driver, elementName);
		try {
			element.clear();
		} catch (NoSuchElementException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Function returns the element text
	 * 
	 * @author kiranantony
	 * @param elementName
	 * @throws Exception
	 */
	public String getElementText(WebDriver driver, String elementName) throws Exception {
		WebElement element = getElementValue(driver, elementName);
		try {
			return element.getText();
		} catch (NoSuchElementException e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Function to drag an element and drop on another element
	 * 
	 * @author kiranantony
	 * @param fromElementName,toElementName
	 * @throws Exception
	 */

	@SuppressWarnings("rawtypes")
	public void dragAndDrop(WebDriver driver, String fromElementName, String toElementName) throws Exception {
		WebElement ele1 = (WebElement) getElementValue(driver, fromElementName);
		WebElement ele2 = (WebElement) getElementValue(driver, toElementName);
		TouchAction action = new TouchAction((PerformsTouchActions) driver);
		action.longPress(ElementOption.element(ele1)).waitAction().moveTo(ElementOption.element(ele2)).release()
				.perform();
	}

	/**
	 * Function to get all element text
	 * 
	 * @author kiranantony
	 * @param toElementName
	 * @throws Exception
	 */

	@SuppressWarnings("null")
	public List<String> getAllElementsTexts(WebDriver driver, String elementName) throws Exception {

		List<WebElement> elements = getElements(driver, elementName);
		List<String> text = null;

		for (WebElement element : elements) {
			text.add(element.getText());

		}

		return text;

	}

	/**
	 * Function to move to an element
	 * 
	 * @author kiranantony
	 * @param elementName
	 * @throws Exception
	 */
	public void moveToElement(WebDriver driver, String elementName) throws Exception {
		WebElement element = getElementValue(driver, elementName);
		TouchActions action = new TouchActions(driver);

		action.moveToElement(element);

	}

	/**
	 * Function to get the window size
	 * 
	 * @author kiranantony
	 * @param elementName
	 * @throws Exception
	 */
	public Dimension getScreenSize(WebDriver driver) throws Exception {
		Dimension windowSize = driver.manage().window().getSize();
		return windowSize;

	}

	/**
	 * Function to get the screen width
	 * 
	 * @author kiranantony
	 * @param elementName
	 * @throws Exception
	 */

	public int getScreenWidth(WebDriver driver) throws Exception {
		int width = driver.manage().window().getSize().getWidth();
		return width;
	}

	/**
	 * Function to get the screen height
	 * 
	 * @author kiranantony
	 * @param elementName
	 * @throws Exception
	 */
	public int getScreenHeight(WebDriver driver) throws Exception {
		int height = driver.manage().window().getSize().getHeight();
		return height;
	}

	/**
	 * Function to get the element size
	 * 
	 * @author kiranantony
	 * @param elementName
	 * @throws Exception
	 */
	public Dimension getElementSize(WebDriver driver, String elementName) throws Exception {
		WebElement element = getElementValue(driver, elementName);
		Dimension elementSize = element.getSize();
		return elementSize;
	}

	/**
	 * Generic function to handle the Android and iOS Element locators details which
	 * return a list of web elements
	 * 
	 * @author kiranantony
	 * @param elementName
	 * @throws Exception
	 */

	public List<WebElement> getElements(WebDriver driver, String elementName) throws Exception {
		List<WebElement> elements = null;
		String waittime = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "timeout");
		long time = Long.parseLong(waittime);
		WebDriverWait wait = new WebDriverWait(driver, time);

		String elementValue = null;
		String CurrentPlatform = (String) ((RemoteWebDriver) driver).getCapabilities().getCapability("platformName");
		if (CurrentPlatform.contains("Android") || CurrentPlatform.equalsIgnoreCase("Android")) {
			elementValue = PropertiesDataHandler.getProperty(AutomationConstants.ANDROID_OBJECTS, elementName);
		} else if (CurrentPlatform.contains("iOS")) {
			elementValue = PropertiesDataHandler.getProperty(AutomationConstants.iOS_OBJECTS, elementName);
		} else {
			System.out.println("Current Platform is =" + CurrentPlatform + ">>>>>>>>>>>>>>>>>>>>");
			System.out.println(
					"##########********===Please check the current running platform, Unable to identify objects for the current platform===**********###########");
		}

		try {
			if (elementValue.startsWith(".//") || elementValue.startsWith("//") || elementValue.startsWith("(//")) {
				elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(elementValue)));

			} else {
				if (elementValue.startsWith("AccessId")) {
					elements = wait.until(ExpectedConditions.visibilityOfAllElements(
							((IOSDriver<?>) driver).findElementByAccessibilityId(elementValue.split(">>")[1])));
				}

				if (elementValue.startsWith("contains")) {
					elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
							By.xpath(("//*[contains(@value, '" + (elementValue.split(">>")[1])
									+ "') or contains(@text, '" + (elementValue.split(">>")[1])
									+ "') or contains(@name, '" + (elementValue.split(">>")[1])
									+ "') or contains(@label, '" + (elementValue.split(">>")[1]) + "')]"))));
				}

				if (elementValue.startsWith("name")) {
					Thread.sleep(3000);
					elements = wait.until(ExpectedConditions
							.visibilityOfAllElements(((AndroidDriver<?>) driver).findElementByAndroidUIAutomator(
									"new UiSelector().text(\"" + elementValue.split(">>")[1] + "\")")));
				}

				if (elementValue.startsWith("id")) {
					elements = wait.until(
							ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(elementValue.split(">>")[1])));
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return elements;
	}
	
	/**
	 * Function to get the device log
	 * @author rahul.raman
	 * @param driver
	 * @throws IOException 
	 */
	public static void getDeviceLog(WebDriver driver) throws IOException {
		String DEVICE_LOG_FOLDER=PropertiesDataHandler.getProperty(AutomationConstants.EMAIL_CONFIG, "DEVICE_LOG_FOLDER");
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		String line = null;
		try {
			if (driver == null || driver.equals("")) {
				System.out.println("driver is getting as null");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			if (driver != null) {
				DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH-mm-ss");
				Calendar cal = Calendar.getInstance();

				String CurrentPlatform = (String) ((RemoteWebDriver) driver).getCapabilities()
						.getCapability("platformName");
				if (CurrentPlatform.contains("Android")) {
					String deviceUDID = (String) ((RemoteWebDriver) driver).getCapabilities().getCapability("udid").toString();
					new File(new File(System.getProperty("user.dir")) + DEVICE_LOG_FOLDER).mkdirs();
					File logFile = null;
					logFile = new File(System.getProperty("user.dir") + DEVICE_LOG_FOLDER + "aLogcat" + "_"
							+ deviceUDID + "_Android_" + dateFormat.format(cal.getTime()) + ".txt");
					if (!logFile.exists()) {
						logFile.createNewFile();
					}
					Process process = Runtime.getRuntime().exec("adb -s " + deviceUDID + " logcat -d");
					bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					fileWriter = new FileWriter(logFile.getAbsoluteFile(), false);
					bufferedWriter = new BufferedWriter(fileWriter);
					while (((line = bufferedReader.readLine()) != null)) {
						if (!(line.isEmpty())) {
							bufferedWriter.write(line);
							bufferedWriter.write("\n");
						}
					}
					process.waitFor();
					process.destroy();
				}
				if (CurrentPlatform.contains("iOS")) {
					String deviceUDID = (String) ((RemoteWebDriver) driver).getCapabilities().getCapability("udid").toString();
					new File(new File(System.getProperty("user.dir")) + DEVICE_LOG_FOLDER).mkdirs();
					File logFile = null;
					logFile = new File(System.getProperty("user.dir") + DEVICE_LOG_FOLDER + "aLogcat"
							+ "_iOS_" + deviceUDID + "_" + dateFormat.format(new Date()) + ".txt");
					if (!logFile.exists()) {
						logFile.createNewFile();
					}
					Process process = Runtime.getRuntime().exec("idevicesyslog -u " + deviceUDID);
					bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					fileWriter = new FileWriter(logFile.getAbsoluteFile(), false);
					bufferedWriter = new BufferedWriter(fileWriter);
					while ((line = bufferedReader.readLine()) != null) {
						if (!(line.isEmpty())) {
							bufferedWriter.write(line);
							bufferedWriter.write("\n");
							Thread.sleep(5);
							break;
						}
					}
					process.waitFor();
					process.destroy();
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
