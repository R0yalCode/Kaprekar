package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Registro de una ejecución de Kaprekar.
 */
public class RunRecord {
    private final int seed;
    private final int iterations; // -1 if not reached
    private final boolean reached;
    private final LocalDateTime timestamp;

    public RunRecord(int seed, int iterations) {
        this.seed = seed;
        this.iterations = iterations;
        this.reached = (iterations > 0); // consider >0 as success; -1 or 0 => not reached
        this.timestamp = LocalDateTime.now();
    }

    public int getSeed() { return seed; }
    public int getIterations() { return iterations; }
    public boolean isReached() { return reached; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String seedStr() {
        return String.format("%04d", seed);
    }
}
