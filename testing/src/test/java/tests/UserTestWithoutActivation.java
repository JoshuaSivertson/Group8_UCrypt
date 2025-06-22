package tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.urangus.configuration.LoadProperties;
import org.uranus.pages.HomePage;

// Negative Test Case: Verify that a non-activated user cannot log in.
public class UserTestWithoutActivation extends TestBase{
    HomePage homePage;

    String name = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String password = "12345abc";
    String role = "USER";

    @Test(priority = 0)
    public void registerUser(){
        homePage = new HomePage(webDriver);
        homePage.signUp(name, email, password, password, role);
        assertIsEqual(homePage.toastMsg, "Registrd successfully, please wait for admin approval to login!");
        homePage.click(homePage.closeToastMsg);
    }

    @Test(priority = 1)
    public void signUpCheck(){
        homePage = new HomePage(webDriver);
        homePage.login(LoadProperties.env.getProperty(email), LoadProperties.env.getProperty(password));
        assertIsEqual(homePage.toastMsg, "Invalid Credentials!");
        homePage.click(homePage.closeToastMsg);
    }

    //  Negative Test Case: Verify that a user cannot access features without admin activation.
    @Test(priority = 2)
    public void tryDecryptionCheck(){
        homePage = new HomePage(webDriver);
        homePage.click(decryptButton);
        homePage.login(LoadProperties.env.getProperty(email), LoadProperties.env.getProperty(password));
        assertIsEqual(homePage.toastMsg, "Invalid Credentials!");
        homePage.click(homePage.closeToastMsg);
    }

    @Test(priority = 3)
    public void tryEncryptionCheck(){
        homePage = new HomePage(webDriver);
        homePage.click(encryptButton);
        homePage.login(LoadProperties.env.getProperty(email), LoadProperties.env.getProperty(password));
        assertIsEqual(homePage.toastMsg, "Invalid Credentials!");
        homePage.click(homePage.closeToastMsg);
    }


}