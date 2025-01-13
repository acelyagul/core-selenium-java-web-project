package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.Driver;
import utilities.LoggerUtil;
import utilities.PageHelper;
import java.util.List;

public class QAPositionsPage extends BasePage {

    @FindBy(id = "select2-filter-by-location-container")
    private WebElement locationDropdown;

    @FindBy(id = "select2-filter-by-department-container")
    private WebElement departmentDropdown;

    @FindBy(css = ".position-list")
    private WebElement jobListContainer;

    private final By jobItemsLocator = By.cssSelector(".position-list-item-wrapper.bg-light");
    private final By positionLocator = By.cssSelector(".position-title");
    private final By departmentLocator = By.cssSelector(".position-department");
    private final By locationLocator = By.cssSelector(".position-location");
    private final By viewRoleButtonLocator = By.cssSelector("a[class*='btn-navy']");

    public void filterJobs(String location, String department) {
        PageHelper.waitForSeconds(10);
        PageHelper.waitForElementVisible(locationDropdown);
        PageHelper.scrollToElement(locationDropdown);
        PageHelper.waitForElementVisible(departmentDropdown);
        selectDropdownOption(locationDropdown, location);
        selectDropdownOption(departmentDropdown, department);
    }

    private void selectDropdownOption(WebElement dropdown, String optionText) {
        int maxAttempts = 5;
        optionText = optionText.trim();

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                if (dropdown.getText().trim().contains(optionText)) {
                    LoggerUtil.info("Option already selected: " + optionText);
                    return;
                }

                PageHelper.clickElement(dropdown);
                PageHelper.waitForSeconds(2);

                List<WebElement> options = Driver.getDriver().findElements(
                    By.xpath("//ul[@class='select2-results__options']//li[contains(@class, 'select2-results__option')]")
                );

                for (WebElement option : options) {
                    String currentOptionText = option.getText().trim();
                    if (currentOptionText.contains(optionText) || currentOptionText.equalsIgnoreCase(optionText)) {
                        PageHelper.clickElement(option);
                        PageHelper.waitForSeconds(2);

                        if (dropdown.getText().trim().contains(optionText)) {
                            LoggerUtil.info("Option successfully selected: " + optionText);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                LoggerUtil.error("Attempt " + (attempt + 1) + " failed");
                if (attempt == maxAttempts - 1) throw e;
            }
        }
        
    }

    public boolean validateJobDetails(String position, String department, String location) {
         
        PageHelper.waitForElementVisible(jobListContainer);
            List<WebElement> jobs = Driver.getDriver().findElements(jobItemsLocator);
            LoggerUtil.info("Number of job postings found: " + jobs.size());
            
            position = position.toLowerCase().trim();
            department = department.toLowerCase().trim();
            location = location.toLowerCase().split(",")[0].trim();
            
            for (WebElement job : jobs) {
                PageHelper.scrollToElement(job);
                
                String jobPosition = job.findElement(positionLocator).getText().toLowerCase();
                String jobDepartment = job.findElement(departmentLocator).getText().toLowerCase();
                String jobLocation = job.findElement(locationLocator).getText().toLowerCase();
                
                LoggerUtil.info("Job check - Position: " + jobPosition + 
                               ", Department: " + jobDepartment + 
                               ", Location: " + jobLocation);

            boolean isPositionMatch = jobPosition.contains(position) || 
                                    jobPosition.contains("qa") || 
                                    jobPosition.contains("quality assurance");

            if (isPositionMatch && 
                jobDepartment.contains(department) && 
                jobLocation.contains(location)) {
                LoggerUtil.info("Matching job found");
                return true;
            }
        }
        
        LoggerUtil.error("No suitable job found!");
        return false;
    }

    public void clickViewRoleAndSwitchTab(int index) {
        List<WebElement> jobs = Driver.getDriver().findElements(jobItemsLocator);
        if (index >= 0 && index < jobs.size()) {
            WebElement selectedJob = jobs.get(index);
            PageHelper.hoverAndClick(selectedJob, viewRoleButtonLocator);
            PageHelper.switchToLastTab();
        }
    }

    public boolean isJobListDisplayed() {
        return !Driver.getDriver().findElements(jobItemsLocator).isEmpty();
    }
} 