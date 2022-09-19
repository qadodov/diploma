package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {

    private static Faker faker = new Faker();

    private DataHelper() {

    }

    @Value
    public static class CardInfo {
        private final String cardNumber;
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo("4444 4444 4444 4441");
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo("4444 4444 4444 4442");
    }

    public static CardInfo getNonExistentCard() {
        return new CardInfo("1234 1234 1234 1234");
    }

    public static CardInfo getInvalidCardNumber() {
        return new CardInfo("7856 2323 454");
    }

    public static String getValidMonth() {
        String s = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return s;
    }

    public static String getInvalidMonth() {
        return "7";
    }

    public static String getNonExistentMonth() {
        return "13";
    }

    public static String getYear(int i) {
        String s = LocalDate.now().plusYears(i).format(DateTimeFormatter.ofPattern("yy"));
        return s;
    }

    public static String getHolder() {
        String name = faker.name().firstName();
        String surname = faker.name().lastName();
        String holder = name + " " + surname;
        return holder;
    }

    public static String getInvalidHolder() {
        String invalidHolder = "#$4Я ';(F";
        return invalidHolder;
    }

    public static String getCVV() {
        return String.valueOf(faker.number().randomNumber(3, true));
    }

    public static String getInvalidCVV() {
        return String.valueOf(faker.number().numberBetween(1, 99));
    }

}
