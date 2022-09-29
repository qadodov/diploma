package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {

    private SelenideElement heading = $x("//h2");

    private SelenideElement paymentButton = $x("//span[text()=\"Купить\"]/ancestor::button");

    private SelenideElement creditButton = $x("//span[text()=\"Купить в кредит\"]/ancestor::button");

    private ElementsCollection caption = $$x("//h3");

    private SelenideElement inputCardNumber = $x("//input[@placeholder=\"0000 0000 0000 0000\"]");

    private SelenideElement inputMonth = $x("//input[@placeholder=\"08\"]");

    private SelenideElement inputYear = $x("//input[@placeholder=\"22\"]");

    private SelenideElement inputOwner = $x("//*[text()=\"Владелец\"]/parent::span//input");

    private SelenideElement inputCVV = $x("//*[@placeholder=\"999\"]");

    private SelenideElement continueButton = $x("//*[text()=\"Продолжить\"]/ancestor::button");

    private SelenideElement successNotification = $x("//*[contains(@class, \"notification_status_ok\")]/div[@class=\"notification__content\"]");

    private SelenideElement errorNotification = $x("//*[contains(@class, \"notification_status_error\")]/div[@class=\"notification__content\"]");

    private SelenideElement cardNumberInputError = $x("//span[text()=\"Номер карты\"]/parent::span/span[@class=\"input__sub\"]");

    private SelenideElement monthInputError = $x("//span[text()=\"Месяц\"]/parent::span/span[@class=\"input__sub\"]");

    private SelenideElement yearInputError = $x("//span[text()=\"Год\"]/parent::span/span[@class=\"input__sub\"]");

    private SelenideElement holderInputError = $x("//span[text()=\"Владелец\"]/parent::span/span[@class=\"input__sub\"]");

    private SelenideElement CVVInputError = $x("//span[text()=\"CVC/CVV\"]/parent::span/span[@class=\"input__sub\"]");
    public DashboardPage() {
        heading.should(Condition.visible);
        inputCardNumber.should(Condition.visible);
        inputMonth.should(Condition.visible);
        inputYear.should(Condition.visible);
        inputOwner.should(Condition.visible);
        inputCVV.should(Condition.visible);
        continueButton.should(Condition.visible);
    }

    public void enterCardNumber(String s) {
        inputCardNumber.setValue(s);
    }

    public void enterCardMonth(String s) {
        inputMonth.setValue(s);
    }

    public void enterCardYear(String s) {
        inputYear.setValue(s);
    }

    public void enterCardHolder(String s) {
        inputOwner.setValue(s);
    }

    public void enterCardCVV(String s) {
        inputCVV.setValue(s);
    }

    public void pressContinue() {
        continueButton.click();
    }

    public void fillFieldsAndSendForm(String cardNumber, String month, String year, String owner, String CVV) {
        inputCardNumber.setValue(cardNumber);
        inputMonth.setValue(month);
        inputYear.setValue(year);
        inputOwner.setValue(owner);
        inputCVV.setValue(CVV);
        continueButton.click();
    }

    public void showSuccessNotification() {
        successNotification.shouldHave(Condition.text("Операция одобрена Банком."), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    public void showErrorNotification() {
        errorNotification.shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    public void showCardNumberInputError() {
        cardNumberInputError.shouldHave(Condition.text("Неверный формат")).should(Condition.appear);
    }

    public void showHolderInputError() {
        holderInputError.shouldHave(Condition.text("Неверный формат")).should(Condition.appear);
    }

    public void showMonthInputError() {
        monthInputError.shouldHave(Condition.text("Неверный формат")).should(Condition.appear);
    }

    public void showIncorrectlySpecifiedErrorForMonth() {
        monthInputError.shouldHave(Condition.text("Неверно указан срок действия карты")).should(Condition.appear);
    }

    public void showIncorrectlySpecifiedErrorForYear() {
        yearInputError.shouldHave(Condition.text("Неверно указан срок действия карты")).should(Condition.appear);
    }

    public void showExpiredCardError() {
        yearInputError.shouldHave(Condition.text("Истёк срок действия карты")).should(Condition.appear);
    }

    public void showCVVInputError() {
        CVVInputError.shouldHave(Condition.text("Неверный формат")).should(Condition.appear);
    }

    public void showEmptyHolderError() {
        holderInputError.shouldHave(Condition.text("Поле обязательно для заполнения")).should(Condition.appear);
    }

    public void showEmptyMonthError() {
        monthInputError.shouldHave(Condition.text("Поле обязательно для заполнения")).should(Condition.appear);
    }

    public void showEmptyCardNumberError() {
        cardNumberInputError.shouldHave(Condition.text("Поле обязательно для заполнения")).should(Condition.appear);
    }

    public void showEmptyCVVError() {
        CVVInputError.shouldHave(Condition.text("Поле обязательно для заполнения")).should(Condition.appear);
    }

    public void showEmptyYearError() {
        yearInputError.shouldHave(Condition.text("Поле обязательно для заполнения")).should(Condition.appear);
    }
}
