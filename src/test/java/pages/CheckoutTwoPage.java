package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutTwoPage extends BasePage {
    private By cancelButton = By.id("cancel");
    private By finishButton = By.id("finish");
    private By itemPrice = By.className("inventory_item_price");
    private By subtotalLabel = By.className("summary_subtotal_label");
    private By taxLabel = By.className("summary_tax_label");
    private By totalLabel = By.className("summary_total_label");

    public CheckoutTwoPage(WebDriver driver) {
        super(driver);
        this.page_url = "checkout-step-two.html";
    }

    @Step("Кликнуть по кнопке Cancel")
    public void clickOnCancelButton() {
        driver.findElement(cancelButton).click();
    }

    @Step("Кликнуть по кнопке Finish")
    public void clickOnFinishButton() {
        driver.findElement(finishButton).click();
    }

    @Step("Рассчитать общую стоимость товаров на странице")
    public double calculateTotalCost() {
        double sum = 0;
        List<WebElement> itemPrices = driver.findElements(itemPrice);
        for (WebElement price : itemPrices) {
            //String rawstring =
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
}
