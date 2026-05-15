package de.Flugbuchungssystem.exception;

/**
 * Wird geworfen, wenn ein angeforderter Sitz bereits belegt ist.
 * Tritt auf, wenn {@code BuchungsService} versucht, einen belegten Sitz zu buchen.
 */
public class SitzNichtVerfuegbarException extends RuntimeException {

    /**
     * Erstellt eine neue Ausnahme mit einer Nachricht, die die Sitznummer enthält.
     *
     * @param sitznummer die Sitznummer des bereits belegten Sitzes, z.B. „A3"
     */
    public SitzNichtVerfuegbarException(String sitznummer) {
        super("Sitz " + sitznummer + " ist bereits belegt.");
    }
}