package de.Flugbuchungssystem.exception;

/**
 * Wird geworfen, wenn ein Flug nicht existiert oder keine freien Plätze hat.
 * Tritt z.B. beim Umbuchen auf einen vollen Flug auf.
 */
public class FlugNichtVerfuegbarException extends RuntimeException {

    /**
     * Erstellt eine neue Ausnahme mit einer Nachricht, die die Flugnummer enthält.
     *
     * @param flugnummer die Flugnummer des nicht verfügbaren Flugs, z.B. „LH400"
     */
    public FlugNichtVerfuegbarException(String flugnummer) {
        super("Flug " + flugnummer + " hat keine freien Plätze.");
    }
}