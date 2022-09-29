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

public class PaymentGateTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1280x720";
        Configuration.browser = "chrome";
        open("http://localhost:8080");
    }

    @Test
    @DisplayName("Покупка тура по карте со статусом APPROVED \n" +
            "В базе данных создается запись в таблице payment_entity и order_entity")
    void shouldSuccessfullyBuyWithApprovedCardAndCheckDatabase() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getApprovedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(2),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showSuccessNotification();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        assertNotNull(paymentEntry);
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNotNull(orderEntry);
        assertEquals("APPROVED", paymentEntry.getStatus());
    }

    @Test
    @DisplayName("Покупка тура по карте со статусом DECLINED \n" +
            "В базе данных создается запись в таблице payment_entity, запись в таблице order_entity " +
            "не создается")
    void shouldNotLetBuyWithDeclinedCard() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getDeclinedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(2),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showErrorNotification();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        assertNotNull(paymentEntry);
        assertEquals("DECLINED", paymentEntry.getStatus());
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка тура по несуществующей карте.\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyWithNonExistentCard() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getNonExistentCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(2),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showErrorNotification();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка при невалидном заполнении поля для ввода номера карты.\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyWithInvalidCardNumber() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getInvalidCardNumber();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(2),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showCardNumberInputError();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка при невалидном заполнении поля для ввода владельца карты\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyWithInvalidHolderField() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getApprovedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(2),
                DataHelper.getInvalidHolder(),
                DataHelper.getCVV());
        dashboard.showHolderInputError();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка при невалидном заполнении поля для ввода месяца\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyWithInvalidMonthField() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getApprovedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getInvalidMonth(),
                DataHelper.getYear(2),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showMonthInputError();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка при заполнении поля для ввода месяца несуществующим месяцем\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetCreditWithNonExistentMonth() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getApprovedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getNonExistentMonth(),
                DataHelper.getYear(2),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showIncorrectlySpecifiedErrorForMonth();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка при невалидном заполнении поля для ввода года\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyWithInvalidYearField() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getApprovedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(20),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showIncorrectlySpecifiedErrorForYear();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка тура по карте с истекшим сроком действия\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyWithExpiredCard() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getApprovedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(-1),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showExpiredCardError();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка тура при невалидном заполнении поля для ввода CVV/CVC\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyWithInvalidCVVField() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getApprovedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYear(2),
                DataHelper.getHolder(),
                DataHelper.getInvalidCVV());
        dashboard.showCVVInputError();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка тура при незаполненном поле для ввода имени\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyIfHolderFieldIsBlank() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getApprovedCard();
        dashboard.enterCardNumber(card.getCardNumber());
        dashboard.enterCardMonth(DataHelper.getValidMonth());
        dashboard.enterCardYear(DataHelper.getYear(2));
        dashboard.enterCardCVV(DataHelper.getCVV());
        dashboard.pressContinue();
        dashboard.showEmptyHolderError();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка тура при незаполненном поле для ввода месяца\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyIfMonthFieldIsBlank() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getApprovedCard();
        dashboard.enterCardNumber(card.getCardNumber());
        dashboard.enterCardYear(DataHelper.getYear(2));
        dashboard.enterCardCVV(DataHelper.getCVV());
        dashboard.enterCardHolder(DataHelper.getHolder());
        dashboard.pressContinue();
        dashboard.showEmptyMonthError();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка тура при незаполненном поле для ввода номера карты\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyIfCardNumberFieldIsBlank() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        dashboard.enterCardYear(DataHelper.getYear(2));
        dashboard.enterCardCVV(DataHelper.getCVV());
        dashboard.enterCardHolder(DataHelper.getHolder());
        dashboard.enterCardMonth(DataHelper.getValidMonth());
        dashboard.pressContinue();
        dashboard.showEmptyCardNumberError();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка тура при незаполненном поле для CVV\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyIfCVVFieldIsBlank() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        var card = DataHelper.getApprovedCard();
        dashboard.enterCardNumber(card.getCardNumber());
        dashboard.enterCardYear(DataHelper.getYear(2));
        dashboard.enterCardHolder(DataHelper.getHolder());
        dashboard.enterCardMonth(DataHelper.getValidMonth());
        dashboard.pressContinue();
        dashboard.showEmptyCVVError();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @Test
    @DisplayName("Покупка тура при незаполненных полях\n" +
            "записи в таблицы payment_entity и order_entity в БД не заносятся")
    void shouldNotLetBuyIfAllFieldsAreBlank() {
        var mainPage = new MainPage();
        var dashboard = mainPage.openPaymentGate();
        dashboard.pressContinue();
        dashboard.showEmptyCardNumberError();
        dashboard.showEmptyMonthError();
        dashboard.showEmptyYearError();
        dashboard.showEmptyHolderError();
        dashboard.showEmptyCVVError();
        var paymentEntry = SQLHelper.getEntryFromPaymentEntityTable();
        var orderEntry = SQLHelper.getEntryFromOrderEntityTable();
        assertNull(paymentEntry);
        assertNull(orderEntry);
    }

    @AfterEach
    void cleanDatabase() {
        SQLHelper.cleanDatabase();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

}
