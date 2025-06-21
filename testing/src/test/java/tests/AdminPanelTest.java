package tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

import java.time.Duration;

public class AdminPanelTest extends TestBase {
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    @BeforeMethod
    public void loginAndOpenAdminPanel() {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.openAdminPanel();
        assertIsEqual(adminPanelPage.adminPanelTitle, "ADMIN PANEL");
    }

    @AfterMethod
    public void afterCompletedTest() {
        // Wait until the toast message disappears (is not visible in the DOM)
        try {
            webDriverWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(homePage.closeToastMsg));
        } catch (Exception e) {
            // Ignore if already gone or not present
        }
        homePage.click(homePage.accountPanelDropdown);
        homePage.click(homePage.logoutButton);
    }

    @Test(priority = 3)
    public void checkThatEditRoleOfEmployeeIsWorkingSuccessfully() {
        adminPanelPage.editRole("User");
        assertIsEqual(homePage.toastMsg, "Role is Edited Successfully");
        softAssert.assertAll();
        homePage.click(homePage.closeToastMsg);
    }

    @Test(priority = 4)
    public void checkThatEditRoleOfUserIsWorkingSuccessfully() {
        adminPanelPage.editRegRole("Employee");
        assertIsEqual(homePage.toastMsg, "Role is Edited Successfully");
        softAssert.assertAll();
        homePage.click(homePage.closeToastMsg);
    }
}
