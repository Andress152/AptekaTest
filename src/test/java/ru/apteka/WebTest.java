package ru.apteka;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Objects;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class WebTest {
    @BeforeAll
    public static void setDriver() throws MalformedURLException {
        String isRemote = System.getenv("IS_REMOTE");
        if (Objects.equals(isRemote, "true")) {
            Configuration.browser = "chrome";
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("enableVNC:", true);
            WebDriver driver = new RemoteWebDriver(URI.create("http:http://localhost:8080")
                    .toURL(), chromeOptions);
            setWebDriver(driver);
        } else {
            Configuration.browser = "chrome";
        }
    }
}
