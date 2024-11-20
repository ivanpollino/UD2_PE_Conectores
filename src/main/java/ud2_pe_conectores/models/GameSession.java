package ud2_pe_conectores.models;

import java.time.LocalDate;

public class GameSession {
    private int gameId;
    private int playerId;
    private int experience;
    private int lifeLevel;
    private int coins;
    private LocalDate sessionDate;

    // Constructor


    public GameSession() {
    }

    public GameSession(int gameId, int playerId, int experience, int lifeLevel, int coins, LocalDate sessionDate) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.experience = experience;
        this.lifeLevel = lifeLevel;
        this.coins = coins;
        this.sessionDate = sessionDate;
    }

    // Getters y Setters
    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public int getLifeLevel() { return lifeLevel; }
    public void setLifeLevel(int lifeLevel) { this.lifeLevel = lifeLevel; }

    public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = coins; }

    public LocalDate getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDate sessionDate) { this.sessionDate = sessionDate; }
}
