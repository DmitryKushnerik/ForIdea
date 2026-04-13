package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {
    private final By addToCartBtn = By.xpath("//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item']//button");
    private final By cartLink = By.cssSelector("a.shopping_cart_link");
    private final By cartBadge = By.cssSelector("span.shopping_cart_badge");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void addToCart() {
        driver.findElement(addToCartBtn).click();
    }

    public String getAddToCartBtnText() {
        return driver.findElement(addToCartBtn).getText();
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

    public void goToCartPage() {
        driver.findElement(cartLink).click();
    }
}
