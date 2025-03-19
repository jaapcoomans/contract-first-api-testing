package nl.jaapcoomans.demo.playerprofile.domain;

import java.util.List;

public record PlayerProfile(
        String playerId,
        String name,
        List<String> favouriteGames,
        SkillLevel skillLevel
) {
    
}
