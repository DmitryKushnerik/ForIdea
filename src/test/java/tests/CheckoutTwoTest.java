package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.UserFactory;

import java.util.Locale;

import static enums.PageDetails.*;
import static org.testng.Assert.assertEquals;
import static testdata.Messages.*;

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
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка доступности страницы подтверждения заказа", priority = 1)
    void checkOutTwoPageIsOpened() {
        SoftAssert soft = new SoftAssert();
        openCheckoutTwoPage(true);
        soft.assertEquals(checkoutTwoPage.getCurrentUrl(), checkoutTwoPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT2.getPageName()));
        soft.assertTrue(checkoutTwoPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(checkoutTwoPage.getTitle(), CHECKOUT2.getPageName(), WRONG_TITLE);
        soft.assertTrue(checkoutTwoPage.isCancelButtonDisplayed(),
                ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted(("Cancel Button")));
        soft.assertTrue(checkoutTwoPage.isFinishButtonDisplayed(),
                ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted(("Cancel Button")));
        soft.assertAll();
    }

    @Feature("Работоспособность страницы оформления заказа")
    @Story("Расчет стоимости товаров, налога и общей стоимости с налогом")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка корректности расчета стоимости товаров, налога и общей стоимости с налогом", priority = 2)
    void checkCorrectCalculations() {
        double sum;
        SoftAssert soft = new SoftAssert();
        openCheckoutTwoPage(true);
        soft.assertEquals(checkoutTwoPage.getCurrentUrl(), checkoutTwoPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT2.getPageName()));
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
    @Test(description = "Проверка доступности страницы подтверждения заказа", priority = 3)
    void checkGotoProductsPage() {
        SoftAssert soft = new SoftAssert();
        openCheckoutTwoPage(false);
        soft.assertEquals(checkoutTwoPage.getCurrentUrl(), checkoutTwoPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT2.getPageName()));
        checkoutTwoPage.clickOnCancelButton();
        assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(PRODUCTS.getPageName()));
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице оформления заказа")
    @Story("Переход на страницу завершения заказа")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка перехода на страницу завершения заказа", priority = 4)
    void checkGotoCheckoutThreePage() {
        SoftAssert soft = new SoftAssert();
        openCheckoutTwoPage(false);
        soft.assertEquals(checkoutTwoPage.getCurrentUrl(), checkoutTwoPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT2.getPageName()));
        checkoutTwoPage.clickOnFinishButton();
        assertEquals(checkoutThreePage.getCurrentUrl(), checkoutThreePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT3.getPageName()));
        soft.assertAll();
    }
}
