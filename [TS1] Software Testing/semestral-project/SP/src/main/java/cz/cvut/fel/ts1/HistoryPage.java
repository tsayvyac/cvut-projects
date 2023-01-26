package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HistoryPage {
    WebDriver driver;

    @FindBy(css = "#content > table > tbody > tr > td.text-sm-center.order-actions > a:nth-child(1)")
    List<WebElement> items;

    public HistoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public DetailsPage goToDetails(int i) {
        items.get(i).click();
        return new DetailsPage(driver);
    }

}