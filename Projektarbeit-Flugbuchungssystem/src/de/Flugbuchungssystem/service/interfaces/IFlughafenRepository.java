package de.Flugbuchungssystem.service.interfaces;

import java.util.List;
import de.Flugbuchungssystem.model.Flughafen;

/**
 * Legt den Vertrag für den Datenzugriff auf Flughäfen fest.
 * Services und die UI sprechen ausschließlich gegen dieses Interface —
 * nie direkt gegen die konkrete Klasse {@code FlughafenRepository}.
 */
public interface IFlughafenRepository {

    /**
     * Fügt einen neuen Flughafen in das Repository ein.
     *
     * @param flughafen der hinzuzufügende Flughafen
     */
    void addFlughafen(Flughafen flughafen);

    /**
     * Gibt alle gespeicherten Flughäfen zurück.
     *
     * @return Liste aller Flughäfen, leer wenn keine vorhanden
     */
    List<Flughafen> getAlleFlughaefen();

    /**
     * Sucht einen Flughafen anhand seines IATA-Codes.
     *
     * @param iataCode der dreistellige IATA-Code, z.B. „FRA"
     * @return der gefundene {@link Flughafen}
     * @throws de.Flugbuchungssystem.exception.FlughafenNichtGefundenException wenn kein Flughafen mit diesem Code existiert
     */
    Flughafen findeNachCode(String iataCode);
}