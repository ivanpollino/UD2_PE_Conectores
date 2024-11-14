package empresa.vista;

import empresa.dao.DAOPostgreSQL;
import empresa.models.Game;
import empresa.models.Player;
import empresa.models.GameSession;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Vista {

    private static DAOPostgreSQL daoPostgreSQL = new DAOPostgreSQL();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Insertar Juego");
            System.out.println("2. Actualizar Juego");
            System.out.println("3. Ver Estadísticas de Juego");
            System.out.println("4. Insertar Jugador");
            System.out.println("5. Ver Jugadores Top por Experiencia");
            System.out.println("6. Insertar Sesión de Juego");
            System.out.println("7. Ver Sesiones de Jugador");
            System.out.println("8. Salir");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    insertarJuego();
                    break;
                case 2:
                    actualizarJuego();
                    break;
                case 3:
                    verEstadisticasJuego();
                    break;
                case 4:
                    insertarJugador();
                    break;
                case 5:
                    verTopJugadores();
                    break;
                case 6:
                    insertarSesionJuego();
                    break;
                case 7:
                    verSesionesJugador();
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void insertarJuego() {
        System.out.print("ISBN del juego: ");
        String isbn = scanner.nextLine();
        System.out.print("Título del juego: ");
        String title = scanner.nextLine();
        System.out.print("Número de jugadores: ");
        int playerCount = scanner.nextInt();
        System.out.print("Número de sesiones: ");
        int totalSessions = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        System.out.print("Fecha de última sesión (yyyy-mm-dd): ");
        String lastSessionStr = scanner.nextLine();
        LocalDate lastSession = LocalDate.parse(lastSessionStr);

        // El gameId no es necesario porque se generará automáticamente en la base de datos
        Game game = new Game(isbn, title, playerCount, totalSessions, lastSession);
        if (daoPostgreSQL.insertGame(game)) {
            System.out.println("Juego insertado correctamente.");
        } else {
            System.out.println("Error al insertar el juego.");
        }
    }

    private static void actualizarJuego() {
        System.out.print("ID del juego a actualizar: ");
        int gameId = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        System.out.print("Nuevo título del juego: ");
        String title = scanner.nextLine();
        System.out.print("Nuevo número de jugadores: ");
        int playerCount = scanner.nextInt();
        System.out.print("Nuevo número de sesiones: ");
        int totalSessions = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        System.out.print("Nueva fecha de última sesión (yyyy-mm-dd): ");
        String lastSessionStr = scanner.nextLine();
        LocalDate lastSession = LocalDate.parse(lastSessionStr);

        Game game = new Game(gameId, null, title, playerCount, totalSessions, lastSession);
        if (daoPostgreSQL.updateGame(game)) {
            System.out.println("Juego actualizado correctamente.");
        } else {
            System.out.println("Error al actualizar el juego.");
        }
    }

    private static void verEstadisticasJuego() {
        System.out.print("ID del juego: ");
        int gameId = scanner.nextInt();
        Game game = daoPostgreSQL.getGameStats(gameId);
        if (game != null) {
            System.out.println("ID: " + game.getGameId());
            System.out.println("ISBN: " + game.getIsbn());
            System.out.println("Título: " + game.getTitle());
            System.out.println("Jugadores: " + game.getPlayerCount());
            System.out.println("Sesiones: " + game.getTotalSessions());
            System.out.println("Última sesión: " + game.getLastSession());
        } else {
            System.out.println("Juego no encontrado.");
        }
    }

    private static void insertarJugador() {
        System.out.print("ID del jugador: ");
        int playerId = scanner.nextInt();
        System.out.print("Experiencia: ");
        int experience = scanner.nextInt();
        System.out.print("Nivel de vida: ");
        int lifeLevel = scanner.nextInt();
        System.out.print("Monedas: ");
        int coins = scanner.nextInt();
        System.out.print("Número de sesiones: ");
        int sessionCount = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        System.out.print("Fecha de última conexión (yyyy-mm-dd): ");
        String lastLoginStr = scanner.nextLine();
        LocalDate lastLogin = LocalDate.parse(lastLoginStr);

        Player player = new Player(playerId, experience, lifeLevel, coins, sessionCount, lastLogin);
        if (daoPostgreSQL.savePlayerProgress(player)) {
            System.out.println("Jugador insertado correctamente.");
        } else {
            System.out.println("Error al insertar el jugador.");
        }
    }

    private static void verTopJugadores() {
        System.out.print("Número de jugadores a mostrar: ");
        int limit = scanner.nextInt();
        List<Player> players = daoPostgreSQL.getTopExperiencePlayers(limit);
        if (!players.isEmpty()) {
            System.out.println("Top jugadores por experiencia:");
            for (Player player : players) {
                System.out.println("ID: " + player.getPlayerId() + ", Experiencia: " + player.getExperience());
            }
        } else {
            System.out.println("No se encontraron jugadores.");
        }
    }

    private static void insertarSesionJuego() {
        System.out.print("ID del juego: ");
        int gameId = scanner.nextInt();
        System.out.print("ID del jugador: ");
        int playerId = scanner.nextInt();
        System.out.print("Experiencia obtenida: ");
        int experience = scanner.nextInt();
        System.out.print("Nivel de vida: ");
        int lifeLevel = scanner.nextInt();
        System.out.print("Monedas obtenidas: ");
        int coins = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        System.out.print("Fecha de la sesión (yyyy-mm-dd): ");
        String sessionDateStr = scanner.nextLine();
        LocalDate sessionDate = LocalDate.parse(sessionDateStr);

        GameSession session = new GameSession(gameId, playerId, experience, lifeLevel, coins, sessionDate);
        if (daoPostgreSQL.saveGameSession(session)) {
            System.out.println("Sesión de juego guardada correctamente.");
        } else {
            System.out.println("Error al guardar la sesión.");
        }
    }

    private static void verSesionesJugador() {
        System.out.print("ID del jugador: ");
        int playerId = scanner.nextInt();
        List<GameSession> sessions = daoPostgreSQL.getPlayerSessions(playerId);
        if (!sessions.isEmpty()) {
            System.out.println("Sesiones del jugador:");
            for (GameSession session : sessions) {
                System.out.println("ID del juego: " + session.getGameId() + ", Fecha: " + session.getSessionDate());
            }
        } else {
            System.out.println("No se encontraron sesiones.");
        }
    }
}
