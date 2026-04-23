package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import user.UserFactory;

import static org.testng.Assert.*;

public class CartTest extends BaseTest {
    final String goodsName = "Sauce Labs Fleece Jacket";

    @BeforeMethod
    public void openProductsPage() {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
    }

    @Test
    public void checkGoodsInCart() {
        assertTrue(productsPage.isTitleDisplayed(), "The 'Products' page's title is not displayed");
        productsPage.addToCart(goodsName);
        productsPage.navigationPanel.switchToCart();
        assertEquals(cartPage.getTitle(), "Your Cart", "The 'Your Cart' web page is not open");
        assertFalse(cartPage.getProductsNames().isEmpty(), "Cart is empty");
        assertEquals(cartPage.getProductsNames().size(), 1,
                "The number of items in the cart does not match the expected quantity.");
        assertTrue(cartPage.getProductsNames().contains(goodsName),
                "The product list does not contain the expected name.");
        assertEquals(cartPage.navigationPanel.getCounterValue(), "1",
                "The cart counter value does not match the expected value.");
        assertEquals(cartPage.navigationPanel.getCounterColor(), cartPage.navigationPanel.getExpectedCounterColor(),
                "The BG color of cart counter does not match what is expected.");
    }

    @Test
    public void checkRemoveGoodsFromCart() {
        assertTrue(productsPage.isTitleDisplayed(), "The 'Products' page's title is not displayed");
        productsPage.addToCart(goodsName);
        productsPage.navigationPanel.switchToCart();
        assertEquals(cartPage.getTitle(), "Your Cart", "The 'Your Cart' web page is not open");
        assertEquals(cartPage.navigationPanel.getCounterValue(), "1",
                "The cart counter value does not match the expected value.");
        cartPage.removeGoods(goodsName);
        assertTrue(cartPage.getProductsNames().isEmpty(), "Cart is not empty");
        assertFalse(cartPage.navigationPanel.isCounterExists(), "The cart counter is present.");
    }
}
