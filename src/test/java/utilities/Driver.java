package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class Driver {
    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    private Driver() {}

    public static WebDriver getDriver() {
        if (driverPool.get() == null) {
            switch (Config.BROWSER.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    
                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
                    prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
                    prefs.put("profile.default_content_setting_values.geolocation", 1);
                    
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    
                    firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                    firefoxOptions.addPreference("geo.enabled", true);
                    firefoxOptions.addPreference("permissions.default.microphone", 1);
                    firefoxOptions.addPreference("permissions.default.camera", 1);
                    
                    driverPool.set(new FirefoxDriver(firefoxOptions));
                    break;

                default:
                    throw new RuntimeException("Unsupported browser type: " + Config.BROWSER);
            }

            driverPool.get().manage().window().maximize();
            driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(Config.IMPLICIT_WAIT));
        }
        return driverPool.get();
    }

    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
} 