package de.Flugbuchungssystem.service;

import de.Flugbuchungssystem.model.*;
import de.Flugbuchungssystem.exception.*;
import de.Flugbuchungssystem.interfaces.*;
import de.Flugbuchungssystem.model.*;

/**
 * Zuständig für Umbuchungen bestehender Buchungen auf einen neuen Flug.
 * Prüft die Verfügbarkeit des neuen Sitzes, berechnet die Preisdifferenz und aktualisiert die Buchung entsprechend.
 */
public class UmbuchungsService implements IUmbuchungsService {
    private IBuchungsRepository buchungsRepo;
    private PreisService preisService;

    /**
     * Erstellt einen neuen UmbuchungsService.
     * @param buchungsRepo das Repository für den Zugriff auf Buchungen
     * @param preisService der Service für die Preisberechnung
     */
    public UmbuchungsService(IBuchungsRepository buchungsRepo, PreisService preisService) {
        this.buchungsRepo = buchungsRepo;
        this.preisService = preisService;
    }

    /**
     * Bucht eine bestehende Buchung auf einen neuen Flug und Sitz um.
     * Gibt den alten Sitz frei, belegt den neuen und aktualisiert Status und Preis.
     * Ein positiver Rückgabewert bedeutet Aufpreis, ein negativer Rückerstattung.
     * <p>
     * Eine Umbuchung ist nur möglich, wenn die Buchung den Status
     * {@link Buchungsstatus#GEBUCHT} oder {@link Buchungsstatus#UMGEBUCHT} hat.
     * Stornierte Buchungen werden abgelehnt.
     * </p>
     *
     * @param buchungsnummer = die Buchungsnummer der umzubuchenden Buchung
     * @param neuerFlug = der neue Zielflug
     * @param neueSitznummer = die neue Sitznummer, z.B. „B5"
     * @return die Preisdifferenz in Euro (positiv = Aufpreis, negativ = Rückerstattung)
     * @throws IllegalStateException wenn die Buchung bereits storniert ist
     * @throws UngueltigeSitznummerException wenn die neue Sitznummer nicht existiert
     * @throws SitzNichtVerfuegbarException wenn der neue Sitz bereits belegt ist
     * @throws de.Flugbuchungssystem.exception.BuchungNichtGefundenException wenn die Buchung nicht existiert
     */
    @Override
    public double bucheUm(String buchungsnummer, Flug neuerFlug, String neueSitznummer) {
        Buchung buchung = buchungsRepo.findeBuchung(buchungsnummer);

        if (buchung.getStatus() == Buchungsstatus.STORNIERT) {
            throw new IllegalStateException(
                "Buchung " + buchungsnummer + " ist storniert und kann nicht umgebucht werden.");
        }

        Sitz neuerSitz = neuerFlug.getFlugzeug().findeSitz(neueSitznummer);
        if (neuerSitz == null) {
            throw new UngueltigeSitznummerException(neueSitznummer);
        }
        if (neuerSitz.isBelegt()) {
            throw new SitzNichtVerfuegbarException(neueSitznummer);
        }

        double alterPreis = buchung.getGesamtpreis();
        double neuerPreis = preisService.berechnePreis(neuerFlug, neuerSitz.getKategorie(), buchung.getGepaeckAnzahl());

        buchung.getSitz().freigeben();
        neuerSitz.belegen();

        buchung.setFlug(neuerFlug);
        buchung.setSitz(neuerSitz);
        buchung.setStatus(Buchungsstatus.UMGEBUCHT);
        buchung.setGesamtpreis(neuerPreis);

        return Math.round((neuerPreis - alterPreis) * 100.0) / 100.0;
    }
}