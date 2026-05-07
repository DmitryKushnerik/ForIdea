package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static enums.PageDetails.CHECKOUT1;

public class CheckoutOnePage extends BasePage {
    private final By cancelButton = By.id("cancel");
    private final By continueButton = By.id("continue");
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By errorMessage = By.cssSelector(DATA_TEST_PATTERN.formatted("error"));

    public CheckoutOnePage(WebDriver driver) {
        super(driver);
        this.page_url = CHECKOUT1.getPageUrl();
    }

    @Step("Нажать на кнопку Cancel")
    public void clickOnCancelButton() {
        driver.findElement(cancelButton).click();
    }

    @Step("Нажать на кнопку Continue")
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

    @Step("Проверить, отображается ли поле ввода First Name")
    public boolean isFirstNameFieldDisplayed() {
        return driver.findElement(firstNameField).isDisplayed();
    }

    @Step("Проверить, отображается ли поле ввода Last Name")
    public boolean isLastNameFieldDisplayed() {
        return driver.findElement(lastNameField).isDisplayed();
    }

    @Step("Проверить, отображается ли поле ввода Zip/Postal code")
    public boolean isPostalCodeFieldDisplayed() {
        return driver.findElement(postalCodeField).isDisplayed();
    }

    @Step("Проверить, отображается ли кнопка Cancel")
    public boolean isCancelButtonDisplayed() {
        return driver.findElement(cancelButton).isDisplayed();
    }

    @Step("Проверить, отображается ли кнопка Continue")
    public boolean isContinueButtonDisplayed() {
        return driver.findElement(continueButton).isDisplayed();
    }
}
