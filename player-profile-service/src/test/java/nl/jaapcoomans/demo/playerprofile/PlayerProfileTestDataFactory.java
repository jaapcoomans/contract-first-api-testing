package nl.jaapcoomans.demo.playerprofile;

import net.datafaker.Faker;
import nl.jaapcoomans.demo.playerprofile.domain.PlayerProfile;
import nl.jaapcoomans.demo.playerprofile.domain.SkillLevel;

import java.util.List;
import java.util.stream.IntStream;

public class PlayerProfileTestDataFactory {
    private static final Faker faker = new Faker();

    public static PlayerProfile aPlayerProfile() {
        var name = faker.name().fullName();

        return new PlayerProfile(
                aPlayerId(name),
                name,
                someBoardGames(),
                faker.options().option(SkillLevel.class)
        );
    }

    public static String aPlayerId() {
        return aPlayerId(faker.name().fullName());
    }

    private static String aPlayerId(String name) {
        return name.toLowerCase().substring(0, 5) + faker.number().digits(3);
    }

    private static List<String> someBoardGames() {
        var numberOfGames = faker.number().numberBetween(1, 5);
        return IntStream.range(1, numberOfGames)
                .mapToObj(_ -> someBoardGame())
                .toList();
    }

    private static String someBoardGame() {
        return faker.options().option(
                "Carcasonne",
                "Catan",
                "Viticulture",
                "Between 2 cities",
                "Terraforming Mars",
                "Smallworld",
                "Take 5",
                "Loveletter",
                "Union Pacific"
        );
    }
}
