package de.Flugbuchungssystem.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.Flugbuchungssystem.model.Buchung;
import de.Flugbuchungssystem.exception.BuchungNichtGefundenException;
import de.Flugbuchungssystem.service.interfaces.IBuchungsRepository;

/**
 * Verwaltet alle Buchung-Objekte der Anwendung in einer internen HashMap.
 * Der Schlüssel ist die Buchungsnummer, dadurch ist der Zugriff in O(1) möglich.
 * Enthält keine Geschäftslogik --> nur Datenhaltung.
 */
public class BuchungsRepository implements IBuchungsRepository {

    /** Interne Zuordnung von Buchungsnummer zu Buchung. */
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
     *
     * @param buchung die zu speichernde Buchung
     */
    @Override
    public void addBuchung(Buchung buchung) {
        buchungen.put(buchung.getBuchungsnummer(), buchung);
    }

    /**
     * Sucht eine Buchung anhand ihrer Buchungsnummer.
     *
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
     * Gibt alle gespeicherten Buchungen als Liste zurück.
     *
     * @return Liste aller Buchungen, leer wenn keine vorhanden
     */
    @Override
    public List<Buchung> getAlleBuchungen() {
        return new ArrayList<>(buchungen.values());
    }

    /**
     * Löscht eine Buchung anhand ihrer Buchungsnummer aus dem Repository.
     *
     * @param buchungsnummer die Buchungsnummer der zu löschenden Buchung
     * @throws BuchungNichtGefundenException wenn keine Buchung mit dieser Nummer existiert
     */
    @Override
    public void loescheBuchung(String buchungsnummer) {
        findeBuchung(buchungsnummer);
        buchungen.remove(buchungsnummer);
    }
}
