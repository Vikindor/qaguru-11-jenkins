package io.github.vikindor.utils;

import net.datafaker.Faker;

import static io.github.vikindor.utils.RandomUtilsExample.getRandomItemFromArray;

public class RandomUtils {

    public static Faker faker = new Faker();

    public static String getRandomFirstName() {
        return faker.name().firstName();
    }

    public static String getRandomLastName() {
        return faker.name().lastName();
    }

    public static String getRandomUserEmail(String firstName, String lastName) { // Personalised email
        String domain = faker.internet().domainName();

        return (firstName + "." + lastName + "@" + domain).toLowerCase();
    }

    public static String getRandomUserEmail() { // Random email, if no names are given
        return faker.internet().emailAddress();
    }

    public static String getRandomGender() {
        return faker.options().option("Male", "Female", "Other");
    }

    public static String getRandomUserNumber() {
        return faker.phoneNumber().subscriberNumber(10);
    }

    public static String getRandomDay() {
        return String.format("%02d", faker.number().numberBetween(1, 29)); // Number between 01 and 28;
    }

    public static String getRandomMonth() {
        return faker.options().option(
                "January","February","March","April","May","June",
                "July","August","September","October","November","December");
    }

    public static String getRandomYear() {
        return String.valueOf(faker.number().numberBetween(1900,2025)); // Year between 1900 and 2024
    }

    public static String getRandomSubject() {
        return faker.options().option(
                "Arts","Biology","Chemistry","Computer Science","English","History","Maths","Physics");
    }

    public static String getRandomHobby() {
        return faker.options().option(
                "Sports","Reading","Music");
    }

    public static String getRandomAddress() {
        return faker.address().fullAddress();
    }

    public static String getRandomPicture() {
        return faker.options().option("village.jpg","modern_city.jpg","neon_city.jpg");
    }

    public static String getRandomState() {
        return faker.options().option("NCR","Uttar Pradesh","Haryana","Rajasthan");
    }

    public static String getRandomCity(String state) {
        return switch (state) {
            case "NCR"           -> faker.options().option("Delhi", "Gurgaon", "Noida");
            case "Uttar Pradesh" -> faker.options().option("Agra", "Lucknow", "Merrut");
            case "Haryana"       -> faker.options().option("Karnal", "Panipat");
            case "Rajasthan"     -> faker.options().option("Jaipur", "Jaiselmer");
            default              -> throw new IllegalArgumentException("Unknown state: " + state);
        };
    }
}
