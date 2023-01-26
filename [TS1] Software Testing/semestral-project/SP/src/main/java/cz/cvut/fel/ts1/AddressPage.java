package cz.cvut.fel.ts1;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddressPage {
    WebDriver driver;

    @FindBy(css = "#content > div.addresses-footer > a")
    WebElement newAddress;

    public AddressPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public NewAddressPage clickToNewAddress() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");
        newAddress.click();
        return new NewAddressPage(driver);
    }

}
