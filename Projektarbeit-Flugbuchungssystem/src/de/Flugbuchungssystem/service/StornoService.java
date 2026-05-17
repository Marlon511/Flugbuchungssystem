package de.Flugbuchungssystem.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import de.Flugbuchungssystem.model.Buchung;
import de.Flugbuchungssystem.model.Buchungsstatus;
import de.Flugbuchungssystem.service.interfaces.IBuchungsRepository;

/**
 * Enthält die Stornogebühren-Logik der Anwendung.
 * Berechnet die Gebühr abhängig vom Zeitabstand zum Abflug,
 * gibt den Sitz frei und setzt den Status auf STORNIERT.
 */
public class StornoService {

    /** Zugriff auf die gespeicherten Buchungen. */
    private IBuchungsRepository buchungsRepo;

    /**
     * Erstellt einen neuen StornoService.
     *
     * @param buchungsRepo das Repository für den Zugriff auf Buchungen
     */
    public StornoService(IBuchungsRepository buchungsRepo) {
        this.buchungsRepo = buchungsRepo;
    }

    /**
     * Storniert eine Buchung anhand der Buchungsnummer.
     * Gibt den Sitz frei, setzt den Status auf STORNIERT und
     * gibt den Rückerstattungsbetrag zurück.
     *
     * @param buchungsnummer die Buchungsnummer der zu stornierenden Buchung
     * @return der Rückerstattungsbetrag in Euro
     * @throws de.Flugbuchungssystem.exception.BuchungNichtGefundenException wenn die Buchung nicht existiert
     */
    public double storniere(String buchungsnummer) {
        Buchung buchung = buchungsRepo.findeBuchung(buchungsnummer);
        double rueckerstattung = berechneRueckerstattung(buchung);
        buchung.getSitz().freigeben();
        buchung.setStatus(Buchungsstatus.STORNIERT);
        return rueckerstattung;
    }

    /**
     * Berechnet die Stornogebühr abhängig vom Zeitabstand zum Abflug.
     * Staffelung:
     * <ul>
     *   <li>Mehr als 30 Tage vor Abflug: 10 % des Gesamtpreises</li>
     *   <li>7 bis 30 Tage vor Abflug: 25 % des Gesamtpreises</li>
     *   <li>Weniger als 7 Tage vor Abflug: 50 % des Gesamtpreises</li>
     * </ul>
     *
     * @param buchung die zu stornierende Buchung
     * @return die Stornogebühr in Euro
     */
    public double berechneStornogebuehr(Buchung buchung) {
        LocalDate abflugdatum = buchung.getFlug().getAbflugzeit().toLocalDate();
        long tageVerbleibend = ChronoUnit.DAYS.between(LocalDate.now(), abflugdatum);

        if (tageVerbleibend > 30) {
            return buchung.getGesamtpreis() * 0.10;
        } else if (tageVerbleibend >= 7) {
            return buchung.getGesamtpreis() * 0.25;
        } else {
            return buchung.getGesamtpreis() * 0.50;
        }
    }

    /**
     * Berechnet den Rückerstattungsbetrag nach Abzug der Stornogebühr.
     *
     * @param buchung die zu stornierende Buchung
     * @return der Rückerstattungsbetrag in Euro
     */
    public double berechneRueckerstattung(Buchung buchung) {
        return buchung.getGesamtpreis() - berechneStornogebuehr(buchung);
    }
}