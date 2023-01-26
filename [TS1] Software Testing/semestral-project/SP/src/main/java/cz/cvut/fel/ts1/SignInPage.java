package cz.cvut.fel.ts1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"login-form\"]/section/div[1]/div[1]/input")
    WebElement emailInput;

    @FindBy(xpath = "//*[@id=\"login-form\"]/section/div[2]/div[1]/div/input")
    WebElement passwordInput;

    @FindBy(id = "submit-login")
    WebElement logInBtn;

    public SignInPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setEmailInput(String email) {
        emailInput.sendKeys(email);
    }

    public void setPasswordInput(String password) {
        passwordInput.sendKeys(password);
    }

    public ProfilePage signIn(String email, String password) {
        emailInput.clear();
        passwordInput.clear();
        setEmailInput(email);
        setPasswordInput(password);
        logInBtn.click();
        return new ProfilePage(driver);
    }

}
