package de.Flugbuchungssystem.repository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.Flugbuchungssystem.exception.FlughafenNichtGefundenException;
import de.Flugbuchungssystem.model.Flughafen;
import de.Flugbuchungssystem.service.interfaces.IFlughafenRepository;

/**
 * Verwaltet alle Flughafen-Objekte in einer Liste.
 * Ist der einzige Ort, an dem Flughäfen gespeichert werden.
 * Ermöglicht die Suche nach IATA-Code.
 */
public class FlughafenRepository implements IFlughafenRepository, Serializable {
	
	private static final long serialVersionUID = 1L;
    private ArrayList<Flughafen> flughaefen;

    /**
     * Erstellt ein neues, leeres FlughafenRepository.
     */
    public FlughafenRepository() {
        this.flughaefen = new ArrayList<>();
    }

    /**
     * Fügt einen neuen Flughafen in das Repository ein.
     * @param flughafen der hinzuzufügende Flughafen
     */
    @Override
    public void addFlughafen(Flughafen flughafen) {
        flughaefen.add(flughafen);
    }

    /**
     * @return Liste aller Flughäfen, leer wenn keine vorhanden
     */
    @Override
    public List<Flughafen> getAlleFlughaefen() {
        return flughaefen;
    }

    /**
     * Sucht einen Flughafen anhand seines IATA-Codes.
     * Groß- und Kleinschreibung wird dabei ignoriert.
     * @param iataCode der dreistellige IATA-Code, z.B. „FRA"
     * @return der gefundene Flughafen
     * @throws FlughafenNichtGefundenException wenn kein Flughafen mit diesem Code existiert
     */
    @Override
    public Flughafen findeNachCode(String iataCode) {
        for (Flughafen flughafen : flughaefen) {
        	// equalsIgnoreCase damit z.B. "fra" und "FRA" gleich behandelt werden
            if (flughafen.getIataCode().equalsIgnoreCase(iataCode)) {
                return flughafen;
            }
            
        }
        throw new FlughafenNichtGefundenException(iataCode);
    }
}