package org.uranus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPanelPage extends PageBase {
    public AdminPanelPage(WebDriver webDriver) {
        super(webDriver);
    }

    public By adminPanelTitle = By.cssSelector(".text-uppercase");
    public By email = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(2) span");
    public By name = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(3) span");
    public By roleNewAccount = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(4) p-celleditor");
    By approve = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(6) .approve");
    By reject = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(6) .reject");
    By staffAccountsTab = By.cssSelector("app-accounts ul #staff-tab");
    By btnEditRoleStaff = By.cssSelector("#staff tbody tr:nth-child(1) td:nth-child(6) button");
    By listStafRolesField = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(4) p-celleditor select");
    By saveEditIcon = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(6) button:nth-child(1)");
    By regAccountsTab = By.cssSelector(("app-accounts ul #reg-tab"));
    By btnEditRoleReg = By.cssSelector("#reg tbody tr:nth-child(1) td:nth-child(6) button");
    By listRegRolesField = By.cssSelector("app-accounts .active .p-datatable-wrapper tbody tr:nth-child(1) td:nth-child(4) p-celleditor select");

    public void approveSignUpRequest() {
        click(approve);
    }

    public void editRole(String newRole) {
        click(staffAccountsTab);
        click(btnEditRoleStaff);
        click(roleNewAccount);
        select(listStafRolesField, newRole);
        click(saveEditIcon);
    }

    public void editRegRole(String newRole) {
        click(regAccountsTab);
        click(btnEditRoleReg);
        click(roleNewAccount);
        select(listRegRolesField, newRole);
        click(saveEditIcon);
    }

}
