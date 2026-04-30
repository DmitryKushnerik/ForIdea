package tests;

import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.User;
import user.UserFactory;

import static org.testng.Assert.assertEquals;

@Owner("Кушнерик Дмитрий")
@Epic("Страница входа в систему")
@TmsLink("ForIdea")
@Issue("issues")
public class LoginTest extends BaseTest {
    @Feature("Вход в систему")
    @Story("Вход в систему с правами администратора")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка входа в систему с правами администора")
    public void checkLogin() {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
        assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted("Products"));
    }

    @Feature("Вход в систему")
    @Story("Вход в систему с некорректными данными")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка входа в систему с некорректными данными",
            dataProvider = "incorrectData")
    public void checkIncorrectLogin(User user, String errorMessage) {
        SoftAssert soft = new SoftAssert();
        loginPage.open();
        loginPage.login(user);
        soft.assertTrue(loginPage.isErrorMsgDisplayed(), "The error message fails to appear");
        soft.assertEquals(loginPage.getErrorMsgText(), errorMessage,
                "The error message text does not match what is expected.");
        soft.assertAll();
    }

    @Feature("Вход в систему")
    @Story("Переход на страницу корзины без входа в систему")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка перехода на страницу корзины без входа в систему")
    public void checkSwitchToCartWithoutLogin() {
        SoftAssert soft = new SoftAssert();
        loginPage.open("cart.html");
        soft.assertTrue(loginPage.isErrorMsgDisplayed(), "The error message fails to appear");
        soft.assertEquals(loginPage.getErrorMsgText(), "Epic sadface: You can only access '/cart.html' when you are logged in.",
                "The error message text does not match what is expected.");
        soft.assertAll();
    }

    @DataProvider(name = "incorrectData")
    public Object[][] loginData() {
        return new Object[][]{
                {UserFactory.withLockedPermission(), "Epic sadface: Sorry, this user has been locked out."},
                {UserFactory.withEmptyLoginPermission(), "Epic sadface: Username is required"},
                {UserFactory.withEmptyPasswordPermission(), "Epic sadface: Password is required"},
                {UserFactory.withIncorrectLoginPermission(), "Epic sadface: Username and password do not match any user in this service"}
        };
    }
}
