package de.Flugbuchungssystem.repository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.Flugbuchungssystem.exception.*;
import de.Flugbuchungssystem.interfaces.*;
import de.Flugbuchungssystem.model.*;

/**
 * Verwaltet alle Flug-Objekte der Anwendung in einer internen Liste.
 * Ist der einzige Ort, an dem Flüge gespeichert werden.
 */
public class FlugRepository implements IFlugRepository, Serializable {
	
	private static final long serialVersionUID = 1L;
    private ArrayList<Flug> fluege;

    /**
     * Erstellt ein neues, leeres FlugRepository.
     */
    public FlugRepository() {
        this.fluege = new ArrayList<>();
    }

    /**
     * Fügt einen neuen Flug in das Repository ein.
     * @param flug der hinzuzufügende Flug
     */
    @Override
    public void addFlug(Flug flug) {
        fluege.add(flug);
    }

    /**
     * @return Liste aller Flüge, leer wenn keine vorhanden
     */
    @Override
    public List<Flug> getAlleFluege() {
        return fluege;
    }

    /**
     * Sucht einen Flug anhand seiner Flugnummer.
     * @param flugnummer die eindeutige Flugnummer, z.B. „LH400"
     * @return der gefundene Flug
     * @throws FlugNichtVerfuegbarException wenn kein Flug mit dieser Nummer existiert
     */
    @Override
    public Flug findeFlug(String flugnummer) {
        for (Flug flug : fluege) {
            if (flug.getFlugnummer().equals(flugnummer)) {
                return flug;
            }
        }
        throw new FlugNichtVerfuegbarException(flugnummer);
    }

    /**
     * Löscht einen Flug anhand seiner Flugnummer aus dem Repository.
     * @param flugnummer die Flugnummer des zu löschenden Flugs
     * @throws FlugNichtVerfuegbarException wenn kein Flug mit dieser Nummer existiert
     */
    @Override
    public void loescheFlug(String flugnummer) {
        Flug zuLoeschen = findeFlug(flugnummer);
        fluege.remove(zuLoeschen);
    }
    
    /**
     * Filtert die gesamte Flugliste anhand der Fluggesellschaft des jeweiligen Fluges.
     * @param airline die gesuchte Fluggesellschaft
     * @return Liste aller Flüge dieser Gesellschaft, leer wenn keine vorhanden
     */
    @Override
    public List<Flug> getFluegeVonAirline(Fluggesellschaft airline) {
        List<Flug> ergebnis = new ArrayList<>();
        for (Flug flug : fluege) {
            if (flug.getAirline().equals(airline)) {
                ergebnis.add(flug);
            }
        }
        return ergebnis;
    }
}