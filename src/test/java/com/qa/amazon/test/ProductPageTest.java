package com.qa.amazon.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.amazon.basetest.BaseTest;
import com.qa.amazon.constant.AppConstants;
import com.qa.amazon.errors.AppErrors;

public class ProductPageTest extends BaseTest {
	
	
	@BeforeTest
	public void setUp() {
		prodp= ahp.dosearch("samsung");
	}
	
	@Test(priority = -1)
	public void titleTest() {
		Assert.assertEquals(prodp.pageTitle(),AppConstants.AMAZON_PRODUCTPAGE_TITLE,AppErrors.TITLE_NOT_MATCHED);
	}
	
	@Test(priority = 0)
	public void urlTest() {
		Assert.assertTrue(prodp.pageUrl().contains(AppConstants.AMAZON_PRODUCTPAGE_FRACTION_URL),AppErrors.FRACTION_URL_NOT_MATCHED);
	}
	
	@Test
	public void productCountTest() {
		prodp= ahp.dosearch("samsung");
		int productCount = prodp.productCount();
		Assert.assertEquals(productCount,24);
	}
	
	
	@DataProvider
	public Object[][] getTestdata(){
		
		return new  Object [][]  {
				{"samsung"},
				{"one plus"},
				{"Nokia"}
				};
	}
	
	@Test(dataProvider = "getTestdata")
	public void productMetaDataTest(String searchKey) {
		prodp= ahp.dosearch(searchKey);
		Map<String, Integer> productMetaData = prodp.productMetaData();
		//SoftAssert need to be added
	}
	
	@Test(dataProvider = "getTestdata")
	public void minPriceonProductListTest(String searchKey) {
		prodp= ahp.dosearch(searchKey);
		prodp.displayMinValProduct();
	}
	
	@Test(dataProvider = "getTestdata")
	public void maxPriceonProductListTest(String searchKey) {
		prodp= ahp.dosearch(searchKey);
		prodp.displayMaxValProduct();
	}
	
	

}
