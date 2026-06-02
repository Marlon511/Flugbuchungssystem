package de.Flugbuchungssystem.service.interfaces;

import java.util.List;
import de.Flugbuchungssystem.model.*;

/**
 * Legt den Vertrag für den Datenzugriff auf Flüge fest.
 */
public interface IFlugRepository {

    /**
     * Fügt einen neuen Flug in das Repository ein.
     * @param flug der hinzuzufügende Flug
     */
    void addFlug(Flug flug);

    /**
     * @return Liste aller Flüge, leer wenn keine vorhanden
     */
    List<Flug> getAlleFluege();

    /**
     * Sucht einen Flug anhand seiner Flugnummer.
     * @param flugnummer die eindeutige Flugnummer, z.B. „LH400"
     * @return der gefundene {@link Flug}
     * @throws de.Flugbuchungssystem.exception.FlugNichtVerfuegbarException wenn kein Flug mit dieser Nummer existiert
     */
    Flug findeFlug(String flugnummer);

    /**
     * Löscht einen Flug anhand seiner Flugnummer aus dem Repository.
     * @param flugnummer die Flugnummer des zu löschenden Flugs
     * @throws de.Flugbuchungssystem.exception.FlugNichtVerfuegbarException wenn kein Flug mit dieser Nummer existiert
     */
    void loescheFlug(String flugnummer);
    
    /**
     * @param airline die gesuchte Fluggesellschaft
     * @return Liste aller Flüge dieser Gesellschaft, leer wenn keine vorhanden
     */
    List<Flug> getFluegeVonAirline(Fluggesellschaft airline);
}