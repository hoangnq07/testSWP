import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestLogin {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    public void setUp() throws Exception {
        // Add logging library dependency here (e.g., for Logback)
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testSuccessfulLogin() throws Exception {
        String email = "thaibaovu0212@gmail.com";
        String password = "@Bao02122003";

        driver.get("http://localhost:8080/demo_war/login.jsp");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // Clear existing content and enter email and password
        emailField.clear();
        emailField.sendKeys(email);
        passwordField.clear();
        passwordField.sendKeys(password);

        // Consider using a more robust locator strategy if applicable
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"signin\"]")); // Replace with a more specific locator if available
        loginButton.click();

    }

    // Add similar test methods for incorrect email and password scenarios
    @Test
    public void testIncorrectEmailLogin() throws Exception {
        String wrongEmail = "wrongemailaddress@gmail.com";
        String password = "@Bao02122003";

        driver.get("http://localhost:8080/demo_war/login.jsp");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));

        emailField.clear();
        emailField.sendKeys(wrongEmail);
        passwordField.clear();
        passwordField.sendKeys(password);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"signin\"]"));
        loginButton.click();


    }


    @Test
    public void testIncorrectPasswordLogin() throws Exception {
        String email = "thaibaovu0212@gmail.com";
        String wrongPassword = "wrongpassword123";

        driver.get("http://localhost:8080/demo_war/login.jsp");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));

        emailField.clear();
        emailField.sendKeys(email);
        passwordField.clear();
        passwordField.sendKeys(wrongPassword);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"signin\"]"));
        loginButton.click();

    }

    @Test
    public void testFailingLogin() throws Exception {
        String email = "thaibaovu0212@gmail.com";
        String password = "@Bao02122003";

        driver.get("http://localhost:8080/demo_war/login.jsp");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // Clear existing content and enter email and password
        emailField.clear();
        emailField.sendKeys(email);
        passwordField.clear();
        passwordField.sendKeys(password);

        // Consider using a more robust locator strategy if applicable
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"signin\"]")); // Replace with a more specific locator if available
        loginButton.click();

        //  kiểm tra URL sai
        assertEquals("http://localhost:8080/demo_war/login.jsp", driver.getCurrentUrl());
    }

}
