package tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.UserFactory;

import static org.testng.Assert.assertEquals;

@Owner("Кушнерик Дмитрий")
@Epic("Страница Checkout: Your Information")
@TmsLink("ForIdea")
@Issue("issues")
public class CheckoutOneTest extends BaseTest {
    private String firstName = "Ivan";
    private String lastName = "Ivanov";
    private String postalCode = "658248";

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
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка доступности страницы оформления заказа")
    void checkCheckoutOnePageIsOpened() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(checkoutOnePage.getCurrentUrl(), checkoutOnePage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Checkout: Your Information"));
        soft.assertTrue(checkoutOnePage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(checkoutOnePage.getTitle(), "Checkout: Your Information",
                WRONG_TITLE);
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице оформления заказа")
    @Story("Возвращение на страницу корзины")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка возможности возвращения на страницу корзины при клике по кнопке Cancel")
    void checkGotoCartPage() {
        SoftAssert soft = new SoftAssert();
        checkoutOnePage.clickOnCancelButton();
        assertEquals(cartPage.getCurrentUrl(), cartPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Your Cart"));
        soft.assertTrue(cartPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(cartPage.getTitle(), "Your Cart", WRONG_TITLE);
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице оформления заказа")
    @Story("Переход на страницу подтверждения заказа")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка перехода на страницу подтверждения заказа с корректным заполнением обязательных полей")
    public void checkCorrectData() {
        SoftAssert soft = new SoftAssert();
        checkoutOnePage.fillCheckoutOneFields(firstName, lastName, postalCode);
        checkoutOnePage.clickOnContinueButton();
        assertEquals(checkoutTwoPage.getCurrentUrl(), checkoutTwoPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Checkout: Overview"));
        soft.assertTrue(checkoutTwoPage.isTitleDisplayed(), NO_TITLE);
        soft.assertEquals(checkoutTwoPage.getTitle(), "Checkout: Overview", WRONG_TITLE);
        soft.assertAll();
    }

    @Feature("Навигационные кнопки на странице оформления заказа")
    @Story("Переход на страницу подтверждения заказа")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка перехода на страницу подтверждения заказа без заполнения обязательных полей",
            dataProvider = "incorrectData")
    public void checkIncorrectData(String firstName, String lastName, String postalCode, String errorMsg) {
        SoftAssert soft = new SoftAssert();
        checkoutOnePage.fillCheckoutOneFields(firstName, lastName, postalCode);
        checkoutOnePage.clickOnContinueButton();
        soft.assertTrue(checkoutOnePage.isErrorMessageDisplayed(), "The error message fails to appear");
        soft.assertEquals(checkoutOnePage.getErrorMessageText(), errorMsg, "The error message text does not match what is expected.");
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
