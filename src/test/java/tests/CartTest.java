package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.UserFactory;

import static enums.PageDetails.*;

@Owner("Кушнерик Дмитрий")
@Epic("Страница Your Cart")
@TmsLink("ForIdea")
@Issue("issues")
public class CartTest extends BaseTest {
    String goodsName = "Sauce Labs Fleece Jacket";

    @Step("Открыть страницу корзина")
    public void openCartPage(boolean product) {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
        if (product)
            productsPage.addToCart(goodsName);
        productsPage.navigationPanel.switchToCart();
    }

    @Feature("Работоспособность страницы корзины")
    @Story("Переход на страницу корзины")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка доступности страницы корзина", priority = 1)
    void checkCartPageIsOpened() {
        SoftAssert soft = new SoftAssert();
        openCartPage(true);
        soft.assertEquals(cartPage.getCurrentUrl(), cartPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CART.getPageName()));
        soft.assertTrue(cartPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(cartPage.getTitle(), CART.getPageName(), WRONG_TITLE);
        soft.assertAll();
    }

    @Feature("Список товаров в корзине")
    @Story("Добавление товара в корзину")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка наличия добавленного товара в корзине", priority = 2)
    public void checkGoodsInCart() {
        SoftAssert soft = new SoftAssert();
        openCartPage(true);
        soft.assertEquals(cartPage.getCurrentUrl(), cartPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CART.getPageName()));
        soft.assertFalse(cartPage.getProductsNames().isEmpty(), CART_IS_EMPTY);
        soft.assertEquals(cartPage.getProductsNames().size(), 1,
                WRONG_CART_QUANTITY);
        soft.assertTrue(cartPage.getProductsNames().contains(goodsName),
                NO_PRODUCT_IN_CART);
        soft.assertEquals(cartPage.navigationPanel.getCounterValue(), "1",
                WRONG_COUNTER_VALUE);
        soft.assertEquals(cartPage.navigationPanel.getCounterBgColor(), cartPage.navigationPanel.getExpectedCounterBgColor(),
                WRONG_BG_COLOR_PATTERN.formatted("Cart counter"));
        soft.assertAll();
    }

    @Feature("Список товаров в корзине")
    @Story("Удаление товара из корзины")
    @Test(description = "Проверка удаления товара со страницы корзины", priority = 3)
    @Severity(SeverityLevel.CRITICAL)
    public void checkRemoveGoodsFromCart() {
        SoftAssert soft = new SoftAssert();
        openCartPage(true);
        soft.assertEquals(cartPage.getCurrentUrl(), cartPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CART.getPageName()));
        soft.assertEquals(cartPage.navigationPanel.getCounterValue(), "1",
                WRONG_COUNTER_VALUE);
        cartPage.removeGoods(goodsName);
        soft.assertTrue(cartPage.getProductsNames().isEmpty(), CART_IS_NOT_EMPTY);
        soft.assertFalse(cartPage.navigationPanel.isCounterExists(), ELEMENT_IS_PRESENT_PATTERN.formatted("Cart counter"));
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице корзины")
    @Story("Возвращение на страницу продуктов")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка возвращения на страницу продуктов", priority = 4)
    public void checkReturnToProductPage() {
        SoftAssert soft = new SoftAssert();
        openCartPage(false);
        soft.assertEquals(cartPage.getCurrentUrl(), cartPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CART.getPageName()));
        cartPage.clickOnContinueButton();
        soft.assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(PRODUCTS.getPageName()));
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице корзины")
    @Story("Переход на страницу оформления заказа")
    @Test(description = "Проверка перехода на страницу оформления заказа", priority = 5)
    @Severity(SeverityLevel.NORMAL)
    public void checkGotoCheckoutPage() {
        SoftAssert soft = new SoftAssert();
        openCartPage(false);
        soft.assertEquals(cartPage.getCurrentUrl(), cartPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CART.getPageName()));
        cartPage.clickOnCheckoutButton();
        soft.assertEquals(checkoutOnePage.getCurrentUrl(), checkoutOnePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT1.getPageName()));
        soft.assertAll();
    }
}
