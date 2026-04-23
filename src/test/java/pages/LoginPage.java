package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import user.User;

public class LoginPage extends BasePage {
    private final By userField = By.cssSelector("#user-name");
    private final By passwordField = By.cssSelector("[placeholder=Password]");
    private final By submitButton = By.id("login-button");
    private final By errorMessage = By.cssSelector(DATA_TEST_PATTERN.formatted("error"));

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(BASE_URL);
    }

    public void open(final String url) {
        driver.get(BASE_URL + url);
    }

    public void login(User user) {
        driver.findElement(userField).sendKeys(user.getLogin());
        driver.findElement(passwordField).sendKeys(user.getPassword());
        driver.findElement(submitButton).click();
    }

    public boolean isErrorMsgDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    public String getErrorMsgText() {
        return driver.findElement(errorMessage).getText();
    }
}
