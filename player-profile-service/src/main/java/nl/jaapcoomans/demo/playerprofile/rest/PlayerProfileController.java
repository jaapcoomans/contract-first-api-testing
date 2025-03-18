package nl.jaapcoomans.demo.playerprofile.rest;


import jakarta.inject.Inject;
import nl.jaapcoomans.demo.api.PlayersApi;
import nl.jaapcoomans.demo.api.model.PlayerProfileView;
import nl.jaapcoomans.demo.api.model.SelectOpponentCommand;
import nl.jaapcoomans.demo.api.model.SelectedOpponentView;
import nl.jaapcoomans.demo.playerprofile.domain.PlayerProfile;
import nl.jaapcoomans.demo.playerprofile.domain.PlayerProfileService;
import nl.jaapcoomans.demo.playerprofile.domain.SkillLevel;

public class PlayerProfileController implements PlayersApi {
    private final PlayerProfileService playerProfileService;

    @Inject
    public PlayerProfileController(PlayerProfileService playerProfileService) {
        this.playerProfileService = playerProfileService;
    }

    @Override
    public PlayerProfileView getPlayerProfile(String playerId) {
        var playerProfile = playerProfileService.getPlayerProfile(playerId);
        return toView(playerProfile);
    }

    @Override
    public SelectedOpponentView selectOpponent(SelectOpponentCommand selectOpponentCommand) {
        var skillLevel = toDomain(selectOpponentCommand.getSkillLevel());
        var opponent = playerProfileService.selectOpponent(selectOpponentCommand.getBoardGame(), skillLevel);
        return SelectedOpponentView.builder()
                .playerId(opponent.playerId())
                .name(opponent.name())
                .build();
    }

    private PlayerProfileView toView(PlayerProfile playerProfile) {
        return PlayerProfileView.builder()
                .playerId(playerProfile.playerId())
                .name(playerProfile.name())
                .favouriteGames(playerProfile.favouriteGames())
                .skillLevel(toView(playerProfile.skillLevel()))
                .build();
    }

    private static nl.jaapcoomans.demo.api.model.SkillLevel toView(SkillLevel skillLevel) {
        return nl.jaapcoomans.demo.api.model.SkillLevel.valueOf(skillLevel.name());
    }

    private static SkillLevel toDomain(nl.jaapcoomans.demo.api.model.SkillLevel skillLevel) {
        return SkillLevel.valueOf(skillLevel.name());
    }
}
