package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static enums.PageDetails.CHECKOUT3;

public class CheckoutThreePage extends BasePage {
    private final By completeImage = By.cssSelector("img.pony_express");
    private final By completeHeader = By.cssSelector("h2.complete-header");
    private final By completeText = By.cssSelector("div.complete-text");
    private final By backHomeButton = By.id("back-to-products");

    public CheckoutThreePage(WebDriver driver) {
        super(driver);
        this.page_url = CHECKOUT3.getPageUrl();
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

    @Step("Проверить, отображается ли кнопка Back Home")
    public boolean isBackHomeButtonDisplayed() {
        return driver.findElement(backHomeButton).isDisplayed();
    }

    @Step("Нажать на кнопку Back Home")
    public void clickOnBackHomeButton() {
        driver.findElement(backHomeButton).click();
    }
}
