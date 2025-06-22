package tests;

import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

public class PasswordValidationTest extends TestBase {

    HomePage homePage;
    AdminPanelPage adminPanelPage;

    String name = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String passwordValid = "Passw0rd!";
    String passwordMissingUpper = "passw0rd!";
    String passwordMissingLower = "PASSW0RD!";
    String passwordMissingNumber = "Password!";
    String passwordMissingSpecial = "passw0rd";
    String role = "EMPLOYEE";


    @Test(priority = 0)
    public void checkThatValidPasswordWorkingSuccessfully()  {
        homePage = new HomePage(webDriver);
        homePage.signUp(name, email, passwordValid, passwordValid, role);
        assertIsEqual(homePage.toastMsg, "Registerd successfully, please wait for admin approval to login!"); // assertion command about the showing success message of sign up
        softAssert.assertAll();
        homePage.click(homePage.closeToastMsg);
    }

    @Test(priority = 1)
    public void checkPasswordMissingUpperFails() {
        homePage = new HomePage(webDriver);
        homePage.signUp(name, email, passwordMissingUpper, passwordMissingUpper, role);
        assertIsEqual(homePage.invalidFeedback, "Password must contain at least one upper case, lower case, number, and special character!"); // assertion command about the showing success message of sign up
        softAssert.assertAll();
        homePage.click(homePage.signUpCloseBtn);
    }

    @Test(priority = 2)
    public void checkPasswordMissingLowerFails() {
        homePage = new HomePage(webDriver);
        homePage.signUp(name, email, passwordMissingLower, passwordMissingLower, role);
        assertIsEqual(homePage.invalidFeedback, "Password must contain at least one upper case, lower case, number, and special character!"); // assertion command about the showing success message of sign up
        softAssert.assertAll();
        homePage.click(homePage.signUpCloseBtn);
    }

    @Test(priority = 3)
    public void checkPasswordMissingNumberFails() {
        homePage = new HomePage(webDriver);
        homePage.signUp(name, email, passwordMissingNumber, passwordMissingNumber, role);
        assertIsEqual(homePage.invalidFeedback, "Password must contain at least one upper case, lower case, number, and special character!"); // assertion command about the showing success message of sign up
        softAssert.assertAll();
        homePage.click(homePage.signUpCloseBtn);
    }

    @Test(priority = 4)
    public void checkPasswordMissingSpecialFails() {
        homePage = new HomePage(webDriver);
        homePage.signUp(name, email, passwordMissingSpecial, passwordMissingSpecial, role);
        assertIsEqual(homePage.invalidFeedback, "Password must contain at least one upper case, lower case, number, and special character!"); // assertion command about the showing success message of sign up
        softAssert.assertAll();
        homePage.click(homePage.signUpCloseBtn);
    }

}
