package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ResultPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"_desktop_cart\"]/div/div/a")
    WebElement shoppingCart;

    @FindBy(xpath = "//*[@id=\"search_widget\"]/form/input[2]")
    WebElement search;

    @FindBy(xpath = "//*[@id=\"search_widget\"]/form/button")
    WebElement searchBtn;

    @FindBy(xpath = "//*[@id=\"js-product-list\"]/div/article/div/a")
    List<WebElement> itemList;

    public ResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setSearch(String itemName) {
        search.sendKeys(itemName);
    }

    public ItemPage clickToItem(int index) {
        itemList.get(index).click();
        return new ItemPage(driver);
    }

    public ResultPage searchThisItem(String itemName) {
        setSearch(itemName);
        searchBtn.click();
        return this;
    }
}
