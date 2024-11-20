package ud2_pe_conectores.dao;
import com.google.gson.Gson;
import ud2_pe_conectores.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DAOSQLite {

    // Ruta de la base de datos SQLite
    private static final String DB_URL = "jdbc:sqlite:UD2_PE_Conectores.db";

    private Gson gson = new Gson();

    // Obtener la conexión a la base de datos
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Guardar configuración del jugador
    public boolean saveLocalConfig(boolean soundEnabled, String resolution, String language) {
        String query = "INSERT INTO PlayerConfig (sound_enabled, resolution, language) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, soundEnabled);
            stmt.setString(2, resolution);
            stmt.setString(3, language);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    // Guardar configuración del jugador
    public boolean savePlayerConfig(Map<String, String> controlSettings, boolean soundEnabled, String resolution, String language) {
        String query = "INSERT INTO PlayerConfig (control_settings, sound_enabled, resolution, language) VALUES (?, ?, ?, ?)";
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
        String query = "UPDATE PlayerConfig SET control_settings = ?, sound_enabled = ?, resolution = ?, language = ? WHERE player_id = ?";
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
        String query = "SELECT * FROM PlayerConfig WHERE player_id = ?";
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
        String query = "INSERT INTO PlayerState (player_id, nick_name, experience, life_level, coins, session_count, last_login) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
        String query = "UPDATE PlayerState SET nick_name = ?, experience = ?, life_level = ?, coins = ?, session_count = ?, last_login = ? WHERE player_id = ?";
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

    public boolean saveOrUpdateLocalConfig(boolean soundEnabled, String resolution, String language) {
        // Consulta para comprobar si ya existe alguna configuración
        String queryCheckExistence = "SELECT COUNT(*) FROM PlayerConfig"; // Verifica si hay alguna fila en la tabla

        // Consultas de actualización e inserción
        String updateQuery = "UPDATE PlayerConfig SET sound_enabled = ?, resolution = ?, language = ? WHERE id = 3";
        String insertQuery = "INSERT INTO PlayerConfig (sound_enabled, resolution, language) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmtCheck = conn.prepareStatement(queryCheckExistence)) {

            // Verificamos si hay alguna fila en la tabla
            ResultSet rs = stmtCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Si ya existe una fila, actualizamos la configuración
                try (PreparedStatement stmtUpdate = conn.prepareStatement(updateQuery)) {
                    stmtUpdate.setBoolean(1, soundEnabled);
                    stmtUpdate.setString(2, resolution);
                    stmtUpdate.setString(3, language);
                    return stmtUpdate.executeUpdate() > 0;
                }
            } else {
                // Si no existe ninguna fila, insertamos una nueva configuración
                try (PreparedStatement stmtInsert = conn.prepareStatement(insertQuery)) {
                    stmtInsert.setBoolean(1, soundEnabled);
                    stmtInsert.setString(2, resolution);
                    stmtInsert.setString(3, language);
                    return stmtInsert.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    // Obtener estado del jugador
    public PlayerState getPlayerState(int playerId) {
        String query = "SELECT * FROM PlayerState WHERE player_id = ?";
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
