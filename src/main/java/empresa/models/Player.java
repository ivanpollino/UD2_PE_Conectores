package empresa.models;

import java.time.LocalDate;

public class Player {
    private int playerId;
    private String nickname;
    private int experience;
    private int lifeLevel;
    private int coins;
    private int sessionCount;
    private LocalDate lastLogin;

    // Constructor
    public Player() {

    }
    public Player(String nickname, int experience, int lifeLevel, int coins, int sessionCount, LocalDate lastLogin) {
        this.nickname = nickname;
        this.experience = experience;
        this.lifeLevel = lifeLevel;
        this.coins = coins;
        this.sessionCount = sessionCount;
        this.lastLogin = lastLogin;
    }

    public Player(int playerId, String nickname, int experience, int lifeLevel, int coins, int sessionCount, LocalDate lastLogin) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.experience = experience;
        this.lifeLevel = lifeLevel;
        this.coins = coins;
        this.sessionCount = sessionCount;
        this.lastLogin = lastLogin;
    }

    // Getters y Setters
    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public int getLifeLevel() { return lifeLevel; }
    public void setLifeLevel(int lifeLevel) { this.lifeLevel = lifeLevel; }

    public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = coins; }

    public int getSessionCount() { return sessionCount; }
    public void setSessionCount(int sessionCount) { this.sessionCount = sessionCount; }

    public LocalDate getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDate lastLogin) { this.lastLogin = lastLogin; }

    @Override
    public String toString() {
        return "Jugador{" +
                "playerId=" + playerId +
                ", nickname='" + nickname + '\'' +
                ", experience=" + experience +
                ", lifeLevel=" + lifeLevel +
                ", coins=" + coins +
                ", sessionCount=" + sessionCount +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
