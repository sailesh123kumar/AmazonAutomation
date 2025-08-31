package com.qa.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.qa.amazon.constant.AppConstants;
import com.qa.amazon.timeutils.TimeUtil;
import com.qa.amazon.utilities.ElementUtil;

public class AmazonHomepage {
	
	private WebDriver driver;
	ElementUtil eleutil;
	
	private By seachbox = By.cssSelector("#twotabsearchtextbox");
	private By searchbtn = By.xpath("//input[@value='Go']");
	
	public AmazonHomepage(WebDriver driver) {
		this.driver=driver;
		eleutil = new ElementUtil(driver);
	}	
	
	public String pageTitle() {
		String title = eleutil.waitForTitleTobe(AppConstants.AMAZON_HOMEPAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);
		System.out.println("Page title is ===> "+title);
		return title;	
	}
	
	public String pageUrl() {
		String url = eleutil.waitForURLContains(AppConstants.AMAZON_HOMEPAGE_FRACTION_URL, TimeUtil.DEFAULT_MEDIUM_TIME);
		System.out.println("Page url is ===> "+url);
		return url;
	}
	
	public Productpage dosearch(String searchkey) {
		eleutil.doSendKeys(seachbox, searchkey, TimeUtil.DEFAULT_MEDIUM_TIME);
		eleutil.doClick(searchbtn, TimeUtil.DEFAULT_MEDIUM_TIME);
		return new Productpage(driver);
	}

}
