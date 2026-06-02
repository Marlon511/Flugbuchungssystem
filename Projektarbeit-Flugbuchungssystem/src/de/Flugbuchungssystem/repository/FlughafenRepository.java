package de.Flugbuchungssystem.repository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.Flugbuchungssystem.exception.FlughafenNichtGefundenException;
import de.Flugbuchungssystem.model.Flughafen;
import de.Flugbuchungssystem.service.interfaces.IFlughafenRepository;

/**
 * Verwaltet alle Flughafen-Objekte der Anwendung in einer internen Liste.
 * Ist der einzige Ort, an dem Flughäfen gespeichert werden.
 * Ermöglicht die Suche nach IATA-Code.
 * Enthält keine Geschäftslogik — nur Datenhaltung.
 */
public class FlughafenRepository implements IFlughafenRepository, Serializable {
	
	private static final long serialVersionUID = 1L;

    /** Interne Liste aller gespeicherten Flughäfen. */
    private ArrayList<Flughafen> flughaefen;

    /**
     * Erstellt ein neues, leeres FlughafenRepository.
     */
    public FlughafenRepository() {
        this.flughaefen = new ArrayList<>();
    }

    /**
     * Fügt einen neuen Flughafen in das Repository ein.
     *
     * @param flughafen der hinzuzufügende Flughafen
     */
    @Override
    public void addFlughafen(Flughafen flughafen) {
        flughaefen.add(flughafen);
    }

    /**
     * Gibt alle gespeicherten Flughäfen zurück.
     *
     * @return Liste aller Flughäfen, leer wenn keine vorhanden
     */
    @Override
    public List<Flughafen> getAlleFlughaefen() {
        return flughaefen;
    }

    /**
     * Sucht einen Flughafen anhand seines IATA-Codes.
     * Der Vergleich erfolgt case-insensitiv.
     *
     * @param iataCode der dreistellige IATA-Code, z.B. „FRA"
     * @return der gefundene Flughafen
     * @throws FlughafenNichtGefundenException wenn kein Flughafen mit diesem Code existiert
     */
    @Override
    public Flughafen findeNachCode(String iataCode) {
        for (Flughafen flughafen : flughaefen) {
            if (flughafen.getIataCode().equalsIgnoreCase(iataCode)) {
                return flughafen;
            }
        }
        throw new FlughafenNichtGefundenException(iataCode);
    }
}