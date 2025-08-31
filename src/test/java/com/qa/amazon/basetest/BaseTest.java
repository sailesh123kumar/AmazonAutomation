package com.qa.amazon.basetest;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.amazon.driverfactory.DriverFactory;
import com.qa.amazon.pages.AmazonHomepage;
import com.qa.amazon.pages.Productpage;

public class BaseTest {
	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	protected AmazonHomepage ahp;
	protected Productpage prodp;
	
	
	protected SoftAssert softassert;
	
	@Parameters({"browser"})
	@BeforeTest
	public void setup(@Optional String browserName) {
		
		df= new DriverFactory();
		prop = df.initproperties();
		
		if(browserName!=null) {
			prop.setProperty("browser", browserName);
			System.out.println("Browser selected from TestNG runner file :"+browserName);
		}
		
		driver = df.initDriver(prop);
		ahp = new AmazonHomepage(driver);
		softassert=new SoftAssert();
	}
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
