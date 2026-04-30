package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOnePage extends BasePage {
    private String page_url = "checkout-step-one.html";
    private By cancelButton = By.id("cancel");
    private By continueButton = By.id("continue");
    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By errorMessage = By.cssSelector(DATA_TEST_PATTERN.formatted("error"));

    public CheckoutOnePage(WebDriver driver) {
        super(driver);
    }

    @Step("Кликнуть по кнопке Cancel")
    public void clickOnCancelButton() {
        driver.findElement(cancelButton).click();
    }

    @Step("Кликнуть по кнопке Continue")
    public void clickOnContinueButton() {
        driver.findElement(continueButton).click();
    }

    @Step("Заполнить поля First Name, Last Name, Zip/Postal")
    public void fillCheckoutOneFields(String firstName, String lastName, String postalCode) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    @Step("Проверить, отображается ли сообщение об ошибке")
    public boolean isErrorMessageDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    @Step("Получить текст сообщения об ошибке")
    public String getErrorMessageText() {
        return driver.findElement(errorMessage).getText();
    }

    @Step("Получить ожидаемый Url")
    public String getExpectedUrl() {
        return BASE_URL + page_url;
    }
}
