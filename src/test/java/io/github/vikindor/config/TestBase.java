package io.github.vikindor.config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    @BeforeAll
    static void setupConfig() {
        Configuration.baseUrl     = System.getProperty("baseUrl", "https://demoqa.com");
//        Configuration.browser     = System.getProperty("browser", "chrome");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.headless    = Boolean.parseBoolean(System.getProperty("headless","false"));
        Configuration.pageLoadStrategy = "eager";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void terminateWebDriver() {
        closeWebDriver();
    }
}
