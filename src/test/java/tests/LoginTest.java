package tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LoginTest extends BaseTest {
    @Test
    public void checkLogin() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
        driver.findElement(By.xpath("//*[@placeholder='Password']")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String title = driver.findElement(By.cssSelector("[data-test=title]")).getText();
        assertEquals(title, "Products", "The Products web page is not open");
    }

    @Test
    public void checkIncorrectLogin() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.cssSelector("#user-name")).sendKeys("locked_out_user");
        driver.findElement(By.xpath("//*[@placeholder='Password']")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String title = driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
        assertEquals(title, "Epic sadface: Sorry, this user has been locked out.", "The error message fails to appear");
    }
}
