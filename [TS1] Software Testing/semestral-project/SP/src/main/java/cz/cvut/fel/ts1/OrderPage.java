package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderPage {
    WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"checkout-addresses-step\"]/div/div/form/div[2]/button")
    WebElement continueBtn;

    @FindBy(xpath = "//*[@id=\"payment-option-2\"]")
    WebElement transferPayment;

    @FindBy(xpath = "//*[@id=\"conditions_to_approve[terms-and-conditions]\"]")
    WebElement agreementCheckbox;

    @FindBy(xpath = "//*[@id=\"payment-confirmation\"]/div[1]/button")
    WebElement submitBtn;

    public SuccessOrderPage selectPayment() {
        transferPayment.click();
        agreementCheckbox.click();
        submitBtn.click();
        return new SuccessOrderPage(driver);
    }

    public OrderPage clickToContinue() {
        continueBtn.click();
        return this;
    }


}
