package tests;

import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import user.User;
import user.UserFactory;

import static enums.PageDetails.CART;
import static enums.PageDetails.PRODUCTS;
import static org.testng.Assert.assertEquals;
import static testdata.Messages.*;

@Owner("Кушнерик Дмитрий")
@Epic("Страница входа в систему")
@TmsLink("ForIdea")
@Issue("issues")
public class LoginTest extends BaseTest {
    @Feature("Работоспособность страницы входа в систему")
    @Story("Переход на страницу входа в систему")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка доступности страницы входа в систему", priority = 1)
    void checkLoginPageIsOpened() {
        SoftAssert soft = new SoftAssert();
        loginPage.open();
        soft.assertEquals(loginPage.getCurrentUrl(), loginPage.getExpectedUrl(), WRONG_PAGE_PATTERN.formatted("Login Page"));
        soft.assertTrue(loginPage.isUserFieldDisplayed(), ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("Username field"));
        soft.assertTrue(loginPage.isPasswordFieldDisplayed(), ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("Password field"));
        soft.assertTrue(loginPage.isSubmitButtonDisplayed(), ELEMENT_IS_NOT_DISPLAYED_PATTERN.formatted("Login button"));
        soft.assertEquals(loginPage.getSubmitButtonBgColor(), loginPage.getSubmitButtonExpectedBgColor(),
                WRONG_BG_COLOR_PATTERN.formatted("'Login' button"));
        soft.assertAll();
    }

    @Feature("Вход в систему")
    @Story("Вход в систему с правами администратора")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Проверка входа в систему с правами администора", priority = 2)
    public void checkLogin() {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
        assertEquals(productsPage.getCurrentUrl(), productsPage.getExpectedUrl(),
                WRONG_PAGE_PATTERN.formatted(PRODUCTS.getPageName()));
    }

    @Feature("Вход в систему")
    @Story("Вход в систему с некорректными данными")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка входа в систему с некорректными данными",
            dataProvider = "incorrectData", priority = 3)
    public void checkIncorrectLogin(User user, String errorMessage) {
        SoftAssert soft = new SoftAssert();
        loginPage.open();
        loginPage.login(user);
        soft.assertTrue(loginPage.isErrorMsgDisplayed(), NO_ERROR_MESSAGE);
        soft.assertEquals(loginPage.getErrorMsgText(), errorMessage, WRONG_ERROR_MESSAGE);
        soft.assertAll();
    }

    @Feature("Вход в систему")
    @Story("Переход на страницу корзины без входа в систему")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Проверка перехода на страницу корзины без входа в систему", priority = 4)
    public void checkSwitchToCartWithoutLogin() {
        SoftAssert soft = new SoftAssert();
        loginPage.open(CART.getPageUrl());
        soft.assertTrue(loginPage.isErrorMsgDisplayed(), NO_ERROR_MESSAGE);
        soft.assertEquals(loginPage.getErrorMsgText(), "Epic sadface: You can only access '/cart.html' when you are logged in.",
                WRONG_ERROR_MESSAGE);
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
