package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {
    private static final String REMOVE_BUTTON_PATTERN = "//div[text()='%s']//ancestor::div[@class='cart_item']//child::button";
    private By product = By.cssSelector(".inventory_item_name");
    private By continueShoppingButton = By.id("continue-shopping");
    private By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
        this.page_url = "cart.html";
    }

    @Step("Получить список продуктов в корзине")
    public ArrayList<String> getProductsNames() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueShoppingButton));
        List<WebElement> allProducts = driver.findElements(product);
        ArrayList<String> names = new ArrayList<>();
        for (WebElement product : allProducts) {
            names.add(product.getText());
        }
        return names;
    }

    @Step("Удалить продукт из корзины")
    public void removeGoods(String goodsName) {
        By button = By.xpath(REMOVE_BUTTON_PATTERN.formatted(goodsName));
        driver.findElement(button).click();
    }

    @Step("Кликнуть по кнопке Continue Shopping")
    public void clickOnContinueButton() {
        driver.findElement(continueShoppingButton).click();
    }

    @Step("Кликнуть по кнопке Checkout")
    public void clickOnCheckoutButton() {
        driver.findElement(checkoutButton).click();
    }
}
