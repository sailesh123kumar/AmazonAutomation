package com.qa.amazon.driverfactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.amazon.constant.AppConstants;
import com.qa.amazon.errors.AppErrors;
import com.qa.amazon.exceptions.BrowserException;
import com.qa.amazon.exceptions.FrameWorkException;



public class DriverFactory {

	WebDriver driver;
	Properties prop;
	public static String highlight = "false";
	OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlocal = new ThreadLocal<WebDriver>();

	
	/**
	 * init Webdriver with different browser option
	 * @param prop
	 * @return
	 */
	public WebDriver initDriver(Properties prop) {

		String browser = prop.getProperty("browser");

		optionsManager = new OptionsManager(prop);

		switch (browser.toLowerCase().trim()) {
		case "chrome":
			tlocal.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;

		case "firefox":
			tlocal.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
			break;

		case "edge":
			tlocal.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		case "safari":
			tlocal.set(new SafariDriver());
			break;

		default:
			System.out.println("Please Pass the Right browser");
			throw new BrowserException(AppErrors.BROWSER_ERROR);

		}

		getDriver().get("https://www.amazon.in/");
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();

	}

	public static WebDriver getDriver() {
		return tlocal.get();
	}

	public Properties initproperties() {
		prop = new Properties();
		FileInputStream fis = null;

		// mvn clean install -Denv="qa"
		String envName = System.getProperty("env");
		System.out.println("running test suite on env ---> " + envName);

		if (envName == null) {
			System.out.println("enviroment name is not given hence running it in qa enviroment...");
			try {
				fis = new FileInputStream(AppConstants.QA_CONFIG_FILE_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {

				switch (envName.toLowerCase().trim()) {
				case "qa":
					fis = new FileInputStream(AppConstants.QA_CONFIG_FILE_PATH);
					break;
				case "stage":
					fis = new FileInputStream(AppConstants.STAGE_CONFIG_FILE_PATH);
					break;
				default:
					System.out.println("Please pass the right environment :" + envName);
					throw new FrameWorkException("====WRONG ENVIROMENT PASS!====");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;

	}

	public static String getScreenshot(String methodName) {
		// Get the driver instance
		TakesScreenshot screenshotTaker = (TakesScreenshot) getDriver();

		// Take the screenshot and save it to a temporary location
		File srcFile = screenshotTaker.getScreenshotAs(OutputType.FILE);

		// Define the path for the screenshots folder
		String screenshotsDirPath = System.getProperty("user.dir") + "/screenshots";

		// Create the screenshots folder if it doesn't exist
		File screenshotsDir = new File(screenshotsDirPath);
		if (!screenshotsDir.exists()) {
			if (screenshotsDir.mkdirs()) {
				System.out.println("Folder 'screenshots' created successfully at: " + screenshotsDirPath);
			} else {
				System.out.println("Failed to create the folder 'screenshots' at: " + screenshotsDirPath);
			}
		}

		// Define the destination path for the screenshot
		String screenshotPath = screenshotsDirPath + "/" + methodName + "_" + System.currentTimeMillis() + ".png";
		File destination = new File(screenshotPath);

		// Copy the screenshot to the destination path
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return destination.getAbsolutePath();
	}

}
