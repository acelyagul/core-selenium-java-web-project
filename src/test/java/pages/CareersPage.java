package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.Driver;
import utilities.LoggerUtil;
import utilities.PageHelper;
import utilities.Config;
import static utilities.Config.EXPLICIT_WAIT;

public class CareersPage extends BasePage {

    @FindBy(css = "#career-our-location")
    private WebElement locationsBlock;

    @FindBy(css = "#career-find-our-calling")
    private WebElement teamsBlock;

    @FindBy(css = ".elementor-section.elementor-top-section.elementor-element.elementor-element-a8e7b90")
    private WebElement lifeAtInsiderBlock;

    @FindBy(css = "a[href*='careers/open-positions/?department=qualityassurance']")
    private WebElement seeAllQAJobsButton;

    public boolean areAllBlocksDisplayed() {
        try {
            boolean isLocationsDisplayed = PageHelper.isElementDisplayed(locationsBlock);
            boolean isTeamsDisplayed = PageHelper.isElementDisplayed(teamsBlock);
            boolean isLifeAtInsiderDisplayed = PageHelper.isElementDisplayed(lifeAtInsiderBlock);
            return isLocationsDisplayed && isTeamsDisplayed && isLifeAtInsiderDisplayed;
        } catch (Exception e) {
            LoggerUtil.error("Careers page error: " + e.getMessage());
            return false;
        }
    }

    public void navigateToQACareers() {
        Driver.getDriver().get(Config.QA_CAREERS_URL);
    }

    public void clickSeeAllQAJobs() {
        PageHelper.waitForElementVisible(seeAllQAJobsButton, EXPLICIT_WAIT);
        PageHelper.clickElement(seeAllQAJobsButton);
    }
}