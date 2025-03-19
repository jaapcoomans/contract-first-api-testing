package nl.jaapcoomans.demo.gameservice.restclient;

import nl.jaapcoomans.demo.api.PlayersApi;
import nl.jaapcoomans.demo.api.model.PlayerProfileView;
import nl.jaapcoomans.demo.api.model.SelectOpponentCommand;
import nl.jaapcoomans.demo.api.model.SelectedOpponentView;
import nl.jaapcoomans.demo.api.model.SkillLevel;
import nl.jaapcoomans.demo.gameservice.domain.Player;
import nl.jaapcoomans.demo.gameservice.domain.PlayerProfileService;

public class PlayerProfileRestClient implements PlayerProfileService {
    private final PlayersApi playersApi;

    public PlayerProfileRestClient(PlayersApi playersApi) {
        this.playersApi = playersApi;
    }

    @Override
    public Player selectOpponent(String boardGame, SkillLevel skillLevel) {
        var command = SelectOpponentCommand.builder()
                .boardGame(boardGame)
                .skillLevel(skillLevel)
                .build();

        var selectedOpponent = playersApi.selectOpponent(command);

        return toDomain(selectedOpponent);
    }

    @Override
    public Player getPlayerById(String playerId) {
        var playerView = playersApi.getPlayerProfile(playerId);

        return toDomain(playerView);
    }

    private static Player toDomain(SelectedOpponentView selectedOpponentView) {
        return new Player(selectedOpponentView.getPlayerId(), selectedOpponentView.getName());
    }

    private static Player toDomain(PlayerProfileView playerProfileView) {
        return new Player(playerProfileView.getPlayerId(), playerProfileView.getName());
    }
}
