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

    public void removeGoodsAndCheck(int goodsNumber) {
        productsPage.addToCart(goodsNumber);
        assertEquals(productsPage.getAddToCartBtnText(goodsNumber), "Remove",
                "The text on the button does not match what is expected.");
        assertEquals(productsPage.navigationPanel.getCounterValue(), "1",
                "The cart counter value does not match the expected value.");
        productsPage.addToCart(goodsNumber);
        assertEquals(productsPage.getAddToCartBtnText(goodsNumber), "Add to cart",
                "The text on the button does not match what is expected.");
        assertFalse(productsPage.navigationPanel.isCounterExists(),
                "The cart counter is present.");
    }

    public void removeGoodsAndCheck(String goodsName) {
        productsPage.addToCart(goodsName);
        assertEquals(productsPage.getAddToCartBtnText(goodsName), "Remove",
                "The text on the button does not match what is expected.");
        assertEquals(productsPage.navigationPanel.getCounterValue(), "1",
                "The cart counter value does not match the expected value.");
        productsPage.addToCart(goodsName);
        assertEquals(productsPage.getAddToCartBtnText(goodsName), "Add to cart",
                "The text on the button does not match what is expected.");
        assertFalse(productsPage.navigationPanel.isCounterExists(), "The cart counter is present.");
    }

    @BeforeMethod
    public void openProductsPage() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
    }

    @Test
    public void checkGoodsAdded() {
        assertTrue(productsPage.isTitleDisplayed(), "The 'Products' page's title is not displayed");
        assertEquals(productsPage.getGoodsQuantity(), 6, "The quantity of goods is not as expected.");
        productsPage.addToCart(0);
        for (String goods : goodsList) {
            productsPage.addToCart(goods);
        }
        assertTrue(productsPage.navigationPanel.isCounterExists(), "The cart counter does not exist");
        assertEquals(productsPage.navigationPanel.getCounterValue(), "4",
                "The cart counter value does not match the expected value.");
        assertEquals(productsPage.navigationPanel.getCounterColor(), productsPage.navigationPanel.getExpectedCounterColor(),
                "The BG color of cart counter does not match what is expected.");
    }

    @Test
    public void checkGoodsRemoved() {
        assertTrue(productsPage.isTitleDisplayed(), "The 'Products' page's title is not displayed");
        assertEquals(productsPage.getGoodsQuantity(), 6, "The quantity of goods is not as expected.");
        removeGoodsAndCheck(0);
        removeGoodsAndCheck(goodsList.get(0));
    }
}
