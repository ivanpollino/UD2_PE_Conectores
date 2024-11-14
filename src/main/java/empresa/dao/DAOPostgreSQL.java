package empresa.dao;

import empresa.models.Game;
import empresa.models.Player;
import empresa.models.GameSession;

import java.net.ConnectException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOPostgreSQL implements IDAOEmpresa {
    private String url = "jdbc:postgresql://ep-yellow-pine-a2a8zmg3.eu-central-1.aws.neon.tech:5432/UD2_PE_Conectores";
    private String user = "UD2_PE_Conectores_owner";
    private String password = "IfK8XkDjiQ6Y";

    // Método para obtener la conexión
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    // Gestión de Videojuegos
    @Override
    public boolean insertGame(Game game) {
        String query = "INSERT INTO games (isbn, title, player_count, total_sessions, last_session) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, game.getIsbn());
            stmt.setString(2, game.getTitle());
            stmt.setInt(3, game.getPlayerCount());
            stmt.setInt(4, game.getTotalSessions());
            stmt.setDate(5, Date.valueOf(game.getLastSession()));

            return stmt.executeUpdate() > 0;  // Devuelve true si se insertó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateGame(Game game) {
        String query = "UPDATE games SET title = ?, player_count = ?, total_sessions = ?, last_session = ? WHERE game_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, game.getTitle());
            stmt.setInt(2, game.getPlayerCount());
            stmt.setInt(3, game.getTotalSessions());
            stmt.setDate(4, Date.valueOf(game.getLastSession()));
            stmt.setInt(5, game.getGameId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String query = "SELECT player_count FROM games WHERE game_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("player_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getTotalSessions(int gameId) {
        String query = "SELECT total_sessions FROM games WHERE game_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_sessions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public LocalDate getLastSessionDate(int gameId) {
        String query = "SELECT last_session FROM games WHERE game_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDate("last_session").toLocalDate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        String query = "UPDATE players SET experience = ?, life_level = ?, coins = ?, session_count = ?, last_login = ? WHERE player_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, player.getExperience());
            stmt.setInt(2, player.getLifeLevel());
            stmt.setInt(3, player.getCoins());
            stmt.setInt(4, player.getSessionCount());
            stmt.setDate(5, Date.valueOf(player.getLastLogin()));
            stmt.setInt(6, player.getPlayerId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String query = "SELECT * FROM players ORDER BY life_level DESC LIMIT ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, limit);  // Establecer el límite de jugadores
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Player player = new Player(
                        rs.getInt("player_id"),
                        rs.getString("nickname"),
                        rs.getInt("experience"),
                        rs.getInt("life_level"),
                        rs.getInt("coins"),
                        rs.getInt("session_count"),
                        rs.getDate("last_login").toLocalDate()
                );
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }


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
            stmt.setDate(6, Date.valueOf(session.getSessionDate()));  // Convertir LocalDate a java.sql.Date

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Retorna true si la inserción fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Retorna false si hubo algún error
    }


    @Override
    public List<GameSession> getPlayerSessions(int playerId) {
        List<GameSession> sessions = new ArrayList<>();
        String query = "SELECT * FROM game_sessions WHERE player_id = ? ORDER BY session_date DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, playerId);  // Establecer el ID del jugador en la consulta
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                GameSession session = new GameSession(
                        rs.getInt("game_id"),
                        rs.getInt("player_id"),
                        rs.getInt("experience"),
                        rs.getInt("life_level"),
                        rs.getInt("coins"),
                        rs.getDate("session_date").toLocalDate()  // Convertir java.sql.Date a LocalDate
                );
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

}
