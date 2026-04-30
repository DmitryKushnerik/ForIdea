package tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.UserFactory;

import static org.testng.Assert.assertEquals;

@Owner("Кушнерик Дмитрий")
@Epic("Cтраница Your Cart")
@TmsLink("ForIdea")
@Issue("issues")
public class CartTest extends BaseTest {
    String goodsName = "Sauce Labs Fleece Jacket";

    @Step("Зайти на сайт под пользователем с админскими правами")
    @BeforeMethod
    public void openProductsPage() {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
    }

    @Feature("Список товаров в корзине")
    @Story("Добавление товара в корзину")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка наличия добавленного товара в корзине")
    public void checkGoodsInCart() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Products"));
        soft.assertTrue(productsPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(productsPage.getTitle(), "Products", WRONG_TITLE);
        productsPage.addToCart(goodsName);
        productsPage.navigationPanel.switchToCart();
        soft.assertEquals(cartPage.getCurrentUrl(), cartPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Your Cart"));
        soft.assertTrue(cartPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(cartPage.getTitle(), "Your Cart", WRONG_TITLE);
        soft.assertFalse(cartPage.getProductsNames().isEmpty(), "Cart is empty");
        soft.assertEquals(cartPage.getProductsNames().size(), 1,
                "The number of items in the cart does not match the expected quantity.");
        soft.assertTrue(cartPage.getProductsNames().contains(goodsName),
                "The product list does not contain the expected name.");
        soft.assertEquals(cartPage.navigationPanel.getCounterValue(), "1",
                "The cart counter value does not match the expected value.");
        soft.assertTrue(cartPage.navigationPanel.getCounterColor().contains(cartPage.navigationPanel.getExpectedCounterColor()),
                "The BG color of cart counter does not match what is expected.");
        soft.assertAll();
    }

    @Feature("Список товаров в корзине")
    @Story("Удаление товара из корзины")
    @Test(description = "Проверка удаления товара со страницы корзины")
    @Severity(SeverityLevel.CRITICAL)
    public void checkRemoveGoodsFromCart() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Products"));
        soft.assertTrue(productsPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(productsPage.getTitle(), "Products", WRONG_TITLE);
        productsPage.addToCart(goodsName);
        productsPage.navigationPanel.switchToCart();
        soft.assertEquals(cartPage.getCurrentUrl(), cartPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Your Cart"));
        soft.assertTrue(cartPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(cartPage.getTitle(), "Your Cart", WRONG_TITLE);
        soft.assertEquals(cartPage.navigationPanel.getCounterValue(), "1",
                "The cart counter value does not match the expected value.");
        cartPage.removeGoods(goodsName);
        soft.assertTrue(cartPage.getProductsNames().isEmpty(), "Cart is not empty");
        soft.assertFalse(cartPage.navigationPanel.isCounterExists(), "The cart counter is present.");
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице корзины")
    @Story("Возвращение на страницу продуктов")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка возвращения на страницу продуктов")
    public void checkReturnToProductPage() {
        productsPage.navigationPanel.switchToCart();
        cartPage.clickOnContinueButton();
        assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Products"));
    }

    @Feature("Навигационные кнопки на странице корзины")
    @Story("Переход на страницу оформления заказа")
    @Test(description = "Проверка перехода на страницу оформления заказа")
    @Severity(SeverityLevel.NORMAL)
    public void checkGotoCheckoutPage() {
        productsPage.navigationPanel.switchToCart();
        cartPage.clickOnCheckoutButton();
        assertEquals(checkoutOnePage.getCurrentUrl(), checkoutOnePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Checkout: Your Information"));
    }
}
