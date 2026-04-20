package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    public static final String BASE_URL = "https://www.saucedemo.com/";
    public static final String DATA_TEST_PATTERN = "[data-test=%s]";
    private final By pageTitle = By.cssSelector(DATA_TEST_PATTERN.formatted("title"));
    public NavigationPanel navigationPanel;
    WebDriver driver;
    WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.navigationPanel = new NavigationPanel(driver);
    }

    public boolean isTitleDisplayed() {
        return driver.findElement(pageTitle).isDisplayed();
    }

    public String getTitle() {
        return driver.findElement(pageTitle).getText();
    }
}
