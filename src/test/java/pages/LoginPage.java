package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By userField = By.cssSelector("#user-name");
    private final By passwordField = By.cssSelector("[placeholder=Password]");
    private final By submitButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test=error]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(BASE_URL);
    }

    public void login(String login, String password) {
        driver.findElement(userField).sendKeys(login);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    public boolean isErrorMsgDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    public String getErrorMsgText() {
        return driver.findElement(errorMessage).getText();
    }
}
