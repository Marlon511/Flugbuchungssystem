package de.Flugbuchungssystem.exception;

/**
 * Wird geworfen, wenn eine Buchung mit der angegebenen Buchungsnummer nicht gefunden wird.
 * Verhindert {@code NullPointerException} bei ungültigen Buchungsnummern.
 */
public class BuchungNichtGefundenException extends RuntimeException {
    /**
     * Erstellt eine neue Ausnahme mit einer Nachricht, die die Buchungsnummer enthält.
     * @param buchungsnummer die nicht gefundene Buchungsnummer, z.B. „BU-0042"
     */
    public BuchungNichtGefundenException(String buchungsnummer) {
        super("Buchung " + buchungsnummer + " nicht gefunden.");
    }
}