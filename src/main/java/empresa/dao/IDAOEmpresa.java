package empresa.dao;

import java.time.LocalDate;

public interface IDAOEmpresa {
    //Gestion de videojuegos
    public abstract String addGame();
    public abstract String updateGame();
    public abstract String getGameStats();
    public abstract int getTotalPlayers();
    public abstract int getTotalSessions();
    public abstract LocalDate getLastSessionDate();

    //Gestion de jugadores
    public abstract boolean savePlayerProgress();
    public abstract boolean updatePlayerProgress();
    public abstract String getTopExperiencePlayers();
    public abstract String getTopLevelPlayers();

    //Gestion de partidas
    public abstract boolean updateGameStats();
}
