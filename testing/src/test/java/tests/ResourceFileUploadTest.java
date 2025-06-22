package tests;

import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.AdminPanelPage;
import org.uranus.pages.HomePage;
import org.openqa.selenium.By;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ResourceFileUploadTest extends TestBase {
    HomePage homePage;
    AdminPanelPage adminPanelPage;

    By resourcesTabButton = By.id("resources-tab");
    By fileInput = By.xpath("//input[@type='file']");
    By uploadButton = By.xpath("//button[.//span[contains(@class, 'p-button-label') and text()='Upload']]");
    By resourcesPage = By.xpath("//a[contains(@class, 'nav-link') and contains(text(), 'Resources')]");
    By uploadedFileName = By.xpath("//p[contains(@class, 'f-name') and text()='textToEncrypt.txt']");

    @BeforeClass
    public void initializePageVariables () {
        homePage = new HomePage(webDriver);
        adminPanelPage = new AdminPanelPage(webDriver);
    }

    @ AfterMethod
    public void logoutOfUserAccount () {
        By profileDropdown = By.xpath("//a[@id='dropdownId' and .//img[contains(@src, 'profile.svg')]]");
        By logoutOption = By.xpath("//a[contains(@class, 'dropdown-item') and .//img[contains(@src, 'logout.svg')]]");
        webDriver.findElement(profileDropdown).click();
        webDriver.findElement(logoutOption).click();
        homePage.click(homePage.closeToastMsg);
    }

    public void toastBannerWait() {
        // Wait until the toast message disappears (is not visible in the DOM)
        try {
            webDriverWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(homePage.closeToastMsg));
        } catch (Exception e) {
            // Ignore if already gone or not present
        }
    }

    @Test(priority = 0)
    public void adminUploadResourceFile() {
        homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
        homePage.click(homePage.closeToastMsg);
        homePage.openAdminPanel();

        // Go to Resources tab
        webDriver.findElement(resourcesTabButton).click();

        // Upload file
        String fileName = "testFileToUpload-4.txt";
        String filePath = "C:/Users/brady/Documents/" + fileName; // Change to a valid file path
        webDriver.findElement(fileInput).sendKeys(filePath);

        // Click upload
        webDriver.findElement(uploadButton).click();
        toastBannerWait();

        // Navigate to global resources tab
        webDriver.findElement(resourcesPage).click();

        // Wait for the uploaded file name to appear in the resources list
        By uploadedFileName = By.xpath("//p[contains(@class, 'f-name') and text()='" + fileName + "']");
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver, java.time.Duration.ofSeconds(10));
        boolean fileUploaded = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(uploadedFileName)) != null;
        assertTrue(fileUploaded, "File should be uploaded and visible in resources list");
    }

    @Test(priority = 1)
    public void userCanSeeUploadedResourceFile() {
        // Log in as a Employee user
        homePage.login(LoadProperties.env.getProperty("EMPLOYEE_EMAIL"), LoadProperties.env.getProperty("EMPLOYEE_PASSWORD"));
        homePage.click(homePage.closeToastMsg);

        // Go to Resources page
        webDriver.findElement(resourcesPage).click();

        // Check for the uploaded file
        String fileName = "fileToUpload.txt";
        By uploadedFileName = By.xpath("//p[contains(@class, 'f-name') and text()='" + fileName + "']");
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver, java.time.Duration.ofSeconds(10));
        boolean fileVisible = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(uploadedFileName)) != null;
        assertTrue(fileVisible, "Uploaded file should be visible to other users in resources list");
    }

    @Test(priority = 2)
    public void userCantSeeUploadedResourceFile() {
        // Log in as a General user
        homePage.login(LoadProperties.env.getProperty("USER_EMAIL"), LoadProperties.env.getProperty("USER_PASSWORD"));
        homePage.click(homePage.closeToastMsg);

        // Check that the Resources page tab is not visible
        boolean resourcesTabPresent = !webDriver.findElements(resourcesPage).isEmpty();
        assertFalse(resourcesTabPresent, "Resources page tab should NOT be visible to general users");
    }

}
