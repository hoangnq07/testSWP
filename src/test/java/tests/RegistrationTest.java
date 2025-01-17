package tests;

import org.openqa.selenium.By;
import pages.RegistrationPage;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.TimeoutException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest {
    private WebDriver driver;
    private RegistrationPage registrationPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("http://localhost:9999/Project/registration.jsp");
        registrationPage = new RegistrationPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @MethodSource("readCsvData")
    public void testRegistration(String email, String password, String phone, String expectedResult) {
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.enterPhone(phone);
        registrationPage.clickSubmitButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        try {
            if (expectedResult.equals("Registration successful")) {
                wait.until(ExpectedConditions.urlToBe("http://localhost:9999/Project/register"));
                assertTrue(driver.getCurrentUrl().equals("http://localhost:9999/Project/register"),
                        "Expected URL: http://localhost:9999/Project/register, Actual URL: " + driver.getCurrentUrl());
            } else {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("status")));
                String statusMessage = registrationPage.getStatusMessage();
                assertTrue(statusMessage.contains(expectedResult),
                        "Expected message to contain: " + expectedResult + ", but got: " + statusMessage);
            }
        } catch (TimeoutException e) {
            if (expectedResult.equals("Registration successful")) {
                assertTrue(false, "Expected URL: http://localhost:9999/Project/register, but URL did not change.");
            } else {
                assertTrue(false, "Expected message to contain: " + expectedResult + ", but element not found.");
            }
        }
    }

    private static Stream<Arguments> readCsvData() throws IOException, CsvException {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("registration.csv")))) {
            List<String[]> records = csvReader.readAll();
            return records.stream().skip(1).map(data -> Arguments.of(data[0], data[1], data[2], data[3]));
        }
    }
}
