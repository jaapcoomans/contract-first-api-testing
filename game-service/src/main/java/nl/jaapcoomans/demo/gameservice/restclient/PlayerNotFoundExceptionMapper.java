package nl.jaapcoomans.demo.gameservice.restclient;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import nl.jaapcoomans.demo.api.model.NoMatchingOpponentFound;
import nl.jaapcoomans.demo.gameservice.domain.NoMatchingOpponent;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

public class PlayerNotFoundExceptionMapper implements ResponseExceptionMapper<NoMatchingOpponent> {
    @Override
    public NoMatchingOpponent toThrowable(Response response) {
        var mediaType = response.getMediaType();

        if (mediaType == MediaType.APPLICATION_JSON_TYPE) {
            var responseBody = response.readEntity(NoMatchingOpponentFound.class);
            return new NoMatchingOpponent("No matching opponent found.", responseBody.getLowerSkillLevelAvailable());
        }

        return null;
    }

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return status == Response.Status.NOT_FOUND.getStatusCode();
    }
}
