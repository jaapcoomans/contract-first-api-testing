package nl.jaapcoomans.demo.gameservice;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import nl.jaapcoomans.demo.api.model.SkillLevel;
import nl.jaapcoomans.demo.gameservice.restclient.PlayerProfileRestClient;
import nl.jaapcoomans.demo.openapi.test.OpenApiContract;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static nl.jaapcoomans.demo.gameservice.PlayerTestDataFactory.aPlayerId;
import static nl.jaapcoomans.demo.gameservice.PlayerTestDataFactory.aPlayerName;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@ConnectWireMock
public class PlayerProfileApiClientTest {
    private static final OpenApiContract apiContract = OpenApiContract.fromClasspath("openapi/player-profile-api.json");

    WireMock wireMock;

    @Inject
    PlayerProfileRestClient playerProfileRestClient;

    @Test
    public void getPlayerProfile() {
        // Given
        var responseBody = """
                {
                    "playerId": "123456",
                    "name": "John Doe"
                }
                """;

        wireMock.register(get(urlPathTemplate("/players/{playerId}"))
                .withPathParam("playerId", equalTo("123456"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)
                ));

        // When
        var player = playerProfileRestClient.getPlayerById("123456");

        // Then
        assertThat(player.getPlayerId()).isEqualTo("123456");
        assertThat(player.getName()).isEqualTo("John Doe");
    }

    @Test
    public void selectOpponent() {
        var name = aPlayerName();
        var playerId = aPlayerId(name);

        wireMock.register(post(urlEqualTo("/players/select-opponent"))
                .willReturn(okJson(validSelectOpponentResponse(playerId, name))));

        // When
        var response = playerProfileRestClient.selectOpponent("Catan", SkillLevel.BEGINNER);

        // Then
        assertThat(response.getPlayerId()).isEqualTo(playerId);
        assertThat(response.getName()).isEqualTo(name);

        var actualRequests = wireMock.find(postRequestedFor(urlEqualTo("/players/select-opponent")));
        assertThat(actualRequests).hasSize(1);

        var requestBody = actualRequests.getFirst().getBodyAsString();
        apiContract.assertThat(requestBody).isValidRequestFor("selectOpponent");
    }

    private static String validSelectOpponentResponse(String playerId, String name) {
        return """ 
                {
                    "playerId": "%s",
                    "name": "%s"
                }
                """.formatted(playerId, name);
    }
}
