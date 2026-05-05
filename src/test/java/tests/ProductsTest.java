package tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.UserFactory;

import java.util.List;

import static enums.PageDetails.PRODUCTS;

@Owner("Кушнерик Дмитрий")
@Epic("Страница Продукты")
@TmsLink("ForIdea")
@Issue("issues")
public class ProductsTest extends BaseTest {
    public List<String> goodsList = List.of(
            "Test.allTheThings() T-Shirt (Red)",
            "Sauce Labs Onesie",
            "Sauce Labs Fleece Jacket"
    );

    @Step("Удаление товара из корзину по номеру с последующей проверкой")
    public void removeGoodsAndCheck(int goodsNumber, SoftAssert soft) {
        productsPage.addToCart(goodsNumber);
        soft.assertEquals(productsPage.getAddToCartBtnText(goodsNumber), "Remove",
                WRONG_TEXT_ON_ELEMENT_PATTERN.formatted("the button"));
        soft.assertEquals(productsPage.navigationPanel.getCounterValue(), "1",
                WRONG_COUNTER_VALUE);
        productsPage.addToCart(goodsNumber);
        soft.assertEquals(productsPage.getAddToCartBtnText(goodsNumber), "Add to cart",
                WRONG_TEXT_ON_ELEMENT_PATTERN.formatted("the button"));
        soft.assertFalse(productsPage.navigationPanel.isCounterExists(),
                ELEMENT_IS_PRESENT_PATTERN.formatted("The cart counter"));
    }

    @Step("Удаление товара из корзины по наименованию с последующей проверкой")
    public void removeGoodsAndCheck(String goodsName, SoftAssert soft) {
        productsPage.addToCart(goodsName);
        soft.assertEquals(productsPage.getAddToCartBtnText(goodsName), "Remove",
                WRONG_TEXT_ON_ELEMENT_PATTERN.formatted("the button"));
        soft.assertEquals(productsPage.navigationPanel.getCounterValue(), "1",
                WRONG_COUNTER_VALUE);
        productsPage.addToCart(goodsName);
        soft.assertEquals(productsPage.getAddToCartBtnText(goodsName), "Add to cart",
                WRONG_TEXT_ON_ELEMENT_PATTERN.formatted("the button"));
        soft.assertFalse(productsPage.navigationPanel.isCounterExists(),
                ELEMENT_IS_PRESENT_PATTERN.formatted("The cart counter"));
    }

    @Step("Переход на страницу Продукты")
    @BeforeMethod
    public void openProductsPage() {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
    }

    @Feature("Работоспособность страницы Продукты")
    @Story("Переход на страницу Продукты")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка доступности страницы Продукты", priority = 1)
    void checkProductsPageIsOpened() {
        SoftAssert soft = new SoftAssert();
        openProductsPage();
        soft.assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(PRODUCTS.getPageName()));
        soft.assertTrue(productsPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(productsPage.getTitle(), PRODUCTS.getPageName(),
                WRONG_TITLE);
        soft.assertAll();
    }

    @Feature("Формирование списка товаров в корзине")
    @Story("Добавление товаров в корзину")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка добавления товаров в корзину", priority = 2)
    public void checkGoodsAdded() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(PRODUCTS.getPageName()));
        soft.assertEquals(productsPage.getGoodsQuantity(), 6, WRONG_PRODUCTS_QUANTITY);
        productsPage.addToCart(0);
        for (String goods : goodsList) {
            productsPage.addToCart(goods);
        }
        soft.assertTrue(productsPage.navigationPanel.isCounterExists(), ELEMENT_IS_NOT_EXIST_PATTERN.formatted("The cart counter"));
        soft.assertEquals(productsPage.navigationPanel.getCounterValue(), "4",
                WRONG_COUNTER_VALUE);
        soft.assertEquals(cartPage.navigationPanel.getCounterBgColor(), cartPage.navigationPanel.getExpectedCounterBgColor(),
                WRONG_BG_COLOR_PATTERN.formatted("Cart counter"));
        soft.assertAll();
    }

    @Feature("Формирование списка товаров в корзине")
    @Story("Удаление товаров из корзины")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка удаления товаров из корзины", priority = 3)
    public void checkGoodsRemoved() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(PRODUCTS.getPageName()));
        soft.assertEquals(productsPage.getGoodsQuantity(), 6, WRONG_PRODUCTS_QUANTITY);
        removeGoodsAndCheck(0, soft);
        removeGoodsAndCheck(goodsList.get(0), soft);
        soft.assertAll();
    }
}
