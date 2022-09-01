package data;

import com.github.javafaker.Faker;
import lombok.Value;

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
        String s = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        s = s.substring(3, 5);
        return s;
    }

    public static String getInvalidMonth() {
        return "7";
    }

    public static String getNonExistentMonth() {
        return "13";
    }

    public static String getCurrentYear() {
        int i = LocalDate.now().getYear();
        String year = String.valueOf(i).substring(2, 4);
        return year;
    }

    public static String getPastYear() {
        int i = LocalDate.now().minusYears(1).getYear();
        String year = String.valueOf(i).substring(2, 4);
        return year;
    }

    public static String getRandomValidYear() {
        return String.valueOf(faker.number().numberBetween(22, 27));
    }

    public static String getRandomInvalidYear() {
        return String.valueOf(faker.number().numberBetween(28, 99));
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
