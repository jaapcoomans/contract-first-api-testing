package nl.jaapcoomans.demo.playerprofile.domain;

public class PlayerProfileNotFound extends RuntimeException {
    private final String requestedPlayerId;
    
    public PlayerProfileNotFound(String requestedPlayerId) {
        this.requestedPlayerId = requestedPlayerId;
    }

    public String getRequestedPlayerId() {
        return requestedPlayerId;
    }
}
