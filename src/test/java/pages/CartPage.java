package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {
    private final static String REMOVE_BUTTON_PATTERN = "//div[text()='%s']//ancestor::div[@class='cart_item']//child::button";
    private final By product = By.cssSelector(".inventory_item_name");
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public ArrayList<String> getProductsNames() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueShoppingButton));
        List<WebElement> allProducts = driver.findElements(product);
        ArrayList<String> names = new ArrayList<>();
        for (WebElement product : allProducts) {
            names.add(product.getText());
        }
        return names;
    }

    public void removeGoods(String goodsName) {
        By button = By.xpath(REMOVE_BUTTON_PATTERN.formatted(goodsName));
        driver.findElement(button).click();
    }
}
