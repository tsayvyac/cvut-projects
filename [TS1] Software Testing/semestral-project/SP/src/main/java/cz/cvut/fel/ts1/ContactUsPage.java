package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ContactUsPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"content\"]/section/form/section/div[2]/div/select")
    WebElement subjectSelect;

    @FindBy(xpath = "//*[@id=\"content\"]/section/form/section/div[3]/div/input")
    WebElement email;

    @FindBy(xpath = "//*[@id=\"content\"]/section/form/section/div[4]/div/select")
    WebElement numberOfOrderSelect;

    @FindBy(xpath = "//*[@id=\"content\"]/section/form/section/div[6]/div/textarea")
    WebElement message;

    @FindBy(xpath = "//*[@id=\"content\"]/section/form/footer/input[3]")
    WebElement submit;

    @FindBy(xpath = "//*[@id=\"content\"]/section/form/div/ul/li")
    WebElement errorMessage;

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setSubjectSelect(String s) {
        Select subject = new Select(subjectSelect);
        subject.selectByVisibleText(s);
    }

    public void setEmail(String e) {
        email.sendKeys(e);
    }

    public void setNumberOfOrderSelect(String n) {
        Select number = new Select(numberOfOrderSelect);
        number.selectByVisibleText(n);
    }

    public void setMessage(String m) {
        message.sendKeys(m);
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public ContactUsPage fillFormContactUs(String s, String e, String n, String m) {
        setSubjectSelect(s);
        email.clear();
        setEmail(e);
        setNumberOfOrderSelect(n);
        setMessage(m);
        submit.click();
        return this;
    }

}
