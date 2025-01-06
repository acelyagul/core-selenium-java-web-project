package utilities;

public class Config {
    public static final String BROWSER;
    public static final int IMPLICIT_WAIT;
    public static final int EXPLICIT_WAIT;
    
    public static final String BASE_URL;
    public static final String QA_CAREERS_URL;
    
    public static final String LOCATION;
    public static final String DEPARTMENT;
    
    private enum Environment {
        PROD("useinsider.com"),
        TEST("test.useinsider.com"),
        DEV("dev.useinsider.com");
        
        private final String domain;
        
        Environment(String domain) {
            this.domain = domain;
        }
        
        public String getDomain() {
            return domain;
        }
    }
    
    private enum Browser {
        CHROME, FIREFOX
    }
    
    static {
        // Browser configs
        String browserType = System.getProperty("browser", "chrome").toUpperCase();
        BROWSER = Browser.valueOf(browserType).name().toLowerCase();
        IMPLICIT_WAIT = 20;
        EXPLICIT_WAIT = 40;
        
        // URLs with environment handling
        String env = "PROD";
        String domain = Environment.valueOf(env).getDomain();
        
        BASE_URL = "https://" + domain;
        QA_CAREERS_URL = BASE_URL + "/careers/quality-assurance/";
        
        // Test Data
        LOCATION = "Istanbul, Turkey";
        DEPARTMENT = "Quality Assurance";
    }
} 