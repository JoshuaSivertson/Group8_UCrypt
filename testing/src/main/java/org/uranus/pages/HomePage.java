package org.uranus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends PageBase {
    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

   // This defines a locators for a Elements in a webpage.
    By signUpBtn = By.cssSelector("#collapsibleNavId div a:first-child");
    By loginBtn = By.cssSelector(".btn-outline-secondary");
    By nameField = By.id("signUpName");
    By emailField = By.id("loginEmail");
    By passField = By.id("loginPassword");
    By confirmPassField = By.id("signUpConfirmPassword");
    By roleField = By.id("signUpRole");
    By signUpSubmitBtn = By.cssSelector("app-sign-up-page form .btn-submit");
   public By toastMsg=By.cssSelector(".p-toast-detail");
   public By closeToastMsg=By.cssSelector(".p-toast-icon-close");
   By emailLoginField=By.cssSelector("app-header #signUp app-login-page .auth-form #loginEmail");
   By passwordLoginField=By.cssSelector("app-header #signUp app-login-page .auth-form #loginPassword");
   By loginSubmitBtn=By.cssSelector("app-header #signUp app-login-page .auth-form button");
   By adminPanelModule=By.cssSelector("div #collapsibleNavId ul li:nth-child(8) a");
   public By accountPanelDropdown=By.cssSelector("div #collapsibleNavId div ul li a");
   public By logoutButton=By.cssSelector("div #collapsibleNavId div ul li div a:nth-child(2)");
   public By invalidFeedback=By.cssSelector(".invalid-feedback");



    //Method to sign up a user with the provided information.
    public void signUp(String name , String email, String password, String confPassword, String role) {
    click(signUpBtn);
    type(nameField,name);
    type(emailField,email);
    type(passField,password);
    type(confirmPassField,confPassword);
    select(roleField,role);
    click(signUpSubmitBtn);
    }


    //Method to log in a user with the provided information.
    public void login(String email, String password){
        click(loginBtn);
        type(emailLoginField,email);
        type(passwordLoginField,password);
        click(loginSubmitBtn);
    }

    public void openAdminPanel(){
        click(adminPanelModule);
    }
}
