package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {
    @Test
    public void checkLogin() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        assertEquals(productsPage.getTitle(), "Products", "The 'Products' web page is not open");
    }

    @Test(dataProvider = "incorrectData")
    public void checkIncorrectLogin(String user, String password, String errorMessage) {
        loginPage.open();
        loginPage.login(user, password);
        assertTrue(loginPage.isErrorMsgDisplayed(), "The error message fails to appear");
        assertEquals(loginPage.getErrorMsgText(), errorMessage, "The error message text does not match what is expected.");
    }

    @DataProvider(name = "incorrectData")
    public Object[][] loginData() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"},
                {"Standard_user", "secret_sauce", "Epic sadface: Username and password do not match any user in this service"}
        };
    }
}
