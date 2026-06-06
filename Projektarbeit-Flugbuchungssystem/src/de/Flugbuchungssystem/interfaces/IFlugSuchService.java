package de.Flugbuchungssystem.interfaces;

import java.time.LocalDate;
import java.util.List;
import de.Flugbuchungssystem.model.*;

/**
 * Legt den Vertrag für die Flugsuche fest.
 */
public interface IFlugSuchService {

    /**
     * Sucht alle Flüge, die der angegebenen Route und dem optionalen Datum entsprechen.
     * Wird {@code datum} als {@code null} übergeben, wird nur nach Route gefiltert.
     * @param start das Datum des gewünschten Abflugs, oder {@code null} für alle Daten
     * @param ziel  der gewünschte Zielflughafen
     * @param datum der gewünschte Abflugtag, oder {@code null} für alle Daten
     * @return Liste aller passenden {@link Flug}-Objekte, leer wenn keine gefunden
     */
    List<Flug> sucheFluege(Flughafen start, Flughafen ziel, LocalDate datum);
}
