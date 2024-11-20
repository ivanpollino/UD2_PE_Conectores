package ud2_pe_conectores.view;

import ud2_pe_conectores.dao.DAOFactory;
import ud2_pe_conectores.dao.DAOMySQL;
import ud2_pe_conectores.dao.DAOPostgreSQL;
import ud2_pe_conectores.dao.IDAOEmpresa;
import ud2_pe_conectores.models.Game;
import ud2_pe_conectores.models.GameSession;
import ud2_pe_conectores.models.Player;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class VistaEmpresa {

    private static IDAOEmpresa dao;
    private static Scanner scanner = new Scanner(System.in);
    private static DAOMySQL daoMySQL = new DAOMySQL();
    private static DAOPostgreSQL daoPostgreSQL = new DAOPostgreSQL();

    public static void main(String[] args) throws SQLException {
        seleccionarBaseDeDatos();
        mainMenu();
    }

    private static void seleccionarBaseDeDatos() {
        System.out.println("===== Selección de Base de Datos =====");
        System.out.println("1. MySQL");
        System.out.println("2. PostgreSQL");
        System.out.print("Seleccione el tipo de base de datos: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        switch (opcion) {
            case 1:
                dao = DAOFactory.getDAO(DAOFactory.DBType.MYSQL);
                break;
            case 2:
                dao = DAOFactory.getDAO(DAOFactory.DBType.POSTGRESQL);
                break;
            default:
                System.out.println("Opción no válida. Se usará PostgreSQL por defecto.");
                dao = DAOFactory.getDAO(DAOFactory.DBType.POSTGRESQL);
        }
        System.out.println("Conectado a la base de datos seleccionada.\n");
    }

    private static void mainMenu() throws SQLException {
        int opcion;
        do {
            System.out.println("===== Menú Principal =====");
            System.out.println("1. Gestionar Juegos");
            System.out.println("2. Gestionar Jugadores");
            System.out.println("3. Gestionar Sesiones");
            System.out.println("4. Salir");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    subMenuJuego();
                    break;
                case 2:
                    subMenuJugador();
                    break;
                case 3:
                    subMenuSesiones();
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }

    private static void subMenuJuego() {
        int opcion;
        do {
            System.out.println("===== Gestionar Juegos =====");
            System.out.println("1. Insertar juego");
            System.out.println("2. Actualizar juego");
            System.out.println("3. Ver estadísticas del juego");
            System.out.println("4. Volver al menú principal");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

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
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }

    private static void subMenuJugador() {
        int opcion;
        do {
            System.out.println("===== Gestionar Jugadores =====");
            System.out.println("1. Insertar jugador");
            System.out.println("2. Ver top jugadores");
            System.out.println("3. Volver al menú principal");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    insertarJugador();
                    break;
                case 2:
                    verTopJugadores();
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);
    }

    private static void subMenuSesiones() throws SQLException {
        int opcion;
        do {
            System.out.println("===== Gestionar Sesiones =====");
            System.out.println("1. Insertar sesión de juego");
            System.out.println("2. Ver sesiones de un jugador");
            System.out.println("3. Volver al menú principal");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    insertarSesionJuego();
                    break;
                case 2:
                    verSesionesJugador();
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);
    }

    private static void insertarJuego() {
        System.out.print("Título del juego: ");
        String title = scanner.nextLine();
        System.out.print("ISBN del juego: ");
        String isbn = scanner.nextLine();

        Game game = new Game(isbn, title, 0, 0, LocalDate.now());
        if (dao.insertGame(game)) {
            System.out.println("Juego insertado correctamente.");
        } else {
            System.out.println("Error al insertar el juego.");
        }
    }

    private static void actualizarJuego() {
        System.out.print("ID del juego a actualizar: ");
        int gameId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo título: ");
        String title = scanner.nextLine();

        Game game = new Game(gameId, null, title, 0, 0, null);
        if (dao.updateGame(game)) {
            System.out.println("Juego actualizado correctamente.");
        } else {
            System.out.println("Error al actualizar el juego.");
        }
    }

    private static void verEstadisticasJuego() {
        System.out.print("ID del juego: ");
        int gameId = scanner.nextInt();

        Game game = dao.getGameStats(gameId);
        if (game != null) {
            System.out.println("Título: " + game.getTitle());
            System.out.println("Jugadores: " + game.getPlayerCount());
        } else {
            System.out.println("Juego no encontrado.");
        }
    }

    private static void insertarJugador() {
        System.out.print("Nickname: ");
        String nickname = scanner.nextLine();

        Player player = new Player(nickname, 0, 0, 0, 0, LocalDate.now());
        if (daoMySQL.savePlayerProgress(player) && daoPostgreSQL.savePlayerProgress(player)) {
            System.out.println("Jugador insertado correctamente.");
        } else {
            System.out.println("Error al insertar el jugador.");
        }
    }

    private static void verTopJugadores() {
        List<Player> players = dao.getTopExperiencePlayers(10);
        if (!players.isEmpty()) {
            for (Player player : players) {
                System.out.println("Nickname: " + player.getNickname() + " - Experiencia: " + player.getExperience());
            }
        } else {
            System.out.println("No se encontraron jugadores.");
        }
    }

    private static void insertarSesionJuego() throws SQLException {
        System.out.println("Insertar nueva sesión de juego:");

        // Solicitar ID del juego
        System.out.print("Ingrese el ID del juego: ");
        int gameId = scanner.nextInt();

        // Solicitar ID del jugador
        System.out.print("Ingrese el ID del jugador: ");
        int playerId = scanner.nextInt();

        // Verificar si el jugador existe
        if (!dao.playerExists(playerId)) {
            System.out.println("Error: El jugador con ID " + playerId + " no existe.");
            return;
        }

        // Solicitar experiencia ganada
        System.out.print("Ingrese la experiencia ganada: ");
        int experience = scanner.nextInt();

        // Solicitar cambio en nivel de vida
        System.out.print("Ingrese el cambio en el nivel de vida (+/-): ");
        int lifeLevel = scanner.nextInt();

        // Solicitar cambio en monedas
        System.out.print("Ingrese el cambio en monedas (+/-): ");
        int coins = scanner.nextInt();

        // Registrar la fecha de la sesión
        LocalDate sessionDate = LocalDate.now();

        // Guardar la sesión en la base de datos
        boolean success = dao.saveGameSession(gameId, playerId, experience, lifeLevel, coins, sessionDate);

        if (success) {
            System.out.println("La sesión de juego se registró exitosamente.");
        } else {
            System.out.println("Error al registrar la sesión de juego.");
        }
    }



    private static void verSesionesJugador() {
        System.out.println("Ver sesiones de un jugador:");

        // Solicitar ID del jugador
        System.out.print("Ingrese el ID del jugador: ");
        int playerId = scanner.nextInt();

        // Obtener las sesiones del jugador
        List<GameSession> sessions = dao.getPlayerSessions(playerId);

        if (sessions.isEmpty()) {
            System.out.println("No se encontraron sesiones para el jugador con ID " + playerId + ".");
            return;
        }

        // Mostrar las sesiones obtenidas
        System.out.println("Sesiones del jugador con ID " + playerId + ":");
        for (GameSession session : sessions) {
            System.out.println("Fecha: " + session.getSessionDate());
            System.out.println("ID del Juego: " + session.getGameId());
            System.out.println("Experiencia ganada: " + session.getExperience());
            System.out.println("Cambio en nivel de vida: " + session.getLifeLevel());
            System.out.println("Cambio en monedas: " + session.getCoins());
            System.out.println("---------------------------");
        }
    }

}
