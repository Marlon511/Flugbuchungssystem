package de.Flugbuchungssystem.service;

import de.Flugbuchungssystem.model.Buchung;
import de.Flugbuchungssystem.model.Buchungsstatus;
import de.Flugbuchungssystem.exception.SitzNichtVerfuegbarException;
import de.Flugbuchungssystem.exception.UngueltigeSitznummerException;
import de.Flugbuchungssystem.model.Flug;
import de.Flugbuchungssystem.model.Sitz;
import de.Flugbuchungssystem.service.interfaces.IBuchungsRepository;
import de.Flugbuchungssystem.service.interfaces.IUmbuchungsService;

/**
 * Zuständig für Umbuchungen bestehender Buchungen auf einen neuen Flug.
 * Prüft die Verfügbarkeit des neuen Sitzes, berechnet die Preisdifferenz
 * und aktualisiert die Buchung entsprechend.
 */
public class UmbuchungsService implements IUmbuchungsService {

    /** Zugriff auf die gespeicherten Buchungen. */
    private IBuchungsRepository buchungsRepo;

    /** Zuständig für die Preisberechnung. */
    private PreisService preisService;

    /**
     * Erstellt einen neuen UmbuchungsService.
     *
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
     *
     * @param buchungsnummer  die Buchungsnummer der umzubuchenden Buchung
     * @param neuerFlug       der neue Zielflug
     * @param neueSitznummer  die neue Sitznummer, z.B. „B5"
     * @return die Preisdifferenz in Euro (positiv = Aufpreis, negativ = Rückerstattung)
     * @throws UngueltigeSitznummerException  wenn die neue Sitznummer nicht existiert
     * @throws SitzNichtVerfuegbarException   wenn der neue Sitz bereits belegt ist
     * @throws de.Flugbuchungssystem.exception.BuchungNichtGefundenException wenn die Buchung nicht existiert
     */
    @Override
    public double bucheUm(String buchungsnummer, Flug neuerFlug, String neueSitznummer) {
        Buchung buchung = buchungsRepo.findeBuchung(buchungsnummer);

        Sitz neuerSitz = neuerFlug.getFlugzeug().findeSitz(neueSitznummer);
        if (neuerSitz == null) {
            throw new UngueltigeSitznummerException(neueSitznummer);
        }
        if (neuerSitz.isBelegt()) {
            throw new SitzNichtVerfuegbarException(neueSitznummer);
        }

        double alterPreis = buchung.getGesamtpreis();
        double neuerPreis = preisService.berechnePreis(neuerFlug, neuerSitz.getKategorie(), buchung.getGepaeckAnzahl());

        // Alten Sitz freigeben, neuen Sitz belegen
        buchung.getSitz().freigeben();
        neuerSitz.belegen();

        // Buchung aktualisieren
        buchung.setFlug(neuerFlug);
        buchung.setSitz(neuerSitz);
        buchung.setStatus(Buchungsstatus.UMGEBUCHT);
        buchung.setGesamtpreis(neuerPreis);

        return neuerPreis - alterPreis;
    }
}
