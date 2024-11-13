package empresa.dao;

import java.time.LocalDate;

public class DAOPostgreSQL implements IDAOEmpresa {
    private String conexion = "jdbc:postgresql://ep-yellow-pine-a2a8zmg3.eu-central-1.aws.neon.tech/UD2_PE_Conectores";
    @Override
    public String addGame() {

        return "";
    }

    @Override
    public String updateGame() {
        return "";
    }

    @Override
    public String getGameStats() {
        return "";
    }

    @Override
    public int getTotalPlayers() {
        return 0;
    }

    @Override
    public int getTotalSessions() {
        return 0;
    }

    @Override
    public LocalDate getLastSessionDate() {
        return null;
    }

    @Override
    public boolean savePlayerProgress() {
        return false;
    }

    @Override
    public boolean updatePlayerProgress() {
        return false;
    }

    @Override
    public String getTopExperiencePlayers() {
        return "";
    }

    @Override
    public String getTopLevelPlayers() {
        return "";
    }

    @Override
    public boolean updateGameStats() {
        return false;
    }
}
