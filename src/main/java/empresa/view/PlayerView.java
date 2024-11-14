package empresa.view;
import java.util.Scanner;

public class PlayerView {

    private Scanner scanner = new Scanner(System.in);

    public void displayConfigMenu() {
        int option;
        do {
            System.out.println("---- Configuración Inicial y Sincronización ----");
            System.out.println("1. Configurar Conexión con el Servidor");
            System.out.println("2. Verificar Credenciales del Jugador");
            System.out.println("3. Guardar Configuración");
            System.out.println("4. Volver");
            System.out.print("Selecciona una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    configureConnection();
                    break;
                case 2:
                    verifyPlayerCredentials();
                    break;
                case 3:
                    savePlayerConfig();
                    break;
                case 4:
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (option != 4);
    }

    private void configureConnection() {
        System.out.print("Introduce la IP o URL del servidor: ");
        String host = scanner.nextLine();
        System.out.print("Introduce el puerto: ");
        String port = scanner.nextLine();
        System.out.print("Introduce el usuario: ");
        String user = scanner.nextLine();
        System.out.print("Introduce la contraseña: ");
        String pass = scanner.nextLine();
        System.out.println("Conexión configurada con éxito.");
    }

    private void verifyPlayerCredentials() {
        System.out.print("Introduce tu nombre de usuario: ");
        String playerName = scanner.nextLine();
        System.out.print("Introduce tu contraseña: ");
        String password = scanner.nextLine();
        // Lógica de verificación de credenciales
        System.out.println("Credenciales verificadas.");
    }

    private void savePlayerConfig() {
        System.out.println("Guardando configuración...");
        // Lógica para guardar configuración del jugador
        System.out.println("Configuración guardada con éxito.");
    }
}
