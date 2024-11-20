package empresa.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import empresa.dao.DAOJSON;
import empresa.dao.DAOSQLite;
import empresa.dao.GameDAO;
import empresa.models.ConnectionConfig;
import empresa.models.Game;
import empresa.models.PlayerState;

import static java.sql.DriverManager.getConnection;

public class PlayerView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DAOJSON daoConfig = new DAOJSON();

    public static void main(String[] args) {
        // Configuración inicial
        System.out.println("Bienvenido al Cliente del Jugador");

        // Menú principal de opciones
        mainMenu();
    }

    /**
     * Mostrar el menú principal de opciones.
     */
    private static void mainMenu() {
        int option;
        do {
            System.out.println("\n---- Menú Principal ----");
            System.out.println("1. Cambiar configuración");
            System.out.println("2. Simular una partida");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (option) {
                case 1:
                    changeSettingsMenu();
                    break;
                case 2:
                    selectAndPlayGame();
                    break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (option != 3);
    }

    /**
     * Menú para cambiar configuraciones.
     */
    private static void changeSettingsMenu() {
        System.out.println("\n---- Menú de Configuración ----");
        System.out.println("1. Configuración de conexión");
        System.out.println("2. Configuración local (sonido, resolución, idioma)");
        System.out.println("3. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        switch (choice) {
            case 1:
                configureConnection();
                break;
            case 2:
                manageLocalSettings();
                break;
            case 3:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    /**
     * Configurar la conexión con el servidor.
     */
    private static void configureConnection() {
        System.out.println("\n---- Configuración de la Conexión con el Servidor ----");
        System.out.print("Ingrese el host (IP o URL): ");
        String host = scanner.nextLine();

        System.out.print("Ingrese el puerto de conexión: ");
        String port = scanner.nextLine();

        System.out.print("Ingrese el usuario: ");
        String user = scanner.nextLine();

        System.out.print("Ingrese la contraseña: ");
        String pass = scanner.nextLine();

        System.out.print("Ingrese el Nick Name del jugador: ");
        String nickName = scanner.nextLine();

        // Guardar en el archivo JSON
        ConnectionConfig config = new ConnectionConfig(host, port, user, pass, nickName);
        boolean saved = daoConfig.saveConnectionConfig(config);

        if (saved) {
            System.out.println("¡Configuración de conexión guardada correctamente!");
        } else {
            System.out.println("Error al guardar la configuración de la conexión.");
        }
    }

    /**
     * Gestionar las configuraciones locales del jugador (configuración de sonido, resolución, etc.)
     */
    public static void manageLocalSettings() {
        System.out.println("\n---- Gestión de la Configuración Local ----");

        System.out.print("Habilitar sonido (si/no): ");
        String soundInput = scanner.nextLine();
        boolean soundEnabled = soundInput.equalsIgnoreCase("si");

        System.out.print("Ingrese la resolución: ");
        String resolution = scanner.nextLine();

        System.out.print("Ingrese el idioma preferido: ");
        String language = scanner.nextLine();

        // Crear instancia de DAOSQLite
        DAOSQLite daoConfig = new DAOSQLite();

        // Guardar o actualizar configuración local
        boolean success = daoConfig.saveOrUpdateLocalConfig(soundEnabled, resolution, language);

        if (success) {
            System.out.println("¡Configuración local guardada correctamente!");
        } else {
            System.out.println("Error al guardar o actualizar la configuración.");
        }
    }



    /**
     * Mostrar el progreso del jugador.
     */
    private static void displayPlayerProgress() {
        // Aquí puedes simular la visualización del progreso del jugador.
        // Ejemplo de un progreso ficticio:
        System.out.println("\n---- Progreso del Jugador ----");
        System.out.println("Nick Name: " + "Jugador123");
        System.out.println("Experiencia: " + 1500);
        System.out.println("Nivel de Vida: " + 100);
        System.out.println("Monedas: " + 350);
        System.out.println("Número de sesiones jugadas: " + 10);
        System.out.println("Última conexión: " + "2024-11-20");
    }

    /**
     * Mostrar lista de juegos y permitir al jugador elegir uno para simular una partida.
     */
    private static void selectAndPlayGame() {
        // Definir las URLs de conexión a ambas bases de datos
        String mysqlUrl = "jdbc:mysql://sql.freedb.tech:3306/freedb_UD2_PE_Conectores";
        String postgresUrl = "jdbc:postgresql://ep-yellow-pine-a2a8zmg3.eu-central-1.aws.neon.tech:5432/UD2_PE_Conectores";
        String dbUsernamePSQL = "UD2_PE_Conectores_owner";
        String dbPasswordPSQL = "IfK8XkDjiQ6Y";
        String dbUsernameMSQL = "freedb_ivanpollino";
        String dbPasswordMSQL = "up%wBGWqS5mmMf3";

        // Crear los DAO para obtener los juegos desde ambas bases de datos
        GameDAO mysqlGameDAO = new GameDAO(mysqlUrl, dbUsernameMSQL, dbPasswordMSQL);
        GameDAO postgresGameDAO = new GameDAO(postgresUrl, dbUsernamePSQL, dbPasswordPSQL);

        // Obtener la lista de juegos desde MySQL
        List<Game> mysqlGames = mysqlGameDAO.getAllGames();
        // Obtener la lista de juegos desde PostgreSQL
        List<Game> postgresGames = postgresGameDAO.getAllGames();

        // Combinar las listas de juegos
        List<Game> allGames = new ArrayList<>();
        allGames.addAll(mysqlGames);
        allGames.addAll(postgresGames);

        if (allGames.isEmpty()) {
            System.out.println("No hay juegos disponibles en ninguna base de datos.");
            return;
        }

        // Mostrar los juegos disponibles
        System.out.println("\nElige un juego para jugar:");
        for (int i = 0; i < allGames.size(); i++) {
            Game game = allGames.get(i);
            System.out.println((i + 1) + ". " + game.getTitle() + " (ID: " + game.getGameId() + ")");
        }

        // Pedir al jugador que elija un juego
        System.out.print("Introduce el número del juego que quieres jugar: ");
        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= allGames.size()) {
            Game selectedGame = allGames.get(choice - 1);
            System.out.println("Has elegido el juego: " + selectedGame.getTitle());
            simulateGame(selectedGame);
        } else {
            System.out.println("Opción no válida.");
        }
    }

    /**
     * Simular una partida cambiando los datos del jugador con Math.random()
     */
    private static void simulateGame(Game selectedGame) {
        // Obtener el progreso del jugador
        System.out.print("Introduce tu ID de jugador: ");
        int playerId = scanner.nextInt();

        // Simulamos obtener el estado del jugador (esto se debe hacer con el DAO correspondiente)
        PlayerState playerState = getPlayerState(playerId); // Este método debe existir en el DAO correspondiente

        if (playerState == null) {
            System.out.println("Jugador no encontrado.");
            return;
        }

        // Mostrar el estado inicial del jugador
        System.out.println("Estado inicial del jugador:");
        System.out.println(playerState);

        // Simular cambios en los datos del jugador usando Math.random()
        playerState.setExperience(playerState.getExperience() + (int)(Math.random() * 100)); // Random experiencia
        playerState.setLifeLevel(playerState.getLifeLevel() + (int)(Math.random() * 10) - 5); // Random nivel de vida
        playerState.setCoins(playerState.getCoins() + (int)(Math.random() * 50)); // Random monedas

        // Mostrar el nuevo estado del jugador
        System.out.println("Nuevo estado del jugador después de la partida:");
        System.out.println(playerState);

        // Guardar los cambios en la base de datos (esto también debe ser gestionado por el DAO)
        updatePlayerState(playerState); // Este método debe existir en el DAO correspondiente
        System.out.println("El progreso ha sido guardado.");
    }

    /**
     * Simulamos obtener el estado del jugador.
     */
    private static PlayerState getPlayerState(int playerId) {
        // Implementa la lógica para obtener el estado del jugador
        // Desde el DAO correspondiente, aquí es solo un ejemplo
        return new PlayerState(playerId, "Jugador123", 1500, 100, 350, 10, "2024-11-20");
    }

    /**
     * Método para actualizar el estado del jugador (debería ir en tu DAO).
     */
    private static void updatePlayerState(PlayerState playerState) {
        // Implementa la lógica para actualizar los datos del jugador
        // Desde el DAO correspondiente
    }
}
