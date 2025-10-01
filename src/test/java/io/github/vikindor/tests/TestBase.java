package io.github.vikindor.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.vikindor.helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    @BeforeAll
    static void setupConfig() {
        // General
        Configuration.baseUrl          = System.getProperty("baseUrl", "https://demoqa.com");
        Configuration.browser          = System.getProperty("browser", "chrome");
        Configuration.browserSize      = System.getProperty("browserSize", "1920x1080");
        Configuration.pageLoadStrategy = "eager";

        Configuration.remote         = System.getProperty("remoteUrl",
                "https://user1:1234@selenoid.autotests.cloud/wd/hub");
        Configuration.browserVersion = System.getProperty("browserVersion", "128.0");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void listener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments(TestInfo testInfo) {
        String rawName = testInfo.getDisplayName(); // @DisplayName or method name
        String safeName = rawName
                .replaceAll("[^a-zA-Z0-9._-]", "_") //  Replace all non-allowed characters with "_"
                .replaceAll("_+", "_");             //  Collapse multiple "_" into a single one

        Attach.screenshotAs("Screenshot_" + safeName);
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

        closeWebDriver();
    }
}
