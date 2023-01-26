package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class NewAddressPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[1]/div[1]/input")
    WebElement alias;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[2]/div[1]/input")
    WebElement name;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[3]/div[1]/input")
    WebElement surname;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[4]/div[1]/input")
    WebElement company;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[5]/div[1]/input")
    WebElement dic;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[6]/div[1]/input")
    WebElement address;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[7]/div[1]/input")
    WebElement address2;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[8]/div[1]/input")
    WebElement psc;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[9]/div[1]/input")
    WebElement city;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[10]/div[1]/select")
    WebElement countrySelect;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[11]/div[1]/input")
    WebElement phone;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/footer/button")
    WebElement submitBtn;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/form/section/div[8]/div[1]/div/ul/li")
    WebElement errorMessage;

    public NewAddressPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setAlias(String al) {
        alias.sendKeys(al);
    }

    public void setName(String n) {
        name.sendKeys(n);
    }

    public void setSurname(String s) {
        surname.sendKeys(s);
    }

    public void setCompany(String c) {
        company.sendKeys(c);
    }

    public void setDic(String d) {
        dic.sendKeys(d);
    }

    public void setAddress(String a) {
        address.sendKeys(a);
    }

    public void setAddress2(String a2) {
        address2.sendKeys(a2);
    }

    public void setPsc(String p) {
        psc.sendKeys(p);
    }

    public void setCity (String ci) {
        city.sendKeys(ci);
    }

    public void setCountrySelect(String co) {
        Select country = new Select(countrySelect);
        country.selectByVisibleText(co);
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public void setPhone(String ph) {
        phone.sendKeys(ph);
    }

    public NewAddressPage fillFormNewAddress(String al, String n, String s, String c, String d,
                                          String a, String a2, String p, String ci, String co, String ph) {
        setAlias(al);
        setName(n);
        setSurname(s);
        setCompany(c);
        setDic(d);
        setAddress(a);
        setAddress2(a2);
        setPsc(p);
        setCity(ci);
        setCountrySelect(co);
        setPhone(ph);
        submitBtn.click();
        return this;
    }
}
