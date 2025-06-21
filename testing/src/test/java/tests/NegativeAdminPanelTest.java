package tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;

public class NegativeAdminPanelTest extends TestBase {
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
            webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(homePage.closeToastMsg));
        } catch (Exception e) {
            // Ignore if already gone or not present
        }
        homePage.click(homePage.accountPanelDropdown);
        homePage.click(homePage.logoutButton);
    }

    @Test(priority = 5)
    public void checkThatCannotEditRoleOfEmployeeToEmployee() {
        adminPanelPage.editRole("Employee");
        assertIsEqual(homePage.toastMsg, "Same role");
        softAssert.assertAll();
        homePage.click(homePage.closeToastMsg);
    }

    @Test(priority = 6)
    public void checkThatCannotEditRoleOfRegUserToUser() {
        adminPanelPage.editRegRole("User");
        assertIsEqual(homePage.toastMsg, "Same role");
        softAssert.assertAll();
        homePage.click(homePage.closeToastMsg);
    }
}
