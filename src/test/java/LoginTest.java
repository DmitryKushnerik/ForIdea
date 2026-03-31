import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LoginTest {
    // 1 - открыть браузер
    // 2 - зайти на сайт

    @Test
    public void checkLogin() {
        // WebDriver browser = new ChromeDriver();
        WebDriver browser = new FirefoxDriver();
        browser.get("https://www.saucedemo.com/");
        browser.findElement(By.cssSelector("#user-name")).sendKeys("standard_usergfdc");
        browser.findElement(By.cssSelector("[id=user-name]")).sendKeys(Keys.CONTROL + "A");
        browser.findElement(By.xpath("//*[@id='user-name']")).sendKeys(Keys.DELETE);
        browser.findElement(By.id("user-name")).sendKeys("standard_user");
        browser.findElement(By.xpath("//*[@placeholder='Password']")).sendKeys("secret_sauce");
        browser.findElement(By.id("login-button")).click();
        String title = browser.findElement(By.cssSelector("[data-test=title]")).getText();
        assertEquals(title, "Products", "The Products web page is not open");
        browser.quit();
    }
}