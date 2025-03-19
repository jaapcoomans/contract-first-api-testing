package nl.jaapcoomans.demo.gameservice.domain;

import nl.jaapcoomans.demo.api.model.SkillLevel;

public interface PlayerProfileService {
    Player selectOpponent(String boardGame, SkillLevel skillLevel);

    Player getPlayerById(String playerId);
}
