package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ArtsPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"js-product-list\"]/div/article/div/a")
    List<WebElement> artsItems;

    public ArtsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ItemPage clickToArtItem(int index) {
        artsItems.get(index).click();
        return new ItemPage(driver);
    }
}
