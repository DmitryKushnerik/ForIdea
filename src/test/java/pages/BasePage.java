package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {
    public static final String BASE_URL = "https://www.saucedemo.com/";
    private final By pageTitle = By.cssSelector("[data-test=title]");
    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isTitleDisplayed() {
        return driver.findElement(pageTitle).isDisplayed();
    }

    public String getTitle() {
        return driver.findElement(pageTitle).getText();
    }
}
