package tests;

import org.testng.annotations.*;
import utilities.Driver;
import utilities.Config;
import utilities.LoggerUtil;

public class TestBase {
    
    @BeforeMethod
    public void setUp() {
        LoggerUtil.info("Starting the test");
        Driver.getDriver().get(Config.BASE_URL);
        Driver.getDriver().manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        LoggerUtil.info("Testing is ending");
        Driver.closeDriver();
    }
} 