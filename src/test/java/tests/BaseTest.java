package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pages.*;
import utils.TestListener;

import java.time.Duration;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {
    public static final String WRONG_PAGE_PATTERN = "The '%s' page is not open";
    public static final String NO_TITLE = "The page title is not displayed";
    public static final String WRONG_TITLE = "The page title does not match what was expected.";
    public static final String WRONG_BG_COLOR_PATTERN = "The background color of %s does not match what was expected.";
    public static final String NO_ERROR_MESSAGE = "The error message fails to appear";
    public static final String WRONG_ERROR_MESSAGE = "The error message text does not match what is expected.";
    public static final String ELEMENT_IS_NOT_DISPLAYED_PATTERN = "%s is not displayed.";
    public static final String ELEMENT_IS_NOT_EXIST_PATTERN = "%s does not exist.";
    public static final String ELEMENT_IS_PRESENT_PATTERN = "%s is present.";
    public static final String CART_IS_EMPTY = "Cart is empty.";
    public static final String CART_IS_NOT_EMPTY = "Cart is not empty";
    public static final String WRONG_CART_QUANTITY = "The number of items in the cart does not match the expected quantity.";
    public static final String NO_PRODUCT_IN_CART = "The product list does not contain the expected name.";
    public static final String WRONG_COUNTER_VALUE = "The cart counter value does not match the expected value.";
    public static final String WRONG_TEXT_ON_ELEMENT_PATTERN = "The text on %s does not match what is expected.";
    public static final String WRONG_PRODUCTS_QUANTITY = "The quantity of goods is not as expected.";
    public WebDriver driver;
    LoginPage loginPage;
    ProductsPage productsPage;
    CartPage cartPage;
    CheckoutOnePage checkoutOnePage;
    CheckoutTwoPage checkoutTwoPage;
    CheckoutThreePage checkoutThreePage;

    @Step("Открыть браузер")
    @Parameters({"browser"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browser, ITestContext context) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.addArguments("headless");
            options.addArguments("guest");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("start-maximized");
            options.addArguments("headless");
            options.addArguments("guest");
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(options);
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        context.setAttribute("driver", driver);
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutOnePage = new CheckoutOnePage(driver);
        checkoutTwoPage = new CheckoutTwoPage(driver);
        checkoutThreePage = new CheckoutThreePage(driver);
    }

    @Step("Закрыть браузер")
    @AfterMethod
    public void close() {
        driver.quit();
    }
}
