package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DetailsPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"order-products\"]/tbody/tr/td[1]/strong/a")
    WebElement itemName;

    @FindBy(xpath = "//*[@id=\"order-products\"]/tfoot/tr[2]/td[2]")
    WebElement total;

    @FindBy(xpath = "//*[@id=\"order-products\"]/tbody/tr/td[2]")
    WebElement itemQuantity;

    public DetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getItemName() {
        return itemName.getText();
    }

    public String getTotal() {
        return total.getText();
    }

    public String getItemQuantity() {
        return itemQuantity.getText();
    }

}
