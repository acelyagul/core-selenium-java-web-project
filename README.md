# Selenium UI Test Without BDD Framework

A robust UI test automation project developed with Java and Selenium WebDriver, built without using a BDD framework while maintaining clean and well-structured code standards.


## Prerequisites
- Java 11 or higher
- Maven 3.8 or higher
- Chrome/Firefox browsers installed

## Technology Stack
- Java 11
- Selenium WebDriver
- TestNG
- Maven
- Log4j

## Project Structure
<pre>
src/test/java/
├── pages/
│ ├── BasePage.java # Base page with common methods
│ ├── HomePage.java # Navigation and cookie handling
│ ├── QAPositionsPage.java # Job filtering and validation
│ └── LeverApplicationPage.java # Application form verification
├── tests/
│ ├── TestBase.java # Browser setup and test lifecycle
│ └── InsiderTest.java # Test scenarios
└── utilities/
├── Config.java # Environment and browser configurations
├── Driver.java # Thread-safe WebDriver factory with automatic cleanup
├── LoggerUtil.java # Custom logging with error tracking
└── PageHelper.java # Reusable UI actions
</pre>

## Key Features
- Page Object Model design pattern for better maintainability
- Thread-safe WebDriver management for parallel execution
- Environment-based configuration (PROD/TEST/DEV)
- Reusable UI interaction methods
- Detailed logging mechanism
- Automatic screenshot capture on test failure
- Cross-browser testing support (Chrome/Firefox)

## Running Tests
- Default configuration:    
mvn clean test

- Specific browser:  
mvn clean test -Dbrowser=chrome

- Specific environment:  
mvn clean test -Denv=test
