package tests;

import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.uranus.configuration.LoadProperties;
import org.uranus.pages.HomePage;
import org.openqa.selenium.By;


/**
 * This test class assumes the existence of encryption/decryption UI elements and flows.
 * Update the locators and expected messages to match your actual application.
 */
public class EncryptionTest extends TestBase {

    HomePage homePage;

    By profileDropdown = By.id("dropdownId");
    By myProfileOption = By.xpath("//a[contains(text(), 'My Profile')]");
    By homeLogoLink = By.xpath("//a[@routerlink='/' and contains(@class, 'navbar-brand')]");

    String plainText = "HelloWorld123!";
    String expectedEncryptedText = "oFmU7YFfMDcPyeqAykTVKw=="; // Replace with actual expected value if deterministic
    String expectedDecryptedText = plainText;
    String encryptionKey = "4OGquDVDNQULH3fbcpN+LWpRM8Lk+eyChBUdebWUtx4=";

    // Setup and Teardown methods for before and after each test case

    @BeforeMethod
    public void setUpAndLogin() {
        if (homePage == null) { homePage = new HomePage(webDriver); }

        // Check if already logged in by looking for the profile dropdown and "My Profile" option
        boolean alreadyLoggedIn = false;
        try {
            if (!webDriver.findElements(profileDropdown).isEmpty()) {
                webDriver.findElement(profileDropdown).click();
                if (!webDriver.findElements(myProfileOption).isEmpty()) {
                    alreadyLoggedIn = true;
                }
                // Optionally close the dropdown
                webDriver.findElement(profileDropdown).click();
            }
        } catch (Exception e) {/*Ignore and proceed to login*/}

        if (!alreadyLoggedIn) {
            homePage.login(LoadProperties.env.getProperty("ADMIN_EMAIL"), LoadProperties.env.getProperty("ADMIN_PASSWORD"));
            homePage.click(homePage.closeToastMsg);
        } else {
            // Click the logo/home link to return to the home page
            webDriver.findElement(homeLogoLink).click();
        }
    }

    @AfterMethod
    public void afterCompletedTest() {
        // Wait until the toast message disappears (is not visible in the DOM)
        try {
            webDriverWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(homePage.closeToastMsg));
        } catch (Exception e) {
            // Ignore if already gone or not present
        }
    }

    // Encryption test cases

    By encryptButton = By.xpath("//a[contains(@class, 'btn') and text()='Try Encryption']");
    By textToEncryptTextarea = By.xpath("//textarea[@formcontrolname='textToEncrypt']");
    By encryptionTypeDropdown = By.xpath("//select[@formcontrolname='encryptionTechnique']");
    By encryptionTypeFirstOption = By.xpath("//select[@formcontrolname='encryptionTechnique']/option[1]");
    By autoGenerateButton = By.xpath("//button[contains(@class, 'btn-secondary') and contains(text(), 'Auto Generate')]");
    By encryptSubmitButton = By.xpath("//button[contains(@class, 'btn-primary') and contains(., 'Encrypt')]");
    By encryptedTextOutputTextarea = By.xpath("//textarea[@formcontrolname='encryptedText']");
    By encryptionKeyTextarea = By.xpath("//textarea[@formcontrolname='encryptionKey']");

    @Test(priority = 0)
    public void testEncryptionFunctionality() {
        // Click the encrypt button to go to the encryption page
        homePage.click(encryptButton);

        // Type plaintext into the encryption textarea
        homePage.type(textToEncryptTextarea, plainText);

        // Click dropdown and select the first option
        webDriver.findElement(encryptionTypeDropdown).click();
        webDriver.findElement(encryptionTypeFirstOption).click();

        // Click the auto generate button
        homePage.click(autoGenerateButton);

        // Click the encrypt submit button
        homePage.click(encryptSubmitButton);

        // Wait for and get the encrypted output
        webDriverWait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver, java.time.Duration.ofSeconds(20));
        webDriverWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(encryptedTextOutputTextarea));
        String encryptedValue = webDriver.findElement(encryptedTextOutputTextarea).getAttribute("value");

        softAssert.assertTrue(!encryptedValue.isEmpty(), "Encrypted value should not be empty");
        softAssert.assertAll();
    }

    @Test(priority = 1)
    public void testEncryptionWithManualKey() {
        // Click the encrypt button to go to the encryption page
        homePage.click(encryptButton);

        // Type plaintext into the encryption textarea
        homePage.type(textToEncryptTextarea, plainText);

        // Click dropdown and select the first option
        webDriver.findElement(encryptionTypeDropdown).click();
        webDriver.findElement(encryptionTypeFirstOption).click();

        // Type the manual encryption key
        homePage.type(encryptionKeyTextarea, encryptionKey);

        // Click the encrypt submit button
        homePage.click(encryptSubmitButton);

        // Wait for and get the encrypted output
        webDriverWait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver, java.time.Duration.ofSeconds(20));
        webDriverWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(encryptedTextOutputTextarea));
        String encryptedValue = webDriver.findElement(encryptedTextOutputTextarea).getAttribute("value");

        softAssert.assertTrue(!encryptedValue.isEmpty(), "Encrypted value should not be empty");
        softAssert.assertEquals(expectedEncryptedText, encryptedValue);
        softAssert.assertAll();
    }

    // Decryption test cases

    By decryptButton = By.xpath("//a[contains(@class, 'btn') and text()='Try Decryption']");
    By textToDecryptTextarea = By.xpath("//textarea[@formcontrolname='textToDecrypt']");
    By decryptionTechniqueDropdown = By.xpath("//select[@formcontrolname='encryptionTechnique']");
    By decryptionTechniqueFirstOption = By.xpath("//select[@formcontrolname='encryptionTechnique']/option[1]");
    By decryptionKeyDropdown = By.xpath("//select[@formcontrolname='encryptionKey']");
    By decryptionKeyTextWindow = By.xpath("//textarea[@formcontrolname='encryptionKeySelected']");
    By decryptSubmitButton = By.xpath("//button[contains(@class, 'btn-primary') and contains(., 'Decrypt')]");
    By decryptedTextOutputTextarea = By.xpath("//textarea[@formcontrolname='decryptedText']");

    @Test(priority = 2)
    public void testDecryptionFunctionality() {
        String encryptedValue = "oFmU7YFfMDcPyeqAykTVKw=="; // Replace with actual encrypted value

        // Click the Decrpytion button to go to the decryption page
        homePage.click(decryptButton);

        // Input the text to be decrypted into the decryption text window
        homePage.type(textToDecryptTextarea, encryptedValue);

        // Click dropdown and select the first option
        webDriver.findElement(decryptionTechniqueDropdown).click();
        webDriver.findElement(decryptionTechniqueFirstOption).click();

        // Click dropdown and select the encryption key option
        /*webDriver.findElement(decryptionTechniqueDropdown).click();
        webDriver.findElement(decryptionTechniqueFirstOption).click();*/

        // Input the decryption key to the key window
        homePage.type(decryptionKeyTextWindow, encryptionKey);

        // Click the decrypt button
        homePage.click(decryptSubmitButton);

        webDriverWait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver, java.time.Duration.ofSeconds(20));
        webDriverWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(decryptedTextOutputTextarea));

        // Use getAttribute("value") for textarea to get its content
        String decryptedValue = webDriver.findElement(decryptedTextOutputTextarea).getAttribute("value");
        softAssert.assertEquals(decryptedValue, expectedDecryptedText, "Decrypted value should match original plain text");

        softAssert.assertAll();
    }

    // Variables to share between file encryption and decryption tests
    String lastFileEncryptionKey = null;
    String lastEncryptedFilePath = null;

    // Shared file input and encryption/decryption locators
    By fileEncryptInput = By.xpath("//input[@type='file']");
    By fileEncryptionTypeDropdown = By.xpath("//select[@formcontrolname='encryptionTechnique']");
    By fileEncryptionTypeFirstOption = By.xpath("//select[@formcontrolname='encryptionTechnique']/option[1]");
    By fileEncryptAutoGenerateButton = By.xpath("//button[contains(@class, 'btn-secondary') and contains(text(), 'Auto Generate')]");
    By fileEncryptSubmitButton = By.xpath("//button[contains(@class, 'btn-primary') and contains(., 'Encrypt')]");
    By fileEncryptionKeyTextarea = By.xpath("//textarea[@formcontrolname='encryptionKey']");
    By fileEncryptionFinishedStatus = By.xpath("//span[contains(@class, 'status') and text()='Finished']");

    @Test(priority = 3)
    public void testFileEncryptionFunctionality() {
        // Click the encrypt button to go to the file encryption page
        homePage.click(encryptButton);

        // Upload the file
        String filePath = "C:\\Users\\brady\\Documents\\textToEncrypt.txt"; // Change to a valid file path on your system
        String fileName = "textToEncrypt.txt"; // Extracted from filePath or set directly
        webDriver.findElement(fileEncryptInput).sendKeys(filePath);

        // Select encryption type (specific to file encryption page)
        webDriver.findElement(fileEncryptionTypeDropdown).click();
        webDriver.findElement(fileEncryptionTypeFirstOption).click();

        // Click the auto generate button for the key
        homePage.click(fileEncryptAutoGenerateButton);

        // Save the generated encryption key
        webDriverWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(fileEncryptionKeyTextarea));
        lastFileEncryptionKey = webDriver.findElement(fileEncryptionKeyTextarea).getAttribute("value");

        // Click the encrypt submit button
        homePage.click(fileEncryptSubmitButton);

        // Wait for the "Finished" status to appear
        webDriverWait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver, java.time.Duration.ofSeconds(30));
        webDriverWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(fileEncryptionFinishedStatus));

        // Assert the "Finished" status is present
        boolean finishedStatusPresent = !webDriver.findElements(fileEncryptionFinishedStatus).isEmpty();
        softAssert.assertTrue(finishedStatusPresent, "File encryption should show 'Finished' status");

        // Save the path to the encrypted file in Downloads
        String downloadsPath = System.getProperty("user.home") + "\\Downloads\\" + fileName;
        lastEncryptedFilePath = downloadsPath;
        java.io.File downloadedFile = new java.io.File(downloadsPath);

        // Wait up to 20 seconds for the file to appear
        int waitSeconds = 20;
        int waited = 0;
        while (!downloadedFile.exists() && waited < waitSeconds) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Ignore
            }
            waited++;
        }

        boolean fileExists = downloadedFile.exists();
        softAssert.assertTrue(fileExists, "Encrypted file should exist in Downloads folder: " + downloadsPath);

        softAssert.assertAll();
    }

    // File decryption locators (updated)
    By fileDecryptInput = By.xpath("//input[@type='file']");
    By fileDecryptTypeDropdown = By.xpath("//select[@formcontrolname='encryptionTechnique']");
    By fileDecryptTypeFirstOption = By.xpath("//select[@formcontrolname='encryptionTechnique']/option[1]");
    By fileDecryptKeyModeDropdown = By.xpath("//select[@formcontrolname='encryptionKey']");
    By fileDecryptKeyModeManualOption = By.xpath("//select[@formcontrolname='encryptionKey']/option[@value='0']");
    By fileDecryptKeyTextarea = By.xpath("//textarea[@formcontrolname='encryptionKeySelected']");
    By fileDecryptSubmitButton = By.xpath("//button[contains(@class, 'btn-primary') and contains(., 'Decrypt')]");
    By fileDecryptFinishedStatus = By.xpath("//span[contains(@class, 'status') and text()='Finished']");

    @Test(priority = 4, dependsOnMethods = "testFileEncryptionFunctionality")
    public void testFileDecryptionFunctionality() {
        // Ensure the encryption key and file path are available
        softAssert.assertNotNull(lastFileEncryptionKey, "Encryption key from previous test should not be null");
        softAssert.assertNotNull(lastEncryptedFilePath, "Encrypted file path from previous test should not be null");

        // Click the decrypt button to go to the file decryption page
        homePage.click(decryptButton);

        // Upload the encrypted file
        webDriver.findElement(fileDecryptInput).sendKeys(lastEncryptedFilePath);

        webDriver.findElement(fileDecryptTypeDropdown).click();
        webDriver.findElement(fileDecryptTypeFirstOption).click();

        webDriver.findElement(fileDecryptKeyModeDropdown).click();
        webDriver.findElement(fileDecryptKeyModeManualOption).click();

        webDriverWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(fileDecryptKeyTextarea));
        webDriver.findElement(fileDecryptKeyTextarea).clear();
        webDriver.findElement(fileDecryptKeyTextarea).sendKeys(lastFileEncryptionKey);

        homePage.click(fileDecryptSubmitButton);

        webDriverWait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver, java.time.Duration.ofSeconds(30));
        webDriverWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(fileDecryptFinishedStatus));
        boolean finishedStatusPresent = !webDriver.findElements(fileDecryptFinishedStatus).isEmpty();
        softAssert.assertTrue(finishedStatusPresent, "File decryption should show 'Finished' status");

        softAssert.assertAll();
    }


}

