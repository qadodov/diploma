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

    public DashboardPage() {
        heading.should(Condition.visible);

    }

    public class PaymentGate {

        public PaymentGate() {
            caption.findBy(Condition.exactText("Оплата по карте")).should(Condition.visible);
            inputCardNumber.should(Condition.visible);
            inputMonth.should(Condition.visible);
            inputYear.should(Condition.visible);
            inputOwner.should(Condition.visible);
            inputCVV.should(Condition.visible);
            continueButton.should(Condition.visible);
        }
    }

    public class CreditGate {

        public CreditGate() {
            caption.findBy(Condition.exactText("Кредит по данным карты")).should(Condition.visible);
            inputCardNumber.should(Condition.visible);
            inputMonth.should(Condition.visible);
            inputYear.should(Condition.visible);
            inputOwner.should(Condition.visible);
            inputCVV.should(Condition.visible);
            continueButton.should(Condition.visible);
        }
    }

    public PaymentGate openPaymentGate() {
        paymentButton.click();
        return new PaymentGate();
    }

    public CreditGate openCreditGate() {
        creditButton.click();
        return new CreditGate();
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


}
