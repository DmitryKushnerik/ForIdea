package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ProductsTest extends BaseTest {
    @BeforeMethod
    public void openProductsPage() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(productsPage.isTitleDisplayed(), "The 'Products' page's title is not displayed");
    }

    @Test
    public void checkGoodsAdded() {
        productsPage.addToCart();
        assertEquals(productsPage.getAddToCartBtnText(), "Remove", "The text on the button does not match what is expected.");
        assertTrue(productsPage.isCounterExists(), "The cart counter does not exist");
        assertEquals(productsPage.getCounterValue(), "1", "The cart counter value does not match the expected value.");
        assertEquals(productsPage.getCounterColor(), "rgba(226, 35, 26, 1)", "The BG color of cart counter does not match what is expected.");
    }

    @Test
    public void checkGoodsRemoved() {
        productsPage.addToCart();
        assertEquals(productsPage.getAddToCartBtnText(), "Remove", "The text on the button does not match what is expected.");
        productsPage.addToCart();
        assertEquals(productsPage.getAddToCartBtnText(), "Add to cart", "The text on the button does not match what is expected.");
        assertFalse(productsPage.isCounterExists(), "The cart counter is present.");
    }
}
