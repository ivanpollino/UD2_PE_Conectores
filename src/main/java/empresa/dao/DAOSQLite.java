package empresa.dao;
import com.google.gson.Gson;
import empresa.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DAOSQLite {

    private static final String DB_URL = "jdbc:sqlite:player_data.db"; // Ruta de la base de datos SQLite
    private Gson gson = new Gson();  // Instanciamos Gson para convertir a y desde JSON

    // Obtener la conexión a la base de datos
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }



    // Guardar configuración del jugador
    public boolean savePlayerConfig(Map<String, String> controlSettings, boolean soundEnabled, String resolution, String language) {
        String query = "INSERT INTO player_config (control_settings, sound_enabled, resolution, language) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            String controlSettingsJson = gson.toJson(controlSettings); // Convertir el mapa en JSON
            stmt.setString(1, controlSettingsJson);  // Guardamos como String
            stmt.setBoolean(2, soundEnabled);
            stmt.setString(3, resolution);
            stmt.setString(4, language);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Actualizar configuración del jugador
    public boolean updatePlayerConfig(int playerId, Map<String, String> controlSettings, boolean soundEnabled, String resolution, String language) {
        String query = "UPDATE player_config SET control_settings = ?, sound_enabled = ?, resolution = ?, language = ? WHERE player_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            String controlSettingsJson = gson.toJson(controlSettings); // Convertir el mapa en JSON
            stmt.setString(1, controlSettingsJson);
            stmt.setBoolean(2, soundEnabled);
            stmt.setString(3, resolution);
            stmt.setString(4, language);
            stmt.setInt(5, playerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Obtener configuración del jugador
    public PlayerConfig getPlayerConfig(int playerId) {
        String query = "SELECT * FROM player_config WHERE player_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PlayerConfig config = new PlayerConfig();
                String controlSettingsJson = rs.getString("control_settings");
                Map<String, String> controlSettings = gson.fromJson(controlSettingsJson, Map.class);
                config.setControlSettings(controlSettings);
                config.setSoundEnabled(rs.getBoolean("sound_enabled"));
                config.setResolution(rs.getString("resolution"));
                config.setLanguage(rs.getString("language"));
                return config;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // --------------- Estado del Jugador ----------------

    // Guardar estado del jugador
    public boolean savePlayerState(PlayerState playerState) {
        String query = "INSERT INTO player_state (player_id, nick_name, experience, life_level, coins, session_count, last_login) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, playerState.getPlayerId());
            stmt.setString(2, playerState.getNickName());
            stmt.setInt(3, playerState.getExperience());
            stmt.setInt(4, playerState.getLifeLevel());
            stmt.setInt(5, playerState.getCoins());
            stmt.setInt(6, playerState.getSessionCount());
            stmt.setDate(7, Date.valueOf(playerState.getLastLogin()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Actualizar estado del jugador
    public boolean updatePlayerState(PlayerState playerState) {
        String query = "UPDATE player_state SET nick_name = ?, experience = ?, life_level = ?, coins = ?, session_count = ?, last_login = ? WHERE player_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, playerState.getNickName());
            stmt.setInt(2, playerState.getExperience());
            stmt.setInt(3, playerState.getLifeLevel());
            stmt.setInt(4, playerState.getCoins());
            stmt.setInt(5, playerState.getSessionCount());
            stmt.setDate(6, Date.valueOf(playerState.getLastLogin()));
            stmt.setInt(7, playerState.getPlayerId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Obtener estado del jugador
    public PlayerState getPlayerState(int playerId) {
        String query = "SELECT * FROM player_state WHERE player_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PlayerState playerState = new PlayerState();
                playerState.setPlayerId(rs.getInt("player_id"));
                playerState.setNickName(rs.getString("nick_name"));
                playerState.setExperience(rs.getInt("experience"));
                playerState.setLifeLevel(rs.getInt("life_level"));
                playerState.setCoins(rs.getInt("coins"));
                playerState.setSessionCount(rs.getInt("session_count"));
                playerState.setLastLogin(rs.getDate("last_login").toLocalDate());
                return playerState;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --------------- Partidas Jugadas ----------------

    // Guardar partida jugada
    public boolean saveGameSession(GameSession session) {
        String query = "INSERT INTO game_sessions (game_id, player_id, experience, life_level, coins, session_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, session.getGameId());
            stmt.setInt(2, session.getPlayerId());
            stmt.setInt(3, session.getExperience());
            stmt.setInt(4, session.getLifeLevel());
            stmt.setInt(5, session.getCoins());
            stmt.setDate(6, Date.valueOf(session.getSessionDate()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Obtener las partidas de un jugador
    public List<GameSession> getPlayerSessions(int playerId) {
        List<GameSession> sessions = new ArrayList<>();
        String query = "SELECT * FROM game_sessions WHERE player_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                GameSession session = new GameSession();
                session.setGameId(rs.getInt("game_id"));
                session.setPlayerId(rs.getInt("player_id"));
                session.setExperience(rs.getInt("experience"));
                session.setLifeLevel(rs.getInt("life_level"));
                session.setCoins(rs.getInt("coins"));
                session.setSessionDate(rs.getDate("session_date").toLocalDate());
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }
}
