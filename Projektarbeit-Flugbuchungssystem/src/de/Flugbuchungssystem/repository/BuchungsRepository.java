package de.Flugbuchungssystem.repository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.Flugbuchungssystem.model.Buchung;
import de.Flugbuchungssystem.exception.BuchungNichtGefundenException;
import de.Flugbuchungssystem.interfaces.IBuchungsRepository;

/**
 * Verwaltet alle Buchung-Objekte in einer HashMap.
 * Der Schlüssel ist die Buchungsnummer, dadurch ist der Zugriff in O(1) möglich.
 */
public class BuchungsRepository implements IBuchungsRepository, Serializable {
	
	private static final long serialVersionUID = 1L;
    private HashMap<String, Buchung> buchungen;
    /**
     * Erstellt ein neues, leeres BuchungsRepository.
     */
    public BuchungsRepository() {
        this.buchungen = new HashMap<>();
    }

    /**
     * Speichert eine neue Buchung im Repository.
     * Als Schlüssel wird die Buchungsnummer der Buchung verwendet.
     * @param buchung die zu speichernde Buchung
     */
    @Override
    public void addBuchung(Buchung buchung) {
        buchungen.put(buchung.getBuchungsnummer(), buchung);
    }

    /**
     * Sucht eine Buchung anhand ihrer Buchungsnummer.
     * @param buchungsnummer die eindeutige Buchungsnummer, z.B. „BU-0001"
     * @return die gefundene Buchung
     * @throws BuchungNichtGefundenException wenn keine Buchung mit dieser Nummer existiert
     */
    @Override
    public Buchung findeBuchung(String buchungsnummer) {
        Buchung buchung = buchungen.get(buchungsnummer);
        if (buchung == null) {
            throw new BuchungNichtGefundenException(buchungsnummer);
        }
        return buchung;
    }

    /**
     * @return Liste aller Buchungen, leer wenn keine vorhanden
     */
    @Override
    public List<Buchung> getAlleBuchungen() {
        return new ArrayList<>(buchungen.values());
    }

    /**
     * Löscht eine Buchung anhand ihrer Buchungsnummer aus dem Repository.
     * @param buchungsnummer die Buchungsnummer der zu löschenden Buchung
     */
    @Override
    public void loescheBuchung(String buchungsnummer) {
        findeBuchung(buchungsnummer);
        buchungen.remove(buchungsnummer);
    }
}
