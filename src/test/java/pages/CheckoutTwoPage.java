package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static enums.PageDetails.CHECKOUT2;

public class CheckoutTwoPage extends BasePage {
    private final By cancelButton = By.id("cancel");
    private final By finishButton = By.id("finish");
    private final By itemPrice = By.className("inventory_item_price");
    private final By subtotalLabel = By.className("summary_subtotal_label");
    private final By taxLabel = By.className("summary_tax_label");
    private final By totalLabel = By.className("summary_total_label");

    public CheckoutTwoPage(WebDriver driver) {
        super(driver);
        this.page_url = CHECKOUT2.getPageUrl();
    }

    @Step("Нажать на кнопку Cancel")
    public void clickOnCancelButton() {
        driver.findElement(cancelButton).click();
    }

    @Step("Нажать на кнопку Finish")
    public void clickOnFinishButton() {
        driver.findElement(finishButton).click();
    }

    @Step("Рассчитать общую стоимость товаров на странице")
    public double calculateTotalCost() {
        double sum = 0;
        List<WebElement> itemPrices = driver.findElements(itemPrice);
        for (WebElement price : itemPrices) {
            sum += Double.parseDouble(price.getText().replace("$", ""));
        }
        return sum;
    }

    @Step("Получить расчитанную стоимость товаров без налога")
    public String getSubtotalCost() {
        return driver.findElement(subtotalLabel).getText();
    }

    @Step("Получить расчитанный налог")
    public String getTax() {
        return driver.findElement(taxLabel).getText();
    }

    @Step("Получить расчитанную стоимость товаров без налога")
    public String getTotalCost() {
        return driver.findElement(totalLabel).getText();
    }

    @Step("Проверить, отображается ли кнопка Cancel")
    public boolean isCancelButtonDisplayed() {
        return driver.findElement(cancelButton).isDisplayed();
    }

    @Step("Проверить, отображается ли кнопка Finish")
    public boolean isFinishButtonDisplayed() {
        return driver.findElement(finishButton).isDisplayed();
    }
}
