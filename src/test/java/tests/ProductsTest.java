package tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.UserFactory;

import java.util.List;

@Owner("Кушнерик Дмитрий")
@Epic("Страница Продукты")
@TmsLink("ForIdea")
@Issue("issues")
public class ProductsTest extends BaseTest {
    String expectedColor = "(226, 35, 26";
    public List<String> goodsList = List.of(
            "Test.allTheThings() T-Shirt (Red)",
            "Sauce Labs Onesie",
            "Sauce Labs Fleece Jacket"
    );

    @Step("Удаление товара из корзину по номеру с последующей проверкой")
    public void removeGoodsAndCheck(int goodsNumber, SoftAssert soft) {
        productsPage.addToCart(goodsNumber);
        soft.assertEquals(productsPage.getAddToCartBtnText(goodsNumber), "Remove",
                "The text on the button does not match what is expected.");
        soft.assertEquals(productsPage.navigationPanel.getCounterValue(), "1",
                "The cart counter value does not match the expected value.");
        productsPage.addToCart(goodsNumber);
        soft.assertEquals(productsPage.getAddToCartBtnText(goodsNumber), "Add to cart",
                "The text on the button does not match what is expected.");
        soft.assertFalse(productsPage.navigationPanel.isCounterExists(),
                "The cart counter is present.");
    }

    @Step("Удаление товара из корзины по наименованию с последующей проверкой")
    public void removeGoodsAndCheck(String goodsName, SoftAssert soft) {
        productsPage.addToCart(goodsName);
        soft.assertEquals(productsPage.getAddToCartBtnText(goodsName), "Remove",
                "The text on the button does not match what is expected.");
        soft.assertEquals(productsPage.navigationPanel.getCounterValue(), "1",
                "The cart counter value does not match the expected value.");
        productsPage.addToCart(goodsName);
        soft.assertEquals(productsPage.getAddToCartBtnText(goodsName), "Add to cart",
                "The text on the button does not match what is expected.");
        soft.assertFalse(productsPage.navigationPanel.isCounterExists(), "The cart counter is present.");
    }

    @Step("Переход на страницу Продукты")
    @BeforeMethod
    public void openProductsPage() {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
    }

    @Feature("Формирование списка товаров в корзине")
    @Story("Добавление товаров в корзину")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка добавления товаров в корзину")
    public void checkGoodsAdded() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Products"));
        soft.assertTrue(productsPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(productsPage.getGoodsQuantity(), 6, "The quantity of goods is not as expected.");
        productsPage.addToCart(0);
        for (String goods : goodsList) {
            productsPage.addToCart(goods);
        }
        soft.assertTrue(productsPage.navigationPanel.isCounterExists(), "The cart counter does not exist");
        soft.assertEquals(productsPage.navigationPanel.getCounterValue(), "4",
                "The cart counter value does not match the expected value.");
        soft.assertTrue(cartPage.navigationPanel.getCounterColor().contains(expectedColor),
                "The BG color of cart counter does not match what is expected.");
        soft.assertAll();
    }

    @Feature("Формирование списка товаров в корзине")
    @Story("Удаление товаров из корзины")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка удаления товаров из корзины")
    public void checkGoodsRemoved() {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(productsPage.isTitleDisplayed(), "The 'Products' page's title is not displayed");
        soft.assertEquals(productsPage.getGoodsQuantity(), 6, "The quantity of goods is not as expected.");
        removeGoodsAndCheck(0, soft);
        removeGoodsAndCheck(goodsList.get(0), soft);
        soft.assertAll();
    }
}
