package io.github.vikindor.pages;

import com.codeborne.selenide.SelenideElement;
import io.github.vikindor.pages.components.CalendarComponent;

import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.cssValue;

public class PracticeFormPage {

    private final SelenideElement
            firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            userEmailInput = $("#userEmail"),
            genderInput = $("#genterWrapper"),
            genderAnyLabel = $("#genterWrapper").$(".custom-control-label"),
            userNumberInput = $("#userNumber"),
            calendarInput = $("#dateOfBirthInput"),
            subjectInput = $("#subjectsInput"),
            hobbyInput = $("#hobbiesWrapper"),
            pictureInput = $("#uploadPicture"),
            addressInput = $("#currentAddress"),
            stateInput = $("#state"),
            cityInput = $("#city"),
            submitButton = $("#submit"),
            userForm = $("#userForm");

    public enum Field { FIRST_NAME, LAST_NAME, GENDER, USER_NUMBER }

    private final Map<Field, SelenideElement> controls = Map.of(
            Field.FIRST_NAME,  firstNameInput,
            Field.LAST_NAME,   lastNameInput,
            Field.GENDER,      genderAnyLabel,
            Field.USER_NUMBER, userNumberInput
    );

    CalendarComponent calendarComponent = new CalendarComponent();

    public PracticeFormPage openPage () {
        open("/automation-practice-form");

        return this;
    }

    public PracticeFormPage removeBanner() {
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");

        return this;
    }

    public PracticeFormPage setFirstName(String value) {
        firstNameInput.val(value);

        return this;
    }

    public PracticeFormPage setLastName(String value) {
        lastNameInput.val(value);

        return this;
    }

    public PracticeFormPage setUserEmail(String value) {
        userEmailInput.val(value);

        return this;
    }

    public PracticeFormPage setGender(String value) {
        genderInput.$(byText(value)).click();

        return this;
    }

    public PracticeFormPage setUserNumber(String value) {
        userNumberInput.val(value);

        return this;
    }

    public PracticeFormPage setBirthDate(String day, String month, String year) {
        calendarInput.click();
        calendarComponent.setDate(day, month, year);

        return this;
    }

    public PracticeFormPage setSubject(String value) {
        subjectInput.val(value).pressEnter();

        return this;
    }

    public PracticeFormPage setHobby(String value) {
        hobbyInput.$(byText(value)).click();

        return this;
    }

    public PracticeFormPage uploadPicture(String value) {
        pictureInput.uploadFromClasspath("img/" + value);

        return this;
    }

    public PracticeFormPage setAddress(String value) {
        addressInput.val(value);

        return this;
    }

    public PracticeFormPage setState(String value) {
        stateInput.click();
        stateInput.$(byText(value)).click();

        return this;
    }

    public PracticeFormPage setCity(String value) {
        cityInput.click();
        cityInput.$(byText(value)).click();

        return this;
    }

    public PracticeFormPage submit() {
        submitButton.click();

        return this;
    }

    public PracticeFormPage shouldBeValidated() {
        userForm.shouldHave(cssClass("was-validated"));

        return this;
    }

    public PracticeFormPage shouldHaveRedBorder(Field fieldName) {
        String redRgb = "rgb(220, 53, 69)";
        String redRgba = "rgba(220, 53, 69, 1)";

        switch (fieldName) {
            case FIRST_NAME  -> firstNameInput.shouldHave(cssValue("border-color", redRgb));
            case LAST_NAME   -> lastNameInput.shouldHave(cssValue("border-color", redRgb));
            case GENDER      -> genderAnyLabel.shouldHave(cssValue("color", redRgba));
            case USER_NUMBER -> userNumberInput.shouldHave(cssValue("border-color", redRgb));
            default          -> throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
        return this;
    }
}
