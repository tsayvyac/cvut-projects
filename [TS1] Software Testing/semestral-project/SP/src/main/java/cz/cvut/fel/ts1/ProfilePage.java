package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"addresses-link\"]/span")
    WebElement address;

    @FindBy(xpath = "//*[@id=\"history-link\"]/span")
    WebElement history;

    @FindBy(xpath = "//*[@id=\"contact-link\"]/a")
    WebElement contactUs;

    @FindBy(xpath = "//*[@id=\"_desktop_logo\"]/a")
    WebElement toHomePage;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public AddressPage clickToAddress() {
        address.click();
        return new AddressPage(driver);
    }

    public HistoryPage clickToHistory() {
        history.click();
        return new HistoryPage(driver);
    }

    public ContactUsPage clickToContactUs() {
        contactUs.click();
        return new ContactUsPage(driver);
    }

    public HomePage clickToHomeLink() {
        toHomePage.click();
        return new HomePage(driver);
    }

}
