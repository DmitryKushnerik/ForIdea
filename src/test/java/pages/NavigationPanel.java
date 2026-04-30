package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationPanel {
    WebDriver driver;
    private By cartLink = By.cssSelector("a.shopping_cart_link");
    private By cartBadge = By.cssSelector("span.shopping_cart_badge");

    public NavigationPanel(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Получить ожидаемый цвет счетчика товаров")
    public String getExpectedCounterColor() {
        return "(226, 35, 26";
    }

    @Step("Проверить, существует ли счетчик товаров")
    public boolean isCounterExists() {
        return !driver.findElements(cartBadge).isEmpty();
    }

    @Step("Получить значение счетчика товаров")
    public String getCounterValue() {
        return driver.findElement(cartBadge).getText();
    }

    @Step("Получить цвет счетчика товаров")
    public String getCounterColor() {
        return driver.findElement(cartBadge).getCssValue("background-color");
    }

    @Step("Перейти на страницу Your Cart")
    public void switchToCart() {
        driver.findElement(cartLink).click();
    }
}
