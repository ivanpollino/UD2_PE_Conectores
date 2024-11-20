package empresa.models;

import java.time.LocalDate;

public class Game {
    private int gameId;
    private String isbn;
    private String title;
    private int playerCount;
    private int totalSessions;
    private LocalDate lastSession;

    // Constructor
    public Game() {
    }
    public Game(String isbn, String title, int playerCount, int totalSessions, LocalDate lastSession) {
        this.isbn = isbn;
        this.title = title;
        this.playerCount = playerCount;
        this.totalSessions = totalSessions;
        this.lastSession = lastSession;
    }

    public Game(int gameId, String isbn, String title, int playerCount, int totalSessions, LocalDate lastSession) {
        this.gameId = gameId;
        this.isbn = isbn;
        this.title = title;
        this.playerCount = playerCount;
        this.totalSessions = totalSessions;
        this.lastSession = lastSession;
    }

    // Getters y Setters
    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getPlayerCount() { return playerCount; }
    public void setPlayerCount(int playerCount) { this.playerCount = playerCount; }

    public int getTotalSessions() { return totalSessions; }
    public void setTotalSessions(int totalSessions) { this.totalSessions = totalSessions; }

    public LocalDate getLastSession() { return lastSession; }
    public void setLastSession(LocalDate lastSession) { this.lastSession = lastSession; }
}
