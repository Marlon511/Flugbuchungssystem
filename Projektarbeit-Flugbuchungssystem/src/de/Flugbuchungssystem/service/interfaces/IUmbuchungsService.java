package de.Flugbuchungssystem.service.interfaces;

import de.Flugbuchungssystem.model.*;

/**
 * Legt den Vertrag für Umbuchungsoperationen fest.
 * Durch dieses Interface kann die Umbuchungslogik im Test durch
 * eine einfache Fake-Implementierung ersetzt werden.
 */
public interface IUmbuchungsService {

    /**
     * Bucht eine bestehende Buchung auf einen neuen Flug und Sitz um.
     * Berechnet die Preisdifferenz zwischen alter und neuer Buchung.
     * Ein positiver Rückgabewert bedeutet Aufpreis, ein negativer Rückerstattung.
     *
     * @param buchungsnummer  die Buchungsnummer der umzubuchenden Buchung
     * @param neuerFlug       der neue Zielflug
     * @param neueSitznummer  die neue Sitznummer, z.B. „B5"
     * @return die Preisdifferenz in Euro (positiv = Aufpreis, negativ = Rückerstattung)
     * @throws de.Flugbuchungssystem.exception.BuchungNichtGefundenException wenn die Buchung nicht existiert
     * @throws de.Flugbuchungssystem.exception.SitzNichtVerfuegbarException wenn der neue Sitz belegt ist
     */
    double bucheUm(String buchungsnummer, Flug neuerFlug, String neueSitznummer);
}