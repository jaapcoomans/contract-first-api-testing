package nl.jaapcoomans.demo.gameservice.restclient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import nl.jaapcoomans.demo.api.PlayersApi;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@ApplicationScoped
public class PlayerProfileRestClientProvider {
    @Produces
    public PlayerProfileRestClient playersApiClient(@ConfigProperty(name = "demo.api.player-profile.baseUrl") String basePath) {
        var playerApi = RestClientBuilder.newBuilder()
                .baseUri(basePath)
                .build(PlayersApi.class);

        return new PlayerProfileRestClient(playerApi);
    }
}
