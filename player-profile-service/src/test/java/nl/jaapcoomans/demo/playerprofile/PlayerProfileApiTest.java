package nl.jaapcoomans.demo.playerprofile;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import nl.jaapcoomans.demo.openapi.test.OpenApiContract;
import nl.jaapcoomans.demo.playerprofile.domain.PlayerProfileService;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static nl.jaapcoomans.demo.playerprofile.PlayerProfileTestDataFactory.aPlayerProfile;
import static org.mockito.Mockito.when;

@QuarkusTest
public class PlayerProfileApiTest {
    @InjectMock
    PlayerProfileService playerProfileService;

    private static final OpenApiContract apiContract = OpenApiContract.fromClasspath("openapi/player-profile-api.json");

    @Test
    public void test() {
        // Given
        var profile = aPlayerProfile();
        var playerId = profile.playerId();

        when(playerProfileService.getPlayerProfile(playerId))
                .thenReturn(profile);

        // When
        var response = given()
                .get("/players/{playerId}", playerId);

        // Then
        response.then()
                .statusCode(200);

        var body = response.body().asString();
        apiContract.assertThat(body).isValidResponseFor("getPlayerProfile", 200);
    }
    
    @Test
    public void testInvalidPlayerId() {
        
    }
}
