package nl.jaapcoomans.demo.playerprofile.rest;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.jaapcoomans.demo.api.model.PlayerNotFoundView;
import nl.jaapcoomans.demo.playerprofile.domain.PlayerProfileNotFound;

@Provider
public class PlayerProfileExceptionMapper implements ExceptionMapper<PlayerProfileNotFound> {
    @Override
    public Response toResponse(PlayerProfileNotFound exception) {
        var notFoundError = PlayerNotFoundView.builder()
                .playerId(exception.getRequestedPlayerId())
                .build();

        return Response.status(Response.Status.NOT_FOUND).entity(notFoundError).build();
    }
}
