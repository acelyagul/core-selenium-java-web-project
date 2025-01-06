package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeverApplicationPage extends BasePage {
    @FindBy(css = ".postings-btn-wrapper .template-btn-submit")
    private WebElement applicationForm;

    public boolean isApplicationFormDisplayed() {
        return applicationForm.isDisplayed();
    }
} 