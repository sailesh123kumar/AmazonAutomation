package com.qa.amazon.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.amazon.basetest.BaseTest;
import com.qa.amazon.constant.AppConstants;
import com.qa.amazon.errors.AppErrors;
import com.qa.amazon.utilities.ExcelUtils;

public class AmazonHomePageTest extends BaseTest {
	
	@Test(priority=-1)
	public void titleTest() {
		Assert.assertEquals(ahp.pageTitle(), AppConstants.AMAZON_HOMEPAGE_TITLE,AppErrors.TITLE_NOT_MATCHED);
	}
	
	@Test
	public void urltest() {
		Assert.assertTrue(ahp.pageUrl().contains(AppConstants.AMAZON_HOMEPAGE_FRACTION_URL),AppErrors.FRACTION_URL_NOT_MATCHED);
	}
	
	@DataProvider
	public Object[][] getTestdata() {
		return new Object[][] {
			{"samsung"},
			{"xiaomi pad 6"},
			{"apple iphone"}
		};
	}
	
	@DataProvider
	public Object[][] getExcelsheetTestData(){
		return ExcelUtils.getTestData("search");
		
	}
	
	
	@Test (dataProvider = "getExcelsheetTestData")
	public void searchTest(String searchKey) {
		ahp.dosearch(searchKey);
	}
	

}
