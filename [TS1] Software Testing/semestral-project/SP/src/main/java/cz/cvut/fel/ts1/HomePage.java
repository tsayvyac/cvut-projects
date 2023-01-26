package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {
    WebDriver driver;

    @FindBy(css = "span.hidden-sm-down")
    WebElement signIn;

    @FindBy(xpath = "//*[@id=\"search_widget\"]/form/input[2]")
    WebElement search;

    @FindBy(xpath = "//*[@id=\"search_widget\"]/form/button")
    WebElement searchBtn;

    @FindBy(xpath = "//*[@id=\"_desktop_cart\"]/div/div/a")
    WebElement shoppingCart;

    @FindBy(xpath = "//*[@id=\"cookie_accept\"]")
    WebElement cookie;

    @FindBy(xpath = "//*[@id=\"content\"]/section/div/article/div/a")
    List<WebElement> items;

    @FindBy(css = "#category-9 > a")
    WebElement toArts;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SignInPage clickToSignIn() {
        cookie.click();
        signIn.click();
        return new SignInPage(driver);
    }

    public ShoppingCart clickToShoppingCart() {
        shoppingCart.click();
        return new ShoppingCart(driver);
    }

    public ItemPage clickToItem(int index) {
        items.get(index).click();
        return new ItemPage(driver);
    }

    public ArtsPage clickToToArts() {
        toArts.click();
        return new ArtsPage(driver);
    }

    public void setSearch(String itemName) {
        search.sendKeys(itemName);
    }

    public ResultPage searchThisItem(String itemName) {
        setSearch(itemName);
        searchBtn.click();
        return new ResultPage(driver);
    }

}
