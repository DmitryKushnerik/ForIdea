package tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.UserFactory;

import static enums.PageDetails.CHECKOUT3;
import static enums.PageDetails.PRODUCTS;
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
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка доступности и корректности отображения страницы подтверждения заказа", priority = 1)
    void checkCheckoutThreePageIsOpened() {
        SoftAssert soft = new SoftAssert();
        assertEquals(checkoutThreePage.getCurrentUrl(), checkoutThreePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT3.getPageName()));
        soft.assertTrue(checkoutThreePage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(checkoutThreePage.getTitle(), CHECKOUT3.getPageName(), WRONG_TITLE);
        soft.assertTrue(checkoutThreePage.isCompleteImageDisplayed(),
                ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("The image"));
        soft.assertTrue(checkoutThreePage.isCompleteHeaderDisplayed(),
                ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("The subtitle"));
        soft.assertTrue(checkoutThreePage.isCompleteTextDisplayed(),
                ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("The text"));
        soft.assertTrue(checkoutThreePage.isBackHomeButtonDisplayed(),
                ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("The Back Home button"));
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице завершения заказа")
    @Story("Переход на страницу Продукты")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка перехода на страницу Продукты при нажатии на кнопку Back Home", priority = 2)
    void checkGotoProductsPage() {
        SoftAssert soft = new SoftAssert();
        assertEquals(checkoutThreePage.getCurrentUrl(), checkoutThreePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT3.getPageName()));
        checkoutThreePage.clickOnBackHomeButton();
        soft.assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(PRODUCTS.getPageName()));
        soft.assertAll();
    }
}
