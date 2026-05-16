package de.Flugbuchungssystem.repository;

import java.util.ArrayList;
import java.util.List;

import de.Flugbuchungssystem.exception.*;
import de.Flugbuchungssystem.model.*;
import de.Flugbuchungssystem.service.interfaces.*;

/**
 * Verwaltet alle Flug-Objekte der Anwendung in einer internen Liste.
 * Ist der einzige Ort, an dem Flüge gespeichert werden.
 * Enthält keine Geschäftslogik — nur Datenhaltung.
 */
public class FlugRepository implements IFlugRepository {

    /** Interne Liste aller gespeicherten Flüge. */
    private ArrayList<Flug> fluege;

    /**
     * Erstellt ein neues, leeres FlugRepository.
     */
    public FlugRepository() {
        this.fluege = new ArrayList<>();
    }

    /**
     * Fügt einen neuen Flug in das Repository ein.
     *
     * @param flug der hinzuzufügende Flug
     */
    @Override
    public void addFlug(Flug flug) {
        fluege.add(flug);
    }

    /**
     * Gibt alle gespeicherten Flüge zurück.
     *
     * @return Liste aller Flüge, leer wenn keine vorhanden
     */
    @Override
    public List<Flug> getAlleFluege() {
        return fluege;
    }

    /**
     * Sucht einen Flug anhand seiner Flugnummer.
     *
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
     *
     * @param flugnummer die Flugnummer des zu löschenden Flugs
     * @throws FlugNichtVerfuegbarException wenn kein Flug mit dieser Nummer existiert
     */
    @Override
    public void loescheFlug(String flugnummer) {
        Flug zuLoeschen = findeFlug(flugnummer);
        fluege.remove(zuLoeschen);
    }
}