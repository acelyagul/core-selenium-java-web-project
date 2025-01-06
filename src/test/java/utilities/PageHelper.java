package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.ArrayList;

public class PageHelper {
    private static final int DEFAULT_TIMEOUT = Config.IMPLICIT_WAIT;
    private static final int EXTENDED_TIMEOUT = Config.EXPLICIT_WAIT;

    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggerUtil.error("Wait interrupted: " + e.getMessage());
        }
    }

    public static void waitForElementVisible(WebElement element, int... timeout) {
        try {
            int waitTimeout = timeout.length > 0 ? timeout[0] : DEFAULT_TIMEOUT;
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(waitTimeout));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            LoggerUtil.error("Element not visible: " + e.getMessage());
            throw e;
        }
    }

    public static void clickElement(Object element) {
        try {
            WebElement webElement = getWebElement(element);
            waitForElementVisible(webElement);
            waitForElementClickable(webElement);
            webElement.click();
        } catch (Exception e) {
            LoggerUtil.error("Click action failed: " + e.getMessage());
            throw e;
        }
    }

    public static void waitForElementClickable(Object element) {
        try {
            WebElement webElement = getWebElement(element);
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
        } catch (TimeoutException e) {
            LoggerUtil.error("Element not clickable: " + e.getMessage());
            throw e;
        }
    }

    public static boolean isElementDisplayed(Object element) {
        try {
            WebElement webElement = getWebElement(element);
            return webElement != null && webElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver())
            .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public static void switchToLastTab() {
        ArrayList<String> tabs = new ArrayList<>(Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(tabs.get(tabs.size() - 1));
    }

    public static boolean verifyURLContains(String expectedText) {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
            return wait.until(driver -> driver.getCurrentUrl().contains(expectedText));
        } catch (Exception e) {
            LoggerUtil.error("URL verification failed: " + e.getMessage());
            return false;
        }
    }

    public static void hoverElement(WebElement element) {
        try {
            waitForElementVisible(element);
            Actions actions = new Actions(Driver.getDriver());
            scrollToElement(element);
            actions.moveToElement(element).pause(Duration.ofSeconds(1)).perform();
        } catch (Exception e) {
            LoggerUtil.error("Hover action failed: " + e.getMessage());
            throw e;
        }
    }

    public static void hoverAndClick(WebElement container, By elementLocator) {
        try {
            waitForElementVisible(container);
            scrollToElement(container);
            
            Actions actions = new Actions(Driver.getDriver());
            actions.moveToElement(container)
                   .pause(Duration.ofSeconds(1))
                   .perform();
            
            WebElement element = container.findElement(elementLocator);
            actions.moveToElement(element)
                   .click()
                   .perform();
        } catch (Exception e) {
            LoggerUtil.error("Hover and click action failed: " + e.getMessage());
            throw e;
        }
    }

    private static WebElement getWebElement(Object element) {
        if (element instanceof By) {
            return Driver.getDriver().findElement((By) element);
        } else if (element instanceof WebElement) {
            return (WebElement) element;
        }
        throw new IllegalArgumentException("Element must be either By or WebElement");
    }
} 