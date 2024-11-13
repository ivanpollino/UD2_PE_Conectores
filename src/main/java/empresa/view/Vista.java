package empresa.view;

import java.time.LocalDate;
import java.util.Scanner;

public class Vista {
    private Scanner scanner = new Scanner(System.in);
    private int opcion;

    public int mainMenu(){
        int opcion = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.println("\n====================");
                System.out.println("   MENÚ PRINCIPAL");
                System.out.println("====================");
                System.out.println("1. Gestion de videojuegos");
                System.out.println("2. Gestion de jugadores");
                System.out.println("3. Gestion de partidas");
                System.out.println("4. Salir");
                System.out.println("====================");
                System.out.print("¿Que quieres gestionar?: ");
                opcion = Integer.parseInt(scanner.nextLine());

                // Verifica que la opción esté dentro del rango
                if (opcion >= 1 && opcion <= 4) {
                    entradaValida = true;
                } else {
                    System.out.println("\n[ERROR] Opción inválida. Debes elegir una opción entre 1 y 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR] Debes introducir un número válido.");
            }
        }

        return opcion;
    }


}
