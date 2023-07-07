package com.rvsappiumautomation.base;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.rvsappiumautomation.data.PropertiesDataHandler;
import com.rvsappiumautomation.exception.ExceptionHandler;
import com.rvsappiumautomation.reporting.AutomationReport;
import com.rvsappiumautomation.utils.AutomationConstants;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AutomationBase extends AutomationReport {
	public static WebDriver mobiledriver;
	private static AppiumDriverLocalService service;
	private static AppiumServiceBuilder builder;
	private static DesiredCapabilities cap;

	public static void launchAppiumServer() throws IOException {
		cap = new DesiredCapabilities();
		cap.setCapability("noReset", "false");
		
		String AppiumIPaddress = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "AppiumIP");
		String AppiumPort = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "AppiumPort");
		int port = Integer.parseInt(AppiumPort);
		builder = new AppiumServiceBuilder();
		builder.withIPAddress(AppiumIPaddress);
		builder.usingPort(port);
		builder.withCapabilities(cap);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
		
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
	}
	
	public static void stopAppiumServer() {
		service.stop();
	}
	
	public static boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}
	
	public static void appLaunch_SetUp_InDevice() throws IOException, ExceptionHandler {
		try {
			String Platform = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "PlatformName");
			String DeviceName = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "DeviceName");
			String DeviceVersion = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "DeviceVersion");
			String DeviceUDID = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "DeviceUDID");
			String AndroidBuildPath = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE,
					"Android_BuildPath");
			String iOSBuildPath = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "iOS_BuildPath");
			String BuildName = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "BuildName");
			String AppPackage = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "AppPackage");
			String AppActivity = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "AppActivity");
			String BundleID = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "BundleID");
			String AppiumURL = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "AppiumURL");
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", DeviceName);
			capabilities.setCapability("version", DeviceVersion);
			capabilities.setCapability("platformName", Platform);
			capabilities.setCapability("udid", DeviceUDID);

			if (Platform.contains("Android")) {
				
				int port = Integer.parseInt(PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "AppiumPort"));
				if(!checkIfServerIsRunnning(port)) {
					launchAppiumServer();
				} else {
					System.out.println("Appium Server already running on Port - " + port);
				}
				System.out.println("Launching app in Android Phone");

				File appFile = new File(System.getProperty("user.dir") + AndroidBuildPath + BuildName + ".apk");

				capabilities.setCapability("app", appFile.getAbsolutePath());
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
				capabilities.setCapability("appPackage", AppPackage);
				capabilities.setCapability("appActivity", AppActivity);

				mobiledriver = new AndroidDriver<WebElement>(new URL(AppiumURL), capabilities);
			}

			else if (Platform.contains("iOS")) {
				System.out.println("Launching app in iPhone");

				File appFile = new File(System.getProperty("user.dir") + iOSBuildPath + BuildName + ".ipa");
				capabilities.setCapability("app", appFile.getAbsolutePath());
				capabilities.setCapability("automationName", "XCUITest");
				capabilities.setCapability("useNewWDA", true);
				capabilities.setCapability("bundleId", BundleID);
				mobiledriver = new IOSDriver<WebElement>(new URL(AppiumURL), capabilities);
			}

		} catch (Exception e) {
			throw new ExceptionHandler(AutomationConstants.EXCEPTION_MESSAGE + getExceptionMessage() + "\n"
					+ AutomationConstants.CAUSETEXT + e.getMessage());
		}
	}
	public static String getExceptionMessage() {
		StringBuffer message = new StringBuffer();

		try {
		message.append("Exception in ");
		message.append(Thread.currentThread().getStackTrace()[2].getClassName());
		message.append(".");
		message.append(Thread.currentThread().getStackTrace()[2].getMethodName());
		message.append("()");
		} catch (Exception e) {
		e.printStackTrace();
		e.getMessage();
		}

		return message.toString();
		}
}
