package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static enums.PageDetails.PRODUCTS;

public class ProductsPage extends BasePage {
    private static final String ADD_TO_CART_PATTERN = "//div[text()='%s']/ancestor::div[@class='inventory_item']//child::button";
    private final By addToCartBtn = By.xpath("//div[@class='inventory_item']//child::button");

    public ProductsPage(WebDriver driver) {
        super(driver);
        this.page_url = PRODUCTS.getPageUrl();
    }

    @Step("Получить количество товаров на странице")
    public int getGoodsQuantity() {
        return driver.findElements(addToCartBtn).size();
    }

    @Step("Добавить в корзину товар с указанным номером")
    public void addToCart(int goodsNumber) {
        driver.findElements(addToCartBtn).get(goodsNumber).click();
    }

    @Step("Добавить в корзину товар с указанным наименованием")
    public void addToCart(String goodsName) {
        By addToCart = By.xpath(ADD_TO_CART_PATTERN.formatted(goodsName));
        driver.findElement(addToCart).click();
    }

    @Step("Получить текст с кнопки товара с указанным номером")
    public String getAddToCartBtnText(int goodsNumber) {
        return driver.findElements(addToCartBtn).get(goodsNumber).getText();
    }

    @Step("Получить текст с кнопки товара с указанным наименованием")
    public String getAddToCartBtnText(String goodsName) {
        By button = By.xpath(ADD_TO_CART_PATTERN.formatted(goodsName));
        return driver.findElement(button).getText();
    }
}
