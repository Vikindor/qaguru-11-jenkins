package io.github.vikindor.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;

public class ResultModal extends Modal {

    public static final String // Name of result table elements
            tableTitle = "Thanks for submitting the form",
            tableRowName = "Student Name",
            tableRowEmail = "Student Email",
            tableRowGender = "Gender",
            tableRowMobile = "Mobile",
            tableRowDateOfBirth = "Date of Birth",
            tableRowSubjects = "Subjects",
            tableRowHobby = "Hobbies",
            tableRowPicture = "Picture",
            tableRowAddress = "Address",
            tableRowStateAndCity = "State and City";

    private final SelenideElement title = $("#example-modal-sizes-title-lg");
//    private final SelenideElement table = $(".modal-body .table-responsive table");
    private final SelenideElement formTable = $(".table-responsive");

    public ResultModal shouldHaveTitle(String expected) {
        title.shouldHave(exactText(expected));
        return this;
    }

    public ResultModal shouldHaveValue(String key, String value) {
        formTable.$(byText(key)).parent().shouldHave(text(value));
        return this;
    }

//    Previous version of checkTable method
//    public ResultModal shouldHaveExact(String label, String expected) {
//        SelenideElement row = table.$$("tbody tr").findBy(text(label));
//        row.$$("td").get(0).shouldHave(exactText(label));
//        row.$$("td").get(1).shouldHave(exactText(expected));
////        return this;
//    }
}
