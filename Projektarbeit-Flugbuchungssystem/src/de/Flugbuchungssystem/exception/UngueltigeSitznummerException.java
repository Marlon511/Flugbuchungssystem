package de.Flugbuchungssystem.exception;

/**
 * Wird geworfen, wenn eine eingegebene Sitznummer keine gültige Sitznummer ist.
 * Nützlich zur Validierung von Nutzereingaben, bevor nach dem Sitz gesucht wird.
 */
public class UngueltigeSitznummerException extends IllegalArgumentException {

    /**
     * Erstellt eine neue Ausnahme mit einer Nachricht, die die ungültige Eingabe enthält.
     * @param eingabe die ungültige Sitznummer-Eingabe, z.B. „X99"
     */
    public UngueltigeSitznummerException(String eingabe) {
        super("'" + eingabe + "' ist keine gültige Sitznummer.");
    }
}