package util;

import java.io.*;
import java.util.*;

/**
 * Utilidad para cargar números de 4 dígitos desde un archivo de texto.
 * Cada línea debe contener un número (0000–9999).
 */
public class FileLoader {

    public static List<Integer> loadSeeds(String path) throws IOException {
        List<Integer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                try {
                    int v = Integer.parseInt(line);
                    if (v >= 0 && v <= 9999) {
                        list.add(v);
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        return list;
    }
}
