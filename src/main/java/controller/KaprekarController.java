package controller;

import model.KaprekarNumber;
import model.RunRecord;
import service.KaprekarService;
import util.ConsoleColors;
import util.FileLoader;

import java.io.IOException;
import java.util.List;

public class KaprekarController {

    private KaprekarService service;

    public KaprekarController(KaprekarService service) {
        this.service = service;
    }

    public void processSingle(int num) {
        try {
            KaprekarNumber k = new KaprekarNumber(num);
            service.runForSeed(k);
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED +
                    "Número inválido. Ingrese entre 0000 y 9999." +
                    ConsoleColors.RESET);
        }
    }

    public void processFromFile(String path) {
        try {
            List<Integer> list = FileLoader.loadSeeds(path);
            if (list.isEmpty()) {
                System.out.println(ConsoleColors.RED +
                        "Archivo vacío o sin números válidos." +
                        ConsoleColors.RESET);
                return;
            }
            for (int v : list) processSingle(v);

        } catch (IOException e) {
            System.out.println(ConsoleColors.RED +
                    "No se pudo leer el archivo." +
                    ConsoleColors.RESET);
        }
    }

    public void processRandom(int n) {
        int[] seeds = KaprekarService.generateUniqueSeeds(n);
        for (int v : seeds) {
            System.out.println(ConsoleColors.CYAN +
                    "\n--- Semilla aleatoria: " + String.format("%04d", v) +
                    ConsoleColors.RESET);
            processSingle(v);
        }
    }

    public void updateSpeed(long ms) {
        this.service = new KaprekarService(ms);
        System.out.println(ConsoleColors.GREEN +
                "Velocidad actualizada: " + ms + " ms." +
                ConsoleColors.RESET);
    }

     

    public void showHistory() {
        service.printHistory();
    }

    public void clearHistory() {
        service.clearHistory();
        System.out.println(ConsoleColors.GREEN + "Historial limpiado." + ConsoleColors.RESET);
    }
}
