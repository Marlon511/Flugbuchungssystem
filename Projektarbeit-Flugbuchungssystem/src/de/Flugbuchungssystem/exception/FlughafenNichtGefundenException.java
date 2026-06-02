package de.Flugbuchungssystem.exception;

/**
 * Wird geworfen, wenn ein Flughafen mit dem angegebenen IATA-Code nicht gefunden wird.
 * Verhindert {@code NullPointerException} bei ungültigen Flughafencodes.
 */
public class FlughafenNichtGefundenException extends RuntimeException {

    /**
     * Erstellt eine neue Ausnahme mit einer Nachricht, die den IATA-Code enthält.
     * @param iataCode der nicht gefundene IATA-Code, z.B. „FRA"
     */
    public FlughafenNichtGefundenException(String iataCode) {
        super("Flughafen " + iataCode + " nicht gefunden.");
    }
}