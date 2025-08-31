package com.qa.amazon.utilities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.amazon.driverfactory.DriverFactory;
import com.qa.amazon.exceptions.ElementException;



//import io.qameta.allure.Step;

public class ElementUtil {
	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	private void highlightElement(WebElement element) {
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
	}


	//@Step("Finding the element using locator : {0}")
	public WebElement getElement(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			highlightElement(element);
			return element;
		} catch (NoSuchElementException e) {
			System.out.println("Element is not present on the page..." + locator);
			e.printStackTrace();
			return null;
		}
	}

	private void nullcheck(String value) {
		if (value == null) {
			throw new ElementException("VALUE IS NULL..." + value);
		}
	}

	//@Step("Entering the Value - {1} in the text field using locator : {0}")
	public void doSendKeys(By locator, String value) {
		nullcheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}
	
	//@Step("Entering the Value - {1} in the text field using locator : {0} and waiting for element with : {2} secs")
	public void doSendKeys(By locator, String value, int timeOut) {
		nullcheck(value);
		waitForElementVisible(locator, timeOut).clear();;
		waitForElementVisible(locator, timeOut).sendKeys(value);
	}
	
	//@Step("Entering the Value - {1} in the text field using locator : [0]")
	public void doSendKeys(By locator, CharSequence... value) {
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}
	

	//@Step("Clicking on the element using locator : {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public void doClick(By locator, int timeOut) {
		waitForElementVisible(locator, timeOut).click();
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetAttribute(By locator, String attributeName) {
		return getElement(locator).getAttribute(attributeName);
	}

	//@Step("checking the state of locator: {0} is displayed or not")
	public boolean doIsDisplayed(By locator) {
		try {
			boolean flag = getElement(locator).isDisplayed();
			System.out.println("element is displayed " + locator);
			return flag;
		} catch (NoSuchElementException e) {
			System.out.println("element with locator " + locator + " is not displayed");
			return false;
		}
	}

	public boolean isElementsDisplayed(By locator, int expectedElementCount) {
		int elementsCount = getElements(locator).size();
		if (elementsCount == expectedElementCount) {
			System.out.println("element is displayed: " + locator + " with occurence of " + expectedElementCount);
			return true;
		} else {
			System.out.println("Multiple or Zero element is displayed " + locator);
			return false;
		}
	}

	public boolean isElementsDisplayed(By locator) {
		int elementsCount = getElements(locator).size();
		if (elementsCount == 1) {
			System.out.println("Single element is displayed " + locator);
			return true;
		} else {
			System.out.println("Multiple or Zero element is displayed " + locator);
			return false;
		}
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public List<String> getAttributelist(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> attrTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String attributeVal = e.getAttribute(attrName);
			if (attributeVal != null && attributeVal.length() != 0) {
				attrTextList.add(attributeVal);
			}
		}
		return attrTextList;
	}

	// ***************************Select drop down utils************************************//

	public void doSelectByIndex(By locator, int index) {
		Select sel = new Select(getElement(locator));
		sel.selectByIndex(index);
	}

	public void doSelectByValue(By locator, String value) {
		Select sel = new Select(getElement(locator));
		sel.selectByValue(value);
	}

	public void doSelectByText(By locator, String visibleText) {
		Select sel = new Select(getElement(locator));
		sel.selectByVisibleText(visibleText);
	}

	public int getDropDownOptionsCount(By locator) {
		Select sel = new Select(getElement(locator));
		return sel.getOptions().size();
	}

	public List<String> getDropDownOptionsTextList(By locator) {
		Select sel = new Select(getElement(locator));
		List<WebElement> optList = sel.getOptions();
		List<String> optTextList = new ArrayList<String>();
		for (WebElement e : optList) {
			optTextList.add(e.getText());
		}
		return optTextList;
	}

	public void selectValuefromDropDown(By locator, String optiontoSelect) {
		Select sel = new Select(getElement(locator));
		List<WebElement> options = sel.getOptions();
		for (WebElement e : options) {
			String text = e.getText();
			if (text.equals(optiontoSelect.trim())) {
				e.click();
				break;
			}
		}
	}

	public void selectValuefromDropDownWithoutSelectClass(By locator, String optiontoSelect) {
		List<WebElement> options = getElements(locator);
		for (WebElement e : options) {
			String text = e.getText();
			if (text.equals(optiontoSelect)) {
				e.click();
				break;
			}
		}
	}

	public void doSearch(By searchField, String Searchkey, By Suggestions, String value) throws InterruptedException {
		doSendKeys(searchField, Searchkey);
		Thread.sleep(3000);
		List<WebElement> sugList = getElements(Suggestions);
		for (WebElement e : sugList) {
			String text = e.getText();
			System.out.println(text);
			if (e.getText().contains(value)) {
				e.click();
				break;
			}

		}
	}

	// ***************************Actions utils************************************//

	public void handleParentSubMenu(By parentLocator, By ChildLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentLocator)).perform();
		Thread.sleep(2000);
		doClick(ChildLocator);
	}

	public void doDragAndDrop(By sourceLocator, By tagetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourceLocator), getElement(tagetLocator)).perform();
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}

	/**
	 * This method is use to enter the value in the text field with a defined long pause
	 * @param locator
	 * @param value
	 * @param pauseTime
	 */
	public void doActionsSendKeyswithPause(By locator, String value, long pauseTime) {
		Actions act = new Actions(driver);
		char[] valueArray = value.toCharArray();
		for (char c : valueArray) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
		}
	}

	/**
	 * This method is use to enter the value in the text field with a pause of 500ms (by default)
	 * @param locator
	 * @param value
	 */
	public void doActionsSendKeyswithPause(By locator, String value) {
		Actions act = new Actions(driver);
		char[] valueArray = value.toCharArray();
		for (char c : valueArray) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(500).perform();
		}
	}
	
	
	public void level4MenuSubMenuHandlingUsingClick(By level1, By level2, By level3, By level4)
			throws InterruptedException {

		doClick(level1);
		Thread.sleep(1000);

		Actions act = new Actions(driver);
		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(1000);

		doClick(level4);

	}
	
	public void level4MenuSubMenuHandlingUsingClick(By level1, String level2, String level3, String level4)
			throws InterruptedException {

		doClick(level1);
		Thread.sleep(1000);

		Actions act = new Actions(driver);
		act.moveToElement(getElement(By.linkText(level2))).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(By.linkText(level3))).perform();
		Thread.sleep(1000);

		doClick(By.linkText(level4));

	}

	public void level4MenuSubMenuHandlingUsingMouseHover(By level1, By level2, By level3, By level4)
			throws InterruptedException {

		Actions act = new Actions(driver);
		act.moveToElement(getElement(level1)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(1000);

		doClick(level4);

	}
	
	// ***************************Wait utils************************************//
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	
	public WebElement waitForElementPresence(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlightElement(element);
		return element;
	}
	
	
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}
	
	public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.ignoring(NoSuchElementException.class)
				.withMessage("=======element is not found=======");
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForPresenceofElementsLocated(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible. 
	 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForVisibilityofElementsLocated(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		catch (TimeoutException e) {
			return List.of(); //Return Empty Array List
		}
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void clickWhenReady(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	public String waitForTitleContains(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("Title not Found");
		}
		return driver.getTitle();
	}
	
	public String waitForTitleTobe(String titleVal, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleIs(titleVal))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("Title not Found");
		}
		return driver.getTitle();
	}
	
	public String waitForURLContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL not Found");
		}
		return driver.getCurrentUrl();
	}
	
	public String waitForURLTobe(String urlVal, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlToBe(urlVal))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL not Found");
		}
		return driver.getCurrentUrl();
	}

	//Wait for Alert
	
	public Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	
	public Alert waitForJSAlert(int timeOut,int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.ignoring(NoAlertPresentException.class)
				.withMessage("=======No Alert is Present=======");
		
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public String getAlertText(int timeOut) {
		return waitForJSAlert(timeOut).getText();
	}

	public String getAlertTextandAccept(int timeOut) {
		Alert alert = waitForJSAlert(timeOut);
		String text = alert.getText();
		alert.accept();
		return text;
	}

	public void acceptAlert(int timeOut) {
		waitForJSAlert(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForJSAlert(timeOut).dismiss();
	}

	public void alertSendKeys(int timeOut, String value) {
		Alert alert = waitForJSAlert(timeOut);
		alert.sendKeys(value);
		alert.accept();
	}
	
	//Wait for iframes/frames
	
	/**
	 * 
	 * An expectation for checking whether the given frame is available to switch to. 
	 * If the frame is available it switches the given driver to the specified frame
	 * @param frameLocator
	 * @param timeOut
	 */
	public void waitForFrameByLocator(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	
	public void waitForFrameByLocator(By frameLocator, int timeOut,int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.ignoring(NoSuchFrameException.class)
				.withMessage("=======Frame is Not Found=======");
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameByIndex(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public void waitForFrameByIDorName(String frameIdorName, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdorName));
	}
	
	public void waitForFrameByElemet(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	
	// Wait for windowsToBe
	public Boolean waitForWindowsToBe(int timeOut, int totalWindows) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindows));
	}
	
	
	public void isPageLoaded(int timeOut) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	    boolean isLoaded = (boolean) wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

	    if (isLoaded) {
	        System.out.println("Page is completely Loaded");
	    } else {
	        throw new RuntimeException("Page is not loaded");
	    }
	}
	
	
}
