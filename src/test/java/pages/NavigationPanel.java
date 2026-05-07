package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;

public class NavigationPanel {
    WebDriver driver;
    private final By cartLink = By.cssSelector("a.shopping_cart_link");
    private final By cartBadge = By.cssSelector("span.shopping_cart_badge");
    private final String expectedCounterBgColor = "rgba(226, 35, 26, 1)";

    public NavigationPanel(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Проверить, существует ли счетчик товаров")
    public boolean isCounterExists() {
        return !driver.findElements(cartBadge).isEmpty();
    }

    @Step("Получить значение счетчика товаров")
    public String getCounterValue() {
        return driver.findElement(cartBadge).getText();
    }

    @Step("Получить цвет фона счетчика товаров")
    public String getCounterBgColor() {
        Color bgColor = Color.fromString(driver.findElement(cartBadge).getCssValue("background-color"));
        return bgColor.asRgba();
    }

    @Step("Получить ожидаемый цвет фона счетчика товаров")
    public String getExpectedCounterBgColor() {
        return expectedCounterBgColor;
    }

    @Step("Перейти на страницу Your Cart")
    public void switchToCart() {
        driver.findElement(cartLink).click();
    }
}
