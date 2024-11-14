package empresa.dao;

import empresa.models.Game;
import empresa.models.Player;
import empresa.models.GameSession;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOMySQL implements IDAOEmpresa {
    private String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_UD2_PE_Conectores";
    private String user = "freedb_ivanpollino";
    private String password = "up%wBGWqS5mmMf3";

    // Método para obtener la conexión a MySQL
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    // Gestión de Videojuegos
    @Override
    public boolean insertGame(Game game) {
        String query = "INSERT INTO games (game_id, isbn, title, player_count, total_sessions, last_session) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, game.getGameId());
            stmt.setString(2, game.getIsbn());
            stmt.setString(3, game.getTitle());
            stmt.setInt(4, game.getPlayerCount());
            stmt.setInt(5, game.getTotalSessions());
            stmt.setDate(6, Date.valueOf(game.getLastSession()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateGame(Game game) {
        return false;
    }

    @Override
    public Game getGameStats(int gameId) {
        String query = "SELECT * FROM games WHERE game_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Game(
                        rs.getInt("game_id"),
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getInt("player_count"),
                        rs.getInt("total_sessions"),
                        rs.getDate("last_session").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getTotalPlayers(int gameId) {
        String query = "SELECT COUNT(*) FROM players WHERE game_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);  // Devuelve el número total de jugadores
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;  // Retorna 0 en caso de error
    }

    @Override
    public int getTotalSessions(int gameId) {
        String query = "SELECT COUNT(*) FROM game_sessions WHERE game_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);  // Devuelve el número total de sesiones
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;  // Retorna 0 en caso de error
    }

    @Override
    public LocalDate getLastSessionDate(int gameId) {
        String query = "SELECT MAX(session_date) FROM game_sessions WHERE game_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Date lastSession = rs.getDate(1);  // Obtiene la última fecha de sesión
                return lastSession != null ? lastSession.toLocalDate() : null;  // Retorna la fecha, o null si no hay sesiones
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Retorna null si no hay sesiones
    }


    // Gestión de Jugadores
    @Override
    public boolean savePlayerProgress(Player player) {
        String query = "INSERT INTO players (player_id, experience, life_level, coins, session_count, last_login) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, player.getPlayerId());
            stmt.setInt(2, player.getExperience());
            stmt.setInt(3, player.getLifeLevel());
            stmt.setInt(4, player.getCoins());
            stmt.setInt(5, player.getSessionCount());
            stmt.setDate(6, Date.valueOf(player.getLastLogin()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePlayerProgress(Player player) {
        return false;
    }

    @Override
    public List<Player> getTopExperiencePlayers(int limit) {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players ORDER BY experience DESC LIMIT ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                players.add(new Player(
                        rs.getInt("player_id"),
                        rs.getString("nickname"),
                        rs.getInt("experience"),
                        rs.getInt("life_level"),
                        rs.getInt("coins"),
                        rs.getInt("session_count"),
                        rs.getDate("last_login").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public List<Player> getTopLevelPlayers(int limit) {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players ORDER BY experience DESC, life_level DESC LIMIT ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                players.add(new Player(
                        rs.getInt("player_id"),
                        rs.getString("nickname"),
                        rs.getInt("experience"),
                        rs.getInt("life_level"),
                        rs.getInt("coins"),
                        rs.getInt("session_count"),
                        rs.getDate("last_login").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    // Gestión de Partidas
    @Override
    public boolean saveGameSession(GameSession session) {
        String query = "INSERT INTO game_sessions (game_id, player_id, experience, life_level, coins, session_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
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

    @Override
    public List<GameSession> getPlayerSessions(int playerId) {
        List<GameSession> sessions = new ArrayList<>();
        String query = "SELECT * FROM game_sessions WHERE player_id = ? ORDER BY session_date DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sessions.add(new GameSession(
                        rs.getInt("game_id"),
                        rs.getInt("player_id"),
                        rs.getInt("experience"),
                        rs.getInt("life_level"),
                        rs.getInt("coins"),
                        rs.getDate("session_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }
}
