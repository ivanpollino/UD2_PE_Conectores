package empresa.dao;

import empresa.models.Game;

import java.sql.*;
import java.util.*;

public class GameDAO {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    public GameDAO(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    // Obtener conexión según la base de datos (MySQL o PostgreSQL)
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    // Obtener todos los juegos disponibles desde la base de datos
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        String query = "SELECT * FROM games"; // Suponiendo que los juegos están en la tabla 'games'

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Game game = new Game();
                game.setGameId(rs.getInt("game_id"));
                game.setIsbn(rs.getString("isbn"));
                game.setTitle(rs.getString("title"));
                game.setPlayerCount(rs.getInt("player_count"));
                game.setTotalSessions(rs.getInt("total_sessions"));
                game.setLastSession(rs.getDate("last_session").toLocalDate());
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }
}
