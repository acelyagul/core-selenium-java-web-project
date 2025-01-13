package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class BasePage {
    protected WebDriver driver;

    public BasePage() {
        this.driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }
}