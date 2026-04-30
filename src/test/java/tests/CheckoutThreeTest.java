package tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.UserFactory;

import static org.testng.Assert.assertEquals;

@Owner("Кушнерик Дмитрий")
@Epic("Страница Checkout: Complete!")
@TmsLink("ForIdea")
@Issue("issues")
public class CheckoutThreeTest extends BaseTest {
    String firstName = "Ivan";
    String lastName = "Ivanov";
    String postalCode = "658248";

    @Step("Перейти на страницу завершения заказа")
    @BeforeMethod
    void openCheckoutThreePage() {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
        productsPage.navigationPanel.switchToCart();
        cartPage.clickOnCheckoutButton();
        checkoutOnePage.fillCheckoutOneFields(firstName, lastName, postalCode);
        checkoutOnePage.clickOnContinueButton();
        checkoutTwoPage.clickOnFinishButton();
    }

    @Feature("Работоспособность страницы завершения заказа")
    @Story("Переход на страницу завершения заказа")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка корректности отображения страницы подтверждения заказа")
    void checkCheckoutThreePageIsOpenen() {
        SoftAssert soft = new SoftAssert();
        assertEquals(checkoutThreePage.getCurrentUrl(), checkoutThreePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Checkout: Complete!"));
        soft.assertTrue(checkoutThreePage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(checkoutThreePage.getTitle(), "Checkout: Complete!", WRONG_TITLE);
        soft.assertTrue(checkoutThreePage.isCompleteImageDisplayed(), "The image is missing.");
        soft.assertTrue(checkoutThreePage.isCompleteHeaderDisplayed(), "The subtitle is missing.");
        soft.assertTrue(checkoutThreePage.isCompleteTextDisplayed(), "The text is missing.");
        soft.assertTrue(checkoutThreePage.isBackHomeButtonDisplayed(), "The 'Back Home' button is missing.");
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице завершения заказа")
    @Story("Переход на страницу завершения заказа")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка корректности отображения страницы подтверждения заказа")
    void checkGotoProductsPage() {
        SoftAssert soft = new SoftAssert();
        checkoutThreePage.clickOnBackHomeButton();
        soft.assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Products"));
        soft.assertAll();
    }
}
