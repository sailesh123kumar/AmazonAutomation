package com.qa.amazon.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.amazon.constant.AppConstants;
import com.qa.amazon.timeutils.TimeUtil;
import com.qa.amazon.utilities.ElementUtil;

public class Productpage {

	WebDriver driver;
	ElementUtil eleutil;

	public Productpage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	private By allproducts = By.cssSelector(".a-size-medium.a-color-base.a-text-normal");
	private By allproductsprices = By.cssSelector(".a-price-whole");
	private Map<String, Integer> productmap;

	public String pageTitle() {
		String title = eleutil.waitForTitleTobe(AppConstants.AMAZON_PRODUCTPAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);
		System.out.println("Page title is ===> " + title);
		return title;
	}

	public String pageUrl() {
		String url = eleutil.waitForURLContains(AppConstants.AMAZON_PRODUCTPAGE_FRACTION_URL,
				TimeUtil.DEFAULT_MEDIUM_TIME);
		System.out.println("Page url is ===> " + url);
		return url;
	}

	public Map<String, Integer> productMetaData() {
		List<WebElement> allProducts = eleutil.waitForVisibilityofElementsLocated(allproducts,
				TimeUtil.DEFAULT_MEDIUM_TIME);
		List<WebElement> allprice = eleutil.getElements(allproductsprices);
		int productsize = allProducts.size();
		productmap = new HashMap<String, Integer>();

		for (int i = 0; i <= productsize-1; i++) {
			productmap.put(allProducts.get(i).getText(),
					Integer.parseInt(allprice.get(i).getText().replaceAll(",", "")));
		}

		return productmap;
	}

	public int productCount() {
		int count = eleutil.waitForVisibilityofElementsLocated(allproducts, TimeUtil.DEFAULT_MEDIUM_TIME).size();
		System.out.println("Product displays ===> " + count);
		return count;
	}

	public void displayMinValProduct() {
		Map<String, Integer> productMetaData = productMetaData();

		List<Entry<String, Integer>> unSorted = new ArrayList<Map.Entry<String, Integer>>(productMetaData.entrySet());
		unSorted.sort(Entry.comparingByValue());

		Entry<String, Integer> entry = unSorted.get(0);
	

		for (Entry e : unSorted) {
			System.out.println(e.getKey() + " : " + e.getValue());
		}
		System.out.println("============================");
		
		System.out.println("key : " + entry.getKey() + "Value : " + entry.getValue());

		System.out.println("============================");


	}

	public void displayMaxValProduct() {
		Map<String, Integer> productMetaData = productMetaData();

		List<Entry<String, Integer>> unSorted = new ArrayList<Map.Entry<String, Integer>>(productMetaData.entrySet());
		unSorted.sort(Entry.comparingByValue());

		Entry<String, Integer> entry = unSorted.get(unSorted.size() - 1);
		
		
		for (Entry e : unSorted) {
			System.out.println(e.getKey() + " : " + e.getValue());
		}
		
		System.out.println("============================");
		
		System.out.println("key : " + entry.getKey() + "Value : " + entry.getValue());

		System.out.println("============================");

		
	}

}
