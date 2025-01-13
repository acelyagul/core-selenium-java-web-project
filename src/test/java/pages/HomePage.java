package pages;

import org.openqa.selenium.*;
import utilities.PageHelper;
import utilities.Driver;
import utilities.LoggerUtil;
import utilities.Config;
import static utilities.Config.EXPLICIT_WAIT;

public class HomePage extends BasePage {
    private final By menuItemsLocator = By.cssSelector("ul.navbar-nav li.nav-item.dropdown");
    private final By dropdownItemsLocator = By.cssSelector(".new-menu-dropdown-layout-6-mid-container a.dropdown-sub");
    private final By acceptCookiesLocator = By.id("wt-cli-accept-all-btn");

    public void handleInitialPopups() {
        try {
            if (PageHelper.isElementDisplayed(acceptCookiesLocator)) {
                PageHelper.waitForElementClickable(acceptCookiesLocator);
                PageHelper.clickElement(acceptCookiesLocator);
                LoggerUtil.info("Accepted cookies popup");
            }
        } catch (Exception e) {
            LoggerUtil.info("Cookie popup handling status: " + e.getMessage());
        }
    }

    public boolean isHomePageOpened() {
        handleInitialPopups();
        String currentUrl = Driver.getDriver().getCurrentUrl().trim();
        String expectedUrl = Config.BASE_URL + "/";
        LoggerUtil.info("Checking URL - Current: " + currentUrl + ", Expected: " + expectedUrl);
        return currentUrl.equals(expectedUrl);
    }


    public void navigateToMenu(String mainMenu, String subMenu) {
        handleInitialPopups();
        WebElement menuItem = findMenuItemByText(mainMenu);
        PageHelper.clickElement(menuItem);
        WebElement subMenuItem = findDropdownItemByText(subMenu);
        PageHelper.waitForElementVisible(subMenuItem, EXPLICIT_WAIT);
        PageHelper.clickElement(subMenuItem);
    }

    private WebElement findMenuItemByText(String menuText) {
        return Driver.getDriver().findElements(menuItemsLocator).stream()
            .filter(element -> element.getText().contains(menuText))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Menu item not found: " + menuText));
    }

    private WebElement findDropdownItemByText(String itemText) {
        PageHelper.waitForSeconds(1);
        return Driver.getDriver().findElements(dropdownItemsLocator).stream()
            .filter(element -> {
                try {
                    return element.isDisplayed() && element.getText().trim().equals(itemText);
                } catch (Exception e) {
                    return false;
                }
            })
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Dropdown item not found: " + itemText));
    }

} 