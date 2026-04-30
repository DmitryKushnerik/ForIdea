package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.UserFactory;

import java.util.Locale;

import static org.testng.Assert.assertEquals;

@Owner("Кушнерик Дмитрий")
@Epic("Страница Checkout: Overview")
@TmsLink("ForIdea")
@Issue("issues")
public class CheckoutTwoTest extends BaseTest {
    String firstName = "Ivan";
    String lastName = "Ivanov";
    String postalCode = "658248";

    @Step("Перейти на страницу подтвердения заказа")
    public void openCheckoutTwoPage(boolean product) {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
        if (product) {
            productsPage.addToCart("Sauce Labs Backpack");
            productsPage.addToCart("Sauce Labs Fleece Jacket");
            productsPage.addToCart("Sauce Labs Onesie");
        }
        productsPage.navigationPanel.switchToCart();
        cartPage.clickOnCheckoutButton();
        checkoutOnePage.fillCheckoutOneFields(firstName, lastName, postalCode);
        checkoutOnePage.clickOnContinueButton();
    }

    @Feature("Работоспособность страницы оформления заказа")
    @Story("Переход на страницу оформления заказа")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка доступности страницы подтверждения заказа")
    void checkOutTwoPageIsOpened() {
        SoftAssert soft = new SoftAssert();
        openCheckoutTwoPage(true);
        soft.assertEquals(checkoutTwoPage.getCurrentUrl(), checkoutTwoPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Checkout: Overview"));
        soft.assertTrue(checkoutTwoPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(checkoutTwoPage.getTitle(), "Checkout: Overview", WRONG_TITLE);
        soft.assertAll();
    }

    @Feature("Работоспособность страницы оформления заказа")
    @Story("Расчет стоимости товаров, налога и общей стоимости с налогом")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка корректности расчета стоимости товаров, налога и общей стоимости с налогом")
    void checkCorrectCalculations() {
        double sum;
        SoftAssert soft = new SoftAssert();
        openCheckoutTwoPage(true);
        sum = checkoutTwoPage.calculateTotalCost();
        soft.assertTrue(checkoutTwoPage.getSubtotalCost().contains(String.format(Locale.US, "%.2f", sum)),
                "Incorrect price without tax.");
        soft.assertTrue(checkoutTwoPage.getTax().contains(String.format(Locale.US, "%.2f", sum * 0.08)),
                "The incorrect tax.");
        soft.assertTrue(checkoutTwoPage.getTotalCost().contains(String.format(Locale.US, "%.2f", sum * 1.08)),
                "Incorrect price with tax.");
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице оформления заказа")
    @Story("Возвращение на страницу Продукты")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка доступности страницы подтверждения заказа")
    void checkGotoProductsPage() {
        SoftAssert soft = new SoftAssert();
        openCheckoutTwoPage(false);
        checkoutTwoPage.clickOnCancelButton();
        assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Products"));
        soft.assertTrue(productsPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(productsPage.getTitle(), "Products", WRONG_TITLE);
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице оформления заказа")
    @Story("Переход на страницу завершения заказа")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка перехода на страницу завершения заказа")
    void checkGotoCheckoutThreePage() {
        SoftAssert soft = new SoftAssert();
        openCheckoutTwoPage(false);
        checkoutTwoPage.clickOnFinishButton();
        assertEquals(checkoutThreePage.getCurrentUrl(), checkoutThreePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Checkout: Complete!"));
        soft.assertTrue(checkoutThreePage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(checkoutThreePage.getTitle(), "Checkout: Complete!", WRONG_TITLE);
        soft.assertAll();
    }
}
