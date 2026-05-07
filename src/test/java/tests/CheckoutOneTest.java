package tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.UserFactory;

import static enums.PageDetails.*;
import static testdata.Messages.*;

@Owner("Кушнерик Дмитрий")
@Epic("Страница Checkout: Your Information")
@TmsLink("ForIdea")
@Issue("issues")
public class CheckoutOneTest extends BaseTest {
    private final String firstName = "Ivan";
    private final String lastName = "Ivanov";
    private final String postalCode = "658248";

    @Step("Открыть страницу оформления заказа")
    @BeforeMethod
    void openCheckoutOnePage() {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
        productsPage.navigationPanel.switchToCart();
        cartPage.clickOnCheckoutButton();
    }

    @Feature("Работоспособность страницы оформления заказа")
    @Story("Переход на страницу оформления заказа")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка доступности страницы оформления заказа", priority = 1)
    void checkCheckoutOnePageIsOpened() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(checkoutOnePage.getCurrentUrl(), checkoutOnePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT1.getPageName()));
        soft.assertTrue(checkoutOnePage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(checkoutOnePage.getTitle(), CHECKOUT1.getPageName(),
                WRONG_TITLE);
        soft.assertTrue(checkoutOnePage.isFirstNameFieldDisplayed(),
                ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("First name field"));
        soft.assertTrue(checkoutOnePage.isLastNameFieldDisplayed(),
                ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("Last name field"));
        soft.assertTrue(checkoutOnePage.isPostalCodeFieldDisplayed(),
                ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("Zip/Postal code field"));
        soft.assertTrue(checkoutOnePage.isCancelButtonDisplayed(),
                ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("Cancel button"));
        soft.assertTrue(checkoutOnePage.isContinueButtonDisplayed(),
                ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("Continue button"));
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице оформления заказа")
    @Story("Возвращение на страницу корзины")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка возвращения на страницу корзины при нажатии на кнопку Cancel", priority = 2)
    void checkGotoCartPage() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(checkoutOnePage.getCurrentUrl(), checkoutOnePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT1.getPageName()));
        checkoutOnePage.clickOnCancelButton();
        soft.assertEquals(cartPage.getCurrentUrl(), cartPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CART.getPageName()));
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице оформления заказа")
    @Story("Переход на страницу подтверждения заказа")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка перехода на страницу подтверждения заказа с корректным заполнением обязательных полей", priority = 3)
    public void checkCorrectData() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(checkoutOnePage.getCurrentUrl(), checkoutOnePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT1.getPageName()));
        checkoutOnePage.fillCheckoutOneFields(firstName, lastName, postalCode);
        checkoutOnePage.clickOnContinueButton();
        soft.assertEquals(checkoutTwoPage.getCurrentUrl(), checkoutTwoPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT2.getPageName()));
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице оформления заказа")
    @Story("Переход на страницу подтверждения заказа")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка перехода на страницу подтверждения заказа без заполнения обязательных полей",
            dataProvider = "incorrectData", priority = 4)
    public void checkIncorrectData(String firstName, String lastName, String postalCode, String errorMsg) {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(checkoutOnePage.getCurrentUrl(), checkoutOnePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(CHECKOUT1.getPageName()));
        checkoutOnePage.fillCheckoutOneFields(firstName, lastName, postalCode);
        checkoutOnePage.clickOnContinueButton();
        soft.assertTrue(checkoutOnePage.isErrorMessageDisplayed(), NO_ERROR_MESSAGE);
        soft.assertEquals(checkoutOnePage.getErrorMessageText(), errorMsg, WRONG_ERROR_MESSAGE);
        soft.assertAll();
    }

    @DataProvider(name = "incorrectData")
    public Object[][] incorrectData() {
        return new Object[][]{
                {"", "Ivanov", "658248", "Error: First Name is required"},
                {"Ivan", "", "658248", "Error: Last Name is required"},
                {"Ivan", "Ivanov", "", "Error: Postal Code is required"}
        };
    }
}
