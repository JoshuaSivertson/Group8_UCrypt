package tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.urangus.configuration.LoadProperties;
import org.uranus.pages.HomePage;
import org.uranus.pages.AdminPage;

// Positive Test Case: Verify that a user can create an account and the account is pending activation.
// Positive Test Case: Verify that an admin can activate a pending user account.
public class AdminActivationTest extends TestBase{
    HomePage homePage;
    AdminPage adminPage;

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
    public void checkStatus(){
        homePage = new HomePage(webDriver);
        adminPage = new AdminPanelPage(webDriver);
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.openAdminPanel();
        assertionIsEqual(adminPanelPage.adminOanelTitle, "ADMIN PANEL");
    }

    @Test(priority = 2)
    public void checkStatus(){
        adminPanelPage.editRole("User");
        assertIsEqual(homePage.toastMsg, "Role is Edited Successfully");
        softAssert.assertAll();
        homePage.click(homePage.closeToastMsg);
    }

    }