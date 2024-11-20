package empresa.dao;

import java.time.LocalDate;
import java.util.List;
import empresa.models.Game;
import empresa.models.Player;
import empresa.models.GameSession;

public interface IDAOEmpresa {
    // Gestión de videojuegos
    boolean insertGame(Game game);
    boolean updateGame(Game game);
    Game getGameStats(int gameId);
    int getTotalPlayers(int gameId);
    int getTotalSessions(int gameId);
    LocalDate getLastSessionDate(int gameId);

    // Gestión de jugadores
    boolean savePlayerProgress(Player player);
    boolean updatePlayerProgress(Player player);
    List<Player> getTopExperiencePlayers(int limit);
    List<Player> getTopLevelPlayers(int limit);


    boolean saveGameSession(int gameId, int playerId, int experience, int lifeLevel, int coins, LocalDate sessionDate);

    List<GameSession> getPlayerSessions(int playerId);
}
