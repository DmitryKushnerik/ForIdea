package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PropertyReader;

import java.time.Duration;

public class BasePage {
    public static final String BASE_URL = PropertyReader.getProperty("foridea.url");
    public static final String DATA_TEST_PATTERN = "[data-test=%s]";
    String page_url;
    private final By pageTitle = By.cssSelector(DATA_TEST_PATTERN.formatted("title"));
    public NavigationPanel navigationPanel;
    WebDriver driver;
    public WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.navigationPanel = new NavigationPanel(driver);
        this.page_url = "";
    }

    @Step("Проверить, отображается ли название страницы")
    public boolean isTitleDisplayed() {
        return driver.findElement(pageTitle).isDisplayed();
    }

    @Step("Получить текст названия открытой страницы")
    public String getTitle() {
        return driver.findElement(pageTitle).getText();
    }

    @Step("Получить URL открытой страницы")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Получить ожидаемый Url")
    public String getExpectedUrl() {
        return BASE_URL + page_url;
    }
}
