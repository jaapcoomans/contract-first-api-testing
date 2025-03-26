package nl.jaapcoomans.demo.playerprofile.domain;

import jakarta.enterprise.context.ApplicationScoped;
import net.datafaker.Faker;

import java.util.List;

@ApplicationScoped
public class PlayerProfileService {
    private static final Faker faker = new Faker();

    public PlayerProfile getPlayerProfile(String playerId) {
        return new PlayerProfile(
                playerId,
                faker.name().fullName(),
                someBoardGames(),
                faker.options().option(SkillLevel.class)
        );
    }

    public PlayerProfile selectOpponent(String boardGame, SkillLevel skillLevel) {
        return new PlayerProfile(
                "opponent1",
                "Oppo Nent",
                List.of(boardGame),
                skillLevel
        );
    }

    private List<String> someBoardGames() {
        return List.of(
                someBoardGame(),
                someBoardGame()
        );
    }

    private String someBoardGame() {
        return faker.options().option(
                "Carcasonne",
                "Catan",
                "Viticulture"
        );
    }
}
