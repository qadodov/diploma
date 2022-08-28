package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;
import static data.SQLHelper.getEntryFromPaymentEntityTable;

public class TravelCardTest {

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
        var dashboard = new DashboardPage();
        var payment = dashboard.openPaymentGate();
        var card = DataHelper.getApprovedCard();
        dashboard.fillFieldsAndSendForm(card.getCardNumber(),
                DataHelper.getRandomValidMonth(),
                DataHelper.getRandomValidYear(),
                DataHelper.getHolder(),
                DataHelper.getCVV());
        dashboard.showSuccessNotification();
        var row = SQLHelper.getEntryFromPaymentEntityTable();
        assertNotNull(row);
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
