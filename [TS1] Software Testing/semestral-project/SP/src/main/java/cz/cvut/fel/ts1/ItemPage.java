package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ItemPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"quantity_wanted\"]")
    WebElement quantity;

    @FindBy(xpath = "//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[2]/button")
    WebElement addToCartBtn;

    @FindBy(xpath = "//*[@id=\"main\"]/div[1]/div[3]/div/div/ul/li[2]/a")
    WebElement itemDetails;

    @FindBy(xpath = "//*[@id=\"product-details\"]/div[2]/span")
    WebElement itemCode;

    @FindBy(xpath = "//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/button")
    WebElement continueInShopping;

    @FindBy(xpath = "//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/a")
    WebElement orderBtn;

    @FindBy(xpath = "//*[@id=\"product-availability\"]")
    WebElement outOfStockMessage;

    @FindBy(xpath = "//*[@id=\"main\"]/div[1]/div[2]/h1")
    WebElement itemName;

    public ItemPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getOutOfStockMessage() {
        return outOfStockMessage.getText();
    }

    public ItemPage setQuantity(String count) {
        quantity.clear();
        quantity.sendKeys(count);
        return this;
    }

    public String getItemName() {
        return itemName.getText();
    }

    public ShoppingCart setOrder(String count) {
        quantity.clear();
        quantity.sendKeys(count);
        addToCartBtn.click();
        orderBtn.click();
        return new ShoppingCart(driver);
    }

    public void continueShopping(String count) {
        quantity.clear();
        quantity.sendKeys(count);
        addToCartBtn.click();
        continueInShopping.click();
    }

    public void clickToItemDetails() {
        itemDetails.click();
    }

    public String getItemCode() {
        return itemCode.getText();
    }
}
