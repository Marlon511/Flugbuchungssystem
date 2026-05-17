package de.Flugbuchungssystem.service;

import de.Flugbuchungssystem.model.Buchung;
import de.Flugbuchungssystem.exception.SitzNichtVerfuegbarException;
import de.Flugbuchungssystem.exception.UngueltigeSitznummerException;
import de.Flugbuchungssystem.model.Flug;
import de.Flugbuchungssystem.model.Sitz;
import de.Flugbuchungssystem.service.interfaces.IBuchungsRepository;
import de.Flugbuchungssystem.service.interfaces.IBuchungsService;
import de.Flugbuchungssystem.model.Passagier;

/**
 * Zuständig für das Erstellen neuer Buchungen und Stornierungen.
 * Prüft Sitzverfügbarkeit, berechnet den Preis und speichert die Buchung.
 * Delegiert Stornierungen an den {@link StornoService}.
 */
public class BuchungsService implements IBuchungsService {

    /** Zugriff auf die gespeicherten Buchungen. */
    private IBuchungsRepository buchungsRepo;

    /** Zuständig für die Preisberechnung. */
    private PreisService preisService;

    /** Zuständig für die Stornierungslogik. */
    private StornoService stornoService;

    /**
     * Erstellt einen neuen BuchungsService mit allen benötigten Abhängigkeiten.
     *
     * @param buchungsRepo  das Repository für den Zugriff auf Buchungen
     * @param preisService  der Service für die Preisberechnung
     * @param stornoService der Service für die Stornierungslogik
     */
    public BuchungsService(IBuchungsRepository buchungsRepo, PreisService preisService, StornoService stornoService) {
        this.buchungsRepo = buchungsRepo;
        this.preisService = preisService;
        this.stornoService = stornoService;
    }

    /**
     * Bucht einen Flug für einen Passagier auf dem angegebenen Sitz.
     * Prüft die Gültigkeit und Verfügbarkeit des Sitzes, berechnet den
     * Gesamtpreis und legt die Buchung im Repository an.
     *
     * @param passagier     der buchende Passagier
     * @param flug          der zu buchende Flug
     * @param sitznummer    die gewünschte Sitznummer, z.B. „A3"
     * @param gepaeckAnzahl die Anzahl der aufzugebenden Gepäckstücke
     * @return die erstellte Buchung
     * @throws UngueltigeSitznummerException  wenn die Sitznummer nicht existiert
     * @throws SitzNichtVerfuegbarException   wenn der Sitz bereits belegt ist
     */
    @Override
    public Buchung bucheFlug(Passagier passagier, Flug flug, String sitznummer, int gepaeckAnzahl) {
        Sitz sitz = flug.getFlugzeug().findeSitz(sitznummer);

        if (sitz == null) {
            throw new UngueltigeSitznummerException(sitznummer);
        }
        if (sitz.isBelegt()) {
            throw new SitzNichtVerfuegbarException(sitznummer);
        }

        double gesamtpreis = preisService.berechnePreis(flug, sitz.getKategorie(), gepaeckAnzahl);
        sitz.belegen();

        Buchung buchung = new Buchung(passagier, flug, sitz, gepaeckAnzahl, gesamtpreis);
        buchungsRepo.addBuchung(buchung);
        return buchung;
    }

    /**
     * Storniert eine bestehende Buchung. Delegiert die Logik an den StornoService.
     *
     * @param buchungsnummer die Buchungsnummer der zu stornierenden Buchung
     * @return der Rückerstattungsbetrag in Euro
     * @throws de.Flugbuchungssystem.exception.BuchungNichtGefundenException wenn die Buchung nicht existiert
     */
    @Override
    public double storniere(String buchungsnummer) {
        return stornoService.storniere(buchungsnummer);
    }
}