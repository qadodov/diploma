package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class CreditGateTest {

    @Test
    @DisplayName("Покупка тура в кредит по карте со статусом APPROVED \n" +
            "В базе данных создается запись в таблице credit_request_entity и order_entity")
    void shouldSuccessfullyCreditWithApprovedCard() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openCreditGate();
        var card = DataHelper.getApprovedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(2),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showSuccessNotification();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNotNull(orderEntry);
        var creditRequestEntry = SQLHelper.getEntryFromCreditRequestEntityTable();
        assertNotNull(creditRequestEntry);
        assertEquals("APPROVED", creditRequestEntry.getStatus());
    }

    @Test
    @DisplayName("Покупка тура в кредит по карте со статусом DECLINED \n" +
            "В базе данных создается запись в таблице credit_request_entity, запись в таблице order_entity " +
            "не создается")
    void shouldNotLetCreditWithDeclinedCard() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openCreditGate();
        var card = DataHelper.getDeclinedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(2),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showErrorNotification();
        var creditRequestEntry = SQLHelper.getEntryFromPaymentEntityTable();
        assertNotNull(creditRequestEntry);
        assertEquals("DECLINED", creditRequestEntry.getStatus());
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка тура кредит по несуществующей карте.\n" +
            "записи в таблицы credit_request_entity и order_entity в БД не заносятся")
    void shouldNotLetCreditWithNonExistentCard() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openCreditGate();
        var card = DataHelper.getNonExistentCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(2),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showErrorNotification();
        var creditRequestEntry = SQLHelper.getEntryFromCreditRequestEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(creditRequestEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка в кредит при невалидном заполнении поля для ввода номера карты.\n" +
            "записи в таблицы credit_request_entity и order_entity в БД не заносятся")
    void shouldNotLetCreditWithInvalidCardNumber() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openCreditGate();
        var card = DataHelper.getInvalidCardNumber();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(2),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showCardNumberInputError();
        var creditRequestEntry = SQLHelper.getEntryFromCreditRequestEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(creditRequestEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка в кредит при невалидном заполнении поля для ввода владельца карты\n" +
            "записи в таблицы credit_request_entity и order_entity в БД не заносятся")
    void shouldNotLetCreditWithInvalidHolderField() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openCreditGate();
        var card = DataHelper.getApprovedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(2),
                DataHelper.getInvalidHolder(),
                DataHelper.getCVV());
        dashboard.showHolderInputError();
        var creditRequestEntry = SQLHelper.getEntryFromCreditRequestEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(creditRequestEntry);
        assertNull(orderEntry);
    }
}
