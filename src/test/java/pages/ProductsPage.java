package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {
    private static final String ADD_TO_CART_PATTERN = "//div[text()='%s']/ancestor::div[@class='inventory_item']//child::button";
    private final By addToCartBtn = By.xpath("//div[@class='inventory_item']//child::button");
    private final By cartLink = By.cssSelector("a.shopping_cart_link");
    private final By cartBadge = By.cssSelector("span.shopping_cart_badge");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public int getGoodsQuantity() {
        return driver.findElements(addToCartBtn).size();
    }

    public void addToCart(int goodsNumber) {
        driver.findElements(addToCartBtn).get(goodsNumber).click();
    }

    public void addToCart(final String goodsName) {
        By addToCart = By.xpath(ADD_TO_CART_PATTERN.formatted(goodsName));
        driver.findElement(addToCart).click();
    }

    public String getAddToCartBtnText(int goodsNumber) {
        return driver.findElements(addToCartBtn).get(goodsNumber).getText();
    }

    public String getAddToCartBtnText(String goodsName) {
        By button = By.xpath(ADD_TO_CART_PATTERN.formatted(goodsName));
        return driver.findElement(button).getText();
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
