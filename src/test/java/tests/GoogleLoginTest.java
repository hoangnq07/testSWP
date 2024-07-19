package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoogleLoginTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("user-data-dir=C:/Users/thede/AppData/Local/Google/Chrome/User Data");
        options.addArguments("profile-directory=Default");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void testGoogleLogin() {
        driver.get("http://localhost:9999/Project/login.jsp");

        WebElement googleLoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn-google")));
        googleLoginButton.click();

        WebElement accountChooser = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-identifier]")));

        accountChooser.click();

        List<WebElement> nextButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("button[class*='VfPpkd-LgbsSe'][class*='VfPpkd-LgbsSe-OWXEXe-INsAgc']")));

        if (nextButtons.size() >= 2) {
            WebElement secondNextButton = nextButtons.get(1);
            js.executeScript("arguments[0].click();", secondNextButton);
        } else {
            System.out.println("Less than two buttons found with the specified CSS selector.");
        }

        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-success-message")));
        assertTrue(successElement.isDisplayed(), "Google login was not successful");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}