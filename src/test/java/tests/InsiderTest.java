package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.HomePage;
import pages.QAPositionsPage;
import pages.LeverApplicationPage;
import utilities.LoggerUtil;
import utilities.PageHelper;
import utilities.Config;

public class InsiderTest extends TestBase {

    @Test
    public void testInsiderCareerFlow() {
        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isHomePageOpened());

        homePage.navigateToMenu("Company", "Careers");
        
        CareersPage careersPage = new CareersPage();
        Assert.assertTrue(careersPage.areAllBlocksDisplayed());

        careersPage.navigateToQACareers();
        careersPage.clickSeeAllQAJobs();
        
        QAPositionsPage qaPositionsPage = new QAPositionsPage();
        qaPositionsPage.filterJobs(Config.LOCATION, Config.DEPARTMENT);
        Assert.assertTrue(qaPositionsPage.isJobListDisplayed());

        Assert.assertTrue(qaPositionsPage.validateJobDetails(
            Config.DEPARTMENT,
            Config.DEPARTMENT,
            Config.LOCATION
        ));

        qaPositionsPage.clickViewRoleAndSwitchTab(0);
        Assert.assertTrue(PageHelper.verifyURLContains("lever.co"));
        
        LeverApplicationPage leverPage = new LeverApplicationPage();
        Assert.assertTrue(leverPage.isApplicationFormDisplayed());
    }
} 