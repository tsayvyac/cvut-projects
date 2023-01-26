package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InformationPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"customer-form\"]/section/div[1]/div[1]/label[1]/span/input")
    WebElement male;

    @FindBy(xpath = "//*[@id=\"customer-form\"]/section/div[1]/div[1]/label[2]/span/input")
    WebElement female;

    @FindBy(xpath = "//*[@id=\"customer-form\"]/section/div[2]/div[1]/input")
    WebElement customerName;

    @FindBy(xpath = "//*[@id=\"customer-form\"]/section/div[3]/div[1]/input")
    WebElement customerSurname;

    @FindBy(xpath = "//*[@id=\"customer-form\"]/section/div[4]/div[1]/input")
    WebElement customerEmail;

    @FindBy(xpath = "//*[@id=\"customer-form\"]/section/div[5]/div[1]/div/input")
    WebElement password;

    @FindBy(xpath = "//*[@id=\"customer-form\"]/section/div[6]/div[1]/div/input")
    WebElement newPassword;

    @FindBy(xpath = "//*[@id=\"customer-form\"]/section/div[6]/div[1]/input")
    WebElement birthDate;

    @FindBy(xpath = "//*[@id=\"customer-form\"]/section/div[7]/div[1]/span/label/input")
    WebElement checkbox1;

    @FindBy(xpath = "//*[@id=\"customer-form\"]/section/div[8]/div[1]/span/label/input")
    WebElement checkbox2;

    @FindBy(xpath = "//*[@id=\"customer-form\"]/footer/button")
    WebElement submitBtn;

    public InformationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectMale() {
        male.click();
    }

    public void selectFemale() {
        female.click();
    }

    public void setCustomerName(String name) {
        customerName.sendKeys(name);
    }

    public void setCustomerSurname(String surname) {
        customerSurname.sendKeys(surname);
    }

    public void setCustomerEmail(String email) {
        customerEmail.sendKeys(email);
    }

    public void setPassword(String pass) {
        password.sendKeys(pass);
    }

    public void setNewPassword(String newPass) {
        newPassword.sendKeys(newPass);
    }

    public void setBirthDate(String date) {
        birthDate.sendKeys(date);
    }

    public void clickToCheckbox1() {
        checkbox1.click();
    }

    public void clickToCheckbox2() {
        checkbox2.click();
    }

    public InformationPage clickToSubmitBtn() {
        submitBtn.click();
        return this;
    }

}

