package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ShoppingCart {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"main\"]/div/div[1]/div/div[2]/ul/li/div/div[3]/div/div[2]/div/div[1]/div/input")
    WebElement quantity;

    @FindBy(xpath = "//*[@id=\"main\"]/div/div[1]/div/div[2]/ul/li/div/div[3]/div/div[2]/div/div[1]/div/span[3]/button[1]")
    WebElement increase;

    @FindBy(xpath = "//*[@id=\"main\"]/div/div[1]/div/div[2]/ul/li/div/div[3]/div/div[2]/div/div[1]/div/span[3]/button[2]")
    WebElement decrease;

    @FindBy(xpath = "//*[@id=\"main\"]/div/div[2]/div[1]/div[2]/div/a")
    WebElement orderBtn;

    @FindBy(xpath = "//*[@id=\"notifications\"]/div/article/ul/li")
    WebElement errorMessage;

    @FindBy(css = "#main > div > div.cart-grid-body.col-xs-12.col-lg-8 > div > div.cart-overview.js-cart > ul > li > div > div.product-line-grid-body.col-md-4.col-xs-8 > div > a")
    List<WebElement> itemNameInCart;

    public ShoppingCart(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public void clickToIncrease() {
        increase.click();
    }

    public ShoppingCart clickToDecrease() {
        decrease.click();
        return this;
    }

    public String getItemNameInCart(int index) {
        return itemNameInCart.get(index).getText();
    }

    public OrderPage clickToOrderBtn() {
        orderBtn.click();
        return new OrderPage(driver);
    }

}
