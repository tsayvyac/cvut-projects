package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SuccessOrderPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"order-items\"]/div[2]/table/tbody/tr[2]/td[2]")
    WebElement total;

    @FindBy(xpath = "//*[@id=\"order-items\"]/div[2]/div/div[2]/span")
    WebElement itemName;

    @FindBy(xpath = "//*[@id=\"order-items\"]/div[2]/div/div[3]/div/div[2]")
    WebElement itemQuantity;

    public SuccessOrderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTotal() {
        return total.getText();
    }

    public String getItemName() {
        return itemName.getText();
    }

    public String getItemQuantity() {
        return itemQuantity.getText();
    }

}
