package de.Flugbuchungssystem.interfaces;

import java.util.List;
import de.Flugbuchungssystem.model.Flughafen;

/**
 * Legt den Vertrag für den Datenzugriff auf Flughäfen fest.
 */
public interface IFlughafenRepository {

    /**
     * Fügt einen neuen Flughafen in das Repository ein.
     * @param flughafen der hinzuzufügende Flughafen
     */
    void addFlughafen(Flughafen flughafen);

    /**
     * @return Liste aller Flughäfen, leer wenn keine vorhanden
     */
    List<Flughafen> getAlleFlughaefen();

    /**
     * Sucht einen Flughafen anhand seines IATA-Codes.
     * @param iataCode der dreistellige IATA-Code, z.B. „FRA"
     * @return der gefundene {@link Flughafen}
     * @throws de.Flugbuchungssystem.exception.FlughafenNichtGefundenException wenn kein Flughafen mit diesem Code existiert
     */
    Flughafen findeNachCode(String iataCode);
}