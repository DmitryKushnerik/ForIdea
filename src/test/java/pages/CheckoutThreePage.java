package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutThreePage extends BasePage {
    private String page_url = "checkout-complete.html";
    private By completeImage = By.cssSelector("img.pony_express");
    private By completeHeader = By.cssSelector("h2.complete-header");
    private By completeText = By.cssSelector("div.complete-text");
    private By backHomeButton = By.id("back-to-products");

    public CheckoutThreePage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверить, отображается ли изображение")
    public boolean isCompleteImageDisplayed() {
        return driver.findElement(completeImage).isDisplayed();
    }

    @Step("Проверить, отображается ли подзаголовок")
    public boolean isCompleteHeaderDisplayed() {
        return driver.findElement(completeHeader).isDisplayed();
    }

    @Step("Проверить, отображается ли текст")
    public boolean isCompleteTextDisplayed() {
        return driver.findElement(completeText).isDisplayed();
    }

    @Step("Проверить, отображается ли кнопка")
    public boolean isBackHomeButtonDisplayed() {
        return driver.findElement(backHomeButton).isDisplayed();
    }

    @Step("Кликнуть по кнопке Home Button")
    public void clickOnBackHomeButton() {
        driver.findElement(backHomeButton).click();
    }

    @Step("Получить ожидаемый Url")
    public String getExpectedUrl() {
        return BASE_URL + page_url;
    }
}
