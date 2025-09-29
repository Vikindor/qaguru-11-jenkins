package io.github.vikindor.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.appear;

public abstract class Modal {

    protected final SelenideElement modal = $(".modal-dialog");

    public Modal shouldAppear() {
        modal.should(appear);
        return this;
    }
}
