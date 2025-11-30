package model;

import java.util.Arrays;

/**
 * Representa un número de 4 dígitos utilizado por la rutina de Kaprekar.
 */
public class KaprekarNumber {

    private final int value;

    public KaprekarNumber(int value) {
        if (value < 0 || value > 9999)
            throw new IllegalArgumentException("Número fuera del rango 0000–9999");
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /** Returns digits as [d0,d1,d2,d3] */
    public int[] digits() {
        int[] d = new int[4];
        int v = value;
        for (int i = 3; i >= 0; i--) {
            d[i] = v % 10;
            v /= 10;
        }
        return d;
    }

    /** Checks if all digits are identical (repdigit) */
    public boolean isRepdigit() {
        int[] d = digits();
        return d[0] == d[1] && d[1] == d[2] && d[2] == d[3];
    }

    /** Integer sorted descending */
    public int toDescending() {
        int[] d = digits();
        Arrays.sort(d);
        int out = 0;
        for (int i = 3; i >= 0; i--) out = out * 10 + d[i];
        return out;
    }

    /** Integer sorted ascending */
    public int toAscending() {
        int[] d = digits();
        Arrays.sort(d);
        int out = 0;
        for (int i = 0; i < 4; i++) out = out * 10 + d[i];
        return out;
    }

    public String toPaddedString() {
        return String.format("%04d", value);
    }

    @Override
    public String toString() {
        return toPaddedString();
    }
}
