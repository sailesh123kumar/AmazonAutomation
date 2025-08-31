package com.qa.amazon.driverfactory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	
	Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("===Browser Launch in Headless Mode===");
			co.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("===Browser Launch in Incognito Mode===");
			co.addArguments("--incognito");
		}
		
		return co;
		
	}
	
	public FirefoxOptions getFireFoxOptions() {
		fo = new FirefoxOptions();
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("===Browser Launch in Headless Mode===");
			fo.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("===Browser Launch in Incognito Mode===");
			fo.addArguments("--incognito");
		}
		
		return fo;
		
	}

	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("===Browser Launch in Headless Mode===");
			eo.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("===Browser Launch in Incognito Mode===");
			eo.addArguments("--inPrivate");
		}
		
		return eo;
		
	}
	
}
