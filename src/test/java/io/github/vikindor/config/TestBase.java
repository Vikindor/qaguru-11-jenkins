package io.github.vikindor.config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    @BeforeAll
    static void setupConfig() {
        Configuration.baseUrl     = System.getProperty("baseUrl", "https://demoqa.com");
        Configuration.browser     = System.getProperty("browser", "chrome");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.headless    = Boolean.parseBoolean(System.getProperty("headless","true"));
        Configuration.pageLoadStrategy = "eager";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--no-sandbox");
        opts.addArguments("--disable-dev-shm-usage");
        opts.addArguments("--disable-gpu");
        opts.addArguments("--remote-allow-origins=*");

        Configuration.browserCapabilities = opts;
    }

    @AfterEach
    void terminateWebDriver() {
        closeWebDriver();
    }
}
