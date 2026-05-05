package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;
import user.User;

public class LoginPage extends BasePage {
    private final By userField = By.cssSelector("#user-name");
    private final By passwordField = By.cssSelector("[placeholder=Password]");
    private final By submitButton = By.id("login-button");
    private final By errorMessage = By.cssSelector(DATA_TEST_PATTERN.formatted("error"));
    private final String submitButtonExpectedBgColor = "rgba(61, 220, 145, 1)";

    public LoginPage(WebDriver driver) {
        super(driver);
        this.page_url = "";
    }

    @Step("Открыть главную страницу")
    public void open() {
        driver.get(BASE_URL);
    }

    @Step("Открыть страницу с указанным URL")
    public void open(String url) {
        driver.get(BASE_URL + url);
    }

    @Step("Войти в систему под указанным пользователем")
    public void login(User user) {
        driver.findElement(userField).sendKeys(user.getLogin());
        driver.findElement(passwordField).sendKeys(user.getPassword());
        driver.findElement(submitButton).click();
    }

    @Step("Проверить, отображается ли сообщение об ошибке")
    public boolean isErrorMsgDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    @Step("Получить текст сообщения об ошибке")
    public String getErrorMsgText() {
        return driver.findElement(errorMessage).getText();
    }

    @Step("Проверить, отображается ли поле ввода логина")
    public boolean isUserFieldDisplayed() {
        return driver.findElement(userField).isDisplayed();
    }

    @Step("Проверить, отображается ли поле ввода пароля")
    public boolean isPasswordFieldDisplayed() {
        return driver.findElement(passwordField).isDisplayed();
    }

    @Step("Проверить, отображается ли кнопка входа в систему")
    public boolean isSubmitButtonDisplayed() {
        return driver.findElement(submitButton).isDisplayed();
    }

    @Step("Получить цвет фона кнопки входа в систему")
    public String getSubmitButtonBgColor() {
        Color bgColor = Color.fromString(driver.findElement(submitButton).getCssValue("background-color"));
        return bgColor.asRgba();
    }

    @Step("Получить ожидаемый цвет фона кнопки входа в систему")
    public String getSubmitButtonExpectedBgColor() {
        return submitButtonExpectedBgColor;
    }
}
