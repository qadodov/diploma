package data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import lombok.Value;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Random;

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

    public static CardInfo getRandomCard() {
        return new CardInfo(faker.finance().creditCard(CreditCardType.MASTERCARD));
    }

    public static String getRandomValidMonth() {
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int month = faker.random().nextInt(0, 11);
        return months[month];
    }

    public static String getCurrentYear() {
        int i = LocalDate.now().getYear();
        String year = String.valueOf(i).substring(2, 3);
        return year;
    }

    public static String getRandomValidYear() {
        return String.valueOf(faker.number().numberBetween(22, 27));
    }

    public static String getHolder() {
        String name = faker.name().firstName();
        String surname = faker.name().lastName();
        String holder = name + " " + surname;
        return holder;
    }

    public static String getCVV() {
        return String.valueOf(faker.number().randomNumber(3, true));
//        var firstDigit = Math.floor(Math.random() * 10);
//        var secondDigit = Math.floor(Math.random() * 10);
//        var thirdDigit = Math.floor(Math.random() * 10);
//
//        int cvv = (int) (firstDigit + secondDigit + thirdDigit);
//        return String.valueOf(cvv);
    }

}
