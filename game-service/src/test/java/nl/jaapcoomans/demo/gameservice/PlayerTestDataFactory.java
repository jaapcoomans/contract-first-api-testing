package nl.jaapcoomans.demo.gameservice;

import net.datafaker.Faker;

public class PlayerTestDataFactory {
    private static final Faker faker = new Faker();

    public static String aPlayerId() {
        return aPlayerId(faker.name().fullName());
    }

    public static String aPlayerId(String name) {
        return name.toLowerCase().substring(0, 5) + faker.number().digits(3);
    }
    
    public static String aPlayerName() {
        return faker.name().fullName();
    }
}
