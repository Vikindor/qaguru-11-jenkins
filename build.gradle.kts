plugins {
    id("java")
    id("io.qameta.allure") version "3.0.0" // allure-framework/allure-gradle
}

group = "io.github.vikindor"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.codeborne:selenide:7.10.1")
    testImplementation(platform("org.junit:junit-bom:6.0.0-M2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("net.datafaker:datafaker:2.5.1")
    testImplementation("io.qameta.allure:allure-selenide:2.30.0")
    allureRawResultElements(files(layout.buildDirectory.dir("allure-results")))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") // JUnit launcher
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.17") // SLF4J SimpleLogger
}

allure {
    report {
        version.set("2.35.1") // allure-framework/allure2
    }
    adapter { // Responsible for creating the build/allure-results folder
        autoconfigure.set(true)
        autoconfigureListeners.set(true)
        frameworks {
            junit5 { // Framework name
                adapterVersion.set("2.30.0") // Same as allure-framework/allure-java
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
    // Redirect SLF4J SimpleLogger output from stderr (default, shown in red in IntelliJ)
    // to stdout so that log messages appear in the normal "gray" console color.
    jvmArgs("-Dorg.slf4j.simpleLogger.logFile=System.out")
    // Disable Selenium Manager telemetry (statistics sent to plausible.io)
    environment("SE_AVOID_STATS", "true")
}

tasks.register<Test>("practiceFormTest") { // Experimenting with running Tags
    useJUnitPlatform {
        includeTags("practice_form_test")
    }
}
