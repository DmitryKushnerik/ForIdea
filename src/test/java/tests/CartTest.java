package tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CartTest extends BaseTest {
    @Test
    public void checkPageTitle() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(productsPage.isTitleDisplayed(), "The 'Products' page's title is not displayed");
        productsPage.addToCart();
        productsPage.goToCartPage();
        assertEquals(cartPage.getTitle(), "Your Cart", "The 'Your Cart' web page is not open");
    }
}
