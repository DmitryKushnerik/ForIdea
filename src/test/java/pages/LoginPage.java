package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import user.User;

public class LoginPage extends BasePage {
    private String page_url = "";
    private By userField = By.cssSelector("#user-name");
    private By passwordField = By.cssSelector("[placeholder=Password]");
    private By submitButton = By.id("login-button");
    private By errorMessage = By.cssSelector(DATA_TEST_PATTERN.formatted("error"));

    public LoginPage(WebDriver driver) {
        super(driver);
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

    @Step("Получить ожидаемый Url")
    public String getExpectedUrl() {
        return BASE_URL + page_url;
    }
}
