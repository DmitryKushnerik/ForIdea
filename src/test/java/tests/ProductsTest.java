package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class ProductsTest extends BaseTest {
    public List<String> goodsList = List.of(
            "Test.allTheThings() T-Shirt (Red)",
            "Sauce Labs Onesie",
            "Sauce Labs Fleece Jacket"
    );

    public void removeGoods(int goodsNumber) {
        productsPage.addToCart(goodsNumber);
        assertEquals(productsPage.getAddToCartBtnText(goodsNumber), "Remove", "The text on the button does not match what is expected.");
        assertEquals(productsPage.getCounterValue(), "1", "The cart counter value does not match the expected value.");
        productsPage.addToCart(goodsNumber);
        assertEquals(productsPage.getAddToCartBtnText(goodsNumber), "Add to cart", "The text on the button does not match what is expected.");
        assertFalse(productsPage.isCounterExists(), "The cart counter is present.");
    }

    public void removeGoods(String goodsName) {
        productsPage.addToCart(goodsName);
        assertEquals(productsPage.getAddToCartBtnText(goodsName), "Remove", "The text on the button does not match what is expected.");
        assertEquals(productsPage.getCounterValue(), "1", "The cart counter value does not match the expected value.");
        productsPage.addToCart(goodsName);
        assertEquals(productsPage.getAddToCartBtnText(goodsName), "Add to cart", "The text on the button does not match what is expected.");
        assertFalse(productsPage.isCounterExists(), "The cart counter is present.");
    }

    @BeforeMethod
    public void openProductsPage() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(productsPage.isTitleDisplayed(), "The 'Products' page's title is not displayed");
        assertEquals(productsPage.getGoodsQuantity(), 6, "The quantity of goods is not as expected.");
    }

    @Test
    public void checkGoodsAdded() {
        productsPage.addToCart(0);
        for (String goods : goodsList) {
            productsPage.addToCart(goods);
        }
        assertTrue(productsPage.isCounterExists(), "The cart counter does not exist");
        assertEquals(productsPage.getCounterValue(), "4", "The cart counter value does not match the expected value.");
        assertEquals(productsPage.getCounterColor(), "rgba(226, 35, 26, 1)", "The BG color of cart counter does not match what is expected.");
    }

    @Test
    public void checkGoodsRemoved() {
        removeGoods(0);
        removeGoods(goodsList.get(0));
    }
}
