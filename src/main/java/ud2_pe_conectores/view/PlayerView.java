package ud2_pe_conectores.view;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ud2_pe_conectores.dao.*;
import ud2_pe_conectores.models.ConnectionConfig;
import ud2_pe_conectores.models.Game;
import ud2_pe_conectores.models.Player;
import ud2_pe_conectores.models.PlayerState;

import static java.sql.DriverManager.getConnection;

public class PlayerView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DAOJSON daoConfig = new DAOJSON();
    private static final DAOMySQL daoMySQL = new DAOMySQL();
    private static final DAOPostgreSQL daoPostgreSQL = new DAOPostgreSQL();
    private static DAOSQLite daoSQLite = new DAOSQLite();

    public static void main(String[] args) throws SQLException {
        login();
        // Configuración inicial
        System.out.println("Bienvenido al Cliente del Jugador");

        // Menú principal de opciones
        mainMenu();
    }

    private static Player login() {
        boolean loginExitoso = false;
        Player loggedInPlayer = null;

        do {
            System.out.println("\n---- Login ----");
            System.out.print("Ingrese su nickname: ");
            String nickname = scanner.nextLine();

            // Intentar login en MySQL
            List<Player> players = daoMySQL.getAllPlayers();
            for (Player p : players) {
                if (p.getNickname().equalsIgnoreCase(nickname)) {
                    System.out.println("Login exitoso");
                    loggedInPlayer = p; // Guardamos el jugador que hizo login
                    loginExitoso = true; // El login fue exitoso, salir del bucle
                    break; // Romper el bucle de MySQL
                }
            }

            // Si el login no fue exitoso, se muestra un mensaje de error
            if (!loginExitoso) {
                System.out.println("Nickname incorrecto. Intente nuevamente.");
            }
        } while (!loginExitoso); // Continuar hasta que el login sea exitoso

        // Verificar si el estado del jugador ya existe en SQLite
        PlayerState playerState = daoSQLite.getPlayerState(loggedInPlayer.getPlayerId());
        if (playerState == null) { // Si no existe, creamos un nuevo estado inicial
            playerState = new PlayerState();
            playerState.setPlayerId(loggedInPlayer.getPlayerId());
            playerState.setNickName(loggedInPlayer.getNickname());
            playerState.setExperience(0);
            playerState.setLifeLevel(100); // Vida inicial
            playerState.setCoins(0); // Monedas iniciales
            playerState.setSessionCount(0);
            playerState.setLastLogin(LocalDate.now());

            // Guardar el estado inicial en SQLite
            if (daoSQLite.savePlayerState(playerState)) {
                System.out.println("Estado inicial del jugador creado en SQLite.");
            } else {
                System.out.println("Hubo un error al guardar el estado inicial del jugador en SQLite.");
            }
        } else {
            System.out.println("El estado del jugador ya existe en SQLite.");
        }

        // Actualizar el estado del jugador en MySQL
        boolean updatedInMySQL = daoMySQL.updatePlayerFromPlayerState(playerState);
        if (updatedInMySQL) {
            System.out.println("Estado del jugador actualizado en MySQL.");
        } else {
            System.out.println("Hubo un error al actualizar el estado del jugador en MySQL.");
        }

        // Actualizar el estado del jugador en PostgreSQL
        boolean updatedInPostgreSQL = daoPostgreSQL.updatePlayerFromPlayerState(playerState);
        if (updatedInPostgreSQL) {
            System.out.println("Estado del jugador actualizado en PostgreSQL.");
        } else {
            System.out.println("Hubo un error al actualizar el estado del jugador en PostgreSQL.");
        }

        System.out.println("===== ESTADO DEL JUGADOR " + loggedInPlayer.getNickname() + " =====");
        playerState = daoSQLite.getPlayerState(loggedInPlayer.getPlayerId());
        System.out.println(playerState);

        return loggedInPlayer; // Retornamos el jugador que hizo login
    }





    /**
     * Mostrar el menú principal de opciones.
     */
    private static void mainMenu() throws SQLException {
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
    private static void selectAndPlayGame() throws SQLException {

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
    private static void simulateGame(Game selectedGame) throws SQLException {
        // Obtener el progreso del jugador
        System.out.print("Introduce tu ID de jugador: ");
        int playerId = scanner.nextInt();

        // Verificar si el jugador existe en SQLite
        PlayerState playerState = daoSQLite.getPlayerState(playerId);
        boolean playerFound = false;

        if (playerState != null) {
            System.out.println("Jugador encontrado en SQLite.");
            playerFound = true;
        } else {
            // Si no está en SQLite, intentar obtenerlo de MySQL
            playerState = daoMySQL.getPlayerState(playerId);
            if (playerState != null) {
                System.out.println("Jugador encontrado en MySQL.");
                playerFound = true;
            }

            // Si no se encuentra en MySQL, intentar en PostgreSQL
            if (!playerFound) {
                playerState = daoPostgreSQL.getPlayerState(playerId);
                if (playerState != null) {
                    System.out.println("Jugador encontrado en PostgreSQL.");
                    playerFound = true;
                }
            }

            // Si se encontró el jugador en MySQL o PostgreSQL, guardar el estado inicial en SQLite
            if (playerFound) {
                System.out.println("Guardando el estado inicial del jugador en SQLite...");
                daoSQLite.savePlayerState(playerState);
            }
        }

        // Si el jugador no se encuentra en ninguna base de datos
        if (!playerFound) {
            System.out.println("Jugador no encontrado en las bases de datos.");
            return;
        }

        // Mostrar el estado inicial del jugador
        System.out.println("Estado inicial del jugador:");
        System.out.println(playerState);

        // Verificar y asignar el nickname si está vacío o nulo
        if (playerState.getNickName() == null || playerState.getNickName().isEmpty()) {
            System.out.print("Introduce el nickname del jugador: ");
            scanner.nextLine(); // Limpiar el buffer del Scanner
            String nickName = scanner.nextLine();
            playerState.setNickName(nickName);
        }

        // Guardar valores iniciales para la sesión
        int initialExperience = playerState.getExperience();
        int initialLifeLevel = playerState.getLifeLevel();
        int initialCoins = playerState.getCoins();

        // Actualizar los atributos del jugador
        playerState.setExperience(playerState.getExperience() + (int) (Math.random() * 100)); // Random experiencia
        playerState.setLifeLevel(playerState.getLifeLevel() + (int) (Math.random() * 10) - 5); // Random nivel de vida
        playerState.setCoins(playerState.getCoins() + (int) (Math.random() * 50)); // Random monedas
        playerState.aumentarSesiones();
        playerState.setLastLogin(LocalDate.now());
        playerState.setGameID(selectedGame.getGameId());

        // Asegurarse de que los valores sean válidos (p.ej., nivel de vida no negativo)
        if (playerState.getLifeLevel() < 0) {
            playerState.setLifeLevel(0);
        }

        // Mostrar el nuevo estado del jugador después de la simulación
        System.out.println("Nuevo estado del jugador después de la partida:");
        System.out.println(playerState);

        // Guardar los cambios en la base de datos SQLite
        if (daoSQLite.updatePlayerState(playerState)) {
            System.out.println("El progreso ha sido guardado en SQLite.");
        } else {
            System.out.println("Hubo un error al guardar el progreso en SQLite.");
        }

        // Registrar la sesión en la base de datos correspondiente (MySQL o PostgreSQL)
        int experienceGained = playerState.getExperience() - initialExperience;
        int lifeLevelChange = playerState.getLifeLevel() - initialLifeLevel;
        int coinsGained = playerState.getCoins() - initialCoins;

        if (daoMySQL.isActive()) {
            // Si MySQL está activo, guardar la sesión en MySQL
            boolean sessionSaved = daoMySQL.saveGameSession(
                    selectedGame.getGameId(),
                    playerId,
                    experienceGained,
                    lifeLevelChange,
                    coinsGained,
                    LocalDate.now()
            );

            if (sessionSaved) {
                System.out.println("La sesión de juego ha sido registrada en MySQL.");
            } else {
                System.out.println("Hubo un error al registrar la sesión de juego en MySQL.");
            }
        } else if (daoPostgreSQL.isActive()) {
            // Si PostgreSQL está activo, guardar la sesión en PostgreSQL
            boolean sessionSaved = daoPostgreSQL.saveGameSession(
                    selectedGame.getGameId(),
                    playerId,
                    experienceGained,
                    lifeLevelChange,
                    coinsGained,
                    LocalDate.now()
            );

            if (sessionSaved) {
                System.out.println("La sesión de juego ha sido registrada en PostgreSQL.");
            } else {
                System.out.println("Hubo un error al registrar la sesión de juego en PostgreSQL.");
            }
        } else {
            System.out.println("No se ha podido conectar a la base de datos.");
        }
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

    private static int obtenerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
            }
        }
    }

    private static String obtenerCadenaNoVacia(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("La entrada no puede estar vacía. Intente nuevamente.");
        }
    }

    private static boolean obtenerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (si/no): ");
            String entrada = scanner.nextLine().trim().toLowerCase();
            if (entrada.equals("si")) return true;
            if (entrada.equals("no")) return false;
            System.out.println("Entrada inválida. Por favor, ingrese 'si' o 'no'.");
        }
    }

}
