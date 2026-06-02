package de.Flugbuchungssystem.service.interfaces;

import de.Flugbuchungssystem.model.*;

/**
 * Legt den Vertrag für alle Buchungsoperationen fest.
 */
public interface IBuchungsService {

    /**
     * Bucht einen Flug für einen Passagier auf dem angegebenen Sitz.
     * Prüft die Verfügbarkeit des Sitzes, berechnet den Preis und legt die Buchung an.
     *
     * @param passagier = der buchende Passagier
     * @param flug = der zu buchende Flug
     * @param sitznummer = die gewünschte Sitznummer, z.B. „A3"
     * @param gepaeckAnzahl = die Anzahl der aufzugebenden Gepäckstücke
     * @return die erstellte {@link Buchung}
     * @throws de.Flugbuchungssystem.exception.SitzNichtVerfuegbarException wenn der Sitz bereits belegt ist
     */
    Buchung bucheFlug(Passagier passagier, Flug flug, String sitznummer, int gepaeckAnzahl);

    /**
     * Storniert eine bestehende Buchung anhand der Buchungsnummer.
     * Berechnet die Stornogebühr und gibt den Rückerstattungsbetrag zurück.
     * @param buchungsnummer = die Buchungsnummer der zu stornierenden Buchung
     * @return der Rückerstattungsbetrag in Euro
     * @throws de.Flugbuchungssystem.exception.BuchungNichtGefundenException wenn die Buchung nicht existiert
     */
    double storniere(String buchungsnummer);
}