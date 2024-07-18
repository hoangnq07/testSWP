// LoginTest.java
package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/login_data.csv", numLinesToSkip = 1)
    void testLogin(String username, String password, String expectedResult) {
        driver.get("http://localhost:9999/Project/login");  // Thay thế bằng URL thực tế
        loginPage.login(username, password);

        switch (expectedResult) {
            case "success":
                assertTrue(loginPage.getTitle().contains("HOME"), loginPage.getCurrentUrl());
                break;
            case "admin":
                assertTrue(loginPage.getTitle().contains("Admin"), "Admin should be redirected to admin page");
                break;
            case "banned":
                assertEquals("Tài khoản của bạn đã bị khóa. Vui lòng liên hệ với quản trị viên để biết thêm chi tiết.", loginPage.getErrorMessage());
                break;
            default:
                assertEquals(expectedResult, loginPage.getErrorMessage());
        }
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}