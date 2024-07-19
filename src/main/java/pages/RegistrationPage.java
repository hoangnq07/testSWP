package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationPage {
    private WebDriver driver;

    // Locators
    private By emailField = By.id("email");
    private By passwordField = By.id("pass");
    private By phoneField = By.id("contact");
    private By submitButton = By.id("signup");
    private By statusMessage = By.id("status");

    // Constructor
    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    // Page actions
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void enterPhone(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickSubmitButton() {
        driver.findElement(submitButton).click();
    }

    public String getStatusMessage() {
        WebElement statusElement = driver.findElement(statusMessage);
        return statusElement != null ? statusElement.getText() : "";
    }
}
