package io.github.vikindor.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import io.github.vikindor.pages.PracticeFormPage;
import io.github.vikindor.pages.components.ResultModal;

import static io.github.vikindor.utils.RandomUtils.*;
import static io.github.vikindor.pages.components.ResultModal.*;
import static io.github.vikindor.pages.PracticeFormPage.Field.*;
import static io.qameta.allure.Allure.step;

@Tag("practice_form_test")
public class PracticeFormTests extends TestBase {

    PracticeFormPage practiceFormPage = new PracticeFormPage();
    ResultModal resultModal = new ResultModal();

    private final String
            firstName = getRandomFirstName(),
            lastName = getRandomLastName(),
            fullName = firstName + " " + lastName,
            email = getRandomUserEmail(firstName, lastName),
            gender = getRandomGender(),
            mobile = getRandomUserNumber(),
            day = getRandomDay(),
            month = getRandomMonth(),
            year = getRandomYear(),
            birthday = day + " " + month + "," + year,
            subject = getRandomSubject(),
            hobby = getRandomHobby(),
            picture = getRandomPicture(),
            address = getRandomAddress(),
            state = getRandomState(),
            city = getRandomCity(state);

    @Test
    void shouldSubmitFormWithAllFieldsTest() {

        step("Open Practice Form and prepare page", () -> {
            practiceFormPage.openPage().removeBanner();
        });

        step("Fill form with full data", () -> {
            practiceFormPage
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setUserEmail(email)
                    .setGender(gender)
                    .setUserNumber(mobile)
                    .setBirthDate(day, month, year)
                    .setSubject(subject)
                    .setHobby(hobby)
                    .uploadPicture(picture)
                    .setAddress(address)
                    .setState(state)
                    .setCity(city);
        });

        step("Submit form", () -> practiceFormPage.submit());

        step("Verify submission modal", () -> {
            resultModal.shouldAppear();
            resultModal
                    .shouldHaveTitle(tableTitle)
                    .shouldHaveValue(tableRowName, fullName)
                    .shouldHaveValue(tableRowEmail, email)
                    .shouldHaveValue(tableRowGender, gender)
                    .shouldHaveValue(tableRowMobile, mobile)
                    .shouldHaveValue(tableRowDateOfBirth, birthday)
                    .shouldHaveValue(tableRowSubjects, subject)
                    .shouldHaveValue(tableRowHobby, hobby)
                    .shouldHaveValue(tableRowPicture, picture)
                    .shouldHaveValue(tableRowAddress, address)
                    .shouldHaveValue(tableRowStateAndCity, state);
        });
    }

    @Test
    void shouldSubmitFormWithRequiredFieldsTest() {

        step("Open Practice Form and prepare page", () -> {
            practiceFormPage.openPage().removeBanner();
        });

        step("Fill form with required data", () -> {
            practiceFormPage
                    .openPage()
                    .removeBanner()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setGender(gender)
                    .setUserNumber(mobile);
        });

        step("Submit form", () -> practiceFormPage.submit());

        step("Verify submission modal", () -> {
            resultModal.shouldAppear();
            resultModal
                    .shouldHaveTitle(tableTitle)
                    .shouldHaveValue(tableRowName, fullName)
                    .shouldHaveValue(tableRowGender,gender)
                    .shouldHaveValue(tableRowMobile,mobile);
        });
    }

    @Test
    void shouldSubmitEmptyFormTest() {

        step("Open Practice Form and prepare page", () -> {
            practiceFormPage.openPage().removeBanner();
        });

        step("Submit form", () -> practiceFormPage.submit());

        step("Check validation", () -> {
            practiceFormPage
                    .shouldBeValidated()
                    .shouldHaveRedBorder(FIRST_NAME)
                    .shouldHaveRedBorder(LAST_NAME)
                    .shouldHaveRedBorder(GENDER)
                    .shouldHaveRedBorder(USER_NUMBER);
        });
    }
}
