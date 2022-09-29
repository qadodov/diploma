package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {

    private SelenideElement heading = $x("//h2");

    private SelenideElement paymentButton = $x("//span[text()=\"Купить\"]/ancestor::button");

    private SelenideElement creditButton = $x("//span[text()=\"Купить в кредит\"]/ancestor::button");

    private ElementsCollection caption = $$x("//h3");

    public MainPage() {
        heading.should(Condition.visible);
    }

    public DashboardPage openPaymentGate() {
        paymentButton.click();
        return new DashboardPage();
    }

    public DashboardPage openCreditGate() {
        creditButton.click();
        return new DashboardPage();
    }
}
