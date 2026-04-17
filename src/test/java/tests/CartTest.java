package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CartTest extends BaseTest {
    final String goodsName = "Sauce Labs Fleece Jacket";

    @BeforeMethod
    public void openProductsPage() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
    }

    @Test
    public void checkGoodsInCart() {
        assertTrue(productsPage.isTitleDisplayed(), "The 'Products' page's title is not displayed");
        productsPage.addToCart(goodsName);
        productsPage.switchToCart();
        assertEquals(cartPage.getTitle(), "Your Cart", "The 'Your Cart' web page is not open");
        assertFalse(cartPage.getProductsNames().isEmpty(), "Cart is empty");
        assertEquals(cartPage.getProductsNames().size(), 1, "The number of items in the cart does not match the expected quantity.");
        assertTrue(cartPage.getProductsNames().contains(goodsName), "The product list does not contain the expected name.");
    }

    @Test
    public void checkRemoveGoodsFromCart() {
        assertTrue(productsPage.isTitleDisplayed(), "The 'Products' page's title is not displayed");
        productsPage.addToCart(goodsName);
        productsPage.switchToCart();
        assertEquals(cartPage.getTitle(), "Your Cart", "The 'Your Cart' web page is not open");
        cartPage.removeGoods(goodsName);
        assertTrue(cartPage.getProductsNames().isEmpty(), "Cart is not empty");
    }
}
