package nl.jaapcoomans.demo.gameservice.domain;

public class NoMatchingOpponent extends RuntimeException {
    private final boolean lowerSkillLevelAvailable;

    public NoMatchingOpponent(String message, boolean lowerSkillLevelAvailable) {
        super(message);
        this.lowerSkillLevelAvailable = lowerSkillLevelAvailable;
    }

    public boolean isLowerSkillLevelAvailable() {
        return lowerSkillLevelAvailable;
    }
}
