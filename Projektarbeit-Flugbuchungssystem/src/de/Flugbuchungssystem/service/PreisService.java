package de.Flugbuchungssystem.service;

import de.Flugbuchungssystem.model.Flug;
import de.Flugbuchungssystem.model.SitzKategorie;

/**
 * Berechnet den Ticketpreis aus Basispreis, Kategoriemultiplikator und Gepäckgebühr.
 */
public class PreisService {

    /**
     * Erstellt einen neuen PreisService.
     */
    public PreisService() {
    }

    /**
     * Berechnet den Gesamtpreis einer Buchung.
     * Formel: Basispreis × Kategoriemultiplikator + Gepäckgebühr
     * @param flug der gebuchte Flug
     * @param kategorie die gewählte Sitzkategorie
     * @param gepaeckAnzahl die Anzahl der aufgegebenen Gepäckstücke
     * @return der Gesamtpreis in Euro
     */
    public double berechnePreis(Flug flug, SitzKategorie kategorie, int gepaeckAnzahl) {
        double ticketpreis = flug.getBasispreis() * kategorie.getPreisMultiplikator();
        double gepaeckgebuehr = berechneGepaeckgebuehr(gepaeckAnzahl);
        return Math.round((ticketpreis + gepaeckgebuehr) * 100.0) / 100.0;
    }

    /**
     * Berechnet die Gepäckgebühr anhand der Anzahl der Koffer.
     * Das erste Gepäckstück ist kostenlos, ab dem zweiten fallen je 30 € an.
     * @param anzahl die Anzahl der aufgegebenen Gepäckstücke
     * @return die Gepäckgebühr in Euro
     */
    public double berechneGepaeckgebuehr(int anzahl) {
        if (anzahl <= 1) {
            return 0.0;
        }
        return (anzahl - 1) * 30.0;
    }
}