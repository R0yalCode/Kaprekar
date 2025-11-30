package view;

import controller.KaprekarController;
import util.ConsoleColors;

import java.util.Scanner;

public class Menu {

    private final KaprekarController controller;
    private final Scanner sc = new Scanner(System.in);

    public Menu(KaprekarController controller) {
        this.controller = controller;
    }

    public void show() {
        int option;
        do {
            System.out.println(ConsoleColors.PURPLE +
                    "\n===== CONSTANTE DE KAPREKAR =====" +
                    ConsoleColors.RESET);
            System.out.println("1. Generar 10 números aleatorios");
            System.out.println("2. Ingresar número manualmente");
            System.out.println("3. Importar desde archivo .txt");
            System.out.println("4. Ajustar velocidad");
            System.out.println("5. Mostrar historial de ejecuciones");
            System.out.println("6. Limpiar historial de ejecuciones");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            option = readInt();
            switch (option) {
                case 1 -> controller.processRandom(10);
                case 2 -> handleManual();
                case 3 -> handleFile();
                case 4 -> handleSpeed();
                case 5 -> controller.showHistory();
                case 6 -> controller.clearHistory();
                case 7 -> System.out.println("Saliendo...");
                default -> System.out.println(ConsoleColors.RED + "Opción inválida" + ConsoleColors.RESET);
            }

        } while (option != 7);
    }

    private void handleManual() {
        System.out.print("Número de 4 dígitos: ");
        int v = readInt();
        controller.processSingle(v);
    }

    private void handleFile() {
        System.out.print("Ruta del archivo: ");
        String path = sc.nextLine();
        controller.processFromFile(path);
    }

    private void handleSpeed() {
        System.out.print("Velocidad en ms (0, 200, 700, 1200): ");
        long ms = readInt();
        controller.updateSpeed(ms);
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
}
