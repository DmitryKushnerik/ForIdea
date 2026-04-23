package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import user.User;
import user.UserFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {
    @Test
    public void checkLogin() {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
        assertEquals(productsPage.getTitle(), "Products", "The 'Products' web page is not open");
    }

    @Test(dataProvider = "incorrectData")
    public void checkIncorrectLogin(User user, String errorMessage) {
        loginPage.open();
        loginPage.login(user);
        assertTrue(loginPage.isErrorMsgDisplayed(), "The error message fails to appear");
        assertEquals(loginPage.getErrorMsgText(), errorMessage,
                "The error message text does not match what is expected.");
    }

    @Test
    public void checkSwitchToCartWithoutLogin() {
        loginPage.open("cart.html");
        assertTrue(loginPage.isErrorMsgDisplayed(), "The error message fails to appear");
        assertEquals(loginPage.getErrorMsgText(), "Epic sadface: You can only access '/cart.html' when you are logged in.",
                "The error message text does not match what is expected.");

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
