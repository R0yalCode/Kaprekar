package service;

import model.KaprekarNumber;
import model.RunRecord;
import util.ConsoleColors;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementa la rutina de Kaprekar y mantiene el historial de ejecución.
 */
public class KaprekarService {

    public static final int TARGET = 6174;
    public static final int MAX_ITER = 500;
    private long delay;

    
    private final List<RunRecord> history = new CopyOnWriteArrayList<>();

    public KaprekarService(long delayMs) {
        this.delay = Math.max(delayMs, 0);
    }

    private void pause() {
        if (delay <= 0) return;
        try { Thread.sleep(delay); }
        catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
    }

    /**
     * Ejecuta la rutina para una semilla y añade el registro al historial.
     * Retorna las iteraciones usadas o -1 si no se alcanzó.
     */
    public int runForSeed(KaprekarNumber seed) {
        System.out.println(ConsoleColors.CYAN +
                "Semilla inicial: " + seed.toPaddedString() +
                ConsoleColors.RESET);

        if (seed.isRepdigit()) {
            System.out.println(ConsoleColors.RED +
                    "Número inválido (todos los dígitos iguales)." +
                    ConsoleColors.RESET);
            RunRecord recInvalid = new RunRecord(seed.getValue(), -1);
            history.add(recInvalid);
            return -1;
        }

        int current = seed.getValue();
        int iter = 0;

        while (iter < MAX_ITER) {
            iter++;

            KaprekarNumber k = new KaprekarNumber(current);
            int desc = k.toDescending();
            int asc  = k.toAscending();
            int diff = desc - asc;

            System.out.println(ConsoleColors.RED +
                    "[" + iter + "] Descendente: " + String.format("%04d", desc) +
                    ConsoleColors.RESET);
            pause();

            System.out.println(ConsoleColors.GREEN +
                    "    Ascendente : " + String.format("%04d", asc) +
                    ConsoleColors.RESET);
            pause();

            System.out.println(ConsoleColors.BLUE +
                    "    Resta      : " +
                    String.format("%04d ( %04d - %04d )", diff, desc, asc) +
                    ConsoleColors.RESET);
            pause();

            if (diff == TARGET) {
                System.out.println(ConsoleColors.WHITE+
                        " => Llegó a 6174 en " + iter + " iteraciones." +
                        ConsoleColors.RESET);
                RunRecord rec = new RunRecord(seed.getValue(), iter);
                history.add(rec);
                return iter;
            }

            if (diff == current) {
                // ciclo sin llegar a 6174
                System.out.println(ConsoleColors.RED +
                        "=> Bucle detectado sin alcanzar 6174." +
                        ConsoleColors.RESET);
                RunRecord recLoop = new RunRecord(seed.getValue(), -1);
                history.add(recLoop);
                return -1;
            }

            current = diff;
        }

        System.out.println(ConsoleColors.RED +
                "No alcanzó 6174 en " + MAX_ITER + " iteraciones. Semilla: " +
                seed.toPaddedString() + ConsoleColors.RESET);

        RunRecord recTimeout = new RunRecord(seed.getValue(), -1);
        history.add(recTimeout);
        return -1;
    }

    /**
     * Genera n semillas únicas (no repdigit).
     */
    public static int[] generateUniqueSeeds(int n) {
        Set<Integer> set = new HashSet<>();
        Random rnd = new Random();

        while (set.size() < n) {
            int v = rnd.nextInt(10000);
            KaprekarNumber k = new KaprekarNumber(v);
            if (!k.isRepdigit()) set.add(v);
        }
        return set.stream().mapToInt(i -> i).toArray();
    }


    /**
     * Obtiene una copia del historial (orden cronológico, más reciente al final).
     */
    public List<RunRecord> getHistory() {
        return new ArrayList<>(history);
    }

    /**
     * Limpia el historial.
     */
    public void clearHistory() {
        history.clear();
    }

    /**
     * Imprime el historial formateado en consola.
     */
    public void printHistory() {
        List<RunRecord> h = getHistory();
        if (h.isEmpty()) {
            System.out.println(ConsoleColors.CYAN + "Historial vacío." + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.PURPLE + "\n==== HISTORIAL DE EJECUCIONES ====" + ConsoleColors.RESET);
        System.out.printf("%-8s %-12s %-12s %-20s%n", "Semilla", "Iteraciones", "Llegó 6174", "Fecha/Hora");
        System.out.println("-----------------------------------------------------------");
        for (RunRecord r : h) {
            String seed = r.seedStr();
            String iter = (r.getIterations() < 0) ? "-" : String.valueOf(r.getIterations());
            String ok   = r.isReached() ? "SI" : "NO";
            String time = r.getFormattedTimestamp();

            System.out.printf("%-8s %-12s %-12s %-20s%n", seed, iter, ok, time);
        }
        System.out.println("-----------------------------------------------------------\n");
    }
}
