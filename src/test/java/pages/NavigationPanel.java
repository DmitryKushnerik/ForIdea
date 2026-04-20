package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationPanel {
    WebDriver driver;
    private final By cartLink = By.cssSelector("a.shopping_cart_link");
    private final By cartBadge = By.cssSelector("span.shopping_cart_badge");
    private final String expectedCounterColor = "rgba(226, 35, 26, 1)";

    public NavigationPanel(WebDriver driver) {
        this.driver = driver;
    }

    public String getExpectedCounterColor() {
        return expectedCounterColor;
    }

    public boolean isCounterExists() {
        return !driver.findElements(cartBadge).isEmpty();
    }

    public String getCounterValue() {
        return driver.findElement(cartBadge).getText();
    }

    public String getCounterColor() {
        return driver.findElement(cartBadge).getCssValue("background-color");
    }

    public void switchToCart() {
        driver.findElement(cartLink).click();
    }
}
