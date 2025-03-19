package nl.jaapcoomans.demo.gameservice;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import nl.jaapcoomans.demo.gameservice.restclient.PlayerProfileRestClient;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@ConnectWireMock
public class PlayerProfileApiClientTest {
    WireMock wireMock;

    @Inject
    PlayerProfileRestClient playerProfileRestClient;

    @Test
    public void test() {
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
}
