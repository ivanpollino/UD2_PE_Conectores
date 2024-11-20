package empresa.models;

import java.time.LocalDate;

public class PlayerState {
    private int playerId;
    private String nickName;
    private int experience;
    private int lifeLevel;
    private int coins;
    private int sessionCount;
    private LocalDate lastLogin;

    public PlayerState() {

    }

    public PlayerState(int playerId, String nickName, int experience, int lifeLevel, int coins, int sessionCount, String date) {
        this.playerId = playerId;
        this.nickName = nickName;
        this.experience = experience;
        this.lifeLevel = lifeLevel;
        this.coins = coins;
        this.sessionCount = sessionCount;
        this.lastLogin = LocalDate.parse(date);
    }

    // Getters and Setters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLifeLevel() {
        return lifeLevel;
    }

    public void setLifeLevel(int lifeLevel) {
        this.lifeLevel = lifeLevel;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(int sessionCount) {
        this.sessionCount = sessionCount;
    }

    public void aumentarSesiones(){
        this.sessionCount++;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "PlayerState{" +
                "playerId=" + playerId +
                ", nickName='" + nickName + '\'' +
                ", experience=" + experience +
                ", lifeLevel=" + lifeLevel +
                ", coins=" + coins +
                ", sessionCount=" + sessionCount +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
