package org.swaglabs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className="login_logo") WebElement loginLogo;
    @FindBy(id = "user-name") WebElement usernameField;
    @FindBy(id = "password") WebElement passwordField;
    @FindBy(id = "login-button") WebElement loginButton;
    @FindBy(xpath = "//*[@id='login_button_container']//div[@class='error-message-container error']//h3") WebElement errorMessage;


    public void setUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }
    public void setPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    public boolean isLoginLogoVisible(){
        return loginLogo.isDisplayed();
    }
    public boolean isUsernameFieldVisible(){
        return usernameField.isDisplayed();
    }
    public boolean isPasswordFieldVisible(){
        return passwordField.isDisplayed();
    }
    public boolean isLoginButtonVisible(){
        return loginButton.isDisplayed();
    }
    public String getLoginLogoText() {
        return loginLogo.getText();
    }
    public String getErrorMessage() {
        return errorMessage.getText();
    }
    public void clickLoginButton() {
       loginButton.click();
    }
    public void performLogin(String username, String password){
        setUsername(username);
        setPassword(password);
        clickLoginButton();
    }
}
