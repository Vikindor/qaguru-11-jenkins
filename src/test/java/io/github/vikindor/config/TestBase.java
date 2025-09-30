package io.github.vikindor.config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.vikindor.helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    @BeforeAll
    static void setupConfig() {
        // General
        Configuration.baseUrl          = System.getProperty("baseUrl", "https://demoqa.com");
        Configuration.browser          = System.getProperty("browser", "chrome");
        Configuration.browserSize      = System.getProperty("browserSize", "1920x1080");
        Configuration.timeout          = Long.parseLong(System.getProperty("timeout", "10000"));
        Configuration.pageLoadStrategy = System.getProperty("pageLoadStrategy", "eager");

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        // false - run locally; true - run by Configuration.remote
        boolean remote = Boolean.parseBoolean(System.getProperty("remote", "true"));

        if (remote) {
            // --- Remote Selenoid ---
            Configuration.remote         = System.getProperty("remoteUrl",
                    "https://user1:1234@selenoid.autotests.cloud/wd/hub");
            Configuration.browserVersion = System.getProperty("browserVersion", "128.0");

            ChromeOptions options = new ChromeOptions();

            // selenoid:options
            Map<String, Object> selenoid = new HashMap<>();
            selenoid.put("name", System.getProperty("sessionName", "PracticeForm"));
            selenoid.put("sessionTimeout", System.getProperty("sessionTimeout", "15m"));
            selenoid.put("enableVNC", Boolean.parseBoolean(System.getProperty("enableVNC", "true")));
            selenoid.put("enableVideo", Boolean.parseBoolean(System.getProperty("enableVideo", "true")));
            selenoid.put("env", List.of(System.getProperty("tz", "TZ=UTC"))); // часовой пояс
            // "trash" button
            Map<String, Object> labels = new HashMap<>();
            labels.put("manual", "true");
            selenoid.put("labels", labels);

            options.setCapability("selenoid:options", selenoid);

            Configuration.browserCapabilities = options;

        } else {
            // --- Locally ---
            Configuration.headless = Boolean.parseBoolean(System.getProperty("headless", "true"));

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu");

            Configuration.browserCapabilities = options;
        }
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
