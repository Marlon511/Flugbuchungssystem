package de.Flugbuchungssystem.interfaces;

import java.util.List;
import de.Flugbuchungssystem.model.*;

/**
 * Legt den Vertrag für den Datenzugriff auf Buchungen fest.
 */
public interface IBuchungsRepository {
    /**
     * Speichert eine neue Buchung im Repository.
     * @param buchung die zu speichernde Buchung
     */
    void addBuchung(Buchung buchung);

    /**
     * Sucht eine Buchung anhand ihrer Buchungsnummer.
     * @param buchungsnummer die eindeutige Buchungsnummer, z.B. „BU-0001"
     * @return die gefundene {@link Buchung}
     * @throws de.Flugbuchungssystem.exception.BuchungNichtGefundenException wenn keine Buchung mit dieser Nummer existiert
     */
    Buchung findeBuchung(String buchungsnummer);

    /**
     * Gibt alle gespeicherten Buchungen zurück.
     * @return Liste aller Buchungen, leer wenn keine vorhanden
     */
    List<Buchung> getAlleBuchungen();

    /**
     * Löscht eine Buchung anhand ihrer Buchungsnummer aus dem Repository.
     * @param buchungsnummer die Buchungsnummer der zu löschenden Buchung
     * @throws de.Flugbuchungssystem.exception.BuchungNichtGefundenException wenn keine Buchung mit dieser Nummer existiert
     */
    void loescheBuchung(String buchungsnummer);
}